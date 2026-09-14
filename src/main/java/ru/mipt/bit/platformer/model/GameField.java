package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class GameField {

    private final List<Tree> trees;

    public GameField(List<Tree> trees) {
        this.trees = List.copyOf(trees);
    }

    public boolean isFree(GridPoint2 tile) {
        return trees.stream().noneMatch(tree -> tree.occupies(tile));
    }
}
