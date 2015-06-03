package com.GGI.Fooze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Assets {

	/**Images*/
	public Texture sprint;
	
	/**fonts*/
	public BitmapFont font;
	public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
	
	
	public Assets(Fooze fooze){
		//loadImages();
		loadFonts();
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
