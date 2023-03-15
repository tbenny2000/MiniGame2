package Controller;

import Model.RoomDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class Room {
    private int roomID;
    private String roomName;
    private String roomDescription;
    private String roomVisited;

    /**
     * Constructor: Room
     */
    public Room() {
        RoomDB rdb = new RoomDB();
        try{
            roomID = rdb.getNextRoomID();
        }
        catch(SQLException sqe){
            System.out.println(sqe.getMessage());
        }
    }

    /**
     * Method: getRoom
     * Purpose: Retrieves a room based upon the supplied ID
     * @param id
     * @return Room
     * @throws SQLException
     */
    public Room getRoom(int id) throws SQLException, ClassNotFoundException {
        RoomDB rdb = new RoomDB();
        return rdb.getRoom(id);
    }

    public ArrayList<Room> getAllRooms() throws SQLException, ClassNotFoundException {
        RoomDB rdb = new RoomDB();
        return rdb.getAllRooms();
    }

    /**
     * Method: getRoomID
     * @return the roomID
     */
    public int getRoomID() {
        return roomID;
    }

    /**
     * Method: setRoomID
     * @param roomID the roomID to set
     */
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    /**
     * Method: getRoomName
     * @return the roomName
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Method: setRoomName
     * @param roomName the roomName to set
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * Method: getRoomDescription
     * @return the roomDescription
     */
    public String getRoomDescription() {
        return roomDescription;
    }

    /**
     * Method: setRoomDescription
     * @param roomDescription the roomDescription to set
     */
    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public String getRoomVisited() {return roomVisited; }
    public void setRoomVisited(String roomVisited) {this.roomVisited = roomVisited; }

    /**
     * Method: toString
     * @return
     */
    @Override
    public String toString() {
        return "Room roomID = " + roomID + "\nroomName = " + roomName + "\nroomDescription = " + roomDescription + "\nroomVisited = " + roomVisited;
    }


}

