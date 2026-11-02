package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class LevelGraphicsFactory {

    private final String levelMapPath;
    private final String tankTexturePath;
    private final String treeTexturePath;

    public LevelGraphicsFactory(String levelMapPath, String tankTexturePath, String treeTexturePath) {
        this.levelMapPath = levelMapPath;
        this.tankTexturePath = tankTexturePath;
        this.treeTexturePath = treeTexturePath;
    }

    public GameGraphics create(Level level) {
        TiledMap levelMap = new TmxMapLoader().load(levelMapPath);
        TiledMapTileLayer groundLayer = getSingleLayer(levelMap);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        ObjectGraphicsFactory objectGraphicsFactory = new ObjectGraphicsFactory(
                tileMovement, groundLayer, tankTexturePath, treeTexturePath);
        GameGraphics graphics = new GameGraphics(levelMap, level, objectGraphicsFactory);
        level.subscribe(graphics);
        return graphics;
    }
}
