package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

public class ObjectGraphicsFactory {

    private final TileMovement tileMovement;
    private final TiledMapTileLayer groundLayer;
    private final String tankTexturePath;
    private final String treeTexturePath;
    private final HealthBars healthBars;

    public ObjectGraphicsFactory(TileMovement tileMovement, TiledMapTileLayer groundLayer,
                                 String tankTexturePath, String treeTexturePath, HealthBars healthBars) {
        this.tileMovement = tileMovement;
        this.groundLayer = groundLayer;
        this.tankTexturePath = tankTexturePath;
        this.treeTexturePath = treeTexturePath;
        this.healthBars = healthBars;
    }

    public ObjectGraphics create(GameObject object) {
        if (object instanceof Tank tank) {
            TankGraphics graphics = new TankGraphics(tank, tileMovement, tankTexturePath);
            return new HealthBarGraphics(graphics, tank, healthBars);
        }
        if (object instanceof Tree tree) {
            return new TreeGraphics(tree, groundLayer, treeTexturePath);
        }
        if (object instanceof Bullet bullet) {
            return new BulletGraphics(bullet, groundLayer);
        }
        throw new IllegalArgumentException("Unknown game object: " + object.getClass());
    }
}
