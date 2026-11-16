package ru.mipt.bit.platformer.model.state;

public class LightTankState implements TankState {

    @Override
    public float movementDuration(float defaultDuration) {
        return defaultDuration;
    }

    @Override
    public float shotInterval(float defaultInterval) {
        return defaultInterval;
    }

    @Override
    public boolean canShoot() {
        return true;
    }
}
