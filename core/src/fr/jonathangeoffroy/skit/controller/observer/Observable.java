package fr.jonathangeoffroy.skit.controller.observer;

/**
 * @author Jonathan Geoffroy
 */
public interface Observable<T> {
    void addObserver(T observer);

    void removeObserver(T observer);
}
