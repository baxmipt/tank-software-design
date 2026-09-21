package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Input.Keys;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Level;

public class PlayerControls {

    private final Level level;

    public PlayerControls(Level level) {
        this.level = level;
    }

    public KeyboardController createController(Keyboard keyboard) {
        KeyboardController controller = new KeyboardController(keyboard);
        controller.bind(new MoveCommand(level, Direction.UP), Keys.UP, Keys.W);
        controller.bind(new MoveCommand(level, Direction.LEFT), Keys.LEFT, Keys.A);
        controller.bind(new MoveCommand(level, Direction.DOWN), Keys.DOWN, Keys.S);
        controller.bind(new MoveCommand(level, Direction.RIGHT), Keys.RIGHT, Keys.D);
        return controller;
    }
}
