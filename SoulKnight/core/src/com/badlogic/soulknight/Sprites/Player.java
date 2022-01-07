package com.badlogic.soulknight.Sprites;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.soulknight.SoulKnight;
import com.badlogic.soulknight.Tools.Contactable;
import com.badlogic.soulknight.Tools.Info;

public class Player extends Sprite implements Contactable {
    public World world;
    public Body b2body;
    private OrthographicCamera camera;
    private final Vector2 START_POS = new Vector2(100, 100);
    public static Vector2 currentPos;

    private Vector3 mousePos;

    private SpriteBatch spriteBatch = new SpriteBatch();
    private Texture texture = new Texture("Knight_Monster.png");
    private Sprite sprite = new Sprite(texture, 91, 15, 16, 16);

    private Info info;

    private float timer;
    private final float ICD = 0.6f;
    private float ICDTimer;
    private boolean isAttacked = false;
    //private final TextureRegion characterStand;

    public int health = 10;

    //Movement variables
    private final float SPEED_ACCELERATION = 16f;
    private final float MAX_SPEED = 85f;

    Music attackSound = SoulKnight.manager.get("audio/sounds/BulletSound.wav");

    public boolean gameOver = false;

    public Player (World world, Vector3 mousePos, OrthographicCamera camera){
        //super(screen.getAtlas().findRegion("KnightTexture"));
        this.world = world;
        this.mousePos = mousePos;
        this.camera = camera;

        defineCharacter();
        //setup character size
        //characterStand = new TextureRegion(getTexture(),0,0,1, 73);
        //setRegion(characterStand);

        info = new Info("player");

        attackSound.setVolume(0.6f);
    }

    @Override
    public Info getInfo() {
        return info;
    }

    public int getHealth(){
        return health;
    }

    public void setAttacked(boolean attacked) {
        isAttacked = attacked;
    }

    public boolean getGameOver(){
        return gameOver;
    }

    public void defineCharacter(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(START_POS);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        currentPos = b2body.getWorldCenter();

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);

        fdef.shape = shape;
        fdef.filter.categoryBits = 2;

        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt) {
//        set position for knight and body2box
        setPosition(b2body.getWorldCenter().x, b2body.getWorldCenter().y);

        if(!gameOver) {
            handleInput();
            attack(dt);
            takeDamage(dt);
        }

        render();
    }

    public void render(){
        spriteBatch.setProjectionMatrix(camera.combined);
        sprite.setPosition(currentPos.x - 8, currentPos.y - 8);

        spriteBatch.begin();
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }

    //  setup the camera so that for each of the movement using W,A,S,D key
    //  the camera will follow the knight
    public void handleInput(){
        Vector2 currentSpeed = b2body.getLinearVelocity();

        boolean dKeyPressed = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean sKeyPressed = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean aKeyPressed = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean wKeyPressed = Gdx.input.isKeyPressed(Input.Keys.W);

        b2body.setLinearDamping(10);

        if (dKeyPressed && currentSpeed.x <= MAX_SPEED)
            b2body.applyLinearImpulse(new Vector2(SPEED_ACCELERATION, 0), currentPos, true);

        if (aKeyPressed && currentSpeed.x >= -MAX_SPEED)
            b2body.applyLinearImpulse(new Vector2(-SPEED_ACCELERATION, 0), currentPos, true);

        if (wKeyPressed && currentSpeed.y <= MAX_SPEED)
            b2body.applyLinearImpulse(new Vector2(0, SPEED_ACCELERATION), currentPos, true);

        if (sKeyPressed && currentSpeed.y >= -MAX_SPEED)
            b2body.applyLinearImpulse(new Vector2(0, -SPEED_ACCELERATION), currentPos, true);
    }

    void attack(float dt){
        timer += dt;

        if(Gdx.input.isTouched() && timer > 0.7 && !gameOver){
            timer = 0;
            new Bullet(world, currentPos, new Vector2(mousePos.x, mousePos.y).add(currentPos.scl(-1)).nor().scl(250));

            attackSound.play();
        }
    }

    void takeDamage(float dt){
        ICDTimer += dt;
        if(isAttacked && ICDTimer > ICD){
            healthUpdate(1);
            ICDTimer = 0;
        }
    }

    public void healthUpdate(int damage){
        if(health > 0) {
            health -= damage;
        }

        if(health == 0)
            gameOver();
    }

    public void gameOver(){
        gameOver = true;
    }

    @Override
    public void onContact(Contactable object) {
        Info objInfo = object.getInfo();

        if(objInfo != null) {
            if (objInfo.getType() == "monster")
                setAttacked(true);
        }
    }

    @Override
    public void offContact(Contactable object) {
        Info objInfo = object.getInfo();

        if(objInfo != null) {
            if (objInfo.getType() == "monster")
                setAttacked(false);
        }
    }
}
