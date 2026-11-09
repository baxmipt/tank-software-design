package ru.mipt.bit.platformer.graphics;

public class HealthBars {

    private boolean visible;

    public void toggle() {
        visible = !visible;
    }

    public boolean isVisible() {
        return visible;
    }
}
