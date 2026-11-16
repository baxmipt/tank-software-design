package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

/**
 * Domain port for checking whether a tile can be occupied.
 */
public interface CollisionDetector {

    boolean isFree(GridPoint2 tile);
}
