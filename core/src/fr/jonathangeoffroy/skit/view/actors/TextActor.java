package fr.jonathangeoffroy.skit.view.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

import fr.jonathangeoffroy.skit.controller.observer.Observable;
import fr.jonathangeoffroy.skit.controller.observer.TextActorListener;
import fr.jonathangeoffroy.skit.model.Skit;

/**
 * @author Jonathan Geoffroy
 */
public class TextActor extends SkitActor implements Observable<TextActorListener> {
    private final static float SECONDS_PER_LETTER = 0.033f;
    private static final float MARGIN = 0.05f;

    private Array<TextActorListener> observers;
    private float deltaTime;
    private BitmapFont font;
    private boolean textDisplayed;

    public TextActor(Skit skit) {
        super(skit);
        font = new BitmapFont();
        observers = new Array<TextActorListener>();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        deltaTime += Gdx.graphics.getDeltaTime();

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


        // TODO: split text into multiple lines when needed
        font.draw(batch, textToDisplay, getX() + MARGIN * getWidth(), getY() + getHeight());
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

