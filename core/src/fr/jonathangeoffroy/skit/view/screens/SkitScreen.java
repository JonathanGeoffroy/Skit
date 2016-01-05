package fr.jonathangeoffroy.skit.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;

import fr.jonathangeoffroy.skit.SkitGame;

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
     * Retrieve the path of a texture using <code>character</code> values
     *
     * @param character the character we have to find the texture's path
     * @return the texture's path
     */
    public static String findTexturePath(fr.jonathangeoffroy.skit.model.Character character) {
        StringBuilder builder = new StringBuilder();
        builder
                .append("people/")
                .append(character.getName())
                .append('/')
                .append(character.getName());
        if (character.getState() != null) {
            builder
                    .append('-')
                    .append(character.getState());
        }
        builder.append(".png");
        return builder.toString();
    }
}
