package com.badlogic.soulknight.Tools;

import com.badlogic.gdx.physics.box2d.*;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() instanceof Contactable && fixB.getUserData() instanceof Contactable) {
            ((Contactable) fixA.getUserData()).onContact((Contactable) fixB.getUserData());
            ((Contactable) fixB.getUserData()).onContact((Contactable) fixA.getUserData());
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() instanceof Contactable && fixB.getUserData() instanceof Contactable) {
            ((Contactable) fixA.getUserData()).offContact((Contactable) fixB.getUserData());
            ((Contactable) fixB.getUserData()).offContact((Contactable) fixA.getUserData());
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
