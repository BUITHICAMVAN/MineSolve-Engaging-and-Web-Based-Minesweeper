package com.badlogic.soulknight.Sprites.Bullets;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.soulknight.Screens.PlayScreen;
import com.badlogic.soulknight.Sprites.Monster.Monster;
import com.badlogic.soulknight.Tools.Contactable;
import com.badlogic.soulknight.Tools.Info;

public class PlayerBullet extends Bullet{

    public PlayerBullet(World world, Vector2 startPos, Vector2 direction, int damage, int range) {
        super((short) 4, (short) (1 | 8), world, startPos, direction, damage, range);

        info = new Info("bullet");
    }

    @Override
    public void onContact(Contactable object) {
        PlayScreen.addBodyToDestroy(bulletBody);
        Bullet.bullets.remove(this);

        Info objInfo = object.getInfo();

        if(objInfo != null)
            if (objInfo.getType() == "monster")
                ((Monster) object).healthUpdate(damage);
    }
}
