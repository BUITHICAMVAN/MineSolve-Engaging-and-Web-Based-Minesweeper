package com.badlogic.soulknight.Sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.soulknight.Tools.Gun;

public class Shotgun implements Gun {
    private final int DAMGAGE = 5;
    private final int RANGE = 20;
    private final int SPEED = 1000;
    private final int SPREAD = 3;

    @Override
    public void fire(World world, Vector2 startPos, Vector2 direction) {
        Vector2 trueDirection = direction.scl(SPEED);
        new Bullet(world, startPos, trueDirection, DAMGAGE, RANGE);
        new Bullet(world, startPos, trueDirection.rotateDeg(SPREAD), DAMGAGE,RANGE);
        new Bullet(world, startPos, trueDirection.rotateDeg(-SPREAD * 2), DAMGAGE, RANGE);
    }
}
