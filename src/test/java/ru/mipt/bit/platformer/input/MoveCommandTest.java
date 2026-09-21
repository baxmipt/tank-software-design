package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveCommandTest {

    private static final float MOVEMENT_DURATION = 0.4f;

    private final Tank tank = new Tank(new GridPoint2(1, 1), Direction.RIGHT, MOVEMENT_DURATION);
    private final Level level = new Level(tank, List.of());

    @Test
    void shouldMovePlayerTankInItsDirection() {
        new MoveCommand(level, Direction.UP).execute();
        level.update(MOVEMENT_DURATION);

        assertEquals(new GridPoint2(1, 2), tank.getCoordinates());
    }
}
