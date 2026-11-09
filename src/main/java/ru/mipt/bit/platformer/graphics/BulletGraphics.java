package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.model.Bullet;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class BulletGraphics extends TexturedObjectGraphics {

    private final Bullet bullet;
    private final TiledMapTileLayer groundLayer;

    public BulletGraphics(Bullet bullet, TiledMapTileLayer groundLayer) {
        super(createTexture());
        this.bullet = bullet;
        this.groundLayer = groundLayer;
    }

    private static Texture createTexture() {
        Pixmap pixmap = new Pixmap(12, 12, Pixmap.Format.RGBA8888);
        pixmap.setColor(1f, 0.8f, 0.1f, 1f);
        pixmap.fillCircle(6, 6, 5);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    @Override
    public void render(Batch batch) {
        moveRectangleAtTileCenter(groundLayer, rectangle, bullet.getCoordinates());
        draw(batch, 0f);
    }
}
