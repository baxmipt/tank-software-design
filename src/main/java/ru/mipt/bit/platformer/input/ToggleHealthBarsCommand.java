package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.graphics.HealthBars;

public class ToggleHealthBarsCommand implements Command {

    private final HealthBars healthBars;

    public ToggleHealthBarsCommand(HealthBars healthBars) {
        this.healthBars = healthBars;
    }

    @Override
    public void execute() {
        healthBars.toggle();
    }
}
