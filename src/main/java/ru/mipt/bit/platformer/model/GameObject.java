package ru.mipt.bit.platformer.model;

public interface GameObject extends Obstacle {

    void update(float deltaTime, Level level);

    default void hit(int damage, Level level) {
    }
}
