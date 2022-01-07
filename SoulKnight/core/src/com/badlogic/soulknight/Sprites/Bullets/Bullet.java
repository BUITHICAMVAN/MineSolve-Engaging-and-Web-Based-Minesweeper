package com.badlogic.soulknight.Sprites.Bullets;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.soulknight.Tools.Contactable;
import com.badlogic.soulknight.Tools.Info;

public abstract class Bullet implements Contactable {
    protected Info info;
    protected Body bulletBody;
    protected FixtureDef fdef;

    protected int damage;
    private int range;

    public Bullet(short catBit, short maskBit, World world, Vector2 startPos, Vector2 direction, int damage, int range){
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
    }

    public void update(){

    }

    public abstract void onContact(Contactable object);

    @Override
    public void offContact(Contactable object) {

    }

    @Override
    public Info getInfo() {
        return info;
    }
}
