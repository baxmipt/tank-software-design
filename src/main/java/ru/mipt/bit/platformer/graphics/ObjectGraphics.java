package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;

/**
 * Infrastructure rendering abstraction for a domain object.
 */
public interface ObjectGraphics extends Disposable {

    void render(Batch batch);
}
