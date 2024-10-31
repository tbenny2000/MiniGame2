package Controller;

import Model.ExitsDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class Exits {
    private ArrayList<String> roomExits;

    // Constructor
    public Exits() {
        this.roomExits = new ArrayList<>();
    }

    // Method: getRoomExits
    // Purpose: Retrieves the list of room exits
    // @return List of exit directions
    public ArrayList<String> getRoomExits() {
        return roomExits;
    }

    // Method: setRoomExits
    // Purpose: Sets the list of exits for the room
    // roomExits List of exit directions
    public void setRoomExits(ArrayList<String> roomExits) {
        this.roomExits = roomExits;
    }

    // Method: loadExits
    // Purpose: Loads exits from the database for a given room ID
    // roomId ID of the room
    public void loadExits(int roomId) throws SQLException, ClassNotFoundException {
        ExitsDB exitsDB = new ExitsDB();
        Exits roomExits = exitsDB.getExits(roomId);
        setRoomExits(roomExits.getRoomExits());
    }
}

