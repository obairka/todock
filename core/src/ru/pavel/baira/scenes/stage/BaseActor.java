package ru.pavel.baira.scenes.stage;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseActor extends Actor {

    public Body body;

    public BaseActor(Body body) {
        this.body = body;
    }
}
