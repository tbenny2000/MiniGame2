package Controller;

import Model.SQLiteDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemCommands {

    // Inspects an item and prints its description.
    // liteObj SQLite database object for queries.
    // userInput User input containing the item name.
    public static void inspectItem(SQLiteDB liteObj, String userInput) {
        String itemName = extractItemName(userInput);

        try {
            ResultSet itemTable = liteObj.queryDB("SELECT itemDescription FROM item WHERE LOWER(itemName) = \"" + itemName + "\"");
            if (itemTable.next()) {
                String itemDescription = itemTable.getString("itemDescription");
                System.out.println(itemDescription);
            } else {
                System.out.println("Item not found.");
            }
        } catch (SQLException e) {
            System.err.println("SQL error while inspecting item: " + e.getMessage());
        }
    }

    // Displays the player's inventory.
    // playerID The ID of the player.
    // liteObj SQLite database object for queries.
    public static void itemInventory(int playerID, SQLiteDB liteObj) {
        List<String> itemList = new ArrayList<>();

        try {
            ResultSet itemsFromDB = liteObj.queryDB("SELECT i.itemDescription FROM Item i JOIN PlayerItem pi ON i.itemID = pi.itemID WHERE pi.playerID = " + playerID);
            while (itemsFromDB.next()) {
                itemList.add(itemsFromDB.getString("itemDescription"));
            }
            if (itemList.isEmpty()) {
                System.out.println("Inventory is Empty.");
            } else {
                System.out.println("Inventory: " + Arrays.toString(itemList.toArray()));
            }
        } catch (SQLException e) {
            System.err.println("SQL error while fetching inventory: " + e.getMessage());
        }
    }

    // Removes an item from the player's inventory.
    // userInput User input containing the item name.
    // liteObj SQLite database object for updates.
    // playerID The ID of the player.
    public static void removeItem(String userInput, SQLiteDB liteObj, int playerID) {
        String itemName = extractItemName(userInput);

        try {
            ResultSet itemTable = liteObj.queryDB("SELECT itemID FROM item WHERE LOWER(itemName) = \"" + itemName + "\"");
            if (itemTable.next()) {
                int itemID = itemTable.getInt("itemID");
                liteObj.updateDB("DELETE FROM playerItem WHERE playerID = " + playerID + " AND itemID = " + itemID);
                System.out.println(itemName.toUpperCase() + " has been removed from your inventory.");
            } else {
                System.out.println(itemName.toUpperCase() + " not in Inventory.");
            }
        } catch (SQLException e) {
            System.err.println("SQL error while removing item: " + e.getMessage());
        }
    }

    // Retrieves an item from the room and adds it to the player's inventory.
    // userInput User input containing the item name.
    // roomID The current room ID.
    // liteObj SQLite database object for updates.
    // playerID The ID of the player.
    // itemID The ID of the item to add.
    public static void getItem(String userInput, int roomID, SQLiteDB liteObj, int playerID, int itemID) {
        String itemName = extractItemName(userInput);

        try {
            switch (itemName) {
                case "torch":
                    if (roomID == 2) {
                        addItemToPlayer(liteObj, playerID, itemID, "Torch has been added to your inventory.");
                    } else {
                        itemNotInRoom(itemName);
                    }
                    break;
                case "rusty sword":
                    if (roomID == 3) {
                        addItemToPlayer(liteObj, playerID, itemID, "Rusty Sword has been added to your inventory.");
                    } else {
                        itemNotInRoom(itemName);
                    }
                    break;
                case "shield":
                    if (roomID == 5) {
                        addItemToPlayer(liteObj, playerID, itemID, "Shield has been added to your inventory.");
                    } else {
                        itemNotInRoom(itemName);
                    }
                    break;
                default:
                    itemNotInRoom(itemName);
            }
        } catch (SQLException e) {
            System.err.println("SQL error while retrieving item: " + e.getMessage());
        }
    }

    // Helper Methods
    // Extracts the item name from user input.
    // userInput User input string.
    // The item name in lowercase.
    private static String extractItemName(String userInput) {
        return userInput.substring(userInput.indexOf(" ") + 1).toLowerCase();
    }

    // Adds an item to the player's inventory and prints a confirmation message.
    // liteObj SQLite database object for updates.
    // playerID The ID of the player.
    // itemID The ID of the item to add.
    private static void addItemToPlayer(SQLiteDB liteObj, int playerID, int itemID, String message) throws SQLException {
        liteObj.updateDB("INSERT INTO playerItem VALUES(" + playerID + "," + itemID + ")");
        System.out.println(message);
    }

    // Prints a message indicating the item is not in the room.
    // itemName The name of the item.
    private static void itemNotInRoom(String itemName) {
        System.out.println(itemName.toUpperCase() + " not in room.");
    }
}
