package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.input.Command;

/**
 * Application extension point for selecting an AI command.
 */
public interface MovementStrategy {

    Command nextCommand();
}
