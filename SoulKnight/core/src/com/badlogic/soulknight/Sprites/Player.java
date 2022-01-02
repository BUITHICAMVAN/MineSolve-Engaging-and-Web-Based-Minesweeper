package com.badlogic.soulknight.Sprites;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.soulknight.Screens.PlayScreen;

public class Player extends Sprite {
    public World world;
    public Body b2body;
    private final TextureRegion characterStand;

    public Player (World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("KnightTexture"));
        this.world = world;
        defineCharacter();
        //setup character size
        characterStand = new TextureRegion(getTexture(),0,0,1, 73);
        setBounds(0, 0, 16);
        setRegion(characterStand);
    }

    private void setBounds(int i, int i1, int i2) {
    }

    public void update(float dt ) {
//        set position for knight and body2box
        setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() /2);
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
        b2body.createFixture(fdef);
    }

}
