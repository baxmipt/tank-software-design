package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileLevelFactory implements LevelFactory {

    private static final char TREE = 'T';
    private static final char PLAYER_TANK = 'X';
    private static final char EMPTY = '_';

    private final Path path;
    private final float tankMovementDuration;

    public FileLevelFactory(Path path, float tankMovementDuration) {
        this.path = path;
        this.tankMovementDuration = tankMovementDuration;
    }

    @Override
    public Level create() {
        List<String> rows = readRows();
        Tank playerTank = null;
        List<Tree> trees = new ArrayList<>();

        for (int row = 0; row < rows.size(); row++) {
            String line = rows.get(row);
            if (line.length() != rows.get(0).length()) {
                throw new IllegalArgumentException("Row " + (row + 1) + " has different length than the first row");
            }
            for (int x = 0; x < line.length(); x++) {
                GridPoint2 tile = new GridPoint2(x, rows.size() - 1 - row);
                char symbol = line.charAt(x);
                if (symbol == TREE) {
                    trees.add(new Tree(tile));
                } else if (symbol == PLAYER_TANK) {
                    if (playerTank != null) {
                        throw new IllegalArgumentException("Level has more than one player tank");
                    }
                    playerTank = new Tank(tile, Direction.RIGHT, tankMovementDuration);
                } else if (symbol != EMPTY) {
                    throw new IllegalArgumentException("Unknown symbol '" + symbol + "' in row " + (row + 1));
                }
            }
        }

        if (playerTank == null) {
            throw new IllegalArgumentException("Level has no player tank");
        }
        return new Level(rows.get(0).length(), rows.size(), playerTank, List.of(), trees);
    }

    private List<String> readRows() {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read level from " + path, e);
        }
    }
}
