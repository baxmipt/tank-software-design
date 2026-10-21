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
    private Tank aiTank;
    private Tree tree;
    private Level level;

    @BeforeEach
    void createLevel() {
        tank = new Tank(new GridPoint2(1, 1), Direction.RIGHT, MOVEMENT_DURATION);
        aiTank = new Tank(new GridPoint2(3, 3), Direction.RIGHT, MOVEMENT_DURATION);
        tree = new Tree(new GridPoint2(1, 2));
        level = new Level(5, 5, tank, List.of(aiTank), List.of(tree));
    }

    @Test
    void shouldMoveTankToFreeTile() {
        level.move(tank, Direction.RIGHT);
        level.update(MOVEMENT_DURATION);

        assertEquals(new GridPoint2(2, 1), tank.getCoordinates());
    }

    @Test
    void shouldNotMoveTankIntoTree() {
        level.move(tank, Direction.UP);
        level.update(MOVEMENT_DURATION);

        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
    }

    @Test
    void shouldNotMoveTankIntoStandingTank() {
        level.move(aiTank, Direction.LEFT);
        level.update(MOVEMENT_DURATION);
        level.move(aiTank, Direction.DOWN);
        level.update(MOVEMENT_DURATION);
        level.move(aiTank, Direction.DOWN);
        level.update(MOVEMENT_DURATION);
        level.move(aiTank, Direction.LEFT);
        level.update(MOVEMENT_DURATION);

        assertEquals(new GridPoint2(2, 1), aiTank.getCoordinates());
    }

    @Test
    void shouldNotMoveTankIntoTileWhereAnotherTankIsGoing() {
        Tank first = new Tank(new GridPoint2(0, 0), Direction.RIGHT, MOVEMENT_DURATION);
        Tank second = new Tank(new GridPoint2(2, 0), Direction.LEFT, MOVEMENT_DURATION);
        Level crossing = new Level(3, 1, first, List.of(second), List.of());

        crossing.move(first, Direction.RIGHT);
        crossing.move(second, Direction.LEFT);
        crossing.update(MOVEMENT_DURATION);

        assertEquals(new GridPoint2(1, 0), first.getCoordinates());
        assertEquals(new GridPoint2(2, 0), second.getCoordinates());
    }

    @Test
    void shouldNotMoveTankOutOfField() {
        Tank cornerTank = new Tank(new GridPoint2(0, 0), Direction.RIGHT, MOVEMENT_DURATION);
        Level smallLevel = new Level(2, 2, cornerTank, List.of(), List.of());

        smallLevel.move(cornerTank, Direction.LEFT);
        smallLevel.update(MOVEMENT_DURATION);
        smallLevel.move(cornerTank, Direction.DOWN);
        smallLevel.update(MOVEMENT_DURATION);

        assertEquals(new GridPoint2(0, 0), cornerTank.getCoordinates());
    }

    @Test
    void shouldMoveTankToLastTileOfField() {
        Tank cornerTank = new Tank(new GridPoint2(0, 0), Direction.RIGHT, MOVEMENT_DURATION);
        Level smallLevel = new Level(2, 2, cornerTank, List.of(), List.of());

        smallLevel.move(cornerTank, Direction.RIGHT);
        smallLevel.update(MOVEMENT_DURATION);
        smallLevel.move(cornerTank, Direction.UP);
        smallLevel.update(MOVEMENT_DURATION);

        assertEquals(new GridPoint2(1, 1), cornerTank.getCoordinates());
    }

    @Test
    void shouldUpdateAllTanks() {
        level.move(tank, Direction.RIGHT);
        level.move(aiTank, Direction.UP);
        level.update(MOVEMENT_DURATION);

        assertEquals(new GridPoint2(2, 1), tank.getCoordinates());
        assertEquals(new GridPoint2(3, 4), aiTank.getCoordinates());
    }

    @Test
    void shouldKeepTanksAndTrees() {
        assertSame(tank, level.getPlayerTank());
        assertEquals(List.of(aiTank), level.getAiTanks());
        assertEquals(List.of(tree), level.getTrees());
    }
}
