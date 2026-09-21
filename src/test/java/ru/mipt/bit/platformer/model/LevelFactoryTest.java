package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LevelFactoryTest {

    private final Level level = new LevelFactory().createDefaultLevel();

    @Test
    void shouldPlacePlayerTank() {
        assertEquals(new GridPoint2(1, 1), level.getPlayerTank().getCoordinates());
    }

    @Test
    void shouldPlaceSingleTree() {
        assertEquals(1, level.getTrees().size());
        assertTrue(level.getTrees().get(0).occupies(new GridPoint2(1, 3)));
    }
}
