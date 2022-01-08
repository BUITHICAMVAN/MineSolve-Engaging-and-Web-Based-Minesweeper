package com.badlogic.soulknight.Tools;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.soulknight.Sprites.WinArea;

public class B2WorldCreator implements Contactable{
    private World world;
    private TiledMap map;

    private Info info = new Info("wall");

    public B2WorldCreator(World world, TiledMap map){
        this.world = world;
        this.map = map;

        createRectangle(8);
        createRectangle(9);
    }

    void createRectangle(int layer){
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(RectangleMapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = object.getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef).setUserData(this);
        }
    }

    @Override
    public void onContact(Contactable object) {

    }

    @Override
    public void offContact(Contactable object) {

    }

    @Override
    public Info getInfo() {
        return info;
    }
}
