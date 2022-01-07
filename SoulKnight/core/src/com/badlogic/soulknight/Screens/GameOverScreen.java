package com.badlogic.soulknight.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.soulknight.SoulKnight;

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;
    private Music music;

    public GameOverScreen(Game game){
        this.game = game;
        viewport = new FitViewport(SoulKnight.V_WIDTH, SoulKnight.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((SoulKnight) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER", font);
        Label playAgainLabel = new Label("Click to Play Again", font);

        table.add(new Label("", font)).expandX();
        table.row();
        table.add(new Label("", font)).expandX();
        table.row();
        table.add(playAgainLabel).expandX();

        stage.addActor(table);

        music = SoulKnight.manager.get("audio/music/LivingRoom.mp3", Music.class);
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

        SpriteBatch spriteBatch = new SpriteBatch();
        Texture textureGO = new Texture("game-over-typography-pic-1600x900.jpg");
        Sprite spriteGO = new Sprite(textureGO, 0, 0, 1600, 900);

        spriteBatch.begin();
        spriteGO.draw(spriteBatch);
        spriteBatch.end();

        stage.draw();
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
        stage.dispose();
    }
}
