package ru.mipt.bit.platformer.model;

/**
 * Output port through which the domain reports changes in level objects.
 */
public interface LevelListener {

    void objectAdded(GameObject object);

    void objectRemoved(GameObject object);
}
