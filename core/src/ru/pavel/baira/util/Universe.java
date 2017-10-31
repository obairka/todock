package ru.pavel.baira.util;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ru.pavel.baira.scenes.stage.Player;

public class Universe {

    public World world;
    public Stage stage;

    public Player player;
    public Body iss;

    public Body other;
}
