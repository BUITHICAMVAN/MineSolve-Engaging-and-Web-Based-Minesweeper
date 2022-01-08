package com.badlogic.soulknight.Sprites.Monster;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.soulknight.Sprites.Player;

public class Chaser extends Monster{

    public Chaser(World world, OrthographicCamera camera, Vector2 startPos) {
        super(world, camera, startPos);

        sprite = new Sprite(texture, 35, 14, 16, 16);

        SPEED = 35f;
        RANGE = 150f;
        health = 15;
    }

    @Override
    public void update(float dt) {
        setPosition(b2body.getWorldCenter().x, b2body.getWorldCenter().y);

        if (!isDead) {
            chasePlayer();
            render();
        }
    }

    private void chasePlayer(){
        Vector2 distanceToPlayer = b2body.getWorldCenter().add(new Vector2(Player.currentPos).scl(-1));

        if (distanceToPlayer.len() < RANGE && distanceToPlayer.len() > 10)
            b2body.setLinearVelocity(distanceToPlayer.nor().scl(-SPEED));
        else
            b2body.setLinearVelocity(Vector2.Zero);
    }
}
