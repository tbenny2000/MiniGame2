package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteDB extends DB {
    private Connection conn;

    // Constructor to initialize connection with a default database name
    public SQLiteDB() throws SQLException, ClassNotFoundException {
        // this("C:\Users\tbenn\Desktop\Test\Game.db"); // absolute path to database
        this("Game.db");
    }

    // Constructor with database name
    public SQLiteDB(String dbName) throws SQLException, ClassNotFoundException {
        // Build the connection String
        sJdbc = "jdbc:sqlite";
        sDriverName = "org.sqlite.JDBC";
        // Register the driver
        Class.forName(sDriverName);
        sDbUrl = sJdbc + ":" + dbName; // Make sure dbName includes the full path if necessary
        this.dbName = dbName;
        conn = DriverManager.getConnection(sDbUrl);
    }

    // Method to return the connection
    public Connection getConnection() {
        return conn;
    }

    // Method: close
    // Purpose: Close the database connection
    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    // Method: updateDB
    // Purpose: Execute an update statement (INSERT, UPDATE, DELETE)
    public void updateDB(String sql) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate(); // Executes the update
        }
    }

    // Method: queryDB
    // Purpose: Execute a SELECT statement and return a ResultSet
    public ResultSet queryDB(String sql) throws SQLException {
        return conn.createStatement().executeQuery(sql);
    }
}
