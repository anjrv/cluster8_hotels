import java.nio.file.*;
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
        Path path = Paths.get(DB_PATH);
        try {
            Files.createFile(path);
        } catch (FileAlreadyExistsException ex) {
            System.out.println("Database already exists.");
            System.exit(0);
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
            read = new Scanner(new File(SQL_PATH));
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
