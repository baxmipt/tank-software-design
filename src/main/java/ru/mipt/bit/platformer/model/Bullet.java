package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class Bullet implements GameObject {

    private final GridPoint2 coordinates;
    private final Direction direction;
    private final float movementDuration;
    private final int damage;
    private final Tank owner;
    private float elapsedTime;

    public Bullet(GridPoint2 coordinates, Direction direction, float movementDuration, int damage, Tank owner) {
        if (movementDuration <= 0f || damage < 0) {
            throw new IllegalArgumentException("Invalid bullet settings");
        }
        this.coordinates = new GridPoint2(coordinates);
        this.direction = direction;
        this.movementDuration = movementDuration;
        this.damage = damage;
        this.owner = owner;
    }

    @Override
    public void update(float deltaTime, Level level) {
        if (level.collide(this)) {
            return;
        }
        elapsedTime += deltaTime;
        while (elapsedTime >= movementDuration && level.contains(this)) {
            elapsedTime -= movementDuration;
            coordinates.set(direction.nextCoordinates(coordinates));
            if (level.collide(this)) {
                return;
            }
        }
    }

    @Override
    public void hit(int damage, Level level) {
        level.remove(this);
    }

    @Override
    public boolean occupies(GridPoint2 tile) {
        return coordinates.equals(tile);
    }

    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }

    public Tank getOwner() {
        return owner;
    }

    public int getDamage() {
        return damage;
    }
}
