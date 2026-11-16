package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

/**
 * Domain abstraction for an object that occupies tiles.
 */
public interface Obstacle {

    boolean occupies(GridPoint2 tile);
}
