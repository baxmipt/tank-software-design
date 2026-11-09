package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private final int width;
    private final int height;
    private final Tank playerTank;
    private final List<GameObject> objects = new ArrayList<>();
    private final List<LevelListener> listeners = new ArrayList<>();

    public Level(int width, int height, Tank playerTank, List<Tank> aiTanks,
                 List<? extends GameObject> obstacles) {
        this.width = width;
        this.height = height;
        this.playerTank = playerTank;
        objects.add(playerTank);
        objects.addAll(aiTanks);
        objects.addAll(obstacles);
    }

    public void move(Tank tank, Direction direction) {
        if (contains(tank)) {
            tank.move(direction, new GameField(width, height, objects.stream()
                    .filter(object -> !(object instanceof Bullet)).toList()));
        }
    }

    public void shoot(Tank tank) {
        if (contains(tank)) {
            tank.shoot(this);
        }
    }

    public void add(GameObject object) {
        if (!contains(object)) {
            objects.add(object);
            List.copyOf(listeners).forEach(listener -> listener.objectAdded(object));
        }
    }

    public void remove(GameObject object) {
        if (objects.remove(object)) {
            List.copyOf(listeners).forEach(listener -> listener.objectRemoved(object));
        }
    }

    public void subscribe(LevelListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
            List.copyOf(objects).forEach(listener::objectAdded);
        }
    }

    public void unsubscribe(LevelListener listener) {
        listeners.remove(listener);
    }

    public boolean contains(GameObject object) {
        return objects.contains(object);
    }

    public boolean collide(Bullet bullet) {
        GridPoint2 tile = bullet.getCoordinates();
        if (tile.x < 0 || tile.x >= width || tile.y < 0 || tile.y >= height) {
            remove(bullet);
            return true;
        }
        List<GameObject> targets = objects.stream()
                .filter(object -> object != bullet && object != bullet.getOwner() && object.occupies(tile))
                .toList();
        if (targets.isEmpty()) {
            return false;
        }
        remove(bullet);
        targets.forEach(target -> target.hit(bullet.getDamage(), this));
        return true;
    }

    public void update(float deltaTime) {
        for (GameObject object : List.copyOf(objects)) {
            if (contains(object)) {
                object.update(deltaTime, this);
            }
        }
    }

    public Tank getPlayerTank() {
        return playerTank;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Tank> getAiTanks() {
        return objects.stream().filter(object -> object instanceof Tank && object != playerTank)
                .map(object -> (Tank) object).toList();
    }

    public List<Tree> getTrees() {
        return objects.stream().filter(Tree.class::isInstance).map(Tree.class::cast).toList();
    }

    public List<Bullet> getBullets() {
        return objects.stream().filter(Bullet.class::isInstance).map(Bullet.class::cast).toList();
    }
}
