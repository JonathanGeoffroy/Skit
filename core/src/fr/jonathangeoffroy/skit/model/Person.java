package fr.jonathangeoffroy.skit.model;

/**
 * @author Jonathan Geoffroy
 */
public class Person {
    private String name;
    private String state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return name.equals(person.name) && !(state != null ? !state.equals(person.state) : person.state != null);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
