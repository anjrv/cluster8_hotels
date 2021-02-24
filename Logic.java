import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.util.Scanner;

public class Logic {
    private static final String DB_PATH = "hotels.db";

    public static String[] query(Statement q, String[] values) throws IOException, ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        Statement statement = q;
        Scanner read = null;
        String command = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            statement = connection.createStatement();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return String[];
    }

    public static Hotel[] getHotels(String[] params) {
        // try / catch miðað við parameters...
        // kallar á query með params
        // new Hotel[] miðað við String[] lengd
        // for lykkja parse string = new Hotel

        return Hotel[];
    }

    public static Room[] getRooms(String[] params) {
        // try / catch miðað við parameters...
        // kallar á query með params
        // new Hotel[] miðað við String[] lengd
        // for lykkja parse string = new Hotel

        return Room[];
    }

    public static Reservation[] getReservations(String[] params) {
        // try / catch miðað við parameters...
        // kallar á query með params
        // new Hotel[] miðað við String[] lengd
        // for lykkja parse string = new Hotel

        return Reservation[];

    }

    public static Review[] getReviews(String[] params) {
        // try / catch miðað við parameters...
        // kallar á query með params
        // new Hotel[] miðað við String[] lengd
        // for lykkja parse string = new Hotel

        return Review[];
    }

    public static void insertReservation(Reservation r) {

    }

    public static void insertReview(Review r) {

    }
}
