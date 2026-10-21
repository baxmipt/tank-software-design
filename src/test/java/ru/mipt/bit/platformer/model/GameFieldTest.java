package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameFieldTest {

    @Test
    void shouldHaveEveryTileFreeWithoutTrees() {
        GameField field = new GameField(5, 5, List.of());

        assertTrue(field.isFree(new GridPoint2(1, 1)));
    }

    @Test
    void shouldNotHaveTileWithTreeFree() {
        GameField field = new GameField(5, 5, List.of(new Tree(new GridPoint2(1, 3))));

        assertFalse(field.isFree(new GridPoint2(1, 3)));
        assertTrue(field.isFree(new GridPoint2(1, 4)));
    }

    @Test
    void shouldNotHaveTileWithAnyObstacleFree() {
        Obstacle obstacle = tile -> tile.equals(new GridPoint2(2, 2));
        GameField field = new GameField(5, 5, List.of(obstacle));

        assertFalse(field.isFree(new GridPoint2(2, 2)));
        assertTrue(field.isFree(new GridPoint2(2, 3)));
    }

    @Test
    void shouldHaveEveryTileInsideFieldFree() {
        GameField field = new GameField(3, 2, List.of());

        assertTrue(field.isFree(new GridPoint2(0, 0)));
        assertTrue(field.isFree(new GridPoint2(2, 1)));
    }

    @Test
    void shouldNotHaveTilesOutsideFieldFree() {
        GameField field = new GameField(3, 2, List.of());

        assertFalse(field.isFree(new GridPoint2(-1, 0)));
        assertFalse(field.isFree(new GridPoint2(0, -1)));
        assertFalse(field.isFree(new GridPoint2(3, 0)));
        assertFalse(field.isFree(new GridPoint2(0, 2)));
    }
}
