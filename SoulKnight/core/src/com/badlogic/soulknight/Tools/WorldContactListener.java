package com.badlogic.soulknight.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.soulknight.Scenes.Hud;
import com.badlogic.soulknight.Sprites.Monster;

import java.util.ArrayList;

public class WorldContactListener implements ContactListener {
    public static ArrayList<Body> bodiesToDestroy = new ArrayList();
    private Hud hud;

    public WorldContactListener(Hud hud) {
        this.hud = hud;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() == "bullet" || fixB.getUserData() == "bullet"){
            Fixture bullet = fixA.getUserData() == "bullet" ? fixA : fixB;
            Fixture object = fixA.getUserData() != "bullet" ? fixA : fixB;
            bodiesToDestroy.add(bullet.getBody());

            if(object.getUserData() != null && object.getUserData() instanceof Monster)
                ((Monster) object.getUserData()).healthUpdate(1);
        }

        if((fixA.getUserData() == "player" || fixB.getUserData() == "player")){
            Fixture player = fixA.getUserData() == "player" ? fixA : fixB;
            Fixture object = fixA.getUserData() != "player" ? fixA : fixB;

            if(object.getUserData() != null && object.getUserData() instanceof Monster)
                hud.healthUpdate(1);

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
