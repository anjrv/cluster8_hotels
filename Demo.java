import java.util.ArrayList;
import java.util.Hashtable;
import hotels.Logic;
import hotels.Hotel;

/**
 * Authors: Einar Jónsson, Eydís Sylvía Einarsdóttir, Jaan Jaerving, Snorri
 * Steinn Stefánsson Thors
 */
public class Demo {

    /**
     * Provides an example how to create a business logic item which can be used to
     * forward queries through the hotel package.
     * 
     * In this case we query for hotels and iterate through the resulting ArrayList
     * where we use the Hotel.getName() method to print out the name of each
     * individual hotel.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Logic bn = new Logic();

        Hashtable<String, String> params = new Hashtable<String, String>();
        // Current format of schema allows queries in this format:
        // Name String
        // Region Int
        // Address String
        // Accessibility Int (1 or 0)
        // Gym Int (1 or 0)
        // Spa Int (1 or 0)

        params.put("Region", "1"); // Query for region 1 only.

        try {
            ArrayList<Hotel> hotels = bn.getHotels(params);
            // If working with Review, Room or Reservation objects
            // those Object classes need to be imported in the same
            // way that the Hotel object has been at the top of this
            // file

            for (int i = 0; i < hotels.size(); i++) {
                System.out.println(hotels.get(i).getName());
            }
        } catch (Exception e) {
        }
    }
}
