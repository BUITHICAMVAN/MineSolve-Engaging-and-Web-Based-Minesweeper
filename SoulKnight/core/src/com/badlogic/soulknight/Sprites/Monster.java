package com.badlogic.soulknight.Sprites;

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

public class Monster extends Sprite implements Contactable {
    public World world;
    public Body b2body;

    private int health = 10;
    private final float SPEED = 35f;
    private final float RANGE = 80f;

    private OrthographicCamera camera;

    SpriteBatch spriteBatch = new SpriteBatch();
    BitmapFont font = new BitmapFont();
    CharSequence str = "Monster";

    private boolean isDead = false;

    private Info info;

    public Monster (World world, OrthographicCamera camera){
        this.world = world;
        this.camera = camera;

        defineMonster();

        font.getData().setScale(0.5f);

        info = new Info("monster");
    }

    public void defineMonster(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(380, 120);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);

        fdef.shape = shape;
        fdef.filter.categoryBits = 8;

        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt) {
        setPosition(b2body.getWorldCenter().x, b2body.getWorldCenter().y);

        if (!isDead) {
            chasePlayer();
            render();
        }
    }

    private void chasePlayer(){
        Vector2 distanceToPlayer = b2body.getWorldCenter().add(Player.currentPos.scl(-1));

        if (distanceToPlayer.len() < RANGE && distanceToPlayer.len() > 10)
            b2body.setLinearVelocity(distanceToPlayer.nor().scl(-SPEED));
        else
            b2body.setLinearVelocity(Vector2.Zero);
    }

    private void render(){
        spriteBatch.setProjectionMatrix(camera.combined);
        Texture texture = new Texture("Knight_Monster.png");
        Sprite sprite = new Sprite(texture, 35, 14, 16, 16);
        sprite.setPosition(b2body.getWorldCenter().x - 8, b2body.getWorldCenter().y - 8);

        spriteBatch.begin();
        font.draw(spriteBatch, str, b2body.getWorldCenter().x - 13, b2body.getWorldCenter().y + 15);
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }

    public void healthUpdate(int damage){
        if(health > 0)
            health -= damage;

        if(health == 0)
            isDead();
    }

    private void isDead(){
        //Gdx.app.log("Im dead", "");
        isDead = true;
        PlayScreen.addBodyToDestroy(b2body);
        str = "";
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
