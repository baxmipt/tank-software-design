package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameGraphics implements Disposable {

    private static final String LEVEL_MAP_PATH = "level.tmx";
    private static final String TANK_TEXTURE_PATH = "images/tank_blue.png";
    private static final String TREE_TEXTURE_PATH = "images/greenTree.png";

    private final Batch batch;
    private final TiledMap levelMap;
    private final MapRenderer levelMapRenderer;
    private final List<ObjectGraphics> objects = new ArrayList<>();

    public GameGraphics(Level level) {
        batch = new SpriteBatch();

        // load level tiles
        levelMap = new TmxMapLoader().load(LEVEL_MAP_PATH);
        levelMapRenderer = createSingleLayerMapRenderer(levelMap, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(levelMap);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        objects.add(new TankGraphics(level.getPlayerTank(), tileMovement, TANK_TEXTURE_PATH));
        for (Tree tree : level.getTrees()) {
            objects.add(new TreeGraphics(tree, groundLayer, TREE_TEXTURE_PATH));
        }
    }

    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // render each tile of the level
        levelMapRenderer.render();

        // start recording all drawing commands
        batch.begin();
        for (ObjectGraphics object : objects) {
            object.render(batch);
        }
        // submit all drawing requests
        batch.end();
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        for (ObjectGraphics object : objects) {
            object.dispose();
        }
        levelMap.dispose();
        batch.dispose();
    }
}
