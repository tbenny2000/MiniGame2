package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

abstract public class DB {
    protected String dbName = "Game.db";
    protected String sJdbc;
    protected String sDriverName;
    protected Connection conn;
    protected String sDbUrl;
    protected int timeout = 5;

    // Queries the database and returns a ResultSet object with the results of the query.
    // sql The SQL query to execute.
    // @return ResultSet containing the query results.
    // @throws SQLException If an SQL error occurs.
    public ResultSet queryDB(String sql) throws SQLException {
        ResultSet rs;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setQueryTimeout(timeout);
            rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            throw e;
        }
    }

    // executeUpdate
    // Purpose: Updates the database with the given SQL statement.
    // sql The SQL statement to execute.
    // @throws SQLException If an SQL error occurs.
    public void executeUpdate(String sql) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error executing update: " + e.getMessage());
            throw e; // Re-throw to handle it further up if needed.
        }
    }

    // Method: count
    // Purpose: Gets the count of records in the specified table.
    // table The name of the table to count records from.
    // @return int The count of records in the table.
    public int count(String table) {
        int cnt = 0;
        String sql = "SELECT COUNT(*) AS count FROM \"" + table + "\"";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                cnt = rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("Error counting records: " + e.getMessage());
        }
        return cnt;
    }

    // Method: getMaxValue
    // Purpose: Gets the maximum value for a particular field in a particular table.
    // @param columnName The name of the column.
    // @param table The name of the table.
    // @return int The maximum value found in the specified column.
    public int getMaxValue(String columnName, String table) {
        int max = 0;
        String sql = "SELECT MAX(" + columnName + ") AS max_value FROM " + table;

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                max = rs.getInt("max_value");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching max value: " + e.getMessage());
        }
        return max;
    }
}
