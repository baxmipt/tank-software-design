package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;

import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.LevelListener;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class GameGraphics implements Disposable, LevelListener {

    private final Batch batch;
    private final TiledMap levelMap;
    private final MapRenderer levelMapRenderer;
    private final Map<GameObject, ObjectGraphics> objects = new LinkedHashMap<>();
    private final Level level;
    private final ObjectGraphicsFactory objectGraphicsFactory;

    public GameGraphics(TiledMap levelMap, Level level, ObjectGraphicsFactory objectGraphicsFactory) {
        this.level = level;
        this.objectGraphicsFactory = objectGraphicsFactory;
        this.batch = new SpriteBatch();
        this.levelMap = levelMap;
        this.levelMapRenderer = createSingleLayerMapRenderer(levelMap, batch);
    }

    @Override
    public void objectAdded(GameObject object) {
        objects.put(object, objectGraphicsFactory.create(object));
    }

    @Override
    public void objectRemoved(GameObject object) {
        ObjectGraphics graphics = objects.remove(object);
        if (graphics != null) {
            graphics.dispose();
        }
    }

    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        levelMapRenderer.render();

        batch.begin();
        for (ObjectGraphics object : objects.values()) {
            object.render(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        level.unsubscribe(this);
        for (ObjectGraphics object : objects.values()) {
            object.dispose();
        }
        objects.clear();
        levelMap.dispose();
        batch.dispose();
    }
}
