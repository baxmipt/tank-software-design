package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameFieldTest {

    @Test
    void shouldHaveEveryTileFreeWithoutTrees() {
        GameField field = new GameField(List.of());

        assertTrue(field.isFree(new GridPoint2(1, 1)));
    }

    @Test
    void shouldNotHaveTileWithTreeFree() {
        GameField field = new GameField(List.of(new Tree(new GridPoint2(1, 3))));

        assertFalse(field.isFree(new GridPoint2(1, 3)));
        assertTrue(field.isFree(new GridPoint2(1, 4)));
    }

    @Test
    void shouldNotHaveTileWithAnyObstacleFree() {
        Obstacle obstacle = tile -> tile.equals(new GridPoint2(2, 2));
        GameField field = new GameField(List.of(obstacle));

        assertFalse(field.isFree(new GridPoint2(2, 2)));
        assertTrue(field.isFree(new GridPoint2(2, 3)));
    }
}
