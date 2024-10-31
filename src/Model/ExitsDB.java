package Model;

import Controller.Exits;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExitsDB extends DB {
    private Connection conn;

    // Constructor to initialize the database connection
    public ExitsDB() throws SQLException, ClassNotFoundException {
        SQLiteDB db = new SQLiteDB(); // Create an instance of SQLiteDB
        this.conn = db.getConnection(); // Get the connection from SQLiteDB
    }

    // Method: getExits
    // Purpose: Retrieves exits for a room based on the supplied room ID
    // @return Exits
    // @throws SQLException
    public Exits getExits(int id) throws SQLException {
        Exits roomExits = new Exits();
        ArrayList<String> listOfExits = new ArrayList<>();
        String sql = "SELECT exitDirection FROM Exits WHERE roomID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listOfExits.add(rs.getString("exitDirection"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving exits: " + e.getMessage());
        }
        roomExits.setRoomExits(listOfExits);
        return roomExits;
    }
}







