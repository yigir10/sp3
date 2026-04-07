package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;

import ru.samsung.gamestudio.MyGdxGame;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
    }
    @Override
    public void dispose() {
    }
}
