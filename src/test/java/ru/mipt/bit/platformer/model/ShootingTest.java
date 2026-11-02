package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShootingTest {

    private final Tank player = new Tank(new GridPoint2(0, 0), Direction.RIGHT, 0.4f);

    private Level createLevel(Tank enemy, List<Tree> trees) {
        return new Level(8, 3, player, enemy == null ? List.of() : List.of(enemy), trees);
    }

    @Test
    void shouldCreateBulletInFrontOfTank() {
        Level level = createLevel(null, List.of());

        level.shoot(player);

        assertEquals(1, level.getBullets().size());
        assertEquals(new GridPoint2(1, 0), level.getBullets().get(0).getCoordinates());
        assertEquals(player, level.getBullets().get(0).getOwner());
    }

    @Test
    void shouldShootInCurrentDirectionAfterBlockedMove() {
        Level level = createLevel(null, List.of(new Tree(new GridPoint2(1, 0))));
        level.move(player, Direction.UP);
        level.update(0.4f);
        level.move(player, Direction.LEFT);

        level.shoot(player);

        assertTrue(level.getBullets().isEmpty());
    }

    @Test
    void shouldLimitShootingRate() {
        Level level = createLevel(null, List.of());
        level.shoot(player);
        level.shoot(player);

        assertEquals(1, level.getBullets().size());
        level.update(0.4f);
        level.shoot(player);
        assertEquals(2, level.getBullets().size());
    }

    @Test
    void shouldFlyInShotDirectionAfterTankTurns() {
        Level level = createLevel(null, List.of());
        level.shoot(player);
        level.move(player, Direction.UP);

        level.update(0.1f);

        assertEquals(new GridPoint2(2, 0), level.getBullets().get(0).getCoordinates());
    }

    @Test
    void shouldAccumulateTimeBeforeMoving() {
        Level level = createLevel(null, List.of());
        level.shoot(player);

        level.update(0.04f);
        assertEquals(new GridPoint2(1, 0), level.getBullets().get(0).getCoordinates());
        level.update(0.07f);
        assertEquals(new GridPoint2(2, 0), level.getBullets().get(0).getCoordinates());
    }

    @Test
    void shouldHitTreeWithoutSkippingTilesDuringLongTick() {
        Tree tree = new Tree(new GridPoint2(3, 0));
        Level level = createLevel(null, List.of(tree));
        level.shoot(player);

        level.update(1f);

        assertTrue(level.getBullets().isEmpty());
        assertEquals(List.of(tree), level.getTrees());
    }

    @Test
    void shouldDisappearAtBoundary() {
        Level level = createLevel(null, List.of());
        level.shoot(player);

        level.update(1f);

        assertTrue(level.getBullets().isEmpty());
    }

    @Test
    void shouldHitAdjacentTankImmediately() {
        Tank enemy = new Tank(new GridPoint2(1, 0), Direction.LEFT, 0.4f, 2);
        Level level = createLevel(enemy, List.of());

        level.shoot(player);

        assertEquals(1, enemy.getHealth());
        assertTrue(level.getBullets().isEmpty());
        assertTrue(level.contains(enemy));
    }

    @Test
    void shouldRemoveTankOnlyWhenHealthIsNegative() {
        Tank enemy = new Tank(new GridPoint2(2, 0), Direction.LEFT, 0.4f, 1);
        Level level = createLevel(enemy, List.of());
        level.shoot(player);
        level.update(0.4f);
        assertEquals(0, enemy.getHealth());
        assertTrue(level.contains(enemy));

        level.shoot(player);
        level.update(0.1f);

        assertFalse(level.contains(enemy));
        assertTrue(level.getAiTanks().isEmpty());
        assertTrue(level.getBullets().isEmpty());
        level.move(player, Direction.RIGHT);
        level.update(0.4f);
        level.move(player, Direction.RIGHT);
        level.update(0.4f);
        assertEquals(new GridPoint2(2, 0), player.getCoordinates());
    }

    @Test
    void shouldApplyConfiguredBulletDamage() {
        Tank enemy = new Tank(new GridPoint2(2, 0), Direction.LEFT, 0.4f, 10);
        Level level = createLevel(enemy, List.of());
        level.add(new Bullet(new GridPoint2(1, 0), Direction.RIGHT, 0.1f, 4, player));

        level.update(0.1f);

        assertEquals(6, enemy.getHealth());
        assertTrue(level.getBullets().isEmpty());
    }

    @Test
    void shouldDestroyBothBulletsWhenTheyMeet() {
        Tank enemy = new Tank(new GridPoint2(4, 0), Direction.LEFT, 0.4f);
        Level level = createLevel(enemy, List.of());
        level.shoot(player);
        level.shoot(enemy);

        level.update(0.1f);

        assertTrue(level.getBullets().isEmpty());
        assertEquals(3, player.getHealth());
        assertEquals(3, enemy.getHealth());
    }

    @Test
    void shouldDestroyBulletsSpawnedInSameTile() {
        Tank enemy = new Tank(new GridPoint2(2, 0), Direction.LEFT, 0.4f);
        Level level = createLevel(enemy, List.of());
        level.shoot(player);

        level.shoot(enemy);

        assertTrue(level.getBullets().isEmpty());
    }

    @Test
    void shouldDamageTankMovingIntoBulletTile() {
        Tank enemy = new Tank(new GridPoint2(2, 1), Direction.DOWN, 0.4f);
        Level level = createLevel(enemy, List.of());
        level.shoot(player);
        level.move(enemy, Direction.DOWN);

        level.update(0.1f);

        assertEquals(2, enemy.getHealth());
        assertTrue(level.getBullets().isEmpty());
    }

    @Test
    void shouldIgnoreCommandsForRemovedTank() {
        Level level = createLevel(null, List.of());
        level.remove(player);

        level.shoot(player);
        level.move(player, Direction.RIGHT);
        level.update(1f);

        assertTrue(level.getBullets().isEmpty());
        assertEquals(new GridPoint2(0, 0), player.getCoordinates());
    }

    @Test
    void shouldNotifyAboutInitialObjectsAndChanges() {
        Tree tree = new Tree(new GridPoint2(4, 1));
        Level level = createLevel(null, List.of(tree));
        List<GameObject> added = new ArrayList<>();
        List<GameObject> removed = new ArrayList<>();
        LevelListener listener = new LevelListener() {
            @Override
            public void objectAdded(GameObject object) {
                added.add(object);
            }

            @Override
            public void objectRemoved(GameObject object) {
                removed.add(object);
            }
        };
        level.subscribe(listener);
        level.subscribe(listener);
        assertEquals(List.of(player, tree), added);
        level.shoot(player);
        Bullet bullet = level.getBullets().get(0);
        assertEquals(List.of(player, tree, bullet), added);
        level.update(1f);
        assertEquals(List.of(bullet), removed);
        level.remove(bullet);
        assertEquals(List.of(bullet), removed);
        level.unsubscribe(listener);
        level.remove(player);
        assertEquals(List.of(bullet), removed);
    }
}
