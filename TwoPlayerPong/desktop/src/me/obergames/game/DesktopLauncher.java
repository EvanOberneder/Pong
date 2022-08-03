package me.obergames.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(TwoPlayerPong.GAME_WIDTH, TwoPlayerPong.GAME_HEIGHT);
		config.setResizable(false);
		config.setTitle("PONG - v" + TwoPlayerPong.versionNumber);
		new Lwjgl3Application(new TwoPlayerPong(), config);
	}
}
