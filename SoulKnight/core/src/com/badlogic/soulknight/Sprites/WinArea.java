package com.badlogic.soulknight.Sprites;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.soulknight.Screens.PlayScreen;
import com.badlogic.soulknight.Tools.Contactable;
import com.badlogic.soulknight.Tools.Info;

public class WinArea implements Contactable {
    private Info info;
    private PlayScreen playScreen;

    public WinArea(World world, PlayScreen playScreen){

        this.playScreen = playScreen;

        Body b2body;
        BodyDef bdef = new BodyDef();
        bdef.position.set(896, 136);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16, 8);

        fdef.shape = shape;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

        info = new Info("win");
    }

    @Override
    public void onContact(Contactable object) {
        Info objInfo = object.getInfo();

        if(objInfo != null) {
            if (objInfo.getType() == "player")
                playScreen.win = true;
        }
    }

    @Override
    public void offContact(Contactable object) {

    }

    @Override
    public Info getInfo() {
        return info;
    }
}
