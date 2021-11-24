package com.badlogic.soulknight.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.soulknight.Screens.PlayScreen;

public class Weapon extends Sprite {
    public World world;
    public Body b2body;

    public Weapon(World world, PlayScreen screen) {
        this.world = world;
        defineWeapons();
    }

    public void defineWeapons() {

    }
}
