package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;

    private Connection connection;

    private DBConnection() throws SQLException {

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "12345");

    }

    public Connection getConnection() {
        return connection;
    }

    public static DBConnection getInstance() throws SQLException {

        try {
            if (instance == null) {
                instance = new DBConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;

    }
}
