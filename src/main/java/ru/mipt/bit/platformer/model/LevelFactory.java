package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class LevelFactory {

    private static final float TANK_MOVEMENT_DURATION = 0.4f;

    public Level createDefaultLevel() {
        Tank playerTank = new Tank(new GridPoint2(1, 1), Direction.RIGHT, TANK_MOVEMENT_DURATION);
        List<Tree> trees = List.of(new Tree(new GridPoint2(1, 3)));
        return new Level(playerTank, trees);
    }
}
