package fr.jonathangeoffroy.skit.view.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import fr.jonathangeoffroy.skit.controller.observer.Observable;
import fr.jonathangeoffroy.skit.controller.observer.TextActorListener;
import fr.jonathangeoffroy.skit.model.Skit;
import fr.jonathangeoffroy.skit.view.screens.SkitLoaderScreen;

/**
 * @author Jonathan Geoffroy
 */
public class TextActor extends SkitActor implements Observable<TextActorListener> {
    private final static float SECONDS_PER_LETTER = 0.033f;
    private static final float MARGIN = 0.05f;
    private static final float SECONDS_PER_BLINKING = 0.90f;
    private static final float TRIANGLE_SIZE = 0.08f;

    private Array<TextActorListener> observers;
    private float deltaTime;
    private BitmapFont font;
    private final TextureRegion triangleDown;
    private boolean textDisplayed;

    public TextActor(Skit skit, AssetManager assetManager) {
        super(skit);
        font = new BitmapFont();
        triangleDown = new TextureRegion(assetManager.get(SkitLoaderScreen.TRIANGLE_DOWN_IMAGE, Texture.class), 96, 52);
        observers = new Array<TextActorListener>();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        deltaTime += Gdx.graphics.getDeltaTime();

        // Display text
        String textToDisplay;
        if (!textDisplayed) {
            int nbLettersToDisplay = Math.min(currentDialog.getText().length(), (int) (deltaTime / SECONDS_PER_LETTER));
            textToDisplay = currentDialog.getText().substring(0, nbLettersToDisplay);
            if (textToDisplay.length() == currentDialog.getText().length()) {
                setTextDisplayed(true);
            }
        } else {
            textToDisplay = currentDialog.getText();
        }

        final float margin = Math.max(getWidth(), getHeight()) * MARGIN;
        // TODO: split text into multiple lines when needed
        font.draw(batch, textToDisplay, getX() + margin, getY() + getHeight());

        // If the text is entirely displayed, draw a blinking triangle
        // in order to invite user to play the next dialog
        if (textDisplayed && deltaTime % SECONDS_PER_BLINKING >= SECONDS_PER_BLINKING / 2) {
            final float triangleSize = Math.min(getWidth(), getHeight()) * TRIANGLE_SIZE;

            batch.draw(
                    triangleDown,
                    getX() + getWidth() - triangleSize - margin,
                    getY() + getHeight() - margin,
                    triangleSize,
                    triangleSize);
        }
    }

    @Override
    public void addObserver(TextActorListener observer) {
        if (!observers.contains(observer, true)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(TextActorListener observer) {
        observers.removeValue(observer, true);
    }

    private void setTextDisplayed(boolean textDisplayed) {
        this.textDisplayed = textDisplayed;

        if (textDisplayed) {
            for (TextActorListener observer : observers) {
                observer.onTextDisplayed();
            }
        }
    }
}

