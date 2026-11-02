package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShootCommandTest {

    @Test
    void shouldShootWhenCommandIsExecuted() {
        Tank tank = new Tank(new GridPoint2(0, 0), Direction.RIGHT, 0.4f);
        Level level = new Level(5, 5, tank, List.of(), List.of());

        new ShootCommand(level, tank).execute();

        assertEquals(new GridPoint2(1, 0), level.getBullets().get(0).getCoordinates());
    }

    @Test
    void shouldCollectSpaceCommandBeforeExecution() {
        Tank tank = new Tank(new GridPoint2(0, 0), Direction.RIGHT, 0.4f);
        Level level = new Level(5, 5, tank, List.of(), List.of());
        KeyboardController controller = new PlayerControls(level).createController(key -> key == Keys.SPACE);

        List<Command> commands = controller.getCommands();

        assertTrue(level.getBullets().isEmpty());
        assertEquals(1, commands.size());
        commands.forEach(Command::execute);
        assertEquals(1, level.getBullets().size());
    }
}
