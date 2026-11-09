package ru.mipt.bit.platformer.input;

public interface Keyboard {

    boolean isPressed(int key);

    default boolean isJustPressed(int key) {
        return false;
    }
}
