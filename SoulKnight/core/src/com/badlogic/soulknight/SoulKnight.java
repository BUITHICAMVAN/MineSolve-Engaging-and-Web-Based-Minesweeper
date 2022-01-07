package com.badlogic.soulknight;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.soulknight.Screens.IntroScreen;
import com.badlogic.soulknight.Screens.PlayScreen;

public class SoulKnight extends Game {
	public static final int V_WIDTH = 370;
	public static final int V_HEIGHT = 208;
//	SpriteBatch: hold images and textures
	public SpriteBatch batch;

	public static AssetManager manager;

	@Override
	public void create () {
		batch = new SpriteBatch();

		manager = new AssetManager();
		manager.load("audio/music/Dungeon.mp3", Music.class);
		manager.load("audio/music/LivingRoom.mp3", Music.class);
		manager.load("audio/sounds/BulletSound.wav", Music.class);
		manager.finishLoading();
		setScreen(new IntroScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}

