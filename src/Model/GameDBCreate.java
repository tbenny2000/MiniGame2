package Model;

import Controller.GameException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.Scanner;

public class GameDBCreate {
    SQLiteDB sdb;
    private String dbName;

    public GameDBCreate() {
        dbName = "Game.db";
    }

    public GameDBCreate(String dbName) {
        this.dbName = dbName;
    }

    // Method: buildTables
    // Purpose: Build all tables
    public void buildTables() throws SQLException, ClassNotFoundException, GameException {
        buildRoom();
    }

    // Method: buildRoom
    // Purpose: Build the Room table and load data
    public void buildRoom() throws SQLException, ClassNotFoundException, GameException {
        sdb = new SQLiteDB(dbName);

        FileReader fr;
        try {
            fr = new FileReader("Room.txt");
            Scanner inFile = new Scanner(fr);
            while (inFile.hasNextLine()) {
                String sql = inFile.nextLine();
                sdb.updateDB(sql);
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            throw new GameException("Room.txt was not found");
        }

        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
    }
}

