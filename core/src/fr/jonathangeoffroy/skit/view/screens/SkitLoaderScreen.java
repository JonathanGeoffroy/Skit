package fr.jonathangeoffroy.skit.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Json;

import java.util.LinkedHashSet;
import java.util.Set;

import fr.jonathangeoffroy.skit.SkitGame;
import fr.jonathangeoffroy.skit.model.Character;
import fr.jonathangeoffroy.skit.model.Dialog;
import fr.jonathangeoffroy.skit.model.Skit;

/**
 * Load all textures of a Skit
 * And draw a loading bar until all textures are loaded
 *
 * @author Jonathan Geoffroy
 */
public class SkitLoaderScreen extends SkitScreen {
    public static final String TRIANGLE_DOWN_IMAGE = "images/triangle_down.png";
    private BitmapFont font;
    private Skit skit;

    public SkitLoaderScreen(SkitGame game, String jsonPath) {
        super(game);
        Json json = new Json();
        skit = json.fromJson(Skit.class, Gdx.files.internal(jsonPath));
    }

    @Override
    public void show() {
        // Add all useful people for this skit
        Set<fr.jonathangeoffroy.skit.model.Character> peopleSet = new LinkedHashSet<Character>();
        for (Dialog dialog : skit.getDialogs()) {
            peopleSet.add(dialog.getSpeaker());
        }

        // Add people who's just here at the beginning,
        // Useful for skits where people has a state only at the beginning
        for (Character p : skit.getPeople()) {
            peopleSet.add(p);
        }

        // Load all assets for this skit
        AssetManager assetManager = game.getAssetManager();
        for (Character character : peopleSet) {
            assetManager.load(findTexturePath(character), Texture.class);
        }

        assetManager.load(TRIANGLE_DOWN_IMAGE, Texture.class);

        // Load a default font in order to draw a loading screen
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        AssetManager manager = game.getAssetManager();
        if (manager.update()) {
            game.setScreen(new SkitViewerScreen(game, skit));
            dispose();
        }

        Batch batch = game.getBatch();

        // clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(camera.combined);

        // Write the percentage of loaded assets in the screen
        batch.begin();
        String load = manager.getProgress() * 100 + " %";
        font.draw(batch, load, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        batch.end();
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
