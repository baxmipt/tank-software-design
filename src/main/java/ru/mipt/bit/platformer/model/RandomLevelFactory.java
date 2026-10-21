package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomLevelFactory implements LevelFactory {

    private final int width;
    private final int height;
    private final int treeCount;
    private final int aiTankCount;
    private final float tankMovementDuration;
    private final Random random;

    public RandomLevelFactory(int width, int height, int treeCount, int aiTankCount,
                              float tankMovementDuration, Random random) {
        if (treeCount < 0 || aiTankCount < 0 || treeCount + aiTankCount > width * height - 1) {
            throw new IllegalArgumentException("Level " + width + "x" + height + " cannot hold " + treeCount
                    + " trees, " + aiTankCount + " AI tanks and a player tank");
        }
        this.width = width;
        this.height = height;
        this.treeCount = treeCount;
        this.aiTankCount = aiTankCount;
        this.tankMovementDuration = tankMovementDuration;
        this.random = random;
    }

    @Override
    public Level create() {
        List<GridPoint2> tiles = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles.add(new GridPoint2(x, y));
            }
        }
        Collections.shuffle(tiles, random);

        Tank playerTank = new Tank(tiles.get(0), Direction.RIGHT, tankMovementDuration);
        List<Tank> aiTanks = tiles.subList(1, aiTankCount + 1).stream()
                .map(tile -> new Tank(tile, Direction.RIGHT, tankMovementDuration))
                .toList();
        List<Tree> trees = tiles.subList(aiTankCount + 1, aiTankCount + treeCount + 1).stream()
                .map(Tree::new)
                .toList();
        return new Level(width, height, playerTank, aiTanks, trees);
    }
}
