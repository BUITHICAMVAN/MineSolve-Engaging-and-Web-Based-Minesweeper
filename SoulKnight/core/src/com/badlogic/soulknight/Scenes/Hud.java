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


public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

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
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        soulKnightLabel = new Label("SoulKnight", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(soulKnightLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
