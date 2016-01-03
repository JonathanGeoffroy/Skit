package fr.jonathangeoffroy.skit.view.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

import fr.jonathangeoffroy.skit.model.Person;
import fr.jonathangeoffroy.skit.model.Skit;
import fr.jonathangeoffroy.skit.view.screens.SkitScreen;

/**
 * @author Jonathan Geoffroy
 */
public class PeopleActor extends SkitActor {
    private final AssetManager assetManager;
    private Array<CharacterActor> actors;

    public PeopleActor(Skit skit, AssetManager assetManager) {
        super(skit);
        this.assetManager = assetManager;
        actors = new Array<CharacterActor>();
    }

    @Override
    public void show() {
        super.show();

        int nbPeople = skit.getPeople().size;
        float peopleSize = Math.min(getWidth() / 3, getHeight() / 3);

        CharacterActor actor;
        for (int i = 0; i < nbPeople; i++) {
            Person p = skit.getPeople().get(i);
            actor = new CharacterActor(p, assetManager.get(SkitScreen.findTexturePath(p), Texture.class));

            // TODO: compute right position depending on the number of actors.
            actor.setPosition(getX() + (getWidth() / 2 - peopleSize / 2), getY() + (getHeight() / 2 - peopleSize / 2));
            actor.setSize(peopleSize, peopleSize);

            actors.add(actor);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        for (CharacterActor person : actors) {
            person.draw(batch, parentAlpha);
        }
    }
}
