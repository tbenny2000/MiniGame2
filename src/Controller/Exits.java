package Controller;

import Model.ExitsDB;

import java.util.ArrayList;

public class Exits {
    ExitsDB exits = new ExitsDB();

    ArrayList<String> roomExits;

    public ArrayList<String> getRoomExits() {
        return roomExits;
    }

    public void setRoomExits(ArrayList<String> roomExits) {
        this.roomExits = roomExits;
    }
}
