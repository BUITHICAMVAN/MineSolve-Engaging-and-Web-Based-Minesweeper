package com.badlogic.soulknight.Tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public interface Gun {
    void fire(World world, Vector2 startPos, Vector2 direction);

    float getFirerate();
}
