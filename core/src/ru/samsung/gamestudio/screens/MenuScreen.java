package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.ScreenAdapter;

import ru.samsung.gamestudio.ButtonView;
import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.MovingBackgroundView;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.TextView;

public class MenuScreen extends ScreenAdapter {

    MovingBackgroundView backgroundView;
    MyGdxGame myGdxGame;
    TextView titleView;
    ButtonView startButtonView;
    ButtonView settingsButtonView;
    ButtonView exitButtonView;


    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        startButtonView = new ButtonView(140, 646, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "start");
        settingsButtonView = new ButtonView(140, 551, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "settings");
        exitButtonView = new ButtonView(140, 456, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "exit");


        titleView = new TextView(myGdxGame.largeWhiteFont, 180, 960, "Space Cleaner");
    }

    @Override
    public void render(float delta) {
        backgroundView.draw(myGdxGame.batch);
    }


}
