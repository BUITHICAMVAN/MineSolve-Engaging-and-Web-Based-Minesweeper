package com.badlogic.soulknight.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.badlogic.soulknight.Sprites.Monster;
import com.badlogic.soulknight.Sprites.Player;
import com.badlogic.soulknight.Tools.B2WorldCreator;
import com.badlogic.soulknight.Tools.WorldContactListener;

public class PlayScreen implements Screen {
    private SoulKnight game;
    // loads images from TexturePacker
    private TextureAtlas atlas;

    //  viewport
    private OrthographicCamera camera;
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
    private Monster monster;

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
        player = new Player(world, this, mousePos);
        monster = new Monster(world, this, camera);

        worldContactListener = new WorldContactListener(hud);
        world.setContactListener(worldContactListener);
    }

    //  setup the camera so that for each of the movement using W,A,S,D key
    //  the camera will follow the knight
    public void handleInput(float dt){
        boolean keyIsPressed = false;
        if(Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 100){
            player.b2body.applyLinearImpulse(new Vector2(16f, 0), player.b2body.getWorldCenter(), true);
            keyIsPressed = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -100) {
            player.b2body.applyLinearImpulse(new Vector2(-16f, 0), player.b2body.getWorldCenter(), true);
            keyIsPressed = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W) && player.b2body.getLinearVelocity().y <= 100) {
            player.b2body.applyLinearImpulse(new Vector2(0, 16f), player.b2body.getWorldCenter(), true);
            keyIsPressed = true;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S) && player.b2body.getLinearVelocity().y >= -100) {
            player.b2body.applyLinearImpulse(new Vector2(0, -16f), player.b2body.getWorldCenter().add(new Vector2(10f, -10f)), true);
            keyIsPressed = true;
        }

        if(!keyIsPressed)
            player.b2body.applyLinearImpulse(player.b2body.getLinearVelocity().scl((float) -0.2), player.b2body.getWorldCenter(), true);

    }

    public void update(float dt){
        handleInput(dt);

//        take 1 step
        world.step(1/60f, 6, 2);

        if(!world.isLocked())
            for(Body body : worldContactListener.bodiesToDestroy)
                world.destroyBody(body);

        worldContactListener.bodiesToDestroy.clear();

        player.update(dt);

//        attach gamecam to players.x coordinate, the camera move horizontally
        camera.position.x = player.b2body.getPosition().x;

        mousePos.x = Gdx.input.getX();
        mousePos.y = Gdx.input.getY();
        camera.unproject(mousePos);

        camera.update();
        renderer.setView(camera);

        hud.update();
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
// render game map
        renderer.render();
        monster.update(delta);
//render Box2DDebugLines
        b2dr.render(world, camera.combined);
        /*
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

//        set batch to draw what Hud camera sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
*/
        hud.stage.draw();
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
