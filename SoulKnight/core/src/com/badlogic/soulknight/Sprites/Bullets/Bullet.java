package com.badlogic.soulknight.Sprites.Bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.soulknight.Screens.PlayScreen;
import com.badlogic.soulknight.Tools.Contactable;
import com.badlogic.soulknight.Tools.Info;

import java.util.ArrayList;

public abstract class Bullet extends Sprite implements Contactable {
    protected Info info;
    protected Body bulletBody;
    protected FixtureDef fdef;

    protected int damage;
    private int range;

    protected static ArrayList<Bullet> bullets = new ArrayList<>();
    private static SpriteBatch spriteBatch = new SpriteBatch();
    protected static Texture texture = new Texture("0x72_16x16DungeonTileset.v4.png");
    private static Sprite sprite = new Sprite(texture, 80, 128, 5, 5);

    public Bullet(short catBit, short maskBit, World world, Vector2 startPos, Vector2 direction, int damage, int range){
        super(sprite);
        this.damage = damage;
        this.range = range;

        BodyDef bulletDef = new BodyDef();
        bulletDef.position.set(startPos);

        bulletDef.type = BodyDef.BodyType.DynamicBody;
        bulletBody = world.createBody(bulletDef);

        fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(1);
        fdef.filter.categoryBits = catBit;
        fdef.filter.maskBits = maskBit;

        fdef.shape = shape;
        fdef.isSensor = true;
        bulletBody.createFixture(fdef).setUserData(this);

        bulletBody.setLinearVelocity(direction);

        bullets.add(this);
    }

    public void update(){
        setPosition(bulletBody.getWorldCenter().x - 2.5f, bulletBody.getWorldCenter().y - 2.5f);
    }

    public abstract void onContact(Contactable object);

    public static void render(){
        spriteBatch.setProjectionMatrix(PlayScreen.getCamera().combined);

        spriteBatch.begin();
        for(Bullet bullet : bullets) {
            bullet.update();
            bullet.draw(spriteBatch);
        }
        spriteBatch.end();
    }

    @Override
    public void offContact(Contactable object) {

    }

    @Override
    public Info getInfo() {
        return info;
    }
}
