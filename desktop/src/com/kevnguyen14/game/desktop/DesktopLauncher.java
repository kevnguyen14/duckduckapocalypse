package com.kevnguyen14.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kevnguyen14.game.DesktopGoogleServices;
import com.kevnguyen14.game.duckgame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = duckgame.TITLE;
		config.width = duckgame.V_WIDTH * duckgame.SCALE;
		config.height = duckgame.V_WIDTH * duckgame.SCALE;
		new LwjglApplication(new duckgame(new DesktopGoogleServices()), config);
	}
}
