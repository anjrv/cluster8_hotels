import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Logic {
    private static final String DB_PATH = "hotels.db";

    private Connection query() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return connection;
    }

    public ArrayList<Hotel> getHotels(String[] params) throws ClassNotFoundException {
        String sql = "SELECT name, region, address, image, accessibility, gym, spa FROM hotels";
        ArrayList<Hotel> hotels = new ArrayList<Hotel>();

        try (Connection connection = this.query();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getString("name"));
                hotels.add(new Hotel(rs.getString("name")
                                    ,rs.getString("address")
                                    ,rs.getString("image")
                                    ,rs.getInt("region")
                                    ,rs.getBoolean("accessibility")
                                    ,rs.getBoolean("gym")
                                    ,rs.getBoolean("spa")
                                    ,new Room[0]));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return hotels;
    }

    /*
     * public static Room[] getRooms(String[] params) { // try / catch miðað við
     * parameters... // kallar á query með params // new Hotel[] miðað við String[]
     * lengd // for lykkja parse string = new Hotel
     * 
     * return Room[]; }
     * 
     * public static Reservation[] getReservations(String[] params) { // try / catch
     * miðað við parameters... // kallar á query með params // new Hotel[] miðað við
     * String[] lengd // for lykkja parse string = new Hotel
     * 
     * return Reservation[];
     * 
     * }
     * 
     * public static Review[] getReviews(String[] params) { // try / catch miðað við
     * parameters... // kallar á query með params // new Hotel[] miðað við String[]
     * lengd // for lykkja parse string = new Hotel
     * 
     * return Review[]; }
     * 
     * public static void insertReservation(Reservation r) {
     * 
     * }
     * 
     * public static void insertReview(Review r) {
     * 
     * }
     */
    public static void main(String[] args) {
        Logic bn = new Logic();
        String[] params = { "filler" };
        try {
            bn.getHotels(params);
        } catch (Exception e) {
        }
    }
}
