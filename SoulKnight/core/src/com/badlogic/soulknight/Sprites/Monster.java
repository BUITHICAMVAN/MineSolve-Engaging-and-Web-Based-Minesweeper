package com.badlogic.soulknight.Sprites;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.soulknight.Screens.PlayScreen;

public class Monster extends Sprite{

    protected Integer monsterType;

    protected String monsterId;

    protected Integer x;

    protected Integer y;

    protected Double angle;

    protected Integer speed;

    protected Integer blood;

    protected Integer CD;

    protected Integer visibility;

    protected Integer reward;

    private OrthographicCamera camera;


    public World world;
    public Body b2body;
    //private final TextureRegion characterStand;

    public Monster (World world, PlayScreen screen, OrthographicCamera camera){
        //super(screen.getAtlas().findRegion("KnightTexture"));
        this.world = world;
        defineCharacter();
        //setup character size
        //characterStand = new TextureRegion(getTexture(),0,0,1, 73);
        setBounds(0, 0, 16);
        //setRegion(characterStand);
        this.camera = camera;

    }

    private void setBounds(int i, int i1, int i2) {
    }

    public void update(float dt) {
//        set position for knight and body2box
        setPosition(b2body.getWorldCenter().x, b2body.getWorldCenter().y);
        SpriteBatch spriteBatch;
        BitmapFont font;
        CharSequence str = "Monster";
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();

        spriteBatch.setProjectionMatrix(camera.combined);
        font.getData().setScale(0.5f);
        spriteBatch.begin();
        font.draw(spriteBatch, str, b2body.getWorldCenter().x - 13, b2body.getWorldCenter().y + 15);
        spriteBatch.end();
    }

    public void defineCharacter(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(380, 120);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);

        fdef.shape = shape;
        fdef.filter.categoryBits = 8;

        b2body.createFixture(fdef).setUserData("monster");
    }

}
