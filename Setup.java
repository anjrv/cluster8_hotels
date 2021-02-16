import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Setup {
    private static final String SQL_PATH = "schema.sql";
    private static final String DB_PATH = "hotels.db";

    private static void initDB() throws IOException {
        try {
            File db = new File(DB_PATH);
            if (db.createNewFile())
                System.out.println("Database created.");
            else {
                System.out.println("Database already exists.");
                System.exit(0);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        initDB();

        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        Statement statement = null;
        Scanner read = null;
        String command = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            statement = connection.createStatement();

            File sql = new File(SQL_PATH);
            if (!sql.exists()) {
                System.out.println("No schema found.");
                System.exit(0);
            }

            read = new Scanner(sql);
            read.useDelimiter(";");

            while (read.hasNext()) {
                command = read.next();
                System.out.println(command);
                statement.execute(command);
            }
            read.close();

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
    }
}
