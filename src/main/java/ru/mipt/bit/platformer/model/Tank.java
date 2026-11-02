package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Tank implements GameObject {

    private static final float MOVEMENT_COMPLETED = 1f;

    private static final int INITIAL_HEALTH = 3;
    private static final int BULLET_DAMAGE = 1;
    private static final float BULLET_MOVEMENT_DURATION = 0.1f;
    private static final float SHOT_INTERVAL = 0.4f;

    private final float movementDuration;
    private Direction direction;
    private int health;
    private float shotCooldown;

    private final GridPoint2 coordinates;
    private final GridPoint2 destinationCoordinates;
    private float movementProgress = MOVEMENT_COMPLETED;
    private float rotation;

    public Tank(GridPoint2 coordinates, Direction direction, float movementDuration) {
        this(coordinates, direction, movementDuration, INITIAL_HEALTH);
    }

    public Tank(GridPoint2 coordinates, Direction direction, float movementDuration, int health) {
        this.direction = direction;
        this.health = health;
        this.coordinates = new GridPoint2(coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.rotation = direction.getRotation();
        this.movementDuration = movementDuration;
    }

    public void move(Direction direction, CollisionDetector collisionDetector) {
        if (isMoving()) {
            return;
        }
        this.direction = direction;
        rotation = direction.getRotation();
        GridPoint2 nextCoordinates = direction.nextCoordinates(coordinates);
        if (collisionDetector.isFree(nextCoordinates)) {
            destinationCoordinates.set(nextCoordinates);
            movementProgress = 0f;
        }
    }

    public void shoot(Level level) {
        if (shotCooldown > 0f) {
            return;
        }
        shotCooldown = SHOT_INTERVAL;
        Bullet bullet = new Bullet(direction.nextCoordinates(coordinates), direction,
                BULLET_MOVEMENT_DURATION, BULLET_DAMAGE, this);
        level.add(bullet);
        level.collide(bullet);
    }

    @Override
    public void hit(int damage, Level level) {
        health -= damage;
        if (health < 0) {
            level.remove(this);
        }
    }

    public int getHealth() {
        return health;
    }

    @Override
    public void update(float deltaTime, Level level) {
        update(deltaTime);
    }

    public void update(float deltaTime) {
        shotCooldown = Math.max(0f, shotCooldown - deltaTime);
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
