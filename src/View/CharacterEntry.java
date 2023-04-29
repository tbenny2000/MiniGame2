package View;
import Controller.Exits;
import Controller.ItemCommands;
import Controller.Room;
import Model.ExitsDB;
import Model.RoomDB;
import Model.SQLiteDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;

public class CharacterEntry {
    private Scanner input;
    public CharacterEntry(Scanner input) {
        this.input = input;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        SQLiteDB liteObj = new SQLiteDB();
        String userInput = "";
        Scanner input = new Scanner(System.in);
        ItemCommands inspect = new ItemCommands();
        String strItemDescription = "";
        int itemID = 0;
        boolean checkWord = false;
        boolean checkLetter = false;
        Room roomObject = new Room();
        RoomDB roomDBObject = new RoomDB();
        ExitsDB exitDBObject = new ExitsDB();
        Exits exitsObject = new Exits();
        int hp;
        System.out.println();
        System.out.println("Welcome to the adventure game. You will proceed through rooms based upon your entries.");
        System.out.println("You can navigate by using the entire direction or just the first letter.");
        System.out.println("To exit(X) the game, enter X");
        System.out.println();

        while (!userInput.equalsIgnoreCase("x")) {
            ResultSet rs = liteObj.queryDB("select currentRoom, playerID, health FROM player");
            int playerID = rs.getInt("playerID");
            hp = rs.getInt("health");
            System.out.println(hp);
            int roomID = rs.getInt("currentRoom");
            ResultSet roomTable = liteObj.queryDB("Select roomNumber, roomName, roomDescription, roomVisited From Room where roomNumber =\"" + roomID + "\"");
            String roomName = roomTable.getString("roomName");
            int roomVisited = roomTable.getInt("roomVisited");
            if (roomVisited == 1) {
                System.out.println(roomName + " Visited ");
            } else {
                System.out.println(roomName + " Not visited ");
            }
            liteObj.updateDB("update Room set roomVisited = 1 where roomNumber =\"" + roomID + "\"");
            // item description from DB room
            if (roomID == 2 || roomID == 3 || roomID == 5) {
                ResultSet itemDescription = liteObj.queryDB("select i.itemDescription, i.itemID from item i join itemRoom ir " +
                        "on i.itemID = ir.itemID where ir.roomID =\"" + roomID + "\"");
                itemID = itemDescription.getInt("itemID");
                strItemDescription = itemDescription.getString("itemDescription");
                System.out.println("You see: " + strItemDescription);
            }
            roomObject = roomDBObject.getRoom(roomID);
            System.out.println(roomObject.getRoomID());
            System.out.println(roomObject.getRoomName());
            System.out.println(roomObject.getRoomDescription());
            exitsObject = exitDBObject.getExits(roomID);
            System.out.println("You can go: " + exitsObject.getRoomExits());
            System.out.println("What would you like to do? ");
            userInput = input.nextLine();
            if (userInput.startsWith("b")) {
                inspect.itemInventory(playerID, liteObj);
                System.out.println("What would you like to do? ");
                userInput = input.nextLine();
            }
            if (userInput.startsWith("g")) {
                inspect.getItem(userInput, roomID, liteObj, playerID, itemID);
            }
            if (userInput.startsWith("r")) {
                inspect.removeItem(userInput, roomID, liteObj, playerID);
            }
            if (userInput.startsWith("i")) {
                inspect.inspectItem(liteObj, userInput);
                checkWord = true;
            }
            if (!userInput.startsWith("g") && !userInput.startsWith("r") && !userInput.startsWith("b")
                    && !userInput.startsWith("l") && !userInput.startsWith("i")) {
                // if user choice is in the list of directions
                for (String direction : exitsObject.getRoomExits()) {
                    if (direction.equalsIgnoreCase(userInput)) {
                        checkWord = true;
                        break;
                    }
                    if (direction.toLowerCase().startsWith(userInput.toLowerCase(Locale.ROOT))) {
                        checkLetter = true;
                    }
                }
                // while the user choice is not correct we ask for a correct command
                while (checkWord == false && checkLetter == false && !userInput.equalsIgnoreCase("x")) {
                    System.out.println("Invalid direction entered");
                    System.out.println();
                    System.out.println("What would you like to do? ");
                    userInput = input.nextLine();
                    if (userInput.startsWith("g")) {
                        inspect.getItem(userInput, roomID, liteObj, playerID, itemID);
                        checkWord = true;
                    }
                    if (userInput.startsWith("r")) {
                        inspect.removeItem(userInput, roomID, liteObj, playerID);
                        checkWord = true;
                    }
                    if (userInput.startsWith("b")) {
                        inspect.itemInventory(playerID, liteObj);
                        checkWord = true;
                    }
                    if (userInput.startsWith("l")) {
                        checkWord = true;
                    }
                    if (userInput.startsWith("i")) {
                        inspect.inspectItem(liteObj, userInput);
                        checkWord = true;
                    }
                    if (!userInput.startsWith("g") && !userInput.startsWith("r") && !userInput.startsWith("b")
                            && !userInput.startsWith("l") && !userInput.startsWith("i")) {

                        for (String direction : exitsObject.getRoomExits()) {
                            if (direction.equalsIgnoreCase(userInput)) {
                                checkWord = true;
                            }
                            if (direction.toLowerCase(Locale.ROOT).startsWith(userInput.toLowerCase(Locale.ROOT))) {
                                checkLetter = true;
                            }
                        }
                    }
                }

                if (roomID == 1) {
                    liteObj.updateDB("update player set currentRoom = 2 where playerID = 1");

                    // when roomID 2
                } else if (roomID == 2 && (userInput.equalsIgnoreCase("north") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("n"))) {
                    liteObj.updateDB("update player set currentRoom = 14 where playerID = 1");
                } else if (roomID == 2 && (userInput.equalsIgnoreCase("south") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("s"))) {
                    liteObj.updateDB("update player set currentRoom = 1 where playerID = 1");
                } else if (roomID == 2 && (userInput.equalsIgnoreCase("east") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("e"))) {
                    liteObj.updateDB("update player set currentRoom = 7 where playerID = 1");

                    // when roomID 3
                } else if (roomID == 3 && (userInput.equalsIgnoreCase("south") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("s"))) {
                    liteObj.updateDB("update player set currentRoom = 8 where playerID = 1");
                } else if (roomID == 3 && (userInput.equalsIgnoreCase("east") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("e"))) {
                    liteObj.updateDB("update player set currentRoom = 15 where playerID = 1");
                } else if (roomID == 3 && (userInput.equalsIgnoreCase("west") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("w"))) {
                    liteObj.updateDB("update player set currentRoom = 1 where playerID = 1");

                    // room 4
                } else if (roomID == 4 && (userInput.equalsIgnoreCase("north") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("n"))) {
                    liteObj.updateDB("update player set currentRoom = 1 where playerID = 1");
                } else if (roomID == 4 && (userInput.equalsIgnoreCase("south") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("s"))) {
                    liteObj.updateDB("update player set currentRoom = 17 where playerID = 1");
                } else if (roomID == 4 && (userInput.equalsIgnoreCase("east") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("e"))) {
                    liteObj.updateDB("update player set currentRoom = 8 where playerID = 1");
                } else if (roomID == 4 && (userInput.equalsIgnoreCase("west") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("w"))) {
                    liteObj.updateDB("update player set currentRoom = 9 where playerID = 1");

                    // room 5
                } else if (roomID == 5 && (userInput.equalsIgnoreCase("nort") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("n"))) {
                    liteObj.updateDB("update player set currentRoom = 6 where playerID = 1");
                } else if (roomID == 5 && (userInput.equalsIgnoreCase("east") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("e"))) {
                    liteObj.updateDB("update player set currentRoom = 1 where playerID = 1");
                } else if (roomID == 5 && (userInput.equalsIgnoreCase("west") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("w"))) {
                    liteObj.updateDB("update player set currentRoom = 10 where playerID = 1");

                    // room 6
                } else if (roomID == 6 && (userInput.equalsIgnoreCase("north") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("n"))) {
                    liteObj.updateDB("update player set currentRoom = 13 where playerID = 1");
                } else if (roomID == 6 && (userInput.equalsIgnoreCase("south") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("s"))) {
                    liteObj.updateDB("update player set currentRoom = 5 where playerID = 1");

                    // room 7
                } else if (roomID == 7 && (userInput.equalsIgnoreCase("north") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("n"))) {
                    liteObj.updateDB("update player set currentRoom = 22 where playerID = 1");
                } else if (roomID == 7 && (userInput.equalsIgnoreCase("west") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("w"))) {
                    liteObj.updateDB("update player set currentRoom = 2 where playerID = 1");

                    //room 8
                } else if (roomID == 8 && (userInput.equalsIgnoreCase("north") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("n"))) {
                    liteObj.updateDB("update player set currentRoom = 3 where playerID = 1");
                } else if (roomID == 8 && (userInput.equalsIgnoreCase("west") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("w"))) {
                    liteObj.updateDB("update player set currentRoom = 4 where playerID = 1");

                    //room 9
                } else if (roomID == 9 && (userInput.equalsIgnoreCase("east") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("e"))) {
                    liteObj.updateDB("update player set currentRoom = 4 where playerID = 1");

                    // room 10
                } else if (roomID == 10 && (userInput.equalsIgnoreCase("east") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("e"))) {
                    liteObj.updateDB("update player set currentRoom = 5 where playerID = 1");
                } else if (roomID == 10 && (userInput.equalsIgnoreCase("west") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("w"))) {
                    liteObj.updateDB("update player set currentRoom = 18 where playerID = 1");

                    //room 11
                } else if (roomID == 11 && (userInput.equalsIgnoreCase("north") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("n"))) {
                    liteObj.updateDB("update player set currentRoom = 12 where playerID = 1");
                } else if (roomID == 11 && (userInput.equalsIgnoreCase("west") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("w"))) {
                    liteObj.updateDB("update player set currentRoom = 19 where playerID = 1");

                    // room 12
                } else if (roomID == 12 && (userInput.equalsIgnoreCase("south") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("s"))) {
                    liteObj.updateDB("update player set currentRoom = 11 where playerID = 1");

                    // room 13
                } else if (roomID == 13 && (userInput.equalsIgnoreCase("south") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("s"))) {
                    liteObj.updateDB("update player set currentRoom = 6 where playerID = 1");

                    // room 14
                } else if (roomID == 14 && (userInput.equalsIgnoreCase("north") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("n"))) {
                    liteObj.updateDB("update player set currentRoom = 21 where playerID = 1");
                } else if (roomID == 14 && (userInput.equalsIgnoreCase("south") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("s"))) {
                    liteObj.updateDB("update player set currentRoom = 2 where playerID = 1");

                    // room 15
                } else if (roomID == 15 && (userInput.equalsIgnoreCase("south") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("s"))) {
                    liteObj.updateDB("update player set currentRoom = 16 where playerID = 1");
                } else if (roomID == 15 && (userInput.equalsIgnoreCase("west") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("w"))) {
                    liteObj.updateDB("update player set currentRoom = 3 where playerID = 1");

                    // room 16
                } else if (roomID == 16 && (userInput.equalsIgnoreCase("north") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("n"))) {
                    liteObj.updateDB("update player set currentRoom = 15 where playerID = 1");
                } else if (roomID == 16 && (userInput.equalsIgnoreCase("east") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("e"))) {
                    liteObj.updateDB("update player set currentRoom = 23 where playerID = 1");

                    // room 17
                } else if (roomID == 17 && (userInput.equalsIgnoreCase("north") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("n"))) {
                    liteObj.updateDB("update player set currentRoom = 4 where playerID = 1");
                } else if (roomID == 17 && (userInput.equalsIgnoreCase("south") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("s"))) {
                    liteObj.updateDB("update player set currentRoom = 25 where playerID = 1");

                    // room 18
                } else if (roomID == 18 && (userInput.equalsIgnoreCase("north") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("n"))) {
                    liteObj.updateDB("update player set currentRoom = 19 where playerID = 1");
                } else if (roomID == 18 && (userInput.equalsIgnoreCase("east") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("e"))) {
                    liteObj.updateDB("update player set currentRoom = 10 where playerID = 1");

                    // room 19
                } else if (roomID == 19 && (userInput.equalsIgnoreCase("south") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("s"))) {
                    liteObj.updateDB("update player set currentRoom = 18 where playerID = 1");
                } else if (roomID == 19 && (userInput.equalsIgnoreCase("east") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("e"))) {
                    liteObj.updateDB("update player set currentRoom = 11 where playerID = 1");

                    // room 20
                } else if (roomID == 20 && (userInput.equalsIgnoreCase("east") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("e"))) {
                    liteObj.updateDB("update player set currentRoom = 21 where playerID = 1");

                    // room 21
                } else if (roomID == 21 && (userInput.equalsIgnoreCase("south") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("s"))) {
                    liteObj.updateDB("update player set currentRoom = 14 where playerID = 1");
                } else if (roomID == 21 && (userInput.equalsIgnoreCase("west") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("w"))) {
                    liteObj.updateDB("update player set currentRoom = 20 where playerID = 1");

                    // room 22
                } else if (roomID == 22 && (userInput.equalsIgnoreCase("south") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("s"))) {
                    liteObj.updateDB("update player set currentRoom = 7 where playerID = 1");
                } else if (roomID == 22 && (userInput.equalsIgnoreCase("east") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("e"))) {
                    liteObj.updateDB("update player set currentRoom = 23 where playerID = 1");

                    // room 23
                } else if (roomID == 23 && (userInput.equalsIgnoreCase("north") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("n"))) {
                    liteObj.updateDB("update player set currentRoom = 22 where playerID = 1");
                } else if (roomID == 23 && (userInput.equalsIgnoreCase("south") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("s"))) {
                    liteObj.updateDB("update player set currentRoom = 24 where playerID = 1");
                } else if (roomID == 23 && (userInput.equalsIgnoreCase("east") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("e"))) {
                    liteObj.updateDB("update player set currentRoom = 27 where playerID = 1");
                } else if (roomID == 23 && (userInput.equalsIgnoreCase("west") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("w"))) {
                    liteObj.updateDB("update player set currentRoom = 16 where playerID = 1");

                    // room 24
                } else if (roomID == 24 && (userInput.equalsIgnoreCase("east") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("e"))) {
                    liteObj.updateDB("update player set currentRoom = 23 where playerID = 1");
                } else if (roomID == 24 && (userInput.equalsIgnoreCase("west") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("w"))) {
                    liteObj.updateDB("update player set currentRoom = 25 where playerID = 1");

                    // room 25
                } else if (roomID == 25 && (userInput.equalsIgnoreCase("north") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("n"))) {
                    liteObj.updateDB("update player set currentRoom = 17 where playerID = 1");
                } else if (roomID == 25 && (userInput.equalsIgnoreCase("east") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("e"))) {
                    liteObj.updateDB("update player set currentRoom = 24 where playerID = 1");
                } else if (roomID == 25 && (userInput.equalsIgnoreCase("west") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("w"))) {
                    liteObj.updateDB("update player set currentRoom = 26 where playerID = 1");

                    // room 26
                } else if (roomID == 26 && (userInput.equalsIgnoreCase("east") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("e"))) {
                    liteObj.updateDB("update player set currentRoom = 25 where playerID = 1");

                    // room 27
                } else if (roomID == 27 && (userInput.equalsIgnoreCase("west") ||
                        userInput.toLowerCase(Locale.ROOT).startsWith("w"))) {
                    liteObj.updateDB("update player set currentRoom = 25 where playerID = 1");

                }



                checkLetter = false;
                checkWord = false;
                }
            }
            System.out.println("Exit");
            System.out.println();
            System.out.println("Thank you for playing the Game.");
            liteObj.close();
            input.close();
    }
}
