package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TreeTest {

    private final Tree tree = new Tree(new GridPoint2(1, 3));

    @Test
    void shouldOccupyItsOwnTile() {
        assertTrue(tree.occupies(new GridPoint2(1, 3)));
    }

    @Test
    void shouldNotOccupyOtherTiles() {
        assertFalse(tree.occupies(new GridPoint2(1, 4)));
    }
}
