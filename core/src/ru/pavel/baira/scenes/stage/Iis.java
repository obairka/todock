package ru.pavel.baira.scenes.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Iis extends BaseActor {

    private final Vector2 size;
    private Texture texture;

    public Iis(Body body, Vector2 size) {
        super(body);
        this.size = size;
        setSize(size.x, size.y);
        createTexture((int) getWidth(), (int) getHeight(), Color.BLUE);
    }

    private void createTexture(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, width, height);
        texture = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY(), 0, 0, getWidth(), getHeight(),
                1, 1, getRotation(), 0, 0, texture.getWidth(), texture.getHeight(),
                false, false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
