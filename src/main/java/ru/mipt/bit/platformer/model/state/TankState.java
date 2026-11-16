package ru.mipt.bit.platformer.model.state;

/**
 * Domain extension point for a tank state.
 */
public interface TankState {

    float movementDuration(float defaultDuration);

    float shotInterval(float defaultInterval);

    boolean canShoot();
}
