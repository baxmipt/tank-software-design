package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank {

    private static final float MOVEMENT_COMPLETED = 1f;

    private final float movementDuration;

    // tank current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private final GridPoint2 coordinates;
    // which tile the tank wants to go next
    private final GridPoint2 destinationCoordinates;
    private float movementProgress = MOVEMENT_COMPLETED;
    private float rotation;

    public Tank(GridPoint2 coordinates, Direction direction, float movementDuration) {
        this.coordinates = new GridPoint2(coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.rotation = direction.getRotation();
        this.movementDuration = movementDuration;
    }

    public void move(Direction direction, GameField field) {
        if (isMoving()) {
            return;
        }
        rotation = direction.getRotation();
        GridPoint2 nextCoordinates = direction.nextCoordinates(coordinates);
        if (field.isFree(nextCoordinates)) {
            destinationCoordinates.set(nextCoordinates);
            movementProgress = 0f;
        }
    }

    public void update(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementDuration);
        if (!isMoving()) {
            // record that the tank has reached its destination
            coordinates.set(destinationCoordinates);
        }
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
