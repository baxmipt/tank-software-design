package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class Tree implements GameObject {

    private final GridPoint2 coordinates;

    public Tree(GridPoint2 coordinates) {
        this.coordinates = new GridPoint2(coordinates);
    }

    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }

    @Override
    public void update(float deltaTime, Level level) {
    }

    @Override
    public boolean occupies(GridPoint2 tile) {
        return coordinates.equals(tile);
    }
}
