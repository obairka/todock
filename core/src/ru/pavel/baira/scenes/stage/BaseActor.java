package ru.pavel.baira.scenes.stage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ru.pavel.baira.util.GeomUtils;

public class BaseActor extends Actor {

    public Body body;

    public BaseActor(Body body) {
        this.body = body;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        Vector2 position = body.getPosition();
        setPosition(position.x, position.y);
        setRotation(GeomUtils.toDegrees(body.getAngle()));
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }
}
