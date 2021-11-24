package com.badlogic.soulknight.Sprites;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends Sprite {
    public World world;
    public Body b2body;

    public Player (World world){
        this.world = world;
        defineCharacter();
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
