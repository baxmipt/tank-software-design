package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.model.Tree;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TreeGraphics implements Disposable {

    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;

    public TreeGraphics(Tree tree, TiledMapTileLayer groundLayer, String texturePath) {
        this.texture = new Texture(texturePath);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
        moveRectangleAtTileCenter(groundLayer, rectangle, tree.getCoordinates());
    }

    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, 0f);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
