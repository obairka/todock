package ru.pavel.baira.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ru.pavel.baira.TodockGame;

/**
 * Created by obairka on 29.10.17.
 */

public class GameScreen implements Screen {

    private static final Vector2 WORLD_SIZE = new Vector2(800, 480);

    private OrthographicCamera camera;

    private Box2DDebugRenderer renderer;

    private FitViewport viewport;

    private World world;
    private final Body player;
    private Stage stage2;
    private Touchpad touchpad;
    private Touchpad touchpad2;

    public GameScreen(TodockGame game) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_SIZE.x, WORLD_SIZE.y);
        camera.zoom = 1f;

        world = new World(new Vector2(0, 0), true);

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;

        player = world.createBody(bd);

        // Create a circle shape and set its radius to 6
        PolygonShape playerShape = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        playerShape.setAsBox(16, 8);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerShape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.0f; // Make it bounce a little bit
        // Create our fixture and attach it to the body
        Fixture fixture = player.createFixture(fixtureDef);

        playerShape.dispose();

        player.setTransform(200, 200, 0);


        BodyDef bd2 = new BodyDef();
        bd2.type = BodyDef.BodyType.StaticBody;

        Body body = world.createBody(bd2);

        // Create a circle shape and set its radius to 6
        PolygonShape playerShape2 = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        playerShape2.setAsBox(50, 30);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = playerShape2;
        fixtureDef2.density = 1.0f;
        fixtureDef2.friction = 1f;
        fixtureDef2.restitution = 0.0f; // Make it bounce a little bit
        // Create our fixture and attach it to the body
        body.createFixture(fixtureDef2);

        playerShape2.dispose();

        body.setTransform(300, 300, 0);

        renderer = new Box2DDebugRenderer();

        create();

        addTouchpad();

    }

    public void create() {
        viewport = new FitViewport(WORLD_SIZE.x, WORLD_SIZE.y, camera);
    }

    public void resize(int width, int height) {
        stage2.getViewport().update(width, height, true);
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
    public void show() {

    }

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int strength = 70;

        double angle = 180 * player.getAngle() / Math.PI;
        Vector2 dir = new Vector2(1, 0).rotate((float) angle);
        Vector2 impulse;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            impulse = new Vector2(-dir.x, dir.y).scl(strength);
            player.applyLinearImpulse(impulse, player.getPosition(), true);

        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            impulse = new Vector2(dir.x, dir.y).scl(strength);
            player.applyLinearImpulse(impulse, player.getPosition(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            impulse = new Vector2(-dir.y, dir.x).scl(strength);
            player.applyLinearImpulse(impulse, player.getPosition(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            impulse = new Vector2(dir.y, -dir.x).scl(strength);
            player.applyLinearImpulse(impulse, player.getPosition(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.E)) {
            Vector2 position = player.getPosition();
            position = position.sub(dir.cpy().scl(-8));
            impulse = new Vector2(-dir.y, dir.x).scl(strength / 2);
            player.applyLinearImpulse(impulse, position, true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
            Vector2 position = player.getPosition();
            position = position.sub(dir.cpy().scl(-8));
            impulse = new Vector2(dir.y, -dir.x).scl(strength / 2);
            player.applyLinearImpulse(impulse, position, true);
        }

        Vector2 impulseY = new Vector2(dir).scl(strength).scl(touchpad.getKnobPercentY());
        Vector2 impulseX = new Vector2(-dir.y, dir.x).scl(strength).scl(touchpad.getKnobPercentX());
        impulse = impulseX.add(impulseY);
        player.applyLinearImpulse(impulse, player.getPosition(), true);

        Vector2 position = player.getPosition();
        position = position.sub(dir.cpy().scl(-8));
        impulse = new Vector2(dir.y, -dir.x).scl(strength / 2).scl(touchpad2.getKnobPercentX());
        player.applyAngularImpulse(touchpad2.getKnobPercentX() * strength , true);

        camera.setToOrtho(true);
        camera.rotate((float) angle + 90);
        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        camera.update();

        world.step(delta, 6, 2);
        renderer.render(world, camera.combined);

        stage2.act(delta);
        stage2.draw();
    }

    public void dispose() {

    }

    private void addTouchpad(){
        Viewport viewport = new FitViewport(WORLD_SIZE.x, WORLD_SIZE.y);
        stage2 = new Stage(viewport);
        //Create a touchpad skin
        Skin touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("touchBackground.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("touchKnob.png"));
        //Create TouchPad Style
        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        Drawable touchBackground = touchpadSkin.getDrawable("touchBackground");
        Drawable touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(50, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(WORLD_SIZE.x - 15 - 150, 15, 150, 150);

        stage2.addActor(touchpad);

        touchpad2 = new Touchpad(50, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad2.setBounds(15, 15, 150, 150);

        stage2.addActor(touchpad2);

        Gdx.input.setInputProcessor(stage2);
    }
}
