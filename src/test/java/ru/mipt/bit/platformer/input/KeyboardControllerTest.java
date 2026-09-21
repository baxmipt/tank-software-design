package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Input.Keys;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeyboardControllerTest {

    private final List<String> executed = new ArrayList<>();

    @Test
    void shouldExecuteCommandOfPressedKey() {
        KeyboardController controller = new KeyboardController(key -> key == Keys.UP);
        controller.bind(command("move"), Keys.UP);
        controller.bind(command("shoot"), Keys.SPACE);

        controller.handleInput();

        assertEquals(List.of("move"), executed);
    }

    @Test
    void shouldExecuteNothingWhenNoKeyIsPressed() {
        KeyboardController controller = new KeyboardController(key -> false);
        controller.bind(command("move"), Keys.UP);

        controller.handleInput();

        assertEquals(List.of(), executed);
    }

    @Test
    void shouldExecuteEveryCommandOfPressedKeys() {
        KeyboardController controller = new KeyboardController(key -> true);
        controller.bind(command("move"), Keys.UP);
        controller.bind(command("shoot"), Keys.SPACE);

        controller.handleInput();

        assertEquals(List.of("move", "shoot"), executed);
    }

    @Test
    void shouldExecuteSameCommandForEachBoundKey() {
        KeyboardController controller = new KeyboardController(key -> key == Keys.W);
        controller.bind(command("move"), Keys.UP, Keys.W);

        controller.handleInput();

        assertEquals(List.of("move"), executed);
    }

    private Command command(String name) {
        return () -> executed.add(name);
    }
}
