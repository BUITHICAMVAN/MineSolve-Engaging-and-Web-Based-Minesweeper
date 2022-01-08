package com.badlogic.soulknight.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.soulknight.Scenes.Hud;
import com.badlogic.soulknight.SoulKnight;
import com.badlogic.soulknight.Sprites.Bullets.Bullet;
import com.badlogic.soulknight.Sprites.Monster.Chaser;
import com.badlogic.soulknight.Sprites.Monster.Monster;
import com.badlogic.soulknight.Sprites.Monster.Shooter;
import com.badlogic.soulknight.Sprites.Player;
import com.badlogic.soulknight.Sprites.WinArea;
import com.badlogic.soulknight.Tools.B2WorldCreator;
import com.badlogic.soulknight.Tools.WorldContactListener;

import java.util.ArrayList;

public class PlayScreen implements Screen {
    private SoulKnight game;
    // load images from TexturePacker
    private TextureAtlas atlas;

    //  viewport
    private static OrthographicCamera camera;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader; // load map tmx to the game
    private TiledMap map; // reference to the map
    private OrthogonalTiledMapRenderer renderer; // render the map to screen

    //    create box2d world, b2s variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;
    private Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
    private WorldContactListener worldContactListener;
    private static ArrayList<Body> bodiesToDestroy = new ArrayList();

    private Music music;
    public boolean win = false;

    public PlayScreen(SoulKnight game){
        //put the String to the pack file of image package
        atlas  = new TextureAtlas("Weapons.pack"); //adding weapons pack
        this.game = game;

        camera = new OrthographicCamera(); //create the camera that follow the knight
        // maintain virtual aspect ratio to make player screen viewport fixed
        gamePort = new FitViewport(SoulKnight.V_WIDTH, SoulKnight.V_HEIGHT, camera);
        hud = new Hud(game.batch); //create HUD screen to display scores/timers/level

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // setup a non-gravitational environment, state remains still at first
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        //create knight in game world
        player = new Player(world, mousePos, camera);

        new Chaser(world, camera, new Vector2(300, 120));
        new Shooter(world, camera, new Vector2(600, 115));
        new Chaser(world, camera, new Vector2(380, 150));
        new Shooter(world, camera, new Vector2(380, 60));
        new Shooter(world, camera, new Vector2(200, 150));


        hud.setPlayer(player);

        worldContactListener = new WorldContactListener();
        world.setContactListener(worldContactListener);

        music = SoulKnight.manager.get("audio/music/Dungeon.mp3");
        music.setLooping(true);
        music.play();

        new WinArea(world, this);
    }

    public static OrthographicCamera getCamera(){
        return camera;
    }

    public static void addBodyToDestroy(Body body) {
        bodiesToDestroy.add(body);
    }

    public void update(float dt){
//        take 1 step
        world.step(1/60f, 6, 2);

        destroyBodies();

        player.update(dt);
        Monster.updateAll(dt);

//        attach gamecam to players.x coordinate, the camera move horizontally
        camera.position.x = player.b2body.getPosition().x;

        mousePos.x = Gdx.input.getX();
        mousePos.y = Gdx.input.getY();
        camera.unproject(mousePos);

        camera.update();
        renderer.setView(camera);

        hud.update(dt);
    }

    private void destroyBodies(){
        if(!world.isLocked())
            for(Body body : bodiesToDestroy)
                world.destroyBody(body);

        bodiesToDestroy.clear();
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // render game map
        renderer.render();
        Bullet.render();

        update(delta);
        //render Box2DDebugLines
        //b2dr.render(world, camera.combined);

        hud.stage.draw();

        gameOver();
        win();
    }

    private void gameOver(){
        if (player.getGameOver()){
            Gdx.app.log("GameOver", "");
            music.stop();
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    public void win(){
        if(win){
            music.stop();
            Gdx.app.log("You win", "");
            game.setScreen(new WinScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        map.dispose();
        renderer.dispose();
        b2dr.dispose();
        world.dispose();
        hud.dispose();
    }
}
