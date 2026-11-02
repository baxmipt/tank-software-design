package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.input.Command;
import ru.mipt.bit.platformer.input.MoveCommand;
import ru.mipt.bit.platformer.input.ShootCommand;
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
        int action = random.nextInt(DIRECTIONS.length + 1);
        if (action == DIRECTIONS.length) {
            return new ShootCommand(level, tank);
        }
        return new MoveCommand(level, tank, DIRECTIONS[action]);
    }
}
