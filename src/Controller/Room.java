package Controller;

import Model.RoomDB;

import java.sql.SQLException;

public class Room {
    private int roomID;
    private String roomName;
    private String roomDescription;
    private String roomVisited;

    // Constructor that accepts parameters for roomID and description
    public Room(int roomID, String roomDescription) {
        this.roomID = roomID;
        this.roomDescription = roomDescription;
        // You might want to set roomName and roomVisited as needed
    }

    // Default constructor (optional, if you need it)
    public Room() {
        RoomDB rdb = new RoomDB();
        try {
            roomID = rdb.getNextRoomID();
        } catch (SQLException sqe) {
            System.out.println(sqe.getMessage());
        }
    }

    // Other methods (getRoom, getAllRooms, etc.) remain unchanged

    // Getters and Setters
    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public String getRoomVisited() {
        return roomVisited;
    }

    public void setRoomVisited(String roomVisited) {
        this.roomVisited = roomVisited;
    }

    @Override
    public String toString() {
        return "Room roomID = " + roomID +
                "\nroomName = " + roomName +
                "\nroomDescription = " + roomDescription +
                "\nroomVisited = " + roomVisited;
    }
}

