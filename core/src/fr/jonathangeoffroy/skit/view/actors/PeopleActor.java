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
    private static final int MAX_PEOPLE_PER_ROW = 3;
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
        int nbColumns = nbPeople / MAX_PEOPLE_PER_ROW + (nbPeople % MAX_PEOPLE_PER_ROW == 0 ? 0 : 1);
        int nbRows = nbPeople > MAX_PEOPLE_PER_ROW ? MAX_PEOPLE_PER_ROW : nbPeople;
        float peopleSize = Math.min(getWidth() / (MAX_PEOPLE_PER_ROW + 1), getHeight() / (MAX_PEOPLE_PER_ROW + 1));

        for (int i = 0; i < nbPeople; i++) {
            Character character = skit.getPeople().get(i);
            actor = actors.get(character);

            float positionX, positionY;

            // what a magic placement here!
            // TODO: simplify the position computation.
            positionX = getX() + (i % nbRows + 1) * getWidth() / (nbRows + 1) - peopleSize / 2;
            positionY = getY() + getHeight() - peopleSize - i / nbRows * getHeight() / (nbColumns + 1) - getHeight() / nbRows / 2;

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
