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
    private static final int AI_TANK_COUNT = 4;
    private static final float MOVEMENT_DURATION = 0.4f;

    private Level createLevel(long seed) {
        return new RandomLevelFactory(WIDTH, HEIGHT, TREE_COUNT, AI_TANK_COUNT, MOVEMENT_DURATION, new Random(seed)).create();
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
        assertTrue(level.getAiTanks().stream().allMatch(tank -> isInsideField(tank.getCoordinates())));
    }

    @Test
    void shouldPlaceRequestedNumberOfAiTanks() {
        assertEquals(AI_TANK_COUNT, createLevel(1).getAiTanks().size());
    }

    @Test
    void shouldPlaceAllObjectsOnDifferentTiles() {
        for (long seed = 0; seed < 50; seed++) {
            Level level = createLevel(seed);
            Set<GridPoint2> tiles = new HashSet<>();
            tiles.add(level.getPlayerTank().getCoordinates());
            level.getAiTanks().forEach(tank -> tiles.add(tank.getCoordinates()));
            level.getTrees().forEach(tree -> tiles.add(tree.getCoordinates()));

            assertEquals(1 + AI_TANK_COUNT + TREE_COUNT, tiles.size());
        }
    }

    @Test
    void shouldPlaceAiTanksRandomly() {
        Set<List<GridPoint2>> placements = new HashSet<>();
        for (long seed = 0; seed < 20; seed++) {
            placements.add(createLevel(seed).getAiTanks().stream().map(Tank::getCoordinates).toList());
        }

        assertTrue(placements.size() > 1);
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
        Level level = new RandomLevelFactory(3, 3, 8, 0, MOVEMENT_DURATION, new Random(5)).create();

        assertEquals(8, level.getTrees().size());
    }

    @Test
    void shouldNotAcceptMoreTreesThanFreeTiles() {
        assertThrows(IllegalArgumentException.class,
                () -> new RandomLevelFactory(3, 3, 9, 0, MOVEMENT_DURATION, new Random()));
    }

    @Test
    void shouldNotAcceptMoreObjectsThanTiles() {
        assertThrows(IllegalArgumentException.class,
                () -> new RandomLevelFactory(3, 3, 5, 4, MOVEMENT_DURATION, new Random()));
    }

    @Test
    void shouldNotAcceptNegativeAiTankCount() {
        assertThrows(IllegalArgumentException.class,
                () -> new RandomLevelFactory(3, 3, 0, -1, MOVEMENT_DURATION, new Random()));
    }

    @Test
    void shouldFillWholeFieldWithTanksAndTrees() {
        Level level = new RandomLevelFactory(3, 3, 4, 4, MOVEMENT_DURATION, new Random(6)).create();

        assertEquals(4, level.getAiTanks().size());
        assertEquals(4, level.getTrees().size());
    }

    @Test
    void shouldNotAcceptNegativeTreeCount() {
        assertThrows(IllegalArgumentException.class,
                () -> new RandomLevelFactory(3, 3, -1, 0, MOVEMENT_DURATION, new Random()));
    }

    private List<GridPoint2> coordinatesOf(Level level) {
        return level.getTrees().stream().map(Tree::getCoordinates).toList();
    }
}
