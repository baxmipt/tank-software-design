package ru.mipt.bit.platformer.ai;

import java.util.List;

public class AiController {

    private final List<MovementStrategy> strategies;

    public AiController(List<? extends MovementStrategy> strategies) {
        this.strategies = List.copyOf(strategies);
    }

    public void handleAi() {
        strategies.forEach(strategy -> strategy.nextCommand().execute());
    }
}
