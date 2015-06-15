package com.GGI.Fooze.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.GGI.Fooze.ActionResolver;
import com.GGI.Fooze.Fooze;

public class DesktopLauncher implements ActionResolver{
	
	public DesktopLauncher(){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height=675;
		config.width=1200;
		new LwjglApplication(new Fooze(this), config);
	}
	
	public static void main (String[] arg) {
		new DesktopLauncher();
	}

	@Override
	public void showOrLoadInterstital() {
		// TODO Auto-generated method stub
		
	}
}
