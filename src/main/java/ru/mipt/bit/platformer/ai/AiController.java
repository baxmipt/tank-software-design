package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.input.Command;

import java.util.List;

public class AiController {

    private final List<MovementStrategy> strategies;

    public AiController(List<? extends MovementStrategy> strategies) {
        this.strategies = List.copyOf(strategies);
    }

    public void handleAi() {
        getCommands().forEach(Command::execute);
    }

    public List<Command> getCommands() {
        return strategies.stream().map(MovementStrategy::nextCommand).toList();
    }
}
