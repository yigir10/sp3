package ru.samsung.gamestudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.Gdx;

import ru.samsung.gamestudio.screens.GameScreen;
import ru.samsung.gamestudio.screens.MenuScreen;
import ru.samsung.gamestudio.screens.SettingsScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public AudioManager audioManager;
	public GameScreen gameScreen;
	public MenuScreen menuScreen;
	public World world;
	public SettingsScreen settingsScreen;
	float accumulator = 0;
	public Vector3 touch;
	public BitmapFont commonWhiteFont;
	public BitmapFont largeWhiteFont;
	public BitmapFont commonBlackFont;

	@Override
	public void create () {
		Box2D.init();
		world = new World(new Vector2(0, 0), true);
		audioManager = new AudioManager();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,720,1280);
		touch = new Vector3();
		commonWhiteFont = FontBuilder.generate(24, Color.WHITE, GameResources.FONT_PATH);
		commonBlackFont = FontBuilder.generate(24,Color.BLACK, GameResources.FONT_PATH);
		largeWhiteFont = FontBuilder.generate(48,Color.WHITE,GameResources.FONT_PATH);
		gameScreen = new GameScreen(this);
		settingsScreen = new SettingsScreen(this);
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public void stepWorld() {
		float delta = Gdx.graphics.getDeltaTime();
		accumulator += delta;

		if (accumulator >= GameSettings.STEP_TIME) {
			accumulator -= GameSettings.STEP_TIME;
			world.step(GameSettings.STEP_TIME, GameSettings.VELOCITY_ITERATIONS, GameSettings.POSITION_ITERATIONS);
		}
	}
}
