package fr.jonathangeoffroy.skit.model;

/**
 * @author Jonathan Geoffroy
 */
public class Dialog {
    private Character speaker;
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dialog dialog = (Dialog) o;

        return speaker.equals(dialog.speaker) && text.equals(dialog.text);

    }

    @Override
    public int hashCode() {
        int result = speaker.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }

    public Character getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Character speaker) {
        this.speaker = speaker;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
