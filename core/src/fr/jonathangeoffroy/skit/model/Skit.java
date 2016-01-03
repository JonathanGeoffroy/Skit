package fr.jonathangeoffroy.skit.model;

import com.badlogic.gdx.utils.Array;

/**
 * @author Jonathan Geoffroy
 */
public class Skit {
    private Array<Dialog> dialogs;
    private Array<Person> people;

    public Array<Dialog> getDialogs() {
        return dialogs;
    }

    public void setDialogs(Array<Dialog> dialogs) {
        this.dialogs = dialogs;
    }

    public Array<Person> getPeople() {
        return people;
    }

    public void setPeople(Array<Person> people) {
        this.people = people;
    }
}
