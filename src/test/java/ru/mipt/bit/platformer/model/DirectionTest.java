package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectionTest {

    @Test
    void shouldShiftCoordinates() {
        GridPoint2 coordinates = new GridPoint2(1, 1);

        assertEquals(new GridPoint2(1, 2), Direction.UP.nextCoordinates(coordinates));
        assertEquals(new GridPoint2(1, 0), Direction.DOWN.nextCoordinates(coordinates));
        assertEquals(new GridPoint2(0, 1), Direction.LEFT.nextCoordinates(coordinates));
        assertEquals(new GridPoint2(2, 1), Direction.RIGHT.nextCoordinates(coordinates));
    }

    @Test
    void shouldNotChangeGivenCoordinates() {
        GridPoint2 coordinates = new GridPoint2(1, 1);

        Direction.UP.nextCoordinates(coordinates);

        assertEquals(new GridPoint2(1, 1), coordinates);
    }

    @Test
    void shouldHaveOwnRotation() {
        assertEquals(90f, Direction.UP.getRotation());
        assertEquals(-90f, Direction.DOWN.getRotation());
        assertEquals(-180f, Direction.LEFT.getRotation());
        assertEquals(0f, Direction.RIGHT.getRotation());
    }
}
