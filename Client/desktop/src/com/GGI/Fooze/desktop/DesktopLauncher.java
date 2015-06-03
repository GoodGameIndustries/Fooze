package com.GGI.Fooze.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.GGI.Fooze.Fooze;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height=675;
		config.width=1200;
		new LwjglApplication(new Fooze(), config);
	}
}
