package com.badlogic.soulknight.Scenes;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.soulknight.SoulKnight;
import com.badlogic.soulknight.Sprites.Player;

// class Hud displays world time, onscreen-controller button, bullet capacity, health, warnings,...
public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    // what should be display on the HUD screen
    private int worldTimer;
    private float timeCount;

    // Scenes 2D widgets
    Label countdownLabel;
    Label healthLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label soulKnightLabel;

    public Hud (SpriteBatch sb){
//        define tracking variables which are displayed on HUD screen
        worldTimer = 666;
        timeCount = 0;

//        setup HUD viewport using a new camera seperate from the gamecame (HUD screen remains still)
        viewport = new FitViewport(SoulKnight.V_WIDTH, SoulKnight.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);


        // define a table to organize hud's labels: put all tracking information in one rectangle box
        Table table = new Table();
        table.top(); // display sprites at the top of the page
        table.setFillParent(true); // set the table to the size of the page

        // setup label display
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthLabel = new Label(String.format("HP %d/10", Player.health), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("Time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("Dungeon 1",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        soulKnightLabel = new Label("Knight", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // add to table
        // expandX: labels will be displayed at the whole screen width
        table.add(soulKnightLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row();
        table.add(healthLabel).expandX();
        table.add().expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    public void update(float dt){
        healthLabel.setText(String.format("HP %d/10", Player.health));

        timeCount += dt;

        if(timeCount > 1){
            timeCount = 0;
            countdownLabel.setText(String.format("%03d", --worldTimer));
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
