package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnection {
    public static Connection getDatabaseConnection(String username, String password, String url) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
