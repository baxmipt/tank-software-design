package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.graphics.LevelGraphicsFactory;
import ru.mipt.bit.platformer.input.GdxKeyboard;
import ru.mipt.bit.platformer.input.KeyboardController;
import ru.mipt.bit.platformer.input.PlayerControls;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.LevelFactory;

public class GameDesktopLauncher implements ApplicationListener {

    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 1024;
    private static final String LEVEL_MAP_PATH = "level.tmx";
    private static final String TANK_TEXTURE_PATH = "images/tank_blue.png";
    private static final String TREE_TEXTURE_PATH = "images/greenTree.png";

    private Level level;
    private KeyboardController controller;
    private GameGraphics graphics;

    @Override
    public void create() {
        level = new LevelFactory().createDefaultLevel();
        controller = new PlayerControls(level).createController(new GdxKeyboard(Gdx.input));
        graphics = new LevelGraphicsFactory(LEVEL_MAP_PATH, TANK_TEXTURE_PATH, TREE_TEXTURE_PATH).create(level);
    }

    @Override
    public void render() {
        controller.handleInput();
        level.update(Gdx.graphics.getDeltaTime());
        graphics.render();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        graphics.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
