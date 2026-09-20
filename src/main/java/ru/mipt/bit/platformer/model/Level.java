package ru.mipt.bit.platformer.model;

import java.util.List;

public class Level {

    private final Tank playerTank;
    private final List<Tree> trees;
    private final GameField field;

    public Level(Tank playerTank, List<Tree> trees) {
        this.playerTank = playerTank;
        this.trees = List.copyOf(trees);
        this.field = new GameField(this.trees);
    }

    public void movePlayerTank(Direction direction) {
        playerTank.move(direction, field);
    }

    public void update(float deltaTime) {
        playerTank.update(deltaTime);
    }

    public Tank getPlayerTank() {
        return playerTank;
    }

    public List<Tree> getTrees() {
        return trees;
    }
}
