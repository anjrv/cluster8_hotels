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
            // Solve
        }
    }

    @Test
    public void fillerTest() {
        System.out.println("1");
        assertEquals(1, 1);
    }

    @Test
    public void fillerTest2() {
        System.out.println("2");
        assertEquals(2, 2);
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
