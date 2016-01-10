package fr.jonathangeoffroy.skit.view.actors;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Map;
import java.util.TreeMap;

import fr.jonathangeoffroy.skit.controller.observer.TextActorListener;
import fr.jonathangeoffroy.skit.model.Character;
import fr.jonathangeoffroy.skit.model.Skit;

/**
 * @author Jonathan Geoffroy
 */
public class PeopleActor extends SkitActor implements TextActorListener {
    private static final float PEOPLE_MARGIN = 0.10f;
    private final TextActor textActor;
    private Map<Character, CharacterActor> actors;
    private CharacterActor speaker;

    public PeopleActor(Skit skit, TextActor textActor) {
        super(skit);
        this.textActor = textActor;
        actors = new TreeMap<Character, CharacterActor>();
    }

    @Override
    public void show() {
        CharacterActor actor;

        // Load all actors for this skit
        for (Character character : skit.computeAllCharacters()) {
            actor = new CharacterActor(character);
            actors.put(character, actor);
        }

        // compute the position of each characters who's here at the beginning of the skit
        int nbPeople = skit.getPeople().size;
        float peopleSize = Math.min(getWidth() / 4, getHeight() / 4);

        for (int i = 0; i < nbPeople; i++) {
            Character character = skit.getPeople().get(i);
            actor = actors.get(character);

            // TODO: compute right position depending on the number of actors.
            float positionX = getX() + (getWidth() / (nbPeople + 1) - peopleSize / 2) + i * (peopleSize + PEOPLE_MARGIN * getWidth());
            float positionY = getY() + (getHeight() / 2 - peopleSize / 2);
            actor.setPosition(positionX, positionY);
            actor.setSize(peopleSize, peopleSize);
        }

        textActor.addObserver(this);
        changeSpeaker();
    }

    private void changeSpeaker() {
        if (speaker != null) {
            speaker.setSpeaking(false);
        }

        final Character speaker = currentDialog.getSpeaker();
        this.speaker = actors.get(speaker);
        this.speaker.setCharacter(speaker);
        this.speaker.changeAnimation();
        this.speaker.setSpeaking(true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        for (CharacterActor person : actors.values()) {
            person.draw(batch, parentAlpha);
        }
    }

    @Override
    public boolean nextDialog() {
        if (super.nextDialog()) {
            return true;
        }

        changeSpeaker();
        return false;
    }

    /**
     * Stop the animation as soon as the text is entirely displayed
     */
    @Override
    public void onTextDisplayed() {
        speaker.setSpeaking(false);
    }

    @Override
    public void onNextDialog() {
        nextDialog();
    }
}
