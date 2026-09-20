package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.graphics.TankGraphics;
import ru.mipt.bit.platformer.graphics.TreeGraphics;
import ru.mipt.bit.platformer.input.GdxKeyboard;
import ru.mipt.bit.platformer.input.KeyboardController;
import ru.mipt.bit.platformer.input.PlayerControls;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.LevelFactory;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;

    private TiledMap levelMap;
    private MapRenderer levelMapRenderer;

    private Level level;
    private KeyboardController controller;

    private TankGraphics tankGraphics;
    private TreeGraphics treeGraphics;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        levelMap = new TmxMapLoader().load("level.tmx");
        levelMapRenderer = createSingleLayerMapRenderer(levelMap, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(levelMap);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        level = new LevelFactory().createDefaultLevel();
        controller = new PlayerControls(level).createController(new GdxKeyboard(Gdx.input));

        tankGraphics = new TankGraphics(level.getPlayerTank(), tileMovement, "images/tank_blue.png");
        treeGraphics = new TreeGraphics(level.getTrees().get(0), groundLayer, "images/greenTree.png");
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        controller.handleInput();
        level.update(deltaTime);

        // render each tile of the level
        levelMapRenderer.render();

        // start recording all drawing commands
        batch.begin();
        tankGraphics.render(batch);
        treeGraphics.render(batch);
        // submit all drawing requests
        batch.end();
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
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        treeGraphics.dispose();
        tankGraphics.dispose();
        levelMap.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
