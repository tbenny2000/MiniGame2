package Model;
import Controller.GameCharacter;
import Controller.GameException;
import java.util.ArrayList;

/**Class: CharacterStore
 * This class – stores the information of the Game Characters enter by the user.
 */
public class CharacterStore {
    private ArrayList<GameCharacter> characters;
    private static CharacterStore inst = null;

    private CharacterStore() {
        characters = new ArrayList<>();
    }

    public static CharacterStore getInstance() {
        if (inst == null) {
            inst = new CharacterStore();
        }
        return inst;
    }

    /** Method: addCharacter
     * Allows the user to add a character to the store.
     * @param gc The GameCharacter to be added to the store.
     * throws GameException if the character already exists
     */
    public void addCharacter(GameCharacter gc) throws GameException {
        checkUnique(gc.getName());
        characters.add(gc);
    }

    /** Method: checkUnique
     * Checks to determine if the character name is already used in the store.
     * @param name The name of the GameCharacter to be added to the store.
     * throws GameException if the character already exists
     */
    public void checkUnique(String name) throws GameException {
        for (GameCharacter gc : characters) {
            if (name.equalsIgnoreCase(gc.getName())) {
                throw new GameException((name + " already exists."));
            }
        }
    }

    /** Method: findCharacter
     * Allows the user to find a character in the store.
     * @param name The GameCharacter to be added to the store.
     * throws GameException if the character is not found
     */
    public GameCharacter findCharacter(String name) throws GameException {
        for (GameCharacter gc : characters) {
            if (name.equalsIgnoreCase(gc.getName())) {
                return gc;
            }
        }
        throw new GameException(name + " not found");
    }

    /** Method: getCharacters
     * Allows the user to retrieve all characters from the store.
     * @return ArrayList<GameCharacter> the list of characters in the store
     */
    public ArrayList<GameCharacter> getCharacters() {
        return characters;
    }
}
