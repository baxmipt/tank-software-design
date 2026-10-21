package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileLevelFactoryTest {

    private static final float MOVEMENT_DURATION = 0.4f;

    @TempDir
    Path directory;

    private Level createLevel(String... rows) throws IOException {
        Path file = directory.resolve("level.txt");
        Files.write(file, List.of(rows));
        return new FileLevelFactory(file, MOVEMENT_DURATION).create();
    }

    @Test
    void shouldPlacePlayerTankOnX() throws IOException {
        Level level = createLevel(
                "___",
                "_X_",
                "___");

        assertEquals(new GridPoint2(1, 1), level.getPlayerTank().getCoordinates());
    }

    @Test
    void shouldPlaceTreesOnT() throws IOException {
        Level level = createLevel(
                "T_T",
                "_X_",
                "__T");

        assertEquals(3, level.getTrees().size());
        assertTrue(level.getTrees().stream().anyMatch(tree -> tree.occupies(new GridPoint2(0, 2))));
        assertTrue(level.getTrees().stream().anyMatch(tree -> tree.occupies(new GridPoint2(2, 2))));
        assertTrue(level.getTrees().stream().anyMatch(tree -> tree.occupies(new GridPoint2(2, 0))));
    }

    @Test
    void shouldCountRowsFromBottom() throws IOException {
        Level level = createLevel(
                "X_",
                "__",
                "_T");

        assertEquals(new GridPoint2(0, 2), level.getPlayerTank().getCoordinates());
        assertTrue(level.getTrees().get(0).occupies(new GridPoint2(1, 0)));
    }

    @Test
    void shouldReadLevelFromAssignment() throws IOException {
        Level level = createLevel(
                "___T__T___",
                "__TT__TTTT",
                "_________T",
                "TTTT__T__T",
                "_____X____",
                "__________");

        assertEquals(new GridPoint2(5, 1), level.getPlayerTank().getCoordinates());
        assertEquals(15, level.getTrees().size());
    }

    @Test
    void shouldCreateLevelWithoutAiTanks() throws IOException {
        Level level = createLevel("_X");

        assertTrue(level.getAiTanks().isEmpty());
    }

    @Test
    void shouldKeepPlayerTankInsideLevel() throws IOException {
        Level level = createLevel(
                "X_",
                "__");
        Tank tank = level.getPlayerTank();

        level.move(tank, Direction.LEFT);
        level.update(MOVEMENT_DURATION);
        level.move(tank, Direction.UP);
        level.update(MOVEMENT_DURATION);
        level.move(tank, Direction.UP);
        level.update(MOVEMENT_DURATION);

        assertEquals(new GridPoint2(0, 1), tank.getCoordinates());
    }

    @Test
    void shouldCreateLevelWithoutTrees() throws IOException {
        Level level = createLevel("_X");

        assertTrue(level.getTrees().isEmpty());
    }

    @Test
    void shouldNotAcceptLevelWithoutPlayerTank() {
        assertThrows(IllegalArgumentException.class, () -> createLevel("T_", "__"));
    }

    @Test
    void shouldNotAcceptLevelWithTwoPlayerTanks() {
        assertThrows(IllegalArgumentException.class, () -> createLevel("X_", "_X"));
    }

    @Test
    void shouldNotAcceptUnknownSymbol() {
        assertThrows(IllegalArgumentException.class, () -> createLevel("X_", "_?"));
    }

    @Test
    void shouldNotAcceptRowsOfDifferentLength() {
        assertThrows(IllegalArgumentException.class, () -> createLevel("X__", "__"));
    }

    @Test
    void shouldNotAcceptMissingFile() {
        FileLevelFactory factory = new FileLevelFactory(directory.resolve("missing.txt"), MOVEMENT_DURATION);

        assertThrows(UncheckedIOException.class, factory::create);
    }
}
