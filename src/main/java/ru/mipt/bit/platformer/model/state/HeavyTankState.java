package ru.mipt.bit.platformer.model.state;

public class HeavyTankState implements TankState {

    @Override
    public float movementDuration(float defaultDuration) {
        return defaultDuration * 3f;
    }

    @Override
    public float shotInterval(float defaultInterval) {
        return defaultInterval;
    }

    @Override
    public boolean canShoot() {
        return false;
    }
}
