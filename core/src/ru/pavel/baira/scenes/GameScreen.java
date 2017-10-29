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

    private Touchpad dirTouchpad;
    private Touchpad angleTouchpad;
    private final Viewport cameraViewport;

    public GameScreen(TodockGame game) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_SIZE.x, WORLD_SIZE.y);
        camera.zoom = 1f;

        cameraViewport = new FitViewport(WORLD_SIZE.x, WORLD_SIZE.y, camera);
        renderer = new Box2DDebugRenderer();

        WorldFactory worldFactory = new WorldFactory(new BodyFactory());
        universe = worldFactory.getUniverse();

        universe.player.setTransform(200, 200, 0);
        universe.iss.setTransform(300, 300, 0);

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
        camera.rotate(GeomUtils.toDegrees(universe.player.getAngle()) + 90);
        camera.position.set(universe.player.getPosition().x, universe.player.getPosition().y, 0);

        camera.update();

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
        int force = 70;

        float playerAngle = GeomUtils.toDegrees(universe.player.getAngle());

        Vector2 dir = new Vector2(1, 0).rotate(playerAngle);
        Vector2 impulse;

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            impulse = new Vector2(-dir.x, dir.y).scl(force);
            universe.player.applyLinearImpulse(impulse, universe.player.getPosition(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            impulse = new Vector2(dir.x, dir.y).scl(force);
            universe.player.applyLinearImpulse(impulse, universe.player.getPosition(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            impulse = new Vector2(-dir.y, dir.x).scl(force);
            universe.player.applyLinearImpulse(impulse, universe.player.getPosition(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            impulse = new Vector2(dir.y, -dir.x).scl(force);
            universe.player.applyLinearImpulse(impulse, universe.player.getPosition(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.E)) {
            Vector2 position = universe.player.getPosition();
            position = position.sub(dir.cpy().scl(-8));
            impulse = new Vector2(-dir.y, dir.x).scl(force / 2);
            universe.player.applyLinearImpulse(impulse, position, true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
            Vector2 position = universe.player.getPosition();
            position = position.sub(dir.cpy().scl(-8));
            impulse = new Vector2(dir.y, -dir.x).scl(force / 2);
            universe.player.applyLinearImpulse(impulse, position, true);
        }

        Vector2 impulseY = new Vector2(dir).scl(force).scl(dirTouchpad.getKnobPercentY());
        Vector2 impulseX = new Vector2(-dir.y, dir.x).scl(force).scl(dirTouchpad.getKnobPercentX());
        impulse = impulseX.add(impulseY);
        universe.player.applyLinearImpulse(impulse, universe.player.getPosition(), true);

        universe.player.applyAngularImpulse(angleTouchpad.getKnobPercentX() * force , true);
    }
}
