package com.badlogic.soulknight.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.soulknight.SoulKnight;

public class WinScreen implements Screen {
    private Game game;
    private Music music;

    SpriteBatch spriteBatch = new SpriteBatch();
    Texture textureGO = new Texture("Win.jpg");
    Sprite spriteGO = new Sprite(textureGO, 0, 0, 1600, 900);

    public WinScreen(Game game){
        this.game = game;

        music = SoulKnight.manager.get("audio/music/Easter.mp3", Music.class);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            music.stop();
            game.setScreen(new PlayScreen((SoulKnight) game));
            dispose();
        }


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteGO.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
