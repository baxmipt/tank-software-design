package ru.mipt.bit.platformer.model;

/**
 * Application port for supplying a level to the game.
 */
public interface LevelFactory {

    Level create();
}
