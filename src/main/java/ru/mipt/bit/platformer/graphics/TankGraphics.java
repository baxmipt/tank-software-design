package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

public class TankGraphics extends ObjectGraphics {

    private final Tank tank;
    private final TileMovement tileMovement;

    public TankGraphics(Tank tank, TileMovement tileMovement, String texturePath) {
        super(texturePath);
        this.tank = tank;
        this.tileMovement = tileMovement;
    }

    @Override
    public void render(Batch batch) {
        // calculate interpolated tank screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(
                rectangle, tank.getCoordinates(), tank.getDestinationCoordinates(), tank.getMovementProgress());
        draw(batch, tank.getRotation());
    }
}
