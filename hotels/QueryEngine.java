package hotels;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class QueryEngine {
    private static final String DB_PATH = "hotels/hotels.db";

    private static Connection connect() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return connection;
    }

    static CachedRowSet queryHotels(String sql) throws ClassNotFoundException {
        CachedRowSet res = null;

        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            RowSetFactory factory = RowSetProvider.newFactory();
            res = factory.createCachedRowSet();
            res.populate(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    /*
     * static CachedRowSet queryRooms(String sql) {  }
     * 
     * static CachedRowSet queryReservations(String sql) {  }
     * 
     * static CachedRowSet] queryReviews(String sql) {  }
     * 
     * static void insertReservation(String sql) {  }
     * 
     * static void insertReview(String sql) {  }
     */
}
