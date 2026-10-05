package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TankTest {

    private static final float MOVEMENT_DURATION = 0.4f;

    private final CollisionDetector emptyField = tile -> true;
    private final CollisionDetector fieldWithObstacleAbove = tile -> !tile.equals(new GridPoint2(1, 2));

    private Tank tank;

    @BeforeEach
    void createTank() {
        tank = new Tank(new GridPoint2(1, 1), Direction.RIGHT, MOVEMENT_DURATION);
    }

    @Test
    void shouldStandStillAfterCreation() {
        assertFalse(tank.isMoving());
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
        assertEquals(new GridPoint2(1, 1), tank.getDestinationCoordinates());
        assertEquals(Direction.RIGHT.getRotation(), tank.getRotation());
    }

    @Test
    void shouldStartMovingToFreeTile() {
        tank.move(Direction.UP, emptyField);

        assertTrue(tank.isMoving());
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
        assertEquals(new GridPoint2(1, 2), tank.getDestinationCoordinates());
    }

    @Test
    void shouldReachDestinationAfterMovementDuration() {
        tank.move(Direction.UP, emptyField);
        tank.update(MOVEMENT_DURATION);

        assertFalse(tank.isMoving());
        assertEquals(new GridPoint2(1, 2), tank.getCoordinates());
    }

    @Test
    void shouldStillMoveAfterHalfOfMovementDuration() {
        tank.move(Direction.UP, emptyField);
        tank.update(MOVEMENT_DURATION / 2);

        assertTrue(tank.isMoving());
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
        assertEquals(0.5f, tank.getMovementProgress());
    }

    @Test
    void shouldNotMoveIntoOccupiedTile() {
        tank.move(Direction.UP, fieldWithObstacleAbove);
        tank.update(MOVEMENT_DURATION);

        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
    }

    @Test
    void shouldTurnTowardsOccupiedTile() {
        tank.move(Direction.UP, fieldWithObstacleAbove);

        assertEquals(Direction.UP.getRotation(), tank.getRotation());
    }

    @Test
    void shouldIgnoreNewDirectionWhileMoving() {
        tank.move(Direction.UP, emptyField);
        tank.move(Direction.RIGHT, emptyField);

        assertEquals(new GridPoint2(1, 2), tank.getDestinationCoordinates());
        assertEquals(Direction.UP.getRotation(), tank.getRotation());
    }
}
