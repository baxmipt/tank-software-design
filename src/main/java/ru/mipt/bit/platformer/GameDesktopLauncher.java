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
import ru.mipt.bit.platformer.model.FileLevelFactory;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.LevelFactory;
import ru.mipt.bit.platformer.model.RandomLevelFactory;

import java.nio.file.Path;
import java.util.Random;

public class GameDesktopLauncher implements ApplicationListener {

    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 1024;
    private static final String LEVEL_MAP_PATH = "level.tmx";
    private static final String TANK_TEXTURE_PATH = "images/tank_blue.png";
    private static final String TREE_TEXTURE_PATH = "images/greenTree.png";
    private static final int LEVEL_WIDTH = 10;
    private static final int LEVEL_HEIGHT = 8;
    private static final int TREE_COUNT = 12;
    private static final float TANK_MOVEMENT_DURATION = 0.4f;

    private final LevelFactory levelFactory;

    private Level level;
    private KeyboardController controller;
    private GameGraphics graphics;

    public GameDesktopLauncher(LevelFactory levelFactory) {
        this.levelFactory = levelFactory;
    }

    @Override
    public void create() {
        level = levelFactory.create();
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
        new Lwjgl3Application(new GameDesktopLauncher(createLevelFactory(args)), config);
    }

    private static LevelFactory createLevelFactory(String[] args) {
        if (args.length > 0) {
            return new FileLevelFactory(Path.of(args[0]), TANK_MOVEMENT_DURATION);
        }
        return new RandomLevelFactory(LEVEL_WIDTH, LEVEL_HEIGHT, TREE_COUNT, TANK_MOVEMENT_DURATION, new Random());
    }
}
