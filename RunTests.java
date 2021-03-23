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

public class RunTests {
    private Logic hotelLogic = new Logic();

    @BeforeClass
    public static void setup() {
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
    public void hotelRegionTest() {
        Hashtable<String, String> hotelParams = new Hashtable<String, String>();
        hotelParams.put("Region", "0");
        ArrayList<Hotel> hotels = new ArrayList<Hotel>();
        try {
            hotels = hotelLogic.getHotels(hotelParams);
        } catch (Exception e) {
            System.err.println("Hotel region test error: " + e);
        }

        assertEquals(hotels.size(), 1);
    }

    @AfterClass
    public static void cleanup() {
        File fake = new File("hotels/hotels.db");
        File real = new File("hotels/saved_hotels.db");

        fake.delete();
        if (real.exists()) {
            real.renameTo(fake);
        }
    }
}
