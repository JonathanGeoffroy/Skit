package fr.jonathangeoffroy.skit.view.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import fr.jonathangeoffroy.skit.SkitGame;
import fr.jonathangeoffroy.skit.controller.observer.Observable;
import fr.jonathangeoffroy.skit.controller.observer.TextActorListener;
import fr.jonathangeoffroy.skit.model.Skit;
import fr.jonathangeoffroy.skit.view.screens.SkitLoaderScreen;

/**
 * @author Jonathan Geoffroy
 */
public class TextActor extends SkitActor implements Observable<TextActorListener>, InputProcessor {
    private final static float SECONDS_PER_LETTER = 0.033f;
    private static final float MARGIN = 0.05f;
    private static final float SECONDS_PER_BLINKING = 0.90f;
    private static final float TRIANGLE_SIZE = 0.08f;

    private Array<TextActorListener> observers;
    private float deltaTime;
    private BitmapFont font;
    private final TextureRegion triangleDown;
    private boolean textDisplayed;

    public TextActor(Skit skit) {
        super(skit);
        font = new BitmapFont();
        triangleDown = new TextureRegion(SkitGame.getAssetManager().get(SkitLoaderScreen.TRIANGLE_DOWN_IMAGE, Texture.class), 96, 52);
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

    @Override
    public boolean nextDialog() {
        if (super.nextDialog()) {
            return true;
        }
        setTextDisplayed(false);
        deltaTime = 0;

        for (TextActorListener observer : observers) {
            observer.onNextDialog();
        }

        return false;
    }

    private void setTextDisplayed(boolean textDisplayed) {
        this.textDisplayed = textDisplayed;

        if (textDisplayed) {
            for (TextActorListener observer : observers) {
                observer.onTextDisplayed();
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Check that the user clicked on the text
        float touchedY = Gdx.graphics.getHeight() - screenY;
        if (screenX < getX() || screenX > getX() + getWidth() ||
                touchedY < getY() || touchedY > getY() + getHeight()) {
            return false;
        }

        // If the text is already displayed,
        // then notify observers that we'll display the next dialog,
        // otherwise, display the dialog
        if (textDisplayed) {
            nextDialog();
        } else {
            setTextDisplayed(true);
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

