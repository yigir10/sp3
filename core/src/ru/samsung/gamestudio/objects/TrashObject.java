package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import ru.samsung.gamestudio.GameSettings;

public class TrashObject extends GameObject{
    private static final int paddingHorizontal = 30;
    private int livesLeft;
    public TrashObject(String texturePath,int width, int height, World world) {
        super(
                texturePath,
                width / 2 + paddingHorizontal + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width)),
                GameSettings.SCREEN_HEIGHT + height / 2,
                width, height,
                GameSettings.TRASH_BIT,
                world
        );        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
        body.setLinearDamping(0f);
        livesLeft = 1;
    }
    @Override
    public void hit() {
        livesLeft -= 1;
    }
    public boolean isAlive() {
        return livesLeft > 0;
    }
    public boolean isInFrame(){
        return getY() + height / 2 > 0;
    }

}
