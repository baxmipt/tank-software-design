package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.input.Command;
import ru.mipt.bit.platformer.input.MoveCommand;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

import java.util.Random;

public class RandomMovementStrategy implements MovementStrategy {

    private static final Direction[] DIRECTIONS = Direction.values();

    private final Level level;
    private final Tank tank;
    private final Random random;

    public RandomMovementStrategy(Level level, Tank tank, Random random) {
        this.level = level;
        this.tank = tank;
        this.random = random;
    }

    @Override
    public Command nextCommand() {
        return new MoveCommand(level, tank, DIRECTIONS[random.nextInt(DIRECTIONS.length)]);
    }
}
