package fr.jonathangeoffroy.skit.view.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Map;
import java.util.TreeMap;

import fr.jonathangeoffroy.skit.model.Character;
import fr.jonathangeoffroy.skit.model.Skit;
import fr.jonathangeoffroy.skit.view.screens.SkitScreen;

/**
 * @author Jonathan Geoffroy
 */
public class PeopleActor extends SkitActor {
    private final AssetManager assetManager;
    private final TextActor textActor;
    private Map<Character, CharacterActor> actors;
    private CharacterActor speaker;

    public PeopleActor(Skit skit, AssetManager assetManager, TextActor textActor) {
        super(skit);
        this.assetManager = assetManager;
        this.textActor = textActor;
        actors = new TreeMap<Character, CharacterActor>();
    }

    @Override
    public void show() {
        super.show();

        int nbPeople = skit.getPeople().size;
        float peopleSize = Math.min(getWidth() / 3, getHeight() / 3);

        CharacterActor actor;
        for (int i = 0; i < nbPeople; i++) {
            Character character = skit.getPeople().get(i);
            actor = new CharacterActor(assetManager.get(SkitScreen.findTexturePath(character), Texture.class));

            // TODO: compute right position depending on the number of actors.
            actor.setPosition(getX() + (getWidth() / 2 - peopleSize / 2), getY() + (getHeight() / 2 - peopleSize / 2));
            actor.setSize(peopleSize, peopleSize);

            actors.put(character, actor);
        }

        changeSpeaker();
    }

    private void changeSpeaker() {
        if (speaker != null) {
            speaker.setSpeaking(false);
            textActor.removeObserver(speaker);
        }
        speaker = actors.get(currentDialog.getSpeaker());
        speaker.setSpeaking(true);
        textActor.addObserver(speaker);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        for (CharacterActor person : actors.values()) {
            person.draw(batch, parentAlpha);
        }
    }
}
