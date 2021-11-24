package com.badlogic.soulknight.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.soulknight.SoulKnight;

// class Hud displays world time, onscreen-controller button, bullet capacity, health, warnings,...
public class Hud {
    public Stage stage;
    private Viewport viewport;

//    what should be display on the HUD screen
    private int worldTimer;
    private float timeCount;
    private int score;

    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label soulKnightLabel;

    public Hud (SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        viewport = new FitViewport(SoulKnight.V_WIDTH, SoulKnight.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
//        display all sprites and stuffs at the top of the stage
        table.top();

//        set the table to the size of the stage
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("Time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("World",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        soulKnightLabel = new Label("Soul Knight", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

//        add those to the table
//        expandX the label will be display the whole width of the table
        table.add(soulKnightLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

//        create new row for the table
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

//      add table to our stage
        stage.addActor(table);
    }

}
