package hotels;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

/**
 * Business Logic object to be used to make requests from the hotels API.
 * 
 * @author Einar Jónsson, Eydís Sylvía Einarsdóttir, Jaan Jaerving, Snorri
 *         Steinn Stefánsson Thors
 */
public class Logic {
    private final String[] HOTEL_PARAMS = { "name", "address", "region", "accessibility", "gym", "spa" };
    private final String[] ROOM_PARAMS = { "hname", "price", "beds", "adults", "children", "wifi", "breakfast" };
    private final String[] RESERVATION_PARAMS = { "reservationid", "paid", "contact", "hname", "rnumber", "cancelled" };
    private final String[] REVIEW_SELECT_PARAMS = { "hname", "grade" };
    private final String[] REVIEW_INSERT_PARAMS = { "grade", "hname", "text", "reservationid" };

    /**
     * @return a comprehensive list of valid parameters to getHotels
     */
    public String[] getHotelParams() {
        return HOTEL_PARAMS;
    }

    /**
     * @return a comprehensive list of valid parameters to getRooms
     */
    public String[] getRoomParams() {
        return ROOM_PARAMS;
    }

    /**
     * @return a comprehensive list of valid parameters to use with reservations
     */
    public String[] getReservationParams() {
        return RESERVATION_PARAMS;
    }

    /**
     * @return a comprehensive list of valid parameters to use with review selection
     */
    public String[] getReviewSelectParams() {
        return REVIEW_SELECT_PARAMS;
    }

    /**
     * @return a comprehensive list of valid parameters to use with review insertion
     */
    public String[] getReviewInsertParams() {
        return REVIEW_INSERT_PARAMS;
    }

    /**
     * Private helper function to check whether the keys in the given set of params
     * are valid to be used in a database query.
     * 
     * @param valids String[] a complete array of valid parameters.
     * @param params Set<String> the given parameters to be compared.
     * @throws IllegalArgumentException
     */
    private void validateParams(String[] valids, Set<String> params) throws IllegalArgumentException {
        Set<String> validParams = new HashSet<String>(Arrays.asList(valids));

        for (String key : params) {
            if (!validParams.contains(key.toLowerCase())) {
                throw new IllegalArgumentException("Parameters contain invalid key: " + key);
            }
        }
    }

    /**
     * Private helper function to check whether the start and end times are valid
     * 
     * @param s long start time in milliseconds
     * @param e long end time in milliseconds
     * @throws IllegalArgumentException
     */
    private void validateTimeframe(long s, long e) throws IllegalArgumentException {
        if ((new Date().getTime()) > s || s > e)
            throw new IllegalArgumentException("Start and end times are invalid");
    }

    /**
     * Private helper function to construct an sql query to be used for an update
     * statement
     * 
     * @param sql             start portion of the desired statement
     * @param setOfParameters Set of parameters to be used in the query
     * @return a completed String object that can be used to construct a
     *         preparedStatement
     */
    private String prepareStatement(String sql, Set<String> setOfParameters) {
        String res = "";

        if (sql.contains("SELECT")) {
            if (setOfParameters.size() > 0) {
                sql += " WHERE ";
                int i = 1;
                for (String key : setOfParameters) {
                    if (key.toLowerCase().equals("price")) {
                        sql += key + " <= ? ";
                    } else {
                        sql += key + " = ? ";
                    }
                    if (i != setOfParameters.size())
                        sql += "AND ";
                    i++;
                }
            }

            res += sql;
        } else {
            String vals = ") VALUES(";
            int i = 1;
            for (String key : setOfParameters) {
                sql += key;
                vals += "?";
                if (i != setOfParameters.size()) {
                    sql += ",";
                    vals += ",";
                }
                i++;
            }

            res += sql + vals + ")";
        }
        return res;
    }

    /**
     * Creates an ArrayList of reviews based on the current state of the database
     * and the parameters provided by the argument.
     * 
     * @param params a Hashtable of parameter and value pairs to be added to the
     *               query
     * @return an ArrayList of Review objects that match the parameters
     * @throws IllegalArgumentException
     */
    public ArrayList<Review> getReviews(Hashtable<String, String> params) throws IllegalArgumentException {
        ArrayList<String> setOfValues = new ArrayList<String>(params.values());
        Set<String> setOfParameters = params.keySet();
        validateParams(REVIEW_SELECT_PARAMS, setOfParameters);

        String sql = prepareStatement("SELECT grade, hname, text FROM reviews", setOfParameters);

        ArrayList<Review> reviews = new ArrayList<Review>();

        try {
            CachedRowSet crs = QueryEngine.query(sql, setOfValues);
            while (crs.next()) {
                reviews.add(new Review(crs.getInt("grade"), crs.getString("hname"), crs.getString("text")));
            }
        } catch (SQLException err) {
            System.err.println(err);
        }

        return reviews;
    }

    /**
     * Creates an ArrayList of reservations based on the current state of the
     * database and the parameters provided by the argument.
     * 
     * @param params a Hashtable of parameter and value pairs to be added to the
     *               query
     * @return an ArrayList of Reservation objects that match the parameters
     * @throws IllegalArgumentException
     */
    public ArrayList<Reservation> getReservations(Hashtable<String, String> params) throws IllegalArgumentException {
        ArrayList<String> setOfValues = new ArrayList<String>(params.values());
        Set<String> setOfParameters = params.keySet();
        validateParams(RESERVATION_PARAMS, setOfParameters);

        String sql = prepareStatement("SELECT * FROM reservations", setOfParameters);

        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        try {
            CachedRowSet crs = QueryEngine.query(sql, setOfValues);
            while (crs.next()) {
                reservations.add(new Reservation(crs.getString("reservationID"), crs.getLong("createDate"),
                        crs.getLong("startDate"), crs.getLong("endDate"), crs.getBoolean("cancelled"),
                        crs.getBoolean("paid"), crs.getString("contact"), crs.getString("hname"),
                        crs.getInt("rnumber")));
            }
        } catch (SQLException err) {
            System.err.println(err);
        }

        return reservations;
    }

    /**
     * Creates an ArrayList of reservations based on the current state of the
     * database, the start and end date and the parameters provided by the argument.
     *
     * @param params a Hashtable of parameter and value pairs to be added to the
     *               query
     * @param st     a long that represents the start date to be used (in
     *               milliseconds)
     * @param e      a long that represents the end date to be used (in
     *               milliseconds)
     * @return an ArrayList of Reservation objects that match the parameters within
     *         the timeframe st - e
     * @throws IllegalArgumentException
     */
    public ArrayList<Reservation> getReservations(Hashtable<String, String> params, long st, long e)
            throws IllegalArgumentException {
        validateTimeframe(st, e);

        ArrayList<Reservation> res = getReservations(params);

        ArrayList<Reservation> intersects = new ArrayList<Reservation>();

        for (Reservation r : res) {
            if ((r.getStart() >= st && e >= r.getStart()) || (r.getEnd() >= st && e >= r.getEnd()))
                intersects.add(r);
        }

        return intersects;
    }

    /**
     * Creates an ArrayList of rooms based on the current state of the database and
     * the parameters provided by the argument.
     * 
     * @param params a Hashtable of parameter and value pairs to be added to the
     *               query
     * @return an ArrayList of Room objects that match the parameters
     * @throws IllegalArgumentException
     */
    public ArrayList<Room> getRooms(Hashtable<String, String> params) throws IllegalArgumentException {
        ArrayList<String> setOfValues = new ArrayList<String>(params.values());
        Set<String> setOfParameters = params.keySet();
        validateParams(ROOM_PARAMS, setOfParameters);

        String sql = prepareStatement("SELECT * FROM rooms", setOfParameters);

        ArrayList<Room> rooms = new ArrayList<Room>();
        try {
            CachedRowSet crs = QueryEngine.query(sql, setOfValues);
            while (crs.next()) {
                String hname = crs.getString("hname");
                int rnumber = crs.getInt("rnumber");
                Hashtable<String, String> tmp = new Hashtable<String, String>();
                tmp.put("hname", hname);
                tmp.put("rnumber", String.valueOf(rnumber));

                rooms.add(new Room(rnumber, hname, crs.getInt("price"), crs.getInt("beds"), crs.getInt("adults"),
                        crs.getInt("children"), crs.getBoolean("wifi"), crs.getBoolean("breakfast"),
                        getReservations(tmp)));
            }
        } catch (SQLException err) {
            System.err.println(err);
        }

        return rooms;
    }

    /**
     * Creates an ArrayList of rooms based on the current state of the database, the
     * start and end date and the parameters provided by the argument.
     * 
     * @param params a Hashtable of parameter and value pairs to be added to the
     *               query
     * @param st     a long that represents the start date to be used (in
     *               milliseconds)
     * @param e      a long that represents the end date to be used (in
     *               milliseconds)
     * @return an ArrayList of Room objects that match the parameters
     * @throws IllegalArgumentException
     */
    public ArrayList<Room> getRooms(Hashtable<String, String> params, long st, long e) throws IllegalArgumentException {
        validateTimeframe(st, e);

        Hashtable<String, String> resParams = new Hashtable<String, String>();

        if (params.containsKey("hname")) {
            resParams.put("hname", params.get("hname"));
        }
        resParams.put("cancelled", "0");

        ArrayList<Reservation> res = getReservations(resParams, st, e);
        ArrayList<Room> rms = getRooms(params);
        ArrayList<Room> reserved = new ArrayList<Room>();

        for (Reservation rs : res) {
            for (Room rm : rms) {
                if ((rm.getHname().toLowerCase().equals(rs.getHname().toLowerCase()))
                        && rm.getRnumber() == rs.getRnumber())
                    reserved.add(rm);
            }
        }

        rms.removeAll(reserved);

        return rms;
    }

    /**
     * Creates an ArrayList of hotels based on the current state of the database and
     * the parameters provided by the argument.
     * 
     * @param params a Hashtable of parameter and value pairs to be added to the
     *               query
     * @return an ArrayList of Hotel objects that match the parameters
     * @throws IllegalArgumentException
     */
    public ArrayList<Hotel> getHotels(Hashtable<String, String> params) throws IllegalArgumentException {
        ArrayList<String> setOfValues = new ArrayList<String>(params.values());
        Set<String> setOfParameters = params.keySet();
        validateParams(HOTEL_PARAMS, setOfParameters);

        String sql = prepareStatement("SELECT * FROM hotels", setOfParameters);

        ArrayList<Hotel> hotels = new ArrayList<Hotel>();
        try {
            CachedRowSet crs = QueryEngine.query(sql, setOfValues);
            while (crs.next()) {
                String hname = crs.getString("name");
                Hashtable<String, String> tmp = new Hashtable<String, String>();
                tmp.put("hname", hname);

                hotels.add(new Hotel(hname, crs.getString("address"), crs.getString("image"), crs.getInt("region"),
                        crs.getBoolean("accessibility"), crs.getBoolean("gym"), crs.getBoolean("spa"), getRooms(tmp)));
            }
        } catch (SQLException err) {
            System.err.println(err);
        }

        return hotels;
    }

    /**
     * Creates an ArrayList of hotels based on the current state of the database.
     * 
     * @return an ArrayList of Hotel objects that match the parameters
     * @throws IllegalArgumentException
     */
    public ArrayList<Hotel> getHotels() throws IllegalArgumentException {
        Hashtable<String, String> tmp = new Hashtable<String, String>();
        ArrayList<Hotel> hotels = new ArrayList<Hotel>();

        hotels = getHotels(tmp);
        return hotels;
    }

    /**
     * Creates an ArrayList of hotels based on the current state of the database and
     * the parameters provided by the argument.
     * 
     * @param params     a Hashtable of parameter and value pairs to be added to the
     *                   query
     * @param roomParams a Hashtable of parameter and value pairs to be used to
     *                   query rooms
     * @return an ArrayList of Hotel objects that match the parameters
     * @throws IllegalArgumentException
     */
    public ArrayList<Hotel> getHotels(Hashtable<String, String> params, Hashtable<String, String> roomParams)
            throws IllegalArgumentException {
        ArrayList<String> setOfValues = new ArrayList<String>(params.values());
        Set<String> setOfParameters = params.keySet();
        validateParams(HOTEL_PARAMS, setOfParameters);

        String sql = prepareStatement("SELECT * FROM hotels", setOfParameters);

        ArrayList<Hotel> hotels = new ArrayList<Hotel>();
        try {
            CachedRowSet crs = QueryEngine.query(sql, setOfValues);
            while (crs.next()) {
                String hname = crs.getString("name");
                Hashtable<String, String> tmp = roomParams;
                tmp.put("hname", hname);

                hotels.add(new Hotel(hname, crs.getString("address"), crs.getString("image"), crs.getInt("region"),
                        crs.getBoolean("accessibility"), crs.getBoolean("gym"), crs.getBoolean("spa"), getRooms(tmp)));
            }
        } catch (SQLException err) {
            System.err.println(err);
        }

        ArrayList<Hotel> noAvail = new ArrayList<Hotel>();
        for (Hotel h : hotels) {
            if (h.getRooms().size() == 0)
                noAvail.add(h);
        }

        hotels.removeAll(noAvail);
        return hotels;
    }

    /**
     * Creates an ArrayList of hotels based on the current state of the database,
     * the start and end date and the parameters provided by the argument.
     * 
     * @param params a Hashtable of parameter and value pairs to be added to the
     *               query
     * @param st     a long that represents the start date to be used (in
     *               milliseconds)
     * @param e      a long that represents the end date to be used (in
     *               milliseconds)
     * @return an ArrayList of Hotel objects that match the parameters
     * @throws IllegalArgumentException
     */
    public ArrayList<Hotel> getHotels(Hashtable<String, String> params, long st, long e)
            throws IllegalArgumentException {
        validateTimeframe(st, e);

        ArrayList<String> setOfValues = new ArrayList<String>(params.values());
        Set<String> setOfParameters = params.keySet();
        validateParams(HOTEL_PARAMS, setOfParameters);

        String sql = prepareStatement("SELECT * FROM hotels", setOfParameters);

        ArrayList<Hotel> hotels = new ArrayList<Hotel>();
        try {
            CachedRowSet crs = QueryEngine.query(sql, setOfValues);
            while (crs.next()) {
                String hname = crs.getString("name");
                Hashtable<String, String> tmp = new Hashtable<String, String>();
                tmp.put("hname", hname);

                hotels.add(new Hotel(hname, crs.getString("address"), crs.getString("image"), crs.getInt("region"),
                        crs.getBoolean("accessibility"), crs.getBoolean("gym"), crs.getBoolean("spa"),
                        getRooms(tmp, st, e)));
            }
        } catch (SQLException err) {
            System.err.println(err);
        }

        ArrayList<Hotel> noAvail = new ArrayList<Hotel>();
        for (Hotel h : hotels) {
            if (h.getRooms().size() == 0)
                noAvail.add(h);
        }

        hotels.removeAll(noAvail);
        return hotels;
    }

    /**
     * Creates an ArrayList of hotels based on the current state of the database,
     * the start and end date and the parameters provided by the argument. With
     * rooms matching roomparams.
     * 
     * @param params     a Hashtable of parameter and value pairs to be added to the
     *                   query
     * @param roomParams a Hashtable of parameter and value pairs to be used to
     *                   query rooms
     * @param st         a long that represents the start date to be used (in
     *                   milliseconds)
     * @param e          a long that represents the end date to be used (in
     *                   milliseconds)
     * @return an ArrayList of Hotel objects that match the parameters
     * @throws IllegalArgumentException
     */
    public ArrayList<Hotel> getHotels(Hashtable<String, String> params, Hashtable<String, String> roomParams, long st,
            long e) throws IllegalArgumentException {
        validateTimeframe(st, e);

        ArrayList<String> setOfValues = new ArrayList<String>(params.values());
        Set<String> setOfParameters = params.keySet();
        validateParams(HOTEL_PARAMS, setOfParameters);

        String sql = prepareStatement("SELECT * FROM hotels", setOfParameters);

        ArrayList<Hotel> hotels = new ArrayList<Hotel>();
        try {
            CachedRowSet crs = QueryEngine.query(sql, setOfValues);
            while (crs.next()) {
                String hname = crs.getString("name");
                Hashtable<String, String> tmp = roomParams;
                tmp.put("hname", hname);

                hotels.add(new Hotel(hname, crs.getString("address"), crs.getString("image"), crs.getInt("region"),
                        crs.getBoolean("accessibility"), crs.getBoolean("gym"), crs.getBoolean("spa"),
                        getRooms(tmp, st, e)));
            }
        } catch (SQLException err) {
            System.err.println(err);
        }

        ArrayList<Hotel> noAvail = new ArrayList<Hotel>();
        for (Hotel h : hotels) {
            if (h.getRooms().size() == 0)
                noAvail.add(h);
        }

        hotels.removeAll(noAvail);
        return hotels;
    }

    /**
     * Inserts a reservation based on the current state of the database and the
     * parameters provided by the argument.
     * 
     * @param params a hashtable of parameter and value pairs to be added to the
     *               query
     * @return the reservation ID for the reservation
     * @throws IllegalArgumentException
     */
    public String setReservation(Hashtable<String, String> params, long st, long e) throws IllegalArgumentException {
        Set<String> setOfParameters = new HashSet<String>();
        setOfParameters.addAll(params.keySet());

        validateParams(RESERVATION_PARAMS, setOfParameters);
        validateTimeframe(st, e);

        String createDate = String.valueOf(new Date().getTime());
        String reservationID = params.get("rnumber") + params.get("hname").replaceAll("\\s", "")
                + createDate.substring(createDate.length() - 6);

        Hashtable<String, String> tmp = params;
        tmp.put("reservationID", reservationID);
        tmp.put("createdate", createDate);
        tmp.put("startdate", String.valueOf(st));
        tmp.put("enddate", String.valueOf(e));

        setOfParameters = tmp.keySet();
        ArrayList<String> setOfValues = new ArrayList<String>(tmp.values());

        String sql = prepareStatement("INSERT INTO reservations(", setOfParameters);
        QueryEngine.update(sql, setOfValues);

        String sub = "Confirmation for your booking at " + params.get("hname");
        String msg = "Hello!" + System.lineSeparator() + System.lineSeparator() + "Thank you for booking with "
                + params.get("hname") + System.lineSeparator() + "Your reservation ID is: " + reservationID
                + ". This unique ID can be used to change your booking or cancel if needed!" + System.lineSeparator()
                + System.lineSeparator() + "We hope you enjoy your stay," + System.lineSeparator()
                + "The Cluster 8 Hotels Team";

        EmailEngine.send(params.get("contact"), sub, msg);

        return reservationID;
    }

    /**
     * Return the reservation if one exists, else throw error
     * 
     * @param reservationID the reservation ID to check for
     * 
     * @throws IllegalArgumentException
     */
    private Reservation checkIfExists(String reservationID) throws IllegalArgumentException {
        Hashtable<String, String> tmp = new Hashtable<String, String>();
        tmp.put("reservationID", reservationID);
        tmp.put("cancelled", "0");

        ArrayList<Reservation> res = getReservations(tmp);

        if (res.size() < 1) {
            throw new IllegalArgumentException("ReservationID is invalid");
        }

        return res.get(0);
    }

    /**
     * Inserts a review based on the current state of the database and the
     * parameters provided by the argument.
     * 
     * @param params a hashtable of parameter and value pairs to be added to the
     *               query
     * @throws IllegalArgumentException
     */
    public void setReview(Hashtable<String, String> params) throws IllegalArgumentException {
        ArrayList<String> setOfValues = new ArrayList<String>(params.values());
        Set<String> setOfParameters = params.keySet();
        validateParams(REVIEW_INSERT_PARAMS, setOfParameters);

        String sql = prepareStatement("INSERT INTO reviews(", setOfParameters);
        QueryEngine.update(sql, setOfValues);
    }

    /**
     * Update the start date of a reservation, if one exists
     * 
     * @param reservationID the ID of the reservation to update
     * @param s             the date to be used as the new start date (in
     *                      milliseconds)
     * @throws IllegalArgumentException
     */
    public void updateReservationStart(String reservationID, long s) throws IllegalArgumentException {
        Reservation r = checkIfExists(reservationID);
        validateTimeframe(s, r.getEnd());

        ArrayList<String> setOfValues = new ArrayList<String>();
        setOfValues.add(String.valueOf(s));
        setOfValues.add(reservationID);

        QueryEngine.update("UPDATE reservations SET startdate = ? WHERE reservationID = ?", setOfValues);

        String sub = "Changed start date for your reservation: " + reservationID;
        String msg = "Hello!" + System.lineSeparator() + System.lineSeparator()
                + " The start of your reservation has successfully been adjusted!" + System.lineSeparator()
                + "We hope you enjoy your stay," + System.lineSeparator() + "The Cluster 8 Hotels Team";

        EmailEngine.send(r.getContact(), sub, msg);
    }

    /**
     * Update the end date of a reservation, if one exists
     * 
     * @param reservationID the ID of the reservation to update
     * @param e             the date to be used as the new end date (in
     *                      milliseconds)
     * @throws IllegalArgumentException
     */
    public void updateReservationEnd(String reservationID, long e) throws IllegalArgumentException {
        Reservation r = checkIfExists(reservationID);
        validateTimeframe(r.getStart(), e);

        ArrayList<String> setOfValues = new ArrayList<String>();
        setOfValues.add(String.valueOf(e));
        setOfValues.add(reservationID);

        QueryEngine.update("UPDATE reservations SET enddate = ? WHERE reservationID = ?", setOfValues);

        String sub = "Changed end date for your reservation: " + reservationID;
        String msg = "Hello!" + System.lineSeparator() + System.lineSeparator()
                + " The end of your reservation has successfully been adjusted!" + System.lineSeparator()
                + "We hope you enjoy your stay," + System.lineSeparator() + "The Cluster 8 Hotels Team";

        EmailEngine.send(r.getContact(), sub, msg);
    }

    /**
     * Cancel the reservation, if one exists
     * 
     * @param reservationID the ID of the reservation to update
     * @throws IllegalArgumentException
     */
    public void cancelReservation(String reservationID) throws IllegalArgumentException {
        Reservation r = checkIfExists(reservationID);
        System.out.println(r.getResID());

        ArrayList<String> setOfValues = new ArrayList<String>();
        setOfValues.add("1");
        setOfValues.add(reservationID);

        QueryEngine.update("UPDATE reservations SET cancelled = ? WHERE reservationID = ?", setOfValues);

        String sub = "Cancellation of your reservation: " + reservationID;
        String msg = "Hello!" + System.lineSeparator() + System.lineSeparator()
                + " Your reservation has been cancelled." + System.lineSeparator() + "Thank you for your patronage,"
                + System.lineSeparator() + "The Cluster 8 Hotels Team";

        EmailEngine.send(r.getContact(), sub, msg);
    }
}
