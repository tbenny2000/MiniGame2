package Controller;

import Model.CharacterStore;

import java.util.ArrayList;

// This class – contains the logic and validations for the Game Characters
public class GameCharacter {
    private String name;
    private String type;
    private int hp;
    private int ac;
    private static CharacterStore cs;

    // If already created, does nothing.
    // CharacterStore is static to allow one copy of that class to be shared by
    // many GameCharacter objects
    // Constructor that creates the CharacterStore if needed
    // initializes CharacterStore instance if not already created
    public GameCharacter() {
        if (cs == null) {
            cs = CharacterStore.getInstance();
        }
    }

    public GameCharacter(String name, String type, int hp, int ac) throws GameException {
        this();
        setName(name);
        setType(type);
        setHp(hp);
        setAc(ac);
    }

    // Getters and Setters with Validation Logic
    public String getName() {
        return name;
    }

    public void setName(String name) throws GameException {
        if (name == null || name.length() < 4) {
            throw new GameException("Name must be at least 4 characters.");
        }
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) throws GameException {
        if (!(type.equalsIgnoreCase("Fighter") ||
                type.equalsIgnoreCase("Wizard") ||
                type.equalsIgnoreCase("Druid") ||
                type.equalsIgnoreCase("Thief"))) {
            throw new GameException("Type must be Fighter, Wizard, Druid, or Thief.");
        }
        this.type = type;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) throws GameException {
        if (hp < 0 || hp > 100) {
            throw new GameException("Hit points must be between 0 and 100.");
        }
        this.hp = hp;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) throws GameException {
        if (ac < 0 || ac > 10) {
            throw new GameException("Armor class must be between 0 and 10.");
        }
        this.ac = ac;
    }

    // Save character to the CharacterStore
    public void save() throws GameException {
        cs.addCharacter(this);
    }

    // Retrieve all characters from CharacterStore
    public String getAll() {
        StringBuilder sb = new StringBuilder();
        ArrayList<GameCharacter> characters = cs.getCharacters();
        for (GameCharacter gc : characters) {
            sb.append(gc.toString()).append("\n");
        }
        return sb.toString();
    }

    // Adjusts character's health and updates it in CharacterStore
    public void adjustHealth(int healthChange) throws GameException {
        setHp(this.hp + healthChange);
        save();
    }

    @Override
    public String toString() {
        return name + '\t' + type + '\t' + hp + "\t" + ac;
    }
}
