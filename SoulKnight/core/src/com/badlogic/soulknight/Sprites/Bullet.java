package com.badlogic.soulknight.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.soulknight.Screens.PlayScreen;
import com.badlogic.soulknight.Tools.Contactable;
import com.badlogic.soulknight.Tools.Info;

public class Bullet implements Contactable {
    private Info info;
    public Body bulletBody;

    public Bullet(World world, Vector2 startPos, Vector2 direction){
        BodyDef bulletDef = new BodyDef();
        bulletDef.position.set(startPos.scl(-1));
        bulletDef.type = BodyDef.BodyType.DynamicBody;
        bulletBody = world.createBody(bulletDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(1);

        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = 4;
        fdef.filter.maskBits = 1 | 8;
        bulletBody.createFixture(fdef).setUserData(this);

        bulletBody.setLinearVelocity(direction);

        info = new Info("bullet");
    }

    @Override
    public void onContact(Contactable object) {
        PlayScreen.addBodyToDestroy(bulletBody);

        Info objInfo = object.getInfo();

        if(objInfo != null)
            if (objInfo.getType() == "monster")
            ((Monster) object).healthUpdate(1);
    }

    @Override
    public void offContact(Contactable object) {

    }

    @Override
    public Info getInfo() {
        return info;
    }
}
