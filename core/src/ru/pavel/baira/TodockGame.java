package ru.pavel.baira;

import com.badlogic.gdx.Game;

import ru.pavel.baira.Scenes.GameScreen;

public class TodockGame extends Game {
	@Override
	public void create () {
		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose () {
	}
}
