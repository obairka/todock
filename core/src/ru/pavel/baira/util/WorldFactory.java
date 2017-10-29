package ru.pavel.baira.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class WorldFactory {

    private BodyFactory bodyFactory;

    public WorldFactory(BodyFactory bodyFactory) {
        this.bodyFactory = bodyFactory;
    }

    public Universe getUniverse() {
        World world = new World(new Vector2(0, 0), true);

        Body player = bodyFactory.getPlayer();
        Body iss = bodyFactory.getISS();

        Universe universe = new Universe();
        universe.world = world;
        universe.player = player;
        universe.iss = iss;

        return universe;
    }
}
