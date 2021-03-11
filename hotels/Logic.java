package hotels;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

/**
 * Authors: Einar Jónsson Eydís Sylvía Einarsdóttir Jaan Jaerving Snorri Steinn
 * Stefánsson Thors
 */
public class Logic {

    /**
     * Creates an ArrayList of hotels based on the current state of the database and
     * the parameters provided by the argument.
     * 
     * @param params a String array of parameters to be added to the query
     * @return an ArrayList of hotel objects that match the parameters
     */
    public ArrayList<Hotel> getHotels(Hashtable<String, String> params) {
        // Prepare parameters
        ArrayList<String> setOfValues = new ArrayList<String>(params.values());
        Set<String> setOfParameters = params.keySet();

        String sql = "SELECT * FROM hotels";
        int i = 0;
        for (String key : setOfParameters) {
            sql += " WHERE " + key + " = ? ";
            if(i != setOfParameters.size()) sql += i > 0 ? "AND" : "";
            i++;
        }

        ArrayList<Hotel> hotels = new ArrayList<Hotel>();

        try {
            CachedRowSet crs = QueryEngine.query(sql, setOfValues);
            while (crs.next()) {
                hotels.add(new Hotel(crs.getString("name"), crs.getString("address"), crs.getString("image"),
                        crs.getInt("region"), crs.getBoolean("accessibility"), crs.getBoolean("gym"),
                        crs.getBoolean("spa"), new ArrayList<Room>()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return hotels;
    }

    /*
     * public static ArrayList<Room> getRooms(Hashtable<String, String> params) { }
     * 
     * public static ArrayList<Reservation> getReservations(Hashtable<String,
     * String> params) { }
     * 
     * public static Review[] getReviews(Hashtable<String, String> params) { }
     * 
     * public static void setReservation(Reservation r) { }
     * 
     * public static void setReview(Review r) { }
     */
}
