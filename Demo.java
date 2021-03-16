import java.util.ArrayList;
import java.util.Hashtable;
import hotels.Logic;
import hotels.Hotel;
import hotels.Room;

/**
 * Provides an example how to create a business logic item which can be used to
 * forward queries through the hotel package.
 * 
 * @author: Einar Jónsson, Eydís Sylvía Einarsdóttir, Jaan Jaerving, Snorri
 *          Steinn Stefánsson Thors
 */
public class Demo {

    /**
     * In this case we query for hotels and iterate through the resulting ArrayList
     * where we use the Hotel.getName() method to print out the name of each
     * individual hotel.
     * 
     * @param args not used
     */
    public static void main(String[] args) {
        Logic bn = new Logic();

        String[] validHotelParams = bn.getHotelParams();
        System.out.println("Valid parameters to query hotels are:");
        for (int i = 0; i < validHotelParams.length; i++) {
            System.out.println(validHotelParams[i]);
        }

        try {
            Hashtable<String, String> hotelParams = new Hashtable<String, String>();
            hotelParams.put("Region", "0"); // Query for region 1 only.

            ArrayList<Hotel> hotels = bn.getHotels(hotelParams);
            // If working with Review, Room or Reservation objects
            // those Object classes need to be imported in the same
            // way that the Hotel object has been at the top of this
            // file

            System.out.println("-------------------------------------------------------");
            System.out.println("Query based on region 0 returns the following hotels:");
            for (int i = 0; i < hotels.size(); i++) {
                System.out.println(hotels.get(i).getName());
            }

            System.out.println("-------------------------------------------------------");
            System.out.println("Query of rooms based on hotel result returns the rooms:");
            for (int i = 0; i < hotels.size(); i++) {
                ArrayList<Room> rooms = hotels.get(i).getRooms();
                for (int j = 0; j < rooms.size(); j++) {
                    System.out.println(rooms.get(j).getRnumber());
                }
            }

            Hashtable<String, String> roomParams = new Hashtable<String, String>();
            roomParams.put("wifi","1");
            roomParams.put("adults","1");

            ArrayList<Room> rooms2 = bn.getRooms(roomParams);

            System.out.println("-------------------------------------------------------");
            System.out.println("Query of rooms based on wifi and 1 adult:");
            for (int i = 0; i < rooms2.size(); i++) {
                System.out.println(rooms2.get(i).getRnumber());
            }




        } catch (Exception e) {
            System.err.println("Demo error: " + e);
        }
    }
}
