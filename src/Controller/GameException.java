package Controller;
import java.io.IOException;

/**Class: GameException
 * This class – is the custom exception for the Game Character program
 */
public class GameException extends IOException {

    public GameException() {
        super();
    }

    public GameException(String message) {
        super(message);
    }
}
