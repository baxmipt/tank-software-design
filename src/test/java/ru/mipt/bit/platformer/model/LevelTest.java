package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class LevelTest {

    private static final float MOVEMENT_DURATION = 0.4f;

    private Tank tank;
    private Tree tree;
    private Level level;

    @BeforeEach
    void createLevel() {
        tank = new Tank(new GridPoint2(1, 1), Direction.RIGHT, MOVEMENT_DURATION);
        tree = new Tree(new GridPoint2(1, 2));
        level = new Level(tank, List.of(tree));
    }

    @Test
    void shouldMovePlayerTankToFreeTile() {
        level.movePlayerTank(Direction.RIGHT);
        level.update(MOVEMENT_DURATION);

        assertEquals(new GridPoint2(2, 1), tank.getCoordinates());
    }

    @Test
    void shouldNotMovePlayerTankIntoTree() {
        level.movePlayerTank(Direction.UP);
        level.update(MOVEMENT_DURATION);

        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
    }

    @Test
    void shouldKeepPlayerTankAndTrees() {
        assertSame(tank, level.getPlayerTank());
        assertEquals(List.of(tree), level.getTrees());
    }
}
