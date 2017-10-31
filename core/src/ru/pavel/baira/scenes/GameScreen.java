package ru.pavel.baira.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ru.pavel.baira.TodockGame;
import ru.pavel.baira.scenes.stage.Player;
import ru.pavel.baira.util.BodyFactory;
import ru.pavel.baira.util.GeomUtils;
import ru.pavel.baira.util.TouchpadBuilder;
import ru.pavel.baira.util.Universe;
import ru.pavel.baira.util.WorldFactory;

/**
 * Created by obairka on 29.10.17.
 */

public class GameScreen implements Screen {
    private static final Vector2 WORLD_SIZE = new Vector2(800, 480);
    private final Universe universe;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    private Stage fixedStage;
    private Stage worldStage;

    private Touchpad dirTouchpad;
    private Touchpad angleTouchpad;
    private final Viewport cameraViewport;

    public GameScreen(TodockGame game) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_SIZE.x, WORLD_SIZE.y);
        camera.zoom = 1f;

        cameraViewport = new FitViewport(WORLD_SIZE.x, WORLD_SIZE.y, camera);
        renderer = new Box2DDebugRenderer();

        // Create world
        WorldFactory worldFactory = new WorldFactory(new BodyFactory(), worldStage = new Stage(cameraViewport));
        universe = worldFactory.getUniverse();

        // Create touchpad
        fixedStage = new Stage(new FitViewport(WORLD_SIZE.x, WORLD_SIZE.y));
        Gdx.input.setInputProcessor(fixedStage);

        dirTouchpad = new TouchpadBuilder()
                .withPosition(WORLD_SIZE.x - 15 - 150, 15)
                .withSize(150, 150)
                .build();

        fixedStage.addActor(dirTouchpad);

        angleTouchpad = new TouchpadBuilder()
                .withPosition(15, 15)
                .withSize(150, 150)
                .build();

        fixedStage.addActor(angleTouchpad);
    }

    @Override
    public void show() {

    }

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.handleInput();

        camera.setToOrtho(true);
        camera.rotate(GeomUtils.toDegrees(universe.player.body.getAngle()) + 90);

        float maxZoom = 0.1f;
        float minZoom = 1;

        float len = universe.player.getPosition().sub(universe.iss.getPosition()).len();

        camera.zoom = (float) Math.max(Math.sqrt(len) / 7f, 1f);
        camera.position.set(universe.player.getPosition().x, universe.player.getPosition().y, 0);

        camera.update();

        worldStage.act(delta);
        worldStage.draw();

        fixedStage.act(delta);
        fixedStage.draw();

        universe.world.step(delta, 6, 2);
        renderer.render(universe.world, camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void resize(int width, int height) {

        fixedStage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        this.fixedStage.dispose();
        this.universe.world.dispose();
    }

    private void handleInput()
    {
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            universe.player.enableEngine(Player.EngineDirection.DOWN, 1f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            universe.player.enableEngine(Player.EngineDirection.UP, 1f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            universe.player.enableEngine(Player.EngineDirection.RIGHT, 1f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            universe.player.enableEngine(Player.EngineDirection.LEFT, 1f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.E)) {
            universe.player.enableEngine(Player.EngineDirection.ROTATE_ANTI_CLOCKWISE, 1f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
            universe.player.enableEngine(Player.EngineDirection.ROTATE_CLOCKWISE, 1f);
        }

        universe.player.enableEngine(Player.EngineDirection.UP, dirTouchpad.getKnobPercentY());
        universe.player.enableEngine(Player.EngineDirection.ROTATE_CLOCKWISE, angleTouchpad.getKnobPercentX());
    }
}
