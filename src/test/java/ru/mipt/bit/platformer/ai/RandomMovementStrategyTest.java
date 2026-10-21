package ru.mipt.bit.platformer.ai;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RandomMovementStrategyTest {

    private static final float MOVEMENT_DURATION = 0.4f;
    private static final GridPoint2 START = new GridPoint2(5, 5);

    private static class Game {
        final Tank player = new Tank(new GridPoint2(0, 0), Direction.RIGHT, MOVEMENT_DURATION);
        final Tank tank = new Tank(START, Direction.RIGHT, MOVEMENT_DURATION);
        final Level level = new Level(11, 11, player, List.of(tank), List.of());
    }

    @Test
    void shouldMoveTankByOneTile() {
        Game game = new Game();

        new RandomMovementStrategy(game.level, game.tank, new Random(1)).nextCommand().execute();
        game.level.update(MOVEMENT_DURATION);

        GridPoint2 shift = game.tank.getCoordinates().sub(START);
        assertEquals(1, Math.abs(shift.x) + Math.abs(shift.y));
    }

    @Test
    void shouldChooseAllDirections() {
        Set<GridPoint2> destinations = new HashSet<>();
        Random random = new Random(1);
        for (int i = 0; i < 50; i++) {
            Game game = new Game();
            new RandomMovementStrategy(game.level, game.tank, random).nextCommand().execute();
            destinations.add(game.tank.getDestinationCoordinates());
        }

        assertEquals(Set.of(
                new GridPoint2(5, 6),
                new GridPoint2(5, 4),
                new GridPoint2(4, 5),
                new GridPoint2(6, 5)), destinations);
    }

    @Test
    void shouldChooseSameDirectionsForSameSeed() {
        Game first = new Game();
        Game second = new Game();
        RandomMovementStrategy firstStrategy = new RandomMovementStrategy(first.level, first.tank, new Random(7));
        RandomMovementStrategy secondStrategy = new RandomMovementStrategy(second.level, second.tank, new Random(7));

        for (int i = 0; i < 20; i++) {
            firstStrategy.nextCommand().execute();
            first.level.update(MOVEMENT_DURATION);
            secondStrategy.nextCommand().execute();
            second.level.update(MOVEMENT_DURATION);

            assertEquals(first.tank.getCoordinates(), second.tank.getCoordinates());
        }
    }
}
