package Controller;
import Model.CharacterStore;
import java.util.ArrayList;

/**Class: GameCharacter
 * @author Rick Price
 * @version 1.0
 * Course: ITEC 3860 Fall 2021
 * Written: August 17, 2021
 *
 * This class – contains the logic and validations for the Game Characters
 */
public class GameCharacter {
    private String name;
    private String type;
    private int hp;
    private int ac;

    private static CharacterStore cs;

    /** GameCharacter
     * Constructor that creates the CharacterStore if needed
     * If already created, does nothing.
     * CharacterStore is static to allow one copy of that class to be shared by
     * many GameCharacter objects
     */
    public GameCharacter() {
        if (cs == null) {
            cs = CharacterStore.getInstance();
        }
    }

    /** Method: getName
     * returns the name stored in this object
     * @return String name.
     */
    public String getName() {
        return name;
    }

    /** Method: setName
     * Validates the name
     * @param name the name of the GameCharacter
     * throws GameException
     */
    public void setName(String name) throws Controller.GameException {
        if (name.length() < 4) {
            throw new Controller.GameException("Name must be at least 4 characters.");
        }

        this.name = name;
    }

    /** Method: getType
     * returns the type stored in this object
     * @return String type.
     */
    public String getType() {
        return type;
    }

    /** Method: setType
     * Validates the type
     * @param type the type of the GameCharacter
     * throws GameException
     */
    public void setType(String type) throws Controller.GameException {
        if(!(type.equalsIgnoreCase("Fighter") ||
                type.equalsIgnoreCase("Wizard") ||
                type.equalsIgnoreCase("Druid") ||
                type.equalsIgnoreCase("Thief"))) {
            throw new Controller.GameException("Type must be Fighter, Wizard, Druid or Thief");
        }
        this.type = type;
    }

    /** Method: getHp
     * returns the hit points stored in this object
     * @return int hp.
     */
    public int getHp() {
        return hp;
    }

    /** Method: setHp
     * Validates the hit points
     * @param hp the hit points of the GameCharacter
     * throws GameException
     */
    public void setHp(int hp) throws Controller.GameException {
        if (hp < 0 || hp > 100) {
            throw new Controller.GameException("Hit points must be between 0 and 100");
        }
        this.hp = hp;
    }

    /** Method: getAc
     * returns the armor class stored in this object
     * @return int ac.
     */
    public int getAc() {
        return ac;
    }

    /** Method: setAc
     * Validates the armor class
     * @param ac the armor class of the GameCharacter
     * throws GameException
     */
    public void setAc(int ac) throws Controller.GameException {
        if (ac < 0 || ac > 10) {
            throw new Controller.GameException("Armor class must be between 0 and 10.");
        }
        this.ac = ac;
    }

    /** Method: save
     * Calls CharacterStore to save the current object
     * throws GameException
     */
    public void save() throws Controller.GameException {
        cs.addCharacter(this);
    }

    /** Method: getAll
     * returns all of the characters in the CharacterStore
     * @return String the formatted String of all characters.
     */
    public String getAll() {
        StringBuffer sb = new StringBuffer();
        ArrayList<GameCharacter> characters = cs.getCharacters();
        for (GameCharacter gc : characters) {
            sb.append(gc.toString() + "\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return name + '\t' + type + '\t' + hp + "\t" + ac;
    }
}
