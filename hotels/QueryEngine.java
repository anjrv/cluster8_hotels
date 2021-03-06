package hotels;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.File;

/**
 * Package level QueryEngine used for accessing the database.
 * 
 * @author: Einar Jónsson, Eydís Sylvía Einarsdóttir, Jaan Jaerving, Snorri
 *          Steinn Stefánsson Thors
 */
class QueryEngine {
    private static final String DB_PATH = "hotels" + File.separator + "hotels.db"; // Package path to the generated
                                                                                   // database

    /**
     * Helper function that returns a valid open Connection object
     * 
     * @return Connection
     * @throws ClassNotFoundException
     */
    private static Connection connect() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
        } catch (SQLException err) {
            System.err.println(err);
        }

        return connection;
    }

    /**
     * Helper function that closes open database communication
     * 
     * @param c currently open Connection object
     * @param s currently open Statement object
     */
    private static void close(Connection c, Statement s) {
        try {
            s.close();
        } catch (Exception err) {
            /* Ignored */ }
        try {
            c.close();
        } catch (Exception err) {
            /* Ignored */ }
    }

    /**
     * Queries the database according to the SQL query parameters returns a set of
     * cached row results that can be worked with without needing to have an open
     * database connection.
     * 
     * @param sql  SQL query string to be executed
     * @param vals values to be inserted into the given query
     * @return CachedRowSet cached rows from the result of the performed query
     */
    static CachedRowSet query(String sql, ArrayList<String> vals) {
        Connection connection = null;
        PreparedStatement statement = null;
        CachedRowSet res = null;
        ResultSet rs = null;

        try {
            connection = connect();
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < vals.size(); i++) {
                statement.setString(i + 1, vals.get(i));
            }
            rs = statement.executeQuery();

            RowSetFactory factory = RowSetProvider.newFactory();
            res = factory.createCachedRowSet();
            res.populate(rs);

        } catch (Exception err) {
            System.err.println(err);
        } finally {
            try {
                rs.close();
            } catch (Exception err) {
                /* Ignored */ }
            close(connection, statement);
        }

        return res;
    }

    /**
     * Updates the state of the database with the given information.
     * 
     * @param sql  SQL query string to be executed
     * @param vals values to be inserted into the given query
     * @throws Exception
     */
    static void update(String sql, ArrayList<String> vals) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        connection = connect();
        statement = connection.prepareStatement(sql);

        for (int i = 0; i < vals.size(); i++) {
            statement.setString(i + 1, vals.get(i));
        }

        statement.executeUpdate();
        close(connection, statement);
    }
}
