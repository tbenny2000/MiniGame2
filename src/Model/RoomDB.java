package Model;

import Controller.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDB extends DB {

    public RoomDB() {
    }

    // Method: getNextRoomID
    // Purpose: Gets the next ID for a room
    // @return int
    public int getNextRoomID() throws SQLException {
        int next = getMaxValue("roomNumber", "Room") + 1; // Assuming "Room" is your table name
        return next;
    }

    // Method: getRoom
    // Purpose: Gets a room based upon the supplied ID
    public Room getRoom(int id) throws SQLException {
        Room rm = new Room();
        String sql = "SELECT * FROM Room WHERE roomNumber = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rm.setRoomID(rs.getInt("roomNumber"));
                rm.setRoomName(rs.getString("roomName"));
                rm.setRoomDescription(rs.getString("roomDescription"));
                rm.setRoomVisited(rs.getString("roomVisited"));
            } else {
                throw new SQLException("Room " + id + " not found");
            }
        }
        return rm;
    }


    // Method: getAllRooms
    // Purpose: Gets all rooms
    // @return ArrayList<Room>
    public ArrayList<Room> getAllRooms() throws SQLException {
        ArrayList<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM Room";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Room rm = new Room();
                rm.setRoomID(rs.getInt("roomNumber"));
                rm.setRoomName(rs.getString("roomName"));
                rm.setRoomDescription(rs.getString("roomDescription"));
                rm.setRoomVisited(rs.getString("roomVisited"));
                rooms.add(rm);
            }
        }
        return rooms;
    }
}

