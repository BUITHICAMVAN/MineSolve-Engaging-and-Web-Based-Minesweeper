package com.badlogic.soulknight.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class WorldContactListener implements ContactListener {
    public ArrayList<Body> bulletsToDestroy = new ArrayList();

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() == "bullet" || fixB.getUserData() == "bullet"){
            Fixture bullet = fixA.getUserData() == "bullet" ? fixA : fixB;
            bulletsToDestroy.add(bullet.getBody());
            Gdx.app.log("bullet" + fixA.getUserData(),"");
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
