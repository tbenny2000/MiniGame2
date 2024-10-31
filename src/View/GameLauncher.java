package View;

import Model.SQLiteDB;

import java.sql.SQLException;
import java.util.Scanner;

public class GameLauncher {
    private Scanner input;

    public GameLauncher(Scanner input) {
        this.input = input;
    }

    public static void main(String[] args) {
        try {
            SQLiteDB db = new SQLiteDB(); // Adjust path if necessary
            Game game = new Game(db, new Scanner(System.in));
            game.play(); // Start the game
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
