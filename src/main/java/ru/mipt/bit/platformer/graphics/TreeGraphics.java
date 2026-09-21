package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.model.Tree;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TreeGraphics extends ObjectGraphics {

    public TreeGraphics(Tree tree, TiledMapTileLayer groundLayer, String texturePath) {
        super(texturePath);
        moveRectangleAtTileCenter(groundLayer, rectangle, tree.getCoordinates());
    }

    @Override
    public void render(Batch batch) {
        draw(batch, 0f);
    }
}
