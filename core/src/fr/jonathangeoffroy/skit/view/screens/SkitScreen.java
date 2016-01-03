package fr.jonathangeoffroy.skit.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;

import fr.jonathangeoffroy.skit.SkitGame;
import fr.jonathangeoffroy.skit.model.Person;

/**
 * @author Jonathan Geoffroy
 */
public abstract class SkitScreen extends ScreenAdapter {
    protected final SkitGame game;
    protected final OrthographicCamera camera;

    public SkitScreen(SkitGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Retrieve the path of a texture using <code>person</code> values
     *
     * @param person the person we have to find the texture's path
     * @return the texture's path
     */
    public static String findTexturePath(Person person) {
        StringBuilder builder = new StringBuilder();
        builder
                .append("people/")
                .append(person.getName())
                .append('/')
                .append(person.getName());
        if (person.getState() != null) {
            builder
                    .append('-')
                    .append(person.getState());
        }
        builder.append(".png");
        return builder.toString();
    }
}
