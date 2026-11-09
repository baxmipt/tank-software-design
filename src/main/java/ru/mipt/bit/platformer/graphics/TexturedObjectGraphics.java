package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public abstract class TexturedObjectGraphics implements ObjectGraphics {

    private final Texture texture;
    private final TextureRegion graphics;

    protected final Rectangle rectangle;

    protected TexturedObjectGraphics(String texturePath) {
        this(new Texture(texturePath));
    }

    protected TexturedObjectGraphics(Texture texture) {
        this.texture = texture;
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
    }

    protected void draw(Batch batch, float rotation) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
