package lk.ijse.cozyrobes.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    private final String URL = "jdbc:mysql://localhost:3306/project";
    private final String USER = "root";
    private final String PASSWORD = "sarasi";

    private DBConnection() throws SQLException {
        connection= DriverManager.getConnection(URL,USER,PASSWORD);
    }

    public static DBConnection getInstance() throws SQLException {
        return dbConnection == null ? new DBConnection() : dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }

}
