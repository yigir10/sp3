package ru.samsung.gamestudio;

import com.badlogic.gdx.graphics.Texture;

public class MovingBackgroundView extends View {
    Texture texture;
    int texture1Y;
    int texture2Y;
    int speed = 2;

    public MovingBackgroundView(String pathToTexture) {
        super(0, 0);
        texture1Y = 0;
        texture2Y = GameSettings.SCREEN_HEIGHT;
        texture = new Texture(pathToTexture);
    }
    public void move() {
        texture1Y -= speed;
        texture2Y -= speed;
    }
}