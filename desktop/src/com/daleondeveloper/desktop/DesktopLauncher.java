package com.daleondeveloper.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.daleondeveloper.Game.ElMaster;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = ElMaster.TITLE;
		config.width = ElMaster.APPLICATION_WIDTH;
		config.height = ElMaster.APPLICATION_HEIGHT;
		new LwjglApplication(new ElMaster(), config);
	}
}
