package com.GGI.FoozeServer;

import java.util.Date;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public Main(){
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		Date d = new Date();
		cfg.title = "FoozeServer: " +d.toString();
		cfg.disableAudio=true;
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 320;
		
		new LwjglApplication(new FoozeServer(), cfg);
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
