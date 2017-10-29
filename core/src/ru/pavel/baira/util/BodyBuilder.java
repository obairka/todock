package ru.pavel.baira.util;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyBuilder {

    private BodyDef.BodyType bodyType;
    private Shape shape;
    private float friction = 0;
    private float restitution = 0;
    private float density = 0;
    private boolean isSensor = false;
    private World world;

    public BodyBuilder(World world) {
        this.world = world;
    }

    public Body build() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;

        Body body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    public BodyBuilder setBodyType(BodyDef.BodyType bodyType) {
        this.bodyType = bodyType;
        return this;
    }

    public BodyBuilder setShape(Shape shape) {
        this.shape = shape;
        return this;
    }

    public BodyBuilder setFriction(float friction) {
        this.friction = friction;
        return this;
    }

    public BodyBuilder setRestitution(float restitution) {
        this.restitution = restitution;
        return this;
    }

    public BodyBuilder setDensity(float density) {
        this.density = density;
        return this;
    }

    public BodyBuilder setSensor(boolean sensor) {
        isSensor = sensor;
        return this;
    }
}
