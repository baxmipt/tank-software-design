package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.model.Tank;

public class HealthBarGraphics implements ObjectGraphics {

    private static final float HEIGHT = 6f;
    private static final float MARGIN = 4f;

    private final TankGraphics decorated;
    private final Tank tank;
    private final HealthBars healthBars;
    private final Texture texture;

    public HealthBarGraphics(TankGraphics decorated, Tank tank, HealthBars healthBars) {
        this.decorated = decorated;
        this.tank = tank;
        this.healthBars = healthBars;
        texture = createTexture();
    }

    @Override
    public void render(Batch batch) {
        decorated.render(batch);
        if (!healthBars.isVisible()) {
            return;
        }
        float top = decorated.getTop() + MARGIN;
        batch.setColor(0.2f, 0.1f, 0.1f, 1f);
        batch.draw(texture, decorated.getLeft(), top, decorated.getWidth(), HEIGHT);
        batch.setColor(0.1f, 0.8f, 0.2f, 1f);
        batch.draw(texture, decorated.getLeft(), top, decorated.getWidth() * tank.getHealth() / tank.getInitialHealth(), HEIGHT);
        batch.setColor(1f, 1f, 1f, 1f);
    }

    @Override
    public void dispose() {
        decorated.dispose();
        texture.dispose();
    }

    private Texture createTexture() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1f, 1f, 1f, 1f);
        pixmap.fill();
        Texture result = new Texture(pixmap);
        pixmap.dispose();
        return result;
    }
}
