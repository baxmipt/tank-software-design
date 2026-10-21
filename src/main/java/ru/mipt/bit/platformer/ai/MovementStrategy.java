package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.input.Command;

public interface MovementStrategy {

    Command nextCommand();
}
