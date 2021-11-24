package com.badlogic.soulknight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.soulknight.Screens.PlayScreen;

public class SoulKnight extends Game {
	public static final int V_WIDTH = 370;
	public static final int V_HEIGHT = 208;
//	SpriteBatch: hold images and textures
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
