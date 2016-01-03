package fr.jonathangeoffroy.skit.model;

/**
 * @author Jonathan Geoffroy
 */
public class Dialog {
    private Person person;
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dialog dialog = (Dialog) o;

        return person.equals(dialog.person) && text.equals(dialog.text);

    }

    @Override
    public int hashCode() {
        int result = person.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
