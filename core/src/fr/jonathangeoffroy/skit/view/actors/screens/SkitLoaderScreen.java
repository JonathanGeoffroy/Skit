package fr.jonathangeoffroy.skit.view.actors.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Json;

import java.util.LinkedHashSet;
import java.util.Set;

import fr.jonathangeoffroy.skit.SkitGame;
import fr.jonathangeoffroy.skit.model.Dialog;
import fr.jonathangeoffroy.skit.model.Person;
import fr.jonathangeoffroy.skit.model.Skit;

/**
 * @author Jonathan Geoffroy
 */
public class SkitLoaderScreen implements Screen {

    private final SkitGame game;
    private final OrthographicCamera camera;
    private String jsonPath;
    private BitmapFont font;

    public SkitLoaderScreen(SkitGame game, String jsonPath) {
        this.game = game;
        this.jsonPath = jsonPath;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        Json json = new Json();
        Skit skit = json.fromJson(Skit.class, Gdx.files.internal(jsonPath));

        Set<Person> peopleSet = new LinkedHashSet<Person>();
        for (Dialog dialog : skit.getDialogs()) {
            peopleSet.add(dialog.getPerson());
        }

        AssetManager assetManager = game.getAssetManager();
        for (Person person : peopleSet) {
            assetManager.load(findTexturePath(person), Texture.class);
        }

        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        AssetManager manager = game.getAssetManager();
        if (manager.update()) {
            game.setScreen(new SkitViewerScreen(game));
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
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        font.dispose();
    }

    private String findTexturePath(Person person) {
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
