package com.GGI.Fooze;

import com.GGI.Fooze.Screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Fooze extends Game {

	
	@Override
	public void create () {
		this.setScreen(new GameScreen(this));
	}


}
