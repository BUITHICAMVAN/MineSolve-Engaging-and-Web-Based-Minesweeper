package com.badlogic.soulknight.Sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.soulknight.SoulKnight;
import com.badlogic.soulknight.Tools.Gun;

public class Pistol implements Gun {
    private final int DAMGAGE = 4;
    private final int RANGE = 1000;
    private final int SPEED = 250;

    Music attackSound = SoulKnight.manager.get("audio/sounds/BulletSound.wav");

    Pistol(){
        attackSound.setVolume(0.6f);
    }

    @Override
    public void fire(World world, Vector2 startPos, Vector2 direction){
        new Bullet(world, startPos, direction.scl(SPEED), DAMGAGE, RANGE);
        attackSound.play();
    }
}
