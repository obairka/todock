package ru.pavel.baira.scenes.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ru.pavel.baira.util.GeomUtils;

public class Player extends BaseActor {

    public static final int ENGINE_FORCE = 70;

    public enum EngineDirection {
        UP,
        DOWN,
        RIGHT,
        LEFT,
        ROTATE_CLOCKWISE,
        ROTATE_ANTI_CLOCKWISE
    }

    private final Vector2 size;
    private Texture texture;

    public boolean impulseUp;
    public boolean impulseDown;
    public boolean impulseRight;
    public boolean impulseLeft;

    private Texture textureFire;

    public Player(Body body, Vector2 size) {
        super(body);
        this.size = size;
        setSize(size.x, size.y);
        createTexture((int) getWidth(), (int) getHeight(), Color.BLUE);
    }

    public void enableEngine(EngineDirection direction, float force) {
        float playerAngle = GeomUtils.toDegrees(body.getAngle());

        Vector2 dir = new Vector2(1, 0).rotate(playerAngle);
        Vector2 impulse = null;
        Vector2 position = null;

        switch (direction) {
            case UP:
                impulse = new Vector2(dir.x, dir.y).scl(ENGINE_FORCE);
                break;
            case DOWN:
                impulse = new Vector2(-dir.x, dir.y).scl(ENGINE_FORCE);
                break;
            case RIGHT:
                impulse = new Vector2(-dir.y, dir.x).scl(ENGINE_FORCE);
                break;
            case LEFT:
                impulse = new Vector2(dir.y, -dir.x).scl(ENGINE_FORCE);
                break;
            case ROTATE_CLOCKWISE:
                position = body.getPosition().cpy().sub(dir.cpy().scl(-8));
                impulse = new Vector2(-dir.y, dir.x).scl(ENGINE_FORCE / 2);
                break;
            case ROTATE_ANTI_CLOCKWISE:
                position = body.getPosition().cpy().sub(dir.cpy().scl(-8));
                impulse = new Vector2(dir.y, -dir.x).scl(ENGINE_FORCE / 2);
                break;
        }
        if (position != null) {
            body.applyLinearImpulse(impulse.scl(force), position, true);
        } else {
            body.applyLinearImpulse(impulse.scl(force), body.getPosition(), true);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX() - getWidth() / 2, getY() - getHeight() / 2,
                getWidth() / 2, getHeight() / 2, getWidth(), getHeight(),
                1, 1, getRotation(), 0, 0, texture.getWidth(), texture.getHeight(),
                false, false);

        /*float rotation = getRotation();
        Vector2 posDown = getPosition().cpy()
                .add(new Vector2(0, getHeight() ).rotate(rotation).scl(-1));
        if (true){
            batch.draw(textureFire, posDown.x, posDown.y,
                    0, getHeight() / 4, getWidth() / 2, getHeight() / 4,
                    1, 1, getRotation() + 180, 0, 0,
                    textureFire.getWidth(), textureFire.getHeight(),
                    false, false);
        }*/
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void resetImpulse() {
        impulseUp = false;
        impulseDown = false;
        impulseRight = false;
        impulseLeft = false;
    }

    private void createTexture(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, width, height);
        texture = new Texture(pixmap);
        pixmap.dispose();

        Pixmap pixmap2 = new Pixmap(width / 2, height / 2, Pixmap.Format.RGBA8888);
        pixmap2.setColor(color);
        /*pixmap2.fillTriangle(0, height / 4,
                width / 2, height / 2,
                width / 2, 0);*/
        pixmap2.fillRectangle(0, 0, width, height);
        textureFire = new Texture(pixmap2);
        pixmap2.dispose();
    }
}
