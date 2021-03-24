import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Hashtable;
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
    private Logic hotelLogic = new Logic();

    @BeforeClass
    public static void setup() {
        // Preserve existing database if there is one
        // Create dummy to test with

        File real = new File("hotels/hotels.db");

        if (real.exists()) {
            File save = new File("hotels/saved_hotels.db");
            real.renameTo(save);
        }

        try {
            Setup.setup();
        } catch (Exception e) {
            System.err.println("Problem with database setup: " + e);
        }
    }

    @Test
    public void getHotelInjectionTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("name", "Bates Motel; DROP TABLE hotels");
        
        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(hotels.size(), 0);
    }

    @Test
    public void getHotelRegionTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("Region", "0");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(hotels.size(), 2);
    }

    @Test
    public void getHotelAddressTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("Address", "1054 272nd Street");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(hotels.get(0).getName(), "Bates Motel");
    }

    @Test
    public void getHotelAccessibilityTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("Accessibility", "1");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(hotels.size(), 4);
    }

    @Test
    public void getHotelGymTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("Gym", "1");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(hotels.size(), 3);
    }

    @Test
    public void getHotelSpaTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("Spa", "1");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(hotels.size(), 7);
    }

    @Test
    public void getHotelMultipleParametersTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("accessibility", "1");
        hotelParams.put("gym", "1");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(hotels.size(), 2);
    }

    @Test
    public void getHotelEmptyParametersTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(hotels.size(), 8);
    }

    @Test
    public void getHotelNonExistantValueTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("name", "Banana");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(hotels.size(), 0);
    }

    @Test
    public void getHotelInvalidValueTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("gym", "Cheese");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);

        assertEquals(hotels.size(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getHotelInvalidKeyTest() throws Exception {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("Cheese", "Yes");

        ArrayList<Hotel> hotels = hotelLogic.getHotels(hotelParams);
    }

    @AfterClass
    public static void cleanup() {
        // Delete dummy
        // Restore existing database if there was one

        File fake = new File("hotels/hotels.db");
        File real = new File("hotels/saved_hotels.db");

        fake.delete();
        if (real.exists()) {
            real.renameTo(fake);
        }
    }
}
