package com.badlogic.soulknight.Sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.soulknight.SoulKnight;
import com.badlogic.soulknight.Sprites.Bullets.PlayerBullet;
import com.badlogic.soulknight.Tools.Gun;

public class Pistol implements Gun {
    private final int DAMAGE = 4;
    private final int RANGE = 1000;
    private final int SPEED = 250;
    private final float FIRERATE = 0.5f;

    Music attackSound = SoulKnight.manager.get("audio/sounds/PistolSound.mp3");

    Pistol(){
        attackSound.setVolume(0.6f);
    }

    @Override
    public void fire(World world, Vector2 startPos, Vector2 direction){
        new PlayerBullet(world, startPos, direction.scl(SPEED), DAMAGE, RANGE);
        attackSound.play();
    }

    @Override
    public float getFirerate() {
        return FIRERATE;
    }
}
