package com.badlogic.soulknight.Sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.soulknight.SoulKnight;
import com.badlogic.soulknight.Tools.Gun;

public class Shotgun implements Gun {
    private final int DAMAGE = 5;
    private final int RANGE = 20;
    private final int SPEED = 1000;
    private final int SPREAD = 3;
    private final float FIRERATE = 0.9f;

    Music attackSound = SoulKnight.manager.get("audio/sounds/ShotgunSound2.wav");

    @Override
    public void fire(World world, Vector2 startPos, Vector2 direction) {
        Vector2 trueDirection = direction.scl(SPEED);
        new Bullet(world, startPos, trueDirection, DAMAGE, RANGE);
        new Bullet(world, startPos, trueDirection.rotateDeg(SPREAD), DAMAGE,RANGE);
        new Bullet(world, startPos, trueDirection.rotateDeg(-SPREAD * 2), DAMAGE, RANGE);

        attackSound.play();
    }

    @Override
    public float getFirerate() {
        return FIRERATE;
    }
}
