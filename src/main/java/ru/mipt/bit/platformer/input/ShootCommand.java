package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

public class ShootCommand implements Command {

    private final Level level;
    private final Tank tank;

    public ShootCommand(Level level, Tank tank) {
        this.level = level;
        this.tank = tank;
    }

    @Override
    public void execute() {
        level.shoot(tank);
    }
}
