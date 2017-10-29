package ru.pavel.baira.util;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {

    public static final int PLAYER_WIDTH = 40;
    public static final int PLAYER_HEIGHT = 20;

    public static final int IIS_WIDTH = 100;
    public static final int IIS_HEIGHT = 50;

    private World world;

    public BodyFactory(World world) {
        this.world = world;
    }

    public Body getPlayer() {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(PLAYER_WIDTH, PLAYER_HEIGHT);

        return new BodyBuilder(world)
                .setBodyType(BodyDef.BodyType.DynamicBody)
                .setShape(polygonShape)
                .setFriction(0.2f)
                .setDensity(1)
                .setRestitution(0.1f)
                .build();
    }

    public Body getISS() {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(IIS_WIDTH, IIS_HEIGHT);

        return new BodyBuilder(world)
                .setBodyType(BodyDef.BodyType.StaticBody)
                .setShape(polygonShape)
                .setFriction(0.2f)
                .setDensity(1)
                .setRestitution(0.1f)
                .build();
    }
}
