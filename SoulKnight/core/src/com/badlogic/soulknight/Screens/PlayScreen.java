package com.badlogic.soulknight.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.soulknight.Scenes.Hud;
import com.badlogic.soulknight.SoulKnight;
import com.badlogic.soulknight.Sprites.Player;

public class PlayScreen implements Screen {
    private SoulKnight game;
    private OrthographicCamera camera;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;

    public PlayScreen(SoulKnight game){
        this.game = game;
        camera = new OrthographicCamera();
        gamePort = new FitViewport(SoulKnight.V_WIDTH, SoulKnight.V_HEIGHT, camera);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        player = new Player(world);

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

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
            player.b2body.applyLinearImpulse(new Vector2(0, -16f), player.b2body.getWorldCenter(), true);
            keyIsPressed = true;
        }

        if(!keyIsPressed)
            player.b2body.applyLinearImpulse(player.b2body.getLinearVelocity().scl((float) -0.2), player.b2body.getWorldCenter(), true);

    }

    public void update(float dt){
        handleInput(dt);

        world.step(1/60f, 6, 2);

        camera.position.x = player.b2body.getPosition().x;



        camera.update();
        renderer.setView(camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, camera.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

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

    }
}
