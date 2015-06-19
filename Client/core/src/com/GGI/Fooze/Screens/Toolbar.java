package com.GGI.Fooze.Screens;

import com.GGI.Fooze.Fooze;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Toolbar {

	public Fooze f;
	public ShapeRenderer shape;
	public SpriteBatch pic;
	public float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();
	public int select=0;
	public Rectangle play,color,shop,settings;
	
	public Toolbar(Fooze f){
		this.f=f;
		shape = new ShapeRenderer();
		pic = new SpriteBatch();
		
		play = new Rectangle(w*.003125f, h-((.254f*h)+(h*.0055f)), .09375f*w, .083f*h);
		color = new Rectangle(w*.003125f, h-((.342f*h)+(h*.0055f)), .09375f*w, .083f*h);
		shop = new Rectangle(w*.003125f, h-((.43f*h)+(h*.0055f)), .09375f*w, .083f*h);
		settings = new Rectangle(w*.003125f, h-((.518f*h)+(h*.0055f)), .09375f*w, .083f*h);
	}
	
	public void render(){
		shape.begin(ShapeType.Filled);
		
		//BG
		shape.setColor(Color.DARK_GRAY);
		shape.rect(0,0,w/10,h);
		//end BG
		
		//Title
		shape.setColor(Color.GRAY);
		shape.rect(w*.003125f, h-((.166f*h)+(h*.0055f)), .09375f*w, .166f*h);
		shape.setColor(new Color(0f/255f,171f/255f,235f/255f,1));
		shape.circle(w/15, 18f*h/20, .015f*w);
		shape.setColor(new Color(255f/255f,165f/255f,0f/255f,1));
		shape.circle(w/25, 18.5f*h/20, .025f*w);
		//end title
		
		//Play
		shape.setColor(Color.GRAY);
		if(select==0){shape.setColor(Color.LIGHT_GRAY);}
		shape.rect(play.x,play.y,play.width,play.height);
		//end play
		
		//Select Color
		shape.setColor(Color.GRAY);
		if(select==1){shape.setColor(Color.LIGHT_GRAY);}
		shape.rect(color.x,color.y,color.width,color.height);
		//end Select Color
		
		//Shop
		shape.setColor(Color.GRAY);
		if(select==2){shape.setColor(Color.LIGHT_GRAY);}
		shape.rect(shop.x,shop.y,shop.width,shop.height);
		//end Shop
		
		//settings
		shape.setColor(Color.GRAY);
		if(select==3){shape.setColor(Color.LIGHT_GRAY);}
		shape.rect(settings.x,settings.y,settings.width,settings.height);
		//end settings
		shape.end();
		
		pic.begin();
		f.assets.font.setColor(Color.WHITE);
		f.assets.font.setScale(w/10000);
		Vector2 center = new Vector2();
		f.assets.font.draw(pic, "Play", play.getCenter(center).x-(f.assets.font.getBounds("Play").width/2), play.getCenter(center).y+(f.assets.font.getBounds("Play").height/2));
		f.assets.font.draw(pic, "Color", color.getCenter(center).x-(f.assets.font.getBounds("Color").width/2), color.getCenter(center).y+(f.assets.font.getBounds("Color").height/2));
		f.assets.font.draw(pic, "Shop", shop.getCenter(center).x-(f.assets.font.getBounds("Shop").width/2), shop.getCenter(center).y+(f.assets.font.getBounds("Shop").height/2));
		f.assets.font.draw(pic, "Settings", settings.getCenter(center).x-(f.assets.font.getBounds("Settings").width/2), settings.getCenter(center).y+(f.assets.font.getBounds("Settings").height/2));
		f.assets.font.setScale(w/15000);
		f.assets.font.draw(pic, "$"+f.money, 0, 1.4f*f.assets.font.getBounds("$"+f.money).height);
		pic.end();
	}
	
	public void touch(Rectangle touch){
		if(Intersector.overlaps(touch, play)){select=0;}
		else if(Intersector.overlaps(touch, color)){select=1;}
		else if(Intersector.overlaps(touch, shop)){select=2;}
		else if(Intersector.overlaps(touch, settings)){select=3;}
	}
	
}
