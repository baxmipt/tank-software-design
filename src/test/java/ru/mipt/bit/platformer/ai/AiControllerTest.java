package ru.mipt.bit.platformer.ai;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AiControllerTest {

    private final List<String> executed = new ArrayList<>();

    private MovementStrategy strategy(String name) {
        return () -> () -> executed.add(name);
    }

    @Test
    void shouldExecuteCommandOfEveryStrategy() {
        AiController controller = new AiController(List.of(strategy("first"), strategy("second")));

        controller.handleAi();

        assertEquals(List.of("first", "second"), executed);
    }

    @Test
    void shouldAskStrategiesForNewCommandEachTime() {
        AiController controller = new AiController(List.of(strategy("first")));

        controller.handleAi();
        controller.handleAi();

        assertEquals(List.of("first", "first"), executed);
    }

    @Test
    void shouldDoNothingWithoutStrategies() {
        new AiController(List.of()).handleAi();

        assertEquals(List.of(), executed);
    }
}
