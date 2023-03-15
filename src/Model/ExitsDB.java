package Model;

import Controller.Exits;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExitsDB {


    public Exits getExits(int id) throws SQLException, ClassNotFoundException {
        SQLiteDB sdb = new SQLiteDB();
        Exits roomExits = new Exits();
        ArrayList<String> listOfExits = new ArrayList<>();
        String sql = "Select * from Exits WHERE roomID = " + id;
        ResultSet rs = sdb.queryDB(sql);
        while (rs.next()) {
            listOfExits.add(rs.getString("exitDirection"));
        }
        //Close the SQLiteDB connection since SQLite only allows one updater
        rs.close();
        sdb.close();
        roomExits.setRoomExits(listOfExits);
        return roomExits;
    }
}


