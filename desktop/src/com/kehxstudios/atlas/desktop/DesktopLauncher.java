package com.kehxstudios.atlas.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kehxstudios.atlas.managers.GameManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Atlas";
		config.width = 480;
		config.height = 800;
		config.resizable = false;
		new LwjglApplication(new GameManager(null), config);
	}
}
