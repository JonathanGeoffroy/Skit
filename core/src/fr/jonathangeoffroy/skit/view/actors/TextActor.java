package fr.jonathangeoffroy.skit.view.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import fr.jonathangeoffroy.skit.model.Skit;

/**
 * @author Jonathan Geoffroy
 */
public class TextActor extends SkitActor {
    private final static float SECONDS_PER_LETTER = 0.033f;
    private static final float MARGIN = 0.05f;
    private float deltaTime;

    private BitmapFont font;

    public TextActor(Skit skit) {
        super(skit);
        font = new BitmapFont();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        deltaTime += Gdx.graphics.getDeltaTime();

        int nbLettersToDisplay = Math.min(currentDialog.getText().length(), (int) (deltaTime / SECONDS_PER_LETTER));
        String textToDisplay = currentDialog.getText().substring(0, nbLettersToDisplay);

        // TODO: split text into multiple lines when needed
        font.draw(batch, textToDisplay, getX() + MARGIN * getWidth(), getY() + getHeight());
    }
}

