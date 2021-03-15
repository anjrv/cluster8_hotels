import java.util.ArrayList;
import java.util.Hashtable;
import hotels.Logic;
import hotels.Hotel;
import hotels.Room;

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

        Hashtable<String, String> hotelParams = new Hashtable<String, String>();

        String[] validHotelParams = bn.getHotelParams();
        System.out.println("Valid parameters to query hotels are:");
        for (int i = 0; i < validHotelParams.length; i++) {
            System.out.println(validHotelParams[i]);
        }

        hotelParams.put("Region", "1"); // Query for region 1 only.

        try {
            ArrayList<Hotel> hotels = bn.getHotels(hotelParams);
            // If working with Review, Room or Reservation objects
            // those Object classes need to be imported in the same
            // way that the Hotel object has been at the top of this
            // file

            System.out.println("-----------------------------------------------------");
            System.out.println("Query based on region 1 returns the following hotels:");
            for (int i = 0; i < hotels.size(); i++) {
                System.out.println(hotels.get(i).getName());
            }

            // Example of room query based on hotel name, not yet finished.
            // if(hotels.size() > 0) {
            //     Hashtable<String, String> roomParams = new Hashtable<String, String>();
            //     roomParams.put("hname", hotels.get(0).getName());
            //     
            //     ArrayList<Room> rooms = bn.getRooms(roomParams);
            // }
        } catch (Exception e) {
            System.err.println("Demo error: " + e);
        }
    }
}
