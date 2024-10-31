package View;

import Controller.Exits;
import Controller.ItemCommands;
import Model.ExitsDB;
import Model.SQLiteDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Game {
    private SQLiteDB db;
    private Scanner input;
    private ItemCommands inspect;
    private int playerID = 1;

    public Game(SQLiteDB db, Scanner input) {
        this.db = db;
        this.input = input;
        this.inspect = new ItemCommands();
    }

    public void play() throws SQLException, ClassNotFoundException {
        resetRoomVisitedStatus(); // Reset room visited status at the start of the game
        System.out.println("Welcome to the adventure game. Navigate using directions \nwest, east, south, north, up, or down or first letters.");
        System.out.println("Enter 'X' to exit the game.\n");

        while (true) {
            int roomID = getCurrentRoom();
            displayRoomDetails(roomID);
            String userInput = input.nextLine();

            if (userInput.equalsIgnoreCase("x")) {
                System.out.println("Thank you for playing the Game.");
                break;
            }

            if (!processInput(userInput, roomID) && !processExits(userInput, roomID)) {
                System.out.println("Invalid direction entered. Try again.");
            }
        }
    }

    private void resetRoomVisitedStatus() throws SQLException {
        // Reset all roomVisited statuses to 0 (not visited)
        db.updateDB("UPDATE Room SET roomVisited = 0");
    }

    private int getCurrentRoom() throws SQLException {
        ResultSet rs = db.queryDB("SELECT currentRoom FROM player WHERE playerID = " + playerID);
        if (rs.next()) {
            return rs.getInt("currentRoom");
        }
        throw new SQLException("Unable to retrieve current room.");
    }

    private void displayRoomDetails(int roomID) throws SQLException, ClassNotFoundException {
        ResultSet roomTable = db.queryDB("SELECT roomNumber, roomName, roomDescription, roomVisited FROM Room WHERE roomNumber = " + roomID);
        if (roomTable.next()) {
            String roomName = roomTable.getString("roomName");
            String roomDescription = roomTable.getString("roomDescription");
            boolean roomVisited = roomTable.getInt("roomVisited") == 1;

            // Display room details with visited status
            System.out.println(roomName + (roomVisited ? " (Visited)" : " (Not visited)"));
            System.out.println(roomDescription);

            // Update the visited status only after displaying details
            db.updateDB("UPDATE Room SET roomVisited = 1 WHERE roomNumber = " + roomID);

            // Display items in the room if applicable
            if (roomID == 2 || roomID == 3 || roomID == 5) {
                displayItemsInRoom(roomID);
            }

            Exits exitsObject = new ExitsDB().getExits(roomID);
            System.out.println("You can go: " + String.join(", ", exitsObject.getRoomExits()));
        } else {
            throw new SQLException("Room not found.");
        }
    }

    private void displayItemsInRoom(int roomID) throws SQLException {
        ResultSet itemDescription = db.queryDB(
                "SELECT i.itemDescription FROM item i JOIN itemRoom ir ON i.itemID = ir.itemID WHERE ir.roomID = " + roomID
        );
        if (itemDescription.next()) {
            System.out.println("You see: " + itemDescription.getString("itemDescription"));
        }
        itemDescription.close();
    }

    private boolean processInput(String userInput, int roomID) throws SQLException {
        switch (userInput.toLowerCase().charAt(0)) {
            case 'b':
                inspect.itemInventory(playerID, db);
                return true;
            case 'g':
                inspect.getItem(userInput, roomID, db, playerID, 0);
                return true;
            case 'r':
                inspect.removeItem(userInput, db, playerID);
                return true;
            case 'i':
                inspect.inspectItem(db, userInput);
                return true;
            default:
                return false;
        }
    }

    private boolean processExits(String userInput, int roomID) throws SQLException, ClassNotFoundException {
        Exits exitsObject = new ExitsDB().getExits(roomID);
        for (String direction : exitsObject.getRoomExits()) {
            if (direction.equalsIgnoreCase(userInput) || direction.toLowerCase().startsWith(userInput.toLowerCase())) {
                updateRoomForDirection(direction, roomID);
                return true;
            }
        }
        return false;
    }

    private void updateRoomForDirection(String userInput, int roomID) throws SQLException {
        switch (roomID) {
            case 1:
                db.updateDB("UPDATE player SET currentRoom = 2 WHERE playerID = " + playerID);
                break;
            case 2:
                if (userInput.equalsIgnoreCase("east") || userInput.toLowerCase().startsWith("e")) {
                    db.updateDB("UPDATE player SET currentRoom = 3 WHERE playerID = " + playerID);
                } else if (userInput.equalsIgnoreCase("west") || userInput.toLowerCase().startsWith("w")) {
                    db.updateDB("UPDATE player SET currentRoom = 1 WHERE playerID = " + playerID);
                }
                break;
            case 3:
                if (userInput.equalsIgnoreCase("west") || userInput.toLowerCase().startsWith("w")) {
                    db.updateDB("UPDATE player SET currentRoom = 2 WHERE playerID = " + playerID);
                } else if (userInput.equalsIgnoreCase("down") || userInput.toLowerCase().startsWith("d")) {
                    db.updateDB("UPDATE player SET currentRoom = 6 WHERE playerID = " + playerID);
                }
                break;
            case 4:
                if (userInput.equalsIgnoreCase("north") || userInput.toLowerCase().startsWith("n")) {
                    db.updateDB("UPDATE player SET currentRoom = 5 WHERE playerID = " + playerID);
                }
                break;
            case 5:
                if (userInput.equalsIgnoreCase("south") || userInput.toLowerCase().startsWith("s")) {
                    db.updateDB("UPDATE player SET currentRoom = 4 WHERE playerID = " + playerID);
                } else if (userInput.equalsIgnoreCase("east") || userInput.toLowerCase().startsWith("e")) {
                    db.updateDB("UPDATE player SET currentRoom = 6 WHERE playerID = " + playerID);
                }
                break;
            case 6:
                if (userInput.equalsIgnoreCase("up") || userInput.toLowerCase().startsWith("u")) {
                    db.updateDB("UPDATE player SET currentRoom = 3 WHERE playerID = " + playerID);
                } else if (userInput.equalsIgnoreCase("west") || userInput.toLowerCase().startsWith("w")) {
                    db.updateDB("UPDATE player SET currentRoom = 5 WHERE playerID = " + playerID);
                }
                break;
        }
    }
}
