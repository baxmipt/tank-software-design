package ru.mipt.bit.platformer.input;

import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.graphics.HealthBars;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ToggleHealthBarsCommandTest {

    @Test
    void shouldToggleHealthBarsVisibility() {
        HealthBars healthBars = new HealthBars();
        ToggleHealthBarsCommand command = new ToggleHealthBarsCommand(healthBars);

        command.execute();

        assertTrue(healthBars.isVisible());
        command.execute();
        assertFalse(healthBars.isVisible());
    }
}
