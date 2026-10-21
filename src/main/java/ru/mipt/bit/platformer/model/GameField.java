package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class GameField implements CollisionDetector {

    private final int width;
    private final int height;
    private final List<Obstacle> obstacles;

    public GameField(int width, int height, List<? extends Obstacle> obstacles) {
        this.width = width;
        this.height = height;
        this.obstacles = List.copyOf(obstacles);
    }

    @Override
    public boolean isFree(GridPoint2 tile) {
        return isInside(tile) && obstacles.stream().noneMatch(obstacle -> obstacle.occupies(tile));
    }

    private boolean isInside(GridPoint2 tile) {
        return tile.x >= 0 && tile.x < width && tile.y >= 0 && tile.y < height;
    }
}
