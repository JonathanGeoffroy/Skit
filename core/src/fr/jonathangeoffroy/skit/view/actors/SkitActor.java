package fr.jonathangeoffroy.skit.view.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.jonathangeoffroy.skit.model.Dialog;
import fr.jonathangeoffroy.skit.model.Skit;

/**
 * @author Jonathan Geoffroy
 */
public abstract class SkitActor extends Actor {
    protected Skit skit;
    private int skitIndex;
    protected Dialog currentDialog;

    public SkitActor(Skit skit) {
        this.skit = skit;
        currentDialog = skit.getDialogs().get(0);
    }

    public void show() {

    }

    /**
     * Run the next dialog, if the Skit is not over yet
     *
     * @return true if and only if the skit is over (i.e. if there is no more dialogs to run)
     */
    public boolean nextDialog() {
        if (skitIndex == skit.getDialogs().size) {
            return true;
        }

        skitIndex++;
        currentDialog = skit.getDialogs().get(skitIndex);
        return false;
    }
}
