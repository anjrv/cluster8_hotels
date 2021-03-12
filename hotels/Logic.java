package hotels;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

/**
 * Authors: Einar Jónsson Eydís Sylvía Einarsdóttir Jaan Jaerving Snorri Steinn
 * Stefánsson Thors
 */
public class Logic {
    private final String[] HOTEL_PARAMS = { "name", "address", "region", "accessibility", "gym", "spa" };
    private final String[] ROOM_PARAMS = { "hname", "price", "beds", "adults", "children", "wifi", "breakfast" };
    // private final String[] RESERVATION_PARAMS = {};
    private final String[] REVIEW_PARAMS = { "grade" };

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
     * @return a comprehensive list of valid parameters to getReservations
     */
    public String[] getReservationParams() {
        return RESERVATION_PARAMS;
    }

    /**
     * @return a comprehensive list of valid parameters to getReviews
     */
    public String[] getReviewParams() {
        return REVIEW_PARAMS;
    }

    /**
     * Checks whether the keys in the given set of params are
     * valid to be used in a database query.
     * 
     * @param valids String[] a complete array of valid parameters.
     * @param params Set<String> the given parameters to be compared.
     */
    private void validateParams(String[] valids, Set<String> params) {
        Set<String> validParams = new HashSet<String>(Arrays.asList(valids));

        for (String key : params) {
            if(!validParams.contains(key.toLowerCase())) {
                System.err.println("Request parameters contain invalid key: " + key);
                System.exit(1);
            }
        }
    }

    /**
     * Creates an ArrayList of hotels based on the current state of the database and
     * the parameters provided by the argument.
     * 
     * @param params a String array of parameters to be added to the query
     * @return an ArrayList of hotel objects that match the parameters
     */
    public ArrayList<Hotel> getHotels(Hashtable<String, String> params) {
        ArrayList<String> setOfValues = new ArrayList<String>(params.values());
        Set<String> setOfParameters = params.keySet();
        validateParams(HOTEL_PARAMS, setOfParameters);

        int i = 0;
        String sql = "SELECT * FROM hotels";
        for (String key : setOfParameters) {
            sql += " WHERE " + key + " = ? ";
            if(i != setOfParameters.size()) sql += i > 0 ? "AND" : "";
            i++;
        }

        ArrayList<Hotel> hotels = new ArrayList<Hotel>();
        try {
            CachedRowSet crs = QueryEngine.query(sql, setOfValues);
            while (crs.next()) {
                hotels.add(new Hotel(
                    crs.getString("name"), crs.getString("address"), crs.getString("image"),
                    crs.getInt("region"), crs.getBoolean("accessibility"), crs.getBoolean("gym"),
                    crs.getBoolean("spa"), new ArrayList<Room>() // Fetch available rooms, crosscheck on Rooms and Reservations
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return hotels;
    }

    
    // public static ArrayList<Room> getRooms(Hashtable<String, String> params) { }

    // public static ArrayList<Reservation> getReservations(Hashtable<String, String> params) { }

    // public static Review[] getReviews(Hashtable<String, String> params) { }

    // public static void setReservation(Reservation r) { }

    // public static void setReview(Review r) { }
}
