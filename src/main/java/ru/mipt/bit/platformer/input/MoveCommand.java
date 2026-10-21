package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

public class MoveCommand implements Command {

    private final Level level;
    private final Tank tank;
    private final Direction direction;

    public MoveCommand(Level level, Tank tank, Direction direction) {
        this.level = level;
        this.tank = tank;
        this.direction = direction;
    }

    @Override
    public void execute() {
        level.move(tank, direction);
    }
}
