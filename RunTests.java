import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.io.IOException;
import java.io.File;

import hotels.Logic;
import hotels.Hotel;
import hotels.Room;
import hotels.Reservation;
import hotels.Setup;

/**
 * Testing suite for Cluster 8 Hotels.
 * 
 * @author Einar Jónsson, Eydís Sylvía Einarsdóttir, Jaan Jaerving, Snorri
 *         Steinn Stefánsson Thors
 */
public class RunTests {
    private static Logic hotelLogic = new Logic();

    @BeforeClass
    public static void setup() {
        // If there is no database then create one
        File db = new File("hotels/hotels.db");

        if (!db.exists()) {
            try {
                if (!db.exists()) {
                    Setup.setup();
                }
            } catch (Exception e) {
                System.err.println("Problem with database initialization: " + e);
            }
        }
    }

    @Test
    public void getHotelNameTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("name", "The Plaza Hotel");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(1, hotels.size());
    }

    @Test
    public void getHotelRegionTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("Region", "0");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(2, hotels.size());
    }

    @Test
    public void getHotelAddressTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("Address", "1054 272nd Street");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals("Bates Motel", hotels.get(0).getName());
    }

    @Test
    public void getHotelAccessibilityTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("Accessibility", "1");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(4, hotels.size());
    }

    @Test
    public void getHotelGymTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("Gym", "1");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(3, hotels.size());
    }

    @Test
    public void getHotelSpaTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("Spa", "1");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(7, hotels.size());
    }

    @Test
    public void getHotelMultipleParametersTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("accessibility", "1");
        hotelParams.put("gym", "1");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(2, hotels.size());
    }

    @Test
    public void getHotelEmptyParametersTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(8, hotels.size());
    }

    @Test
    public void getHotelNonExistantValueTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("name", "Banana");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(0, hotels.size());
    }

    @Test
    public void getHotelInvalidValueTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("gym", "Cheese");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(0, hotels.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getHotelInvalidKeyTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("Cheese", "Yes");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);
    }
}
