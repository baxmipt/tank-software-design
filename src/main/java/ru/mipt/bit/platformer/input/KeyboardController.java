package ru.mipt.bit.platformer.input;

import java.util.LinkedHashMap;
import java.util.Map;

public class KeyboardController {

    private final Keyboard keyboard;
    private final Map<Integer, Command> commands = new LinkedHashMap<>();

    public KeyboardController(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public void bind(Command command, int... keys) {
        for (int key : keys) {
            commands.put(key, command);
        }
    }

    public void handleInput() {
        for (Map.Entry<Integer, Command> binding : commands.entrySet()) {
            if (keyboard.isPressed(binding.getKey())) {
                binding.getValue().execute();
            }
        }
    }
}
