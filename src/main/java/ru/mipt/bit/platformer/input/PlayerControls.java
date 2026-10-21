package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Input.Keys;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

public class PlayerControls {

    private final Level level;

    public PlayerControls(Level level) {
        this.level = level;
    }

    public KeyboardController createController(Keyboard keyboard) {
        KeyboardController controller = new KeyboardController(keyboard);
        Tank tank = level.getPlayerTank();
        controller.bind(new MoveCommand(level, tank, Direction.UP), Keys.UP, Keys.W);
        controller.bind(new MoveCommand(level, tank, Direction.LEFT), Keys.LEFT, Keys.A);
        controller.bind(new MoveCommand(level, tank, Direction.DOWN), Keys.DOWN, Keys.S);
        controller.bind(new MoveCommand(level, tank, Direction.RIGHT), Keys.RIGHT, Keys.D);
        return controller;
    }
}
