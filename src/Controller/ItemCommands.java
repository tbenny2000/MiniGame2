package Controller;
import Model.SQLiteDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemCommands{

    public static void inspectItem(SQLiteDB liteObj, String userInput) {

        String itemName = userInput.substring(userInput.indexOf(" ") + 1).toLowerCase();
        try {
            ResultSet itemTable = liteObj.queryDB("select itemDescription from item where lower(itemName) =\"" + itemName + "\"");
            String itemDescription = itemTable.getString("itemDescription");
            System.out.println(itemDescription);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void itemInventory(int playerID, SQLiteDB liteObj) {
        try {

            List<String> itemList = new ArrayList<>();
            ResultSet itemsFromDB = liteObj.queryDB("Select i.itemType from Item i Join PlayerItem pi On i.itemID = pi.itemID " +
                    "where pi.playerID = \"" + playerID + "\"");

            while (itemsFromDB.next()) {
                String item = itemsFromDB.getString("itemType");
                itemList.add(item);
            }
            if (itemList.isEmpty()) {
                System.out.println("Inventory is Empty.");
            } else {
                System.out.println("Inventory: " + Arrays.deepToString(itemList.toArray()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeItem(String userInput, int roomID, SQLiteDB liteObj, int playerID) {

        String itemName = userInput.substring(userInput.indexOf(" ") + 1).toLowerCase();
        try {
            ResultSet itemTable = liteObj.queryDB("select itemID from item where lower(itemName) =\"" + itemName + "\"");
            int itemID = itemTable.getInt("itemID");

            if (itemName.equals("torch")) {
                liteObj.updateDB("Delete From playerItem Where playerID =" + playerID + " and itemID = " + itemID);
                liteObj.updateDB("UPDATE ItemRoom set roomID =" + roomID + " WHERE itemID =" + itemID);
                System.out.println("Torch has been removed from your inventory.");
            } else if (itemName.equals("rusty sword")) {
                liteObj.updateDB("Delete From playerItem Where playerID =" + playerID + " and itemID = " + itemID);
                liteObj.updateDB("UPDATE ItemRoom set roomID =" + roomID + " WHERE itemID =" + itemID);
                System.out.println("Rusty Sword has been removed from your inventory.");
            } else if (itemName.equals("shield")) {
                liteObj.updateDB("Delete From playerItem Where playerID =" + playerID + " and itemID = " + itemID);
                liteObj.updateDB("UPDATE ItemRoom set roomID =" + roomID + " WHERE itemID =" + itemID);
                System.out.println("Shield has been removed from your inventory.");
            } else {
                System.out.println(itemName.toUpperCase() + " not in Inventory.\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getItem(String userInput, int roomID, SQLiteDB liteObj, int playerID, int itemID) {

        try {
            if ((userInput.equalsIgnoreCase("get sword")
                    || userInput.equalsIgnoreCase("g sword")) && roomID == 4) {
                liteObj.updateDB("INSERT INTO playerItem VALUES(" + playerID + "," + itemID + ")");
                //liteObj.updateDB("UPDATE ItemRoom SET roomID = 0  WHERE itemID =" + itemID);
                System.out.println("SWORD has been added to your inventory.");

            } else if ((userInput.equalsIgnoreCase("get health potion")
                    || userInput.equalsIgnoreCase("g health potion")) && roomID == 5) {
                liteObj.updateDB("INSERT INTO playerItem VALUES(" + playerID + "," + itemID + ")");
                //liteObj.updateDB("UPDATE ItemRoom set roomID = 0 WHERE itemID =" + itemID);
                System.out.println("HEALTH POTION has been added to your inventory ");

            } else if ((userInput.equalsIgnoreCase("get health potion")
                    || userInput.equalsIgnoreCase("g health potion")) && roomID == 20) {
                liteObj.updateDB("INSERT INTO playerItem VALUES(" + playerID + "," + itemID + ")");
                //liteObj.updateDB("UPDATE ItemRoom set roomID = 0 WHERE itemID =" + itemID);
                System.out.println("HEALTH POTION has been added to your inventory ");

            } else if ((userInput.equalsIgnoreCase("get health potion")
                    || userInput.equalsIgnoreCase("g health potion")) && roomID == 26) {
                liteObj.updateDB("INSERT INTO playerItem VALUES(" + playerID + "," + itemID + ")");
                //liteObj.updateDB("UPDATE ItemRoom set roomID = 0 WHERE itemID =" + itemID);
                System.out.println("HEALTH POTION has been added to your inventory ");

            } else if ((userInput.equalsIgnoreCase("get scythe")
                    || userInput.equalsIgnoreCase("g scythe")) && roomID == 7) {
                liteObj.updateDB("INSERT INTO playerItem VALUES(" + playerID + "," + itemID + ")");
                //liteObj.updateDB("UPDATE ItemRoom set roomID = 0 WHERE itemID =" + itemID);
                System.out.println("SCYTHE has been added to your inventory ");

            } else if ((userInput.equalsIgnoreCase("get scythe")
                    || userInput.equalsIgnoreCase("g scythe")) && roomID == 11) {
                liteObj.updateDB("INSERT INTO playerItem VALUES(" + playerID + "," + itemID + ")");
                //liteObj.updateDB("UPDATE ItemRoom set roomID = 0 WHERE itemID =" + itemID);
                System.out.println("SCYTHE has been added to your inventory ");

            } else if ((userInput.equalsIgnoreCase("get scythe")
                    || userInput.equalsIgnoreCase("g scythe")) && roomID == 14) {
                liteObj.updateDB("INSERT INTO playerItem VALUES(" + playerID + "," + itemID + ")");
                //liteObj.updateDB("UPDATE ItemRoom set roomID = 0 WHERE itemID =" + itemID);
                System.out.println("SCYTHE has been added to your inventory ");

            } else if ((userInput.equalsIgnoreCase("get club")
                    || userInput.equalsIgnoreCase("g club")) && roomID == 9) {
                liteObj.updateDB("INSERT INTO playerItem VALUES(" + playerID + "," + itemID + ")");
                //liteObj.updateDB("UPDATE ItemRoom set roomID = 0 WHERE itemID =" + itemID);
                System.out.println("CLUB has been added to your inventory ");

            } else if ((userInput.equalsIgnoreCase("get key")
                    || userInput.equalsIgnoreCase("g key")) && roomID == 12) {
                liteObj.updateDB("INSERT INTO playerItem VALUES(" + playerID + "," + itemID + ")");
                //liteObj.updateDB("UPDATE ItemRoom set roomID = 0 WHERE itemID =" + itemID);
                System.out.println("KEY has been added to your inventory ");

            } else if ((userInput.equalsIgnoreCase("get axe")
                    || userInput.equalsIgnoreCase("g axe")) && roomID == 15) {
                liteObj.updateDB("INSERT INTO playerItem VALUES(" + playerID + "," + itemID + ")");
                //liteObj.updateDB("UPDATE ItemRoom set roomID = 0 WHERE itemID =" + itemID);
                System.out.println("AXE has been added to your inventory ");

            } else {
                System.out.println(userInput.substring(userInput.indexOf(" ") + 1).toUpperCase() + " not in room.");
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
