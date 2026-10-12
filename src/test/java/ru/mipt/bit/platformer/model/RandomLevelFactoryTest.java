package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomLevelFactoryTest {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 8;
    private static final int TREE_COUNT = 12;
    private static final float MOVEMENT_DURATION = 0.4f;

    private Level createLevel(long seed) {
        return new RandomLevelFactory(WIDTH, HEIGHT, TREE_COUNT, MOVEMENT_DURATION, new Random(seed)).create();
    }

    private boolean isInsideField(GridPoint2 tile) {
        return tile.x >= 0 && tile.x < WIDTH && tile.y >= 0 && tile.y < HEIGHT;
    }

    @Test
    void shouldPlaceRequestedNumberOfTrees() {
        assertEquals(TREE_COUNT, createLevel(1).getTrees().size());
    }

    @Test
    void shouldPlaceEverythingInsideField() {
        Level level = createLevel(2);

        assertTrue(isInsideField(level.getPlayerTank().getCoordinates()));
        assertTrue(level.getTrees().stream().allMatch(tree -> isInsideField(tree.getCoordinates())));
    }

    @Test
    void shouldPlaceTreesOnDifferentTiles() {
        Set<GridPoint2> tiles = new HashSet<>();
        createLevel(3).getTrees().forEach(tree -> tiles.add(tree.getCoordinates()));

        assertEquals(TREE_COUNT, tiles.size());
    }

    @Test
    void shouldNotPlacePlayerTankOnTree() {
        for (long seed = 0; seed < 50; seed++) {
            Level level = createLevel(seed);

            assertFalse(level.getTrees().stream()
                    .anyMatch(tree -> tree.occupies(level.getPlayerTank().getCoordinates())));
        }
    }

    @Test
    void shouldCreateSameLevelForSameSeed() {
        Level first = createLevel(4);
        Level second = createLevel(4);

        assertEquals(first.getPlayerTank().getCoordinates(), second.getPlayerTank().getCoordinates());
        assertEquals(coordinatesOf(first), coordinatesOf(second));
    }

    @Test
    void shouldCreateDifferentLevelsForDifferentSeeds() {
        Set<GridPoint2> tankPositions = new HashSet<>();
        for (long seed = 0; seed < 20; seed++) {
            tankPositions.add(createLevel(seed).getPlayerTank().getCoordinates());
        }

        assertTrue(tankPositions.size() > 1);
    }

    @Test
    void shouldFillWholeFieldExceptPlayerTank() {
        Level level = new RandomLevelFactory(3, 3, 8, MOVEMENT_DURATION, new Random(5)).create();

        assertEquals(8, level.getTrees().size());
    }

    @Test
    void shouldNotAcceptMoreTreesThanFreeTiles() {
        assertThrows(IllegalArgumentException.class,
                () -> new RandomLevelFactory(3, 3, 9, MOVEMENT_DURATION, new Random()));
    }

    @Test
    void shouldNotAcceptNegativeTreeCount() {
        assertThrows(IllegalArgumentException.class,
                () -> new RandomLevelFactory(3, 3, -1, MOVEMENT_DURATION, new Random()));
    }

    private List<GridPoint2> coordinatesOf(Level level) {
        return level.getTrees().stream().map(Tree::getCoordinates).toList();
    }
}
