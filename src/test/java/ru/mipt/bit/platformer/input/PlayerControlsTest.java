package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PlayerControlsTest {

    private static final float MOVEMENT_DURATION = 0.4f;

    static Stream<Arguments> keysAndDestinations() {
        return Stream.of(
                arguments(Keys.UP, new GridPoint2(1, 2)),
                arguments(Keys.W, new GridPoint2(1, 2)),
                arguments(Keys.DOWN, new GridPoint2(1, 0)),
                arguments(Keys.S, new GridPoint2(1, 0)),
                arguments(Keys.LEFT, new GridPoint2(0, 1)),
                arguments(Keys.A, new GridPoint2(0, 1)),
                arguments(Keys.RIGHT, new GridPoint2(2, 1)),
                arguments(Keys.D, new GridPoint2(2, 1))
        );
    }

    @ParameterizedTest
    @MethodSource("keysAndDestinations")
    void shouldMovePlayerTankWithPressedKey(int pressedKey, GridPoint2 expectedCoordinates) {
        Tank tank = new Tank(new GridPoint2(1, 1), Direction.RIGHT, MOVEMENT_DURATION);
        Level level = new Level(tank, List.of());
        KeyboardController controller = new PlayerControls(level).createController(key -> key == pressedKey);

        controller.handleInput();
        level.update(MOVEMENT_DURATION);

        assertEquals(expectedCoordinates, tank.getCoordinates());
    }
}
