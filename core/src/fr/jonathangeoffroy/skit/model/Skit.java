package fr.jonathangeoffroy.skit.model;

import com.badlogic.gdx.utils.Array;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Jonathan Geoffroy
 */
public class Skit {
    private Array<Dialog> dialogs;
    private Array<Character> people;

    public Set<Character> computeAllCharacters() {
        Set<Character> characters = new TreeSet<Character>();
        for (Character c : people) {
            characters.add(c);
        }
        for (Dialog d : dialogs) {
            characters.add(d.getSpeaker());
        }

        return characters;
    }

    /**
     * Compute a Map containing all characters of this skit
     * and for each character, all used states.
     *
     * @return a Map containing all states of all characters used in this skit
     */
    public Map<Character, Set<String>> computeAllStatesCharacters() {
        Map<Character, Set<String>> statesCharacters = new TreeMap<Character, Set<String>>();

        // Add all useful people for this skit
        for (Dialog dialog : dialogs) {
            addStateCharacter(statesCharacters, dialog.getSpeaker());
        }

        // Add people who's just here at the beginning,
        // Useful for skits where people has a state only at the beginning
        for (Character p : people) {
            addStateCharacter(statesCharacters, p);
        }

        return statesCharacters;
    }

    private void addStateCharacter(Map<Character, Set<String>> statesCharacters, Character character) {
        final Set<String> states;
        if (statesCharacters.containsKey(character)) {
            states = statesCharacters.get(character);
        } else {
            states = new TreeSet<String>();
            statesCharacters.put(character, states);
        }
        if (character.getState() != null) {
            states.add(character.getState());
        } else {
            states.add("");
        }
    }

    public Array<Dialog> getDialogs() {
        return dialogs;
    }

    public void setDialogs(Array<Dialog> dialogs) {
        this.dialogs = dialogs;
    }

    public Array<Character> getPeople() {
        return people;
    }

    public void setPeople(Array<Character> people) {
        this.people = people;
    }
}
