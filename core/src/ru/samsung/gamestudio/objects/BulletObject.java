package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ru.samsung.gamestudio.GameSettings;

public class BulletObject extends GameObject{
    boolean wasHit;
    public BulletObject(String texturePath, int x, int y, int width, int height, World world) {
        super(texturePath, x, y, width, height, GameSettings.BULLET_BIT, world);
        body.setLinearVelocity(new Vector2(0, GameSettings.BULLET_VELOCITY));
        body.setLinearDamping(0);
        body.setBullet(true);
        wasHit = false;
    }
    public boolean hasToBeDestroyed() {
        return wasHit || (getY() - height / 2 > GameSettings.SCREEN_HEIGHT);
    }
    @Override
    public void hit() {
        wasHit = true;
    }
}
