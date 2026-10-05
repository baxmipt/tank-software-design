package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class GameField implements CollisionDetector {

    private final List<Obstacle> obstacles;

    public GameField(List<? extends Obstacle> obstacles) {
        this.obstacles = List.copyOf(obstacles);
    }

    @Override
    public boolean isFree(GridPoint2 tile) {
        return obstacles.stream().noneMatch(obstacle -> obstacle.occupies(tile));
    }
}
