package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class GameGraphics implements Disposable {

    private final Batch batch;
    private final TiledMap levelMap;
    private final MapRenderer levelMapRenderer;
    private final List<ObjectGraphics> objects = new ArrayList<>();

    public GameGraphics(TiledMap levelMap) {
        this.batch = new SpriteBatch();
        this.levelMap = levelMap;
        this.levelMapRenderer = createSingleLayerMapRenderer(levelMap, batch);
    }

    public void add(ObjectGraphics object) {
        objects.add(object);
    }

    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        levelMapRenderer.render();

        batch.begin();
        for (ObjectGraphics object : objects) {
            object.render(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        for (ObjectGraphics object : objects) {
            object.dispose();
        }
        levelMap.dispose();
        batch.dispose();
    }
}
