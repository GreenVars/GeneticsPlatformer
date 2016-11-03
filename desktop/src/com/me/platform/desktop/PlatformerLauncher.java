package com.me.platform.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.me.platform.Platformer;

public class PlatformerLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 400;
		config.width = 600;
		new LwjglApplication(new Platformer(), config);
	}
}
