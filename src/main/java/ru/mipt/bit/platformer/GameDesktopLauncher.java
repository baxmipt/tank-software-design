package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.input.GdxKeyboard;
import ru.mipt.bit.platformer.input.KeyboardController;
import ru.mipt.bit.platformer.input.PlayerControls;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.LevelFactory;

public class GameDesktopLauncher implements ApplicationListener {

    private Level level;
    private KeyboardController controller;
    private GameGraphics graphics;

    @Override
    public void create() {
        level = new LevelFactory().createDefaultLevel();
        controller = new PlayerControls(level).createController(new GdxKeyboard(Gdx.input));
        graphics = new GameGraphics(level);
    }

    @Override
    public void render() {
        controller.handleInput();
        // get time passed since the last render
        level.update(Gdx.graphics.getDeltaTime());
        graphics.render();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        graphics.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
