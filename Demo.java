import java.util.ArrayList;
import hotels.Logic;
import hotels.Hotel;

/**
 * Authors:
 * Einar Jónsson
 * Eydís Sylvía Einarsdóttir
 * Jaan Jaerving
 * Snorri Steinn Stefánsson Thors
 */
public class Demo {

    /**
     * Provides an example how to create
     * a business logic item which can be
     * used to forward queries through
     * the hotel package.
     * 
     * In this case we query for hotels and
     * iterate through the resulting ArrayList
     * where we use the Hotel.getName() method
     * to print out the name of each individual
     * hotel.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Logic bn = new Logic();
        String[] params = { "filler" }; // Parameter handling not yet finished

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
