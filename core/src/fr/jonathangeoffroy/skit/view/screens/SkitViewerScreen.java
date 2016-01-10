package fr.jonathangeoffroy.skit.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fr.jonathangeoffroy.skit.SkitGame;
import fr.jonathangeoffroy.skit.model.Skit;
import fr.jonathangeoffroy.skit.view.actors.PeopleActor;
import fr.jonathangeoffroy.skit.view.actors.TextActor;

/**
 * @author Jonathan Geoffroy
 */
public class SkitViewerScreen extends SkitScreen {
    private final Skit skit;
    private final Stage stage;
    private PeopleActor peopleActor;
    private TextActor textActor;

    public SkitViewerScreen(SkitGame game, Skit skit) {
        super(game);
        this.skit = skit;
        stage = new Stage();
    }

    @Override
    public void show() {
        textActor = new TextActor(skit);
        textActor.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 3);
        textActor.show();

        peopleActor = new PeopleActor(skit, textActor);
        peopleActor.setBounds(0, Gdx.graphics.getHeight() / 3, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * 2 / 3);
        peopleActor.show();

        stage.addActor(peopleActor);
        stage.addActor(textActor);

        Gdx.input.setInputProcessor(textActor);
    }

    @Override
    public void render(float delta) {
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
        stage.draw();
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
