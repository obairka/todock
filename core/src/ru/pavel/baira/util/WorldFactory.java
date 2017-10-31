package ru.pavel.baira.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.codeandweb.physicseditor.PhysicsShapeCache;

import ru.pavel.baira.scenes.stage.Player;

public class WorldFactory {

    private BodyFactory bodyFactory;
    private Stage stage;

    public WorldFactory(BodyFactory bodyFactory, Stage stage) {
        this.bodyFactory = bodyFactory;
        this.stage = stage;
    }

    public Universe getUniverse() {
        World world = new World(new Vector2(0, 0), true);

        Body player = bodyFactory.getPlayer(world);
        Body other = bodyFactory.getISS(world);

        Universe universe = new Universe();
        universe.stage = stage;
        universe.world = world;
        universe.player = new Player(player, new Vector2(BodyFactory.PLAYER_WIDTH, BodyFactory.PLAYER_HEIGHT));
        universe.other = other;

        universe.stage.addActor(universe.player);

        PhysicsShapeCache issB = new PhysicsShapeCache("iss.xml");
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        universe.iss = issB.createBody("iss", world, bodyDef, 3, 3);


        initStartPositions(universe);
        return universe;
    }

    private void initStartPositions(Universe universe) {
        universe.player.body.setTransform(200, 200, 0);
        universe.iss.setTransform(400, 400, 0);

        universe.iss.setTransform(300, 300, 0);
    }
}
