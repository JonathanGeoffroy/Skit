package fr.jonathangeoffroy.skit.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;

import fr.jonathangeoffroy.skit.SkitGame;
import fr.jonathangeoffroy.skit.model.Character;

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
    public static String findTexturePath(Character character) {
        return findTexturePath(character.getName(), character.getState());
    }

    /**
     * Retrieve the path of a texture using character's <code>name</code> and <code>state</code> values
     *
     * @param name  the character's name
     * @param state the character's state
     * @return the texture's path
     */
    public static String findTexturePath(String name, String state) {
        StringBuilder builder = new StringBuilder();
        builder
                .append("people/")
                .append(name)
                .append('/')
                .append(name);
        if (state != null && !"".equals(state)) {
            builder
                    .append('-')
                    .append(state);
        }
        builder.append(".png");
        return builder.toString();
    }
}
