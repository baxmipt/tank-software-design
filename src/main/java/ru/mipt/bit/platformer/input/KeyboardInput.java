package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import ru.mipt.bit.platformer.model.Direction;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class KeyboardInput {

    private final Input input;
    private final Map<Integer, Direction> keyBindings = new LinkedHashMap<>();

    public KeyboardInput(Input input) {
        this.input = input;
        bind(Direction.UP, Keys.UP, Keys.W);
        bind(Direction.LEFT, Keys.LEFT, Keys.A);
        bind(Direction.DOWN, Keys.DOWN, Keys.S);
        bind(Direction.RIGHT, Keys.RIGHT, Keys.D);
    }

    public Optional<Direction> pressedDirection() {
        for (Map.Entry<Integer, Direction> binding : keyBindings.entrySet()) {
            if (input.isKeyPressed(binding.getKey())) {
                return Optional.of(binding.getValue());
            }
        }
        return Optional.empty();
    }

    private void bind(Direction direction, int... keys) {
        for (int key : keys) {
            keyBindings.put(key, direction);
        }
    }
}
