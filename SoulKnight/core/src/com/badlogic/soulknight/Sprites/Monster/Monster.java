package com.badlogic.soulknight.Sprites.Monster;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.soulknight.Screens.PlayScreen;
import com.badlogic.soulknight.Tools.Contactable;
import com.badlogic.soulknight.Tools.Info;

import java.util.ArrayList;

public abstract class Monster extends Sprite implements Contactable {
    private static ArrayList<Monster> monsters = new ArrayList<>();

    public World world;
    public Body b2body;
    protected Vector2 currentPos;
    private Vector2 startPos;

    protected int health = 10;
    protected float SPEED;
    protected float RANGE;

    private OrthographicCamera camera;

    SpriteBatch spriteBatch = new SpriteBatch();
    BitmapFont font = new BitmapFont();
    CharSequence str = "Monster";
    Texture texture = new Texture("Knight_Monster.png");
    protected Sprite sprite;

    protected boolean isDead = false;

    private Info info;

    public Monster (World world, OrthographicCamera camera, Vector2 startPos){
        this.world = world;
        this.camera = camera;
        this.startPos = startPos;

        defineMonster();

        font.getData().setScale(0.5f);

        info = new Info("monster");

        monsters.add(this);
    }

    public void defineMonster(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(startPos);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        currentPos = b2body.getWorldCenter();

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6);

        fdef.shape = shape;
        fdef.filter.categoryBits = 8;

        b2body.createFixture(fdef).setUserData(this);
    }

    public abstract void update(float dt);

    protected void render(){
        spriteBatch.setProjectionMatrix(camera.combined);

        sprite.setPosition(b2body.getWorldCenter().x - 8, b2body.getWorldCenter().y - 8);

        spriteBatch.begin();
        font.draw(spriteBatch, str, b2body.getWorldCenter().x - 13, b2body.getWorldCenter().y + 15);
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }

    public void healthUpdate(int damage){
        if(health > 0)
            health -= damage;

        if(health <= 0)
            isDead();
    }

    private void isDead(){
        isDead = true;
        PlayScreen.addBodyToDestroy(b2body);
        monsters.remove(this);
        str = "";
    }

    public static void updateAll(float dt){
        for(Monster monster : monsters)
            monster.update(dt);
    }

    @Override
    public void onContact(Contactable object) {

    }

    @Override
    public void offContact(Contactable object) {

    }

    @Override
    public Info getInfo() {
        return info;
    }
}
