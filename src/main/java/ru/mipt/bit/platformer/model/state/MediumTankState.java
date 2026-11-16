package ru.mipt.bit.platformer.model.state;

public class MediumTankState implements TankState {

    @Override
    public float movementDuration(float defaultDuration) {
        return defaultDuration * 2f;
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
