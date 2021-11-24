package com.badlogic.soulknight.Sprites;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Pickup extends InteractiveTileObject{
    public Pickup(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
    }
}
