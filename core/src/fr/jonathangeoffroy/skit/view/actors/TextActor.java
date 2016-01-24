package fr.jonathangeoffroy.skit.view.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import fr.jonathangeoffroy.skit.SkitGame;
import fr.jonathangeoffroy.skit.controller.observer.Observable;
import fr.jonathangeoffroy.skit.controller.observer.TextActorListener;
import fr.jonathangeoffroy.skit.model.Skit;
import fr.jonathangeoffroy.skit.view.screens.SkitLoaderScreen;
import fr.jonathangeoffroy.skit.view.utils.SkitGlyphLayout;

import static com.badlogic.gdx.graphics.Color.BLACK;

/**
 * @author Jonathan Geoffroy
 */
public class TextActor extends SkitActor implements Observable<TextActorListener>, InputProcessor {
    private final static float SECONDS_PER_LETTER = 0.033f;
    private static final float MARGIN = 0.05f;
    private static final float SECONDS_PER_BLINKING = 0.90f;
    private static final float TRIANGLE_SIZE = 0.08f;

    /**
     * Observers which receive #onTextDisplayed as soon as the current dialog is entirelly displayed
     */
    private Array<TextActorListener> observers;

    /**
     * Useful to know how much character to draw on the screen
     */
    private float deltaTime;

    /**
     * The font used to draw the current dialog
     */
    private BitmapFont font;
    private final TextureRegion triangleDown;

    /**
     * true if and only if the current dialog has been entirely displayed
     */
    private boolean textDisplayed;

    /**
     * The number of current dialog's characters which have already been displayed on the screen
     */
    private int displayedTextLength;

    /**
     * The real text to display,
     * by taking account on:
     * <ul>
     * <li>the text animation</li>
     * <li>the text scrolling if the current dialog is too long</li>
     * </ul>
     */
    private StringBuilder textToDisplay;

    /**
     * Useful to know if the text is too long to be drawn on the screen
     */
    private SkitGlyphLayout glyphLayout;

    public TextActor(Skit skit) {
        super(skit);
        glyphLayout = new SkitGlyphLayout();
        textToDisplay = new StringBuilder();
        font = new BitmapFont();
        triangleDown = new TextureRegion(SkitGame.getAssetManager().get(SkitLoaderScreen.TRIANGLE_DOWN_IMAGE, Texture.class), 96, 52);
        observers = new Array<TextActorListener>();
    }

    @Override
    public void show() {
        super.show();
        resetDialog();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        deltaTime += Gdx.graphics.getDeltaTime();
        final float margin = Math.max(getWidth(), getHeight()) * MARGIN;

        // Display text
        if (!textDisplayed) {
            // Compute the text to display
            int newDisplayedTextLength = Math.min(currentDialog.getText().length(), (int) (deltaTime / SECONDS_PER_LETTER));
            if (newDisplayedTextLength - displayedTextLength > 0) {
                textToDisplay.append(currentDialog.getText().substring(displayedTextLength, newDisplayedTextLength));
                if (newDisplayedTextLength == currentDialog.getText().length()) {
                    setTextDisplayed(true);
                } else {
                    // If the text's height is greater than the Actor's height, remove the first text line
                    glyphLayout.setText(font, textToDisplay.toString(), BLACK, getWidth() - margin * 2, Align.topLeft, true);
                    if (glyphLayout.height >= getHeight() - 2 * margin) {
                        final int speakerNameLength = currentDialog.getSpeaker().getName().length() + 2;
                        textToDisplay.delete(speakerNameLength, glyphLayout.firstLine().length());
                    }
                }
            }

            displayedTextLength = newDisplayedTextLength;
        }

        // Draw the text, and automatically split it in multiple line if needed
        font.draw(batch, textToDisplay, getX() + margin, getY() + getHeight() - margin, getWidth() - margin * 2, Align.topLeft, true);

        // If the text is entirely displayed, draw a blinking triangle
        // in order to invite user to play the next dialog
        if (textDisplayed && deltaTime % SECONDS_PER_BLINKING >= SECONDS_PER_BLINKING / 2) {
            final float triangleSize = Math.min(getWidth(), getHeight()) * TRIANGLE_SIZE;

            batch.draw(
                    triangleDown,
                    getX() + getWidth() - margin,
                    getY() + triangleSize,
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
        resetDialog();

        for (TextActorListener observer : observers) {
            observer.onNextDialog();
        }

        return false;
    }

    /**
     * Reset dialog's variables in order to draw the current dialog from the beginning
     */
    private void resetDialog() {
        deltaTime = 0;
        displayedTextLength = 0;
        textToDisplay.setLength(0);
        textToDisplay
                .append(currentDialog.getSpeaker().getName())
                .append(": ");
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
            textToDisplay.append(currentDialog.getText().substring(displayedTextLength));

            // Remove as much as lines as needed in order to display the end of the current dialog
            final float margin = Math.max(getWidth(), getHeight()) * MARGIN;
            final int speakerNameLength = currentDialog.getSpeaker().getName().length() + 2;
            boolean isEndOfText = false;
            while (!isEndOfText) {
                glyphLayout.setText(font, textToDisplay.toString(), BLACK, getWidth() - margin * 2, Align.topLeft, true);
                if (glyphLayout.height >= getHeight() - 2 * margin) {
                    textToDisplay.delete(speakerNameLength, glyphLayout.firstLine().length());
                } else {
                    isEndOfText = true; // Stop removing text lines
                }
            }

            // So now that we displayed the end of the dialog, it's entirely displayed
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

