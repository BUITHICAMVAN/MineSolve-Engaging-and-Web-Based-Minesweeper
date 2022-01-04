package com.badlogic.soulknight.Sprites;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.soulknight.Screens.PlayScreen;

public class Player extends Sprite {
    public World world;
    public Body b2body;
    private Vector3 mousePos;
    private float timer;
    private float ICD;
    private static boolean isAttacked = false;
    public static Vector2 currentPos;
    //private final TextureRegion characterStand;

    public static int health = 10;
    public static boolean gameOver = false;

    public Player (World world, PlayScreen screen, Vector3 vector){
        //super(screen.getAtlas().findRegion("KnightTexture"));
        this.world = world;
        defineCharacter();
        mousePos = vector;
        //setup character size
        //characterStand = new TextureRegion(getTexture(),0,0,1, 73);
        setBounds(0, 0, 16);
        //setRegion(characterStand);
    }

    public static void setAttacked(boolean attacked) {
        isAttacked = attacked;
    }

    private void setBounds(int i, int i1, int i2) {
    }

    public void update(float dt) {
//        set position for knight and body2box
        setPosition(b2body.getWorldCenter().x, b2body.getWorldCenter().y);
        timer += dt;
        currentPos = b2body.getWorldCenter();

        if(Gdx.input.isTouched() && timer > 0.3 && !gameOver){
            timer = 0;
            Body bulletBody;
            BodyDef bulletDef = new BodyDef();
            bulletDef.position.set(b2body.getWorldCenter());
            bulletDef.type = BodyDef.BodyType.DynamicBody;
            bulletBody = world.createBody(bulletDef);

            FixtureDef fdef = new FixtureDef();
            CircleShape shape = new CircleShape();
            shape.setRadius(1);

            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = 4;
            fdef.filter.maskBits = 1 | 8;
            bulletBody.createFixture(fdef).setUserData("bullet");

            bulletBody.setLinearVelocity(new Vector2(mousePos.x, mousePos.y).add(b2body.getWorldCenter().scl(-1)).nor().scl(180));
        }

        ICD += dt;
        if(isAttacked && ICD > 0.6){
            healthUpdate(1);
            ICD = 0;
        }
    }



    public void defineCharacter(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(100, 100);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);

        fdef.shape = shape;
        fdef.filter.categoryBits = 2;

        b2body.createFixture(fdef).setUserData("player");
    }

    public static void healthUpdate(int damage){
        if(health > 0) {
            health -= damage;
        }

        if(health == 0)
            gameOver();
    }

    public static void gameOver(){
        gameOver = true;
    }
}
