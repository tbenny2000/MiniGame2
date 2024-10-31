package Model;

import Controller.GameCharacter;
import Controller.GameException;

import java.util.ArrayList;

public class CharacterStore {
    private ArrayList<GameCharacter> characters;
    private static CharacterStore inst = null;

    // Private constructor for Singleton pattern
    private CharacterStore() {
        characters = new ArrayList<>();
    }

    // Singleton instance to ensure only one CharacterStore exists
    public static CharacterStore getInstance() {
        if (inst == null) {
            inst = new CharacterStore();
        }
        return inst;
    }

    // Adds a character to the store if it doesn't already exist.
    // gc The GameCharacter to add.
    // @throws GameException if the character already exists.
    public void addCharacter(GameCharacter gc) throws GameException {
        checkUnique(gc.getName());
        characters.add(gc);
    }

    // Checks if a character name is unique within the store.
    // name The name of the GameCharacter to check.
    // @throws GameException if a character with the same name exists.
    private void checkUnique(String name) throws GameException {
        for (GameCharacter gc : characters) {
            if (name.equalsIgnoreCase(gc.getName())) {
                throw new GameException(name + " already exists.");
            }
        }
    }

    // Finds and returns a character by name.
    // name The name of the GameCharacter to find.
    // @return The GameCharacter object if found.
    // @throws GameException if the character is not found.
    public GameCharacter findCharacter(String name) throws GameException {
        for (GameCharacter gc : characters) {
            if (name.equalsIgnoreCase(gc.getName())) {
                return gc;
            }
        }
        throw new GameException(name + " not found.");
    }

    // Returns a list of all characters in the store.
    // @return An ArrayList of GameCharacter objects.
    public ArrayList<GameCharacter> getCharacters() {
        return new ArrayList<>(characters); // Return a copy to protect internal data
    }
}