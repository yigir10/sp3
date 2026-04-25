package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.components.ButtonView;
import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.managers.MemoryManager;
import ru.samsung.gamestudio.components.MovingBackgroundView;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.RecordsListView;
import ru.samsung.gamestudio.components.TextView;

public class RecordsScreen extends ScreenAdapter {
    TextView recordsTextView;
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    RecordsListView recordsListView;
    ButtonView backButtonView;

    public RecordsScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        recordsListView = new RecordsListView(myGdxGame.commonWhiteFont, 690);
        recordsTextView = new TextView(myGdxGame.largeWhiteFont, 206, 842, "Last records");
        backButtonView = new ButtonView(260, 200, 200, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "back");
    }

    @Override
    public void show() {
        recordsListView.setRecords(MemoryManager.loadRecordsTable());
    }

    @Override
    public void render(float delta) {
        handleInput();
        ScreenUtils.clear(Color.CLEAR);
        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);
        recordsListView.draw(myGdxGame.batch);
        backButtonView.draw(myGdxGame.batch);
        recordsTextView.draw(myGdxGame.batch);
        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (backButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
        }
    }
}