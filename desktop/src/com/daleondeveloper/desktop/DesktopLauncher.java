package com.daleondeveloper.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.daleondeveloper.Game.ElMaster;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = ElMaster.TITLE;
		config.width = (int) (ElMaster.APPLICATION_WIDTH * 0.75f);
		config.height = (int) (ElMaster.APPLICATION_HEIGHT * 0.75f);
		new LwjglApplication(new ElMaster(null), config);
	}
}
