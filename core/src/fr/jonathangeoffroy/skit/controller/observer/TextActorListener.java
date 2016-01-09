package fr.jonathangeoffroy.skit.controller.observer;

/**
 * @author Jonathan Geoffroy
 */
public interface TextActorListener {
    /**
     * Notify observer that the text is entirely displayed
     */
    void onTextDisplayed();

    void onNextDialog();
}
