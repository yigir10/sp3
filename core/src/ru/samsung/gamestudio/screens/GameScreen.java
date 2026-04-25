package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import ru.samsung.gamestudio.components.ButtonView;
import ru.samsung.gamestudio.managers.ContactManager;
import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSession;
import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.GameState;
import ru.samsung.gamestudio.components.ImageView;
import ru.samsung.gamestudio.components.LiveView;
import ru.samsung.gamestudio.managers.MemoryManager;
import ru.samsung.gamestudio.components.MovingBackgroundView;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.RecordsListView;
import ru.samsung.gamestudio.components.TextView;
import ru.samsung.gamestudio.objects.BulletObject;
import ru.samsung.gamestudio.objects.ShipObject;
import ru.samsung.gamestudio.objects.TrashObject;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    ShipObject shipObject;
    GameSession gameSession;
    ArrayList<TrashObject> trashArray;
    ArrayList<BulletObject> bulletArray;
    MovingBackgroundView backgroundView;
    ImageView topBlackoutView;
    LiveView liveView;
    TextView scoreTextView;
    ButtonView pauseButton;
    ImageView  fullBlackoutView;
    ButtonView  homeButton;
    ButtonView  continueButton;
    TextView pauseTextView;
    TextView recordsTextView;
    RecordsListView recordsListView;
    ButtonView homeButton2;
    public GameScreen(MyGdxGame myGdxGame) {
        gameSession = new GameSession();
        trashArray = new ArrayList<>();
        this.myGdxGame = myGdxGame;
        shipObject = new ShipObject(GameSettings.SCREEN_WIDTH / 2, 150, GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT, GameResources.SHIP_IMG_PATH, myGdxGame.world);
        bulletArray = new ArrayList<>();
        new ContactManager(myGdxGame.world);
        scoreTextView = new TextView(myGdxGame.commonWhiteFont, 50, 1215);
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        topBlackoutView = new ImageView(0, 1180, GameResources.BLACKOUT_TOP_IMG_PATH);
        pauseButton = new ButtonView(605, 1200, 46, 54, GameResources.PAUSE_IMG_PATH);
        fullBlackoutView = new ImageView(0,0,GameResources.FULL_BLACKOUT_IMG_PATH);
        homeButton = new ButtonView(GameSettings.SCREEN_WIDTH / 2 - 205,400,200,100,myGdxGame.commonBlackFont,GameResources.BUTTON_BACKGROUND_IMG_PATH,"Home");
        continueButton = new ButtonView(GameSettings.SCREEN_WIDTH / 2 + 5,400,200,100,myGdxGame.commonBlackFont,GameResources.BUTTON_BACKGROUND_IMG_PATH,"Continue");
        pauseTextView = new TextView(myGdxGame.largeWhiteFont, GameSettings.SCREEN_WIDTH / 2 - 90,700,"Pause");
        recordsListView = new RecordsListView(myGdxGame.commonWhiteFont, 690);
        recordsTextView = new TextView(myGdxGame.largeWhiteFont, 206, 842, "Last records");
        homeButton2 = new ButtonView(
                280, 365,
                160, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Home"
        );
        liveView = new LiveView(305, 1215);
    }
    @Override
    public void show() {
        restartGame();
    }

    @Override
    public void render(float delta) {
        handleInput();
        if (gameSession.state == GameState.PLAYING) {
            if (gameSession.shouldSpawnTrash()) {
                TrashObject trashObject = new TrashObject(GameResources.TRASH_IMG_PATH, GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT, myGdxGame.world);
                trashArray.add(trashObject);
            }
            if (shipObject.needToShoot()) {
                BulletObject bulletObject = new BulletObject(GameResources.BULLET_IMG_PATH, shipObject.getX(), shipObject.getY() + 25 + GameSettings.SHIP_HEIGHT / 2, GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT, myGdxGame.world);
                bulletArray.add(bulletObject);
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.shootSound.play(1f);
            }
            if (!shipObject.isAlive()) {
                gameSession.endGame();
                recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }
            gameSession.updateScore();
            scoreTextView.setText("Score: " + gameSession.getScore());

            liveView.setLeftLives(shipObject.getLiveLeft());
            updateBullets();
            updateTrash();
            backgroundView.move();
            myGdxGame.stepWorld();
        }
        draw();
    }
    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            shipObject.move(myGdxGame.touch);
        }
        switch (gameSession.state) {
            case PLAYING:
                if (pauseButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                    gameSession.pauseGame();
                }
                break;

            case PAUSED:
                if (continueButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                    gameSession.resumeGame();
                }
                if (homeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                    myGdxGame.setScreen(myGdxGame.menuScreen);
                }
                break;
            case ENDED:
                if (homeButton2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                    myGdxGame.setScreen(myGdxGame.menuScreen);
                }
                break;
        }

    }

    private void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);
        shipObject.draw(myGdxGame.batch);
        for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.batch);
        for (TrashObject trash : trashArray) trash.draw(myGdxGame.batch);
        topBlackoutView.draw(myGdxGame.batch);
        scoreTextView.draw(myGdxGame.batch);
        liveView.draw(myGdxGame.batch);
        if (gameSession.state == GameState.PAUSED) {
            fullBlackoutView.draw(myGdxGame.batch);
            homeButton.draw(myGdxGame.batch);
            continueButton.draw(myGdxGame.batch);
            pauseTextView.draw(myGdxGame.batch);
        } else if (gameSession.state == GameState.ENDED) {
            fullBlackoutView.draw(myGdxGame.batch);
            recordsTextView.draw(myGdxGame.batch);
            recordsListView.draw(myGdxGame.batch);
            homeButton2.draw(myGdxGame.batch);
        }
        pauseButton.draw(myGdxGame.batch);
        myGdxGame.batch.end();
    }
    private void updateTrash() {
        for (int i = 0; i < trashArray.size(); i++) {
            boolean hasToBeDestroyed = !trashArray.get(i).isAlive() || !trashArray.get(i).isInFrame();

            if (!trashArray.get(i).isAlive()) {
                gameSession.destructionRegistration();
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.explosionSound.play(0.1f);
            }

            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(trashArray.get(i).body);
                trashArray.remove(i--);
            }
        }
    }

    private void updateBullets() {
        for (int i = 0; i < bulletArray.size(); i++) {
            if (bulletArray.get(i).hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(bulletArray.get(i).body);
                bulletArray.remove(i--);
            }
        }
    }
    private void restartGame() {

        for (int i = 0; i < trashArray.size(); i++) {
            myGdxGame.world.destroyBody(trashArray.get(i).body);
            trashArray.remove(i--);
        }

        if (shipObject != null) {
            myGdxGame.world.destroyBody(shipObject.body);
        }

        shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                myGdxGame.world
        );

        bulletArray.clear();
        gameSession.startGame();
    }

}
