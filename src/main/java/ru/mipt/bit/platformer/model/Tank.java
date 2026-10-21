package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Tank implements Obstacle {

    private static final float MOVEMENT_COMPLETED = 1f;

    private final float movementDuration;

    private final GridPoint2 coordinates;
    private final GridPoint2 destinationCoordinates;
    private float movementProgress = MOVEMENT_COMPLETED;
    private float rotation;

    public Tank(GridPoint2 coordinates, Direction direction, float movementDuration) {
        this.coordinates = new GridPoint2(coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.rotation = direction.getRotation();
        this.movementDuration = movementDuration;
    }

    public void move(Direction direction, CollisionDetector collisionDetector) {
        if (isMoving()) {
            return;
        }
        rotation = direction.getRotation();
        GridPoint2 nextCoordinates = direction.nextCoordinates(coordinates);
        if (collisionDetector.isFree(nextCoordinates)) {
            destinationCoordinates.set(nextCoordinates);
            movementProgress = 0f;
        }
    }

    public void update(float deltaTime) {
        movementProgress = clamp(movementProgress + deltaTime / movementDuration, 0f, MOVEMENT_COMPLETED);
        if (!isMoving()) {
            coordinates.set(destinationCoordinates);
        }
    }

    @Override
    public boolean occupies(GridPoint2 tile) {
        return coordinates.equals(tile) || destinationCoordinates.equals(tile);
    }

    public boolean isMoving() {
        return !isEqual(movementProgress, MOVEMENT_COMPLETED);
    }

    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }

    public GridPoint2 getDestinationCoordinates() {
        return new GridPoint2(destinationCoordinates);
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public float getRotation() {
        return rotation;
    }
}
