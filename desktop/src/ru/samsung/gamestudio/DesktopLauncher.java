package ru.samsung.gamestudio;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.samsung.gamestudio.MyGdxGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
		config.setForegroundFPS(60);
		config.setTitle("Space cleaner");
		new Lwjgl3Application(new MyGdxGame(), config);
	}
}
