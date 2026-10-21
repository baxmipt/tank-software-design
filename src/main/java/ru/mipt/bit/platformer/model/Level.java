package ru.mipt.bit.platformer.model;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private final Tank playerTank;
    private final List<Tank> aiTanks;
    private final List<Tree> trees;
    private final GameField field;

    public Level(int width, int height, Tank playerTank, List<Tank> aiTanks, List<Tree> trees) {
        this.playerTank = playerTank;
        this.aiTanks = List.copyOf(aiTanks);
        this.trees = List.copyOf(trees);

        List<Obstacle> obstacles = new ArrayList<>(this.trees);
        obstacles.add(playerTank);
        obstacles.addAll(this.aiTanks);
        this.field = new GameField(width, height, obstacles);
    }

    public void move(Tank tank, Direction direction) {
        tank.move(direction, field);
    }

    public void update(float deltaTime) {
        playerTank.update(deltaTime);
        aiTanks.forEach(tank -> tank.update(deltaTime));
    }

    public Tank getPlayerTank() {
        return playerTank;
    }

    public List<Tank> getAiTanks() {
        return aiTanks;
    }

    public List<Tree> getTrees() {
        return trees;
    }
}
