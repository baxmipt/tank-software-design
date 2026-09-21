package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Input;

public class GdxKeyboard implements Keyboard {

    private final Input input;

    public GdxKeyboard(Input input) {
        this.input = input;
    }

    @Override
    public boolean isPressed(int key) {
        return input.isKeyPressed(key);
    }
}
