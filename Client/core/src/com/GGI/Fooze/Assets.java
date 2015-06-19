package com.GGI.Fooze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Assets {

	/**Images*/
	public Texture sprint;
	
	/**fonts*/
	public BitmapFont font;
	public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
	public Fooze f;
	
	public Assets(Fooze fooze){
		this.f=fooze;
		//loadImages();
		loadFonts();
		loadSaves();
	}

	private void loadSaves() {
		
		if(!Gdx.files.local("Stats.txt").exists()){
			FileHandle from = Gdx.files.internal("Stats.txt");
			from.copyTo(Gdx.files.local("Stats.txt"));
		}
		
			
			
			FileHandle file = Gdx.files.local("Stats.txt");
			String[] text = file.readString().split(":");
			f.money = Integer.parseInt(text[0]);
			f.color = new Color(Float.parseFloat(text[1]),Float.parseFloat(text[2]),Float.parseFloat(text[3]),1);
			f.unlock = Integer.parseInt(text[4]);
		
	}

	private void loadFonts() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("GROBOLD.ttf"));
		
		font = generator.generateFont(150,FONT_CHARACTERS,false);
		generator.dispose();
		font.setColor(1,1,1,1);
		
	}

	private void loadImages() {
		
		
	}
	
}
