package ru.mipt.bit.platformer.model;

/**
 * Domain abstraction for an object that participates in level updates.
 */
public interface GameObject extends Obstacle {

    void update(float deltaTime, Level level);

    default void hit(int damage, Level level) {
    }
}
