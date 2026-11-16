package ru.mipt.bit.platformer.input;

/**
 * Input port for querying keyboard state.
 */
public interface Keyboard {

    boolean isPressed(int key);

    default boolean isJustPressed(int key) {
        return false;
    }
}
