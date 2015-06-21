package com.GGI.Fooze.Screens;

import com.GGI.Fooze.Fooze;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ShopScreen implements Screen, InputProcessor{

	public Fooze f;
	public float w=Gdx.graphics.getWidth(),h=Gdx.graphics.getHeight();
	private ShapeRenderer shape;
	private SpriteBatch pic;
	private Rectangle buy;
	
	public ShopScreen(Fooze f){
		this.f=f;
		shape = new ShapeRenderer();
		pic = new SpriteBatch();
		buy=new Rectangle();
		//f.money=100000;
	}
	
	@Override
	public void render(float delta) {
		switch(f.toolbar.select){
		case 0:f.setScreen(new MainScreen(f));break;
		case 1:f.setScreen(new ColorScreen(f));break;
		case 3:f.setScreen(new SettingsScreen(f));break;
		}
		
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		shape.begin(ShapeType.Line);
		shape.setColor(Color.GRAY);
	for(int i = 0; i < 25;i++){
		
		shape.line((w/25)*i, 0, (w/25)*i, h);
		shape.line(0, (w/25)*i, w, (w/25)*i);
	}
		shape.end();
		
		shape.begin(ShapeType.Filled);
		shape.setColor(Color.GRAY);
		shape.rect(w/9, h/10, .85f*w, .8f*h);
		//f.unlock=6;
		buy = new Rectangle(w/3,(.715f-(.1f*f.unlock))*h,.1f*w,.05f*h);
		
		shape.setColor(Color.LIGHT_GRAY);
		if(f.unlock<6){
		shape.rect(buy.x,buy.y,buy.width,buy.height);
		}
		shape.end();
		
		pic.begin();
		f.assets.font.setColor(Color.WHITE);
		f.assets.font.setScale(h/5000);
		f.assets.font.draw(pic, "Basic-          Owned", w/8, .86f*h);
		f.assets.font.draw(pic, "Tier 1-         "+(f.unlock>0?"Owned":"$100"), w/8, .76f*h);
		f.assets.font.setColor(f.unlock>0?Color.WHITE:Color.LIGHT_GRAY);
		f.assets.font.draw(pic, "Tier 2-         "+(f.unlock>1?"Owned":"$500"), w/8, .66f*h);
		f.assets.font.setColor(f.unlock>1?Color.WHITE:Color.LIGHT_GRAY);
		f.assets.font.draw(pic, "Tier 3-         "+(f.unlock>2?"Owned":"$1000"), w/8, .56f*h);
		f.assets.font.setColor(f.unlock>2?Color.WHITE:Color.LIGHT_GRAY);
		f.assets.font.draw(pic, "Tier 4-         "+(f.unlock>3?"Owned":"$5000"), w/8, .46f*h);
		f.assets.font.setColor(f.unlock>3?Color.WHITE:Color.LIGHT_GRAY);
		f.assets.font.draw(pic, "Tier 5-         "+(f.unlock>4?"Owned":"$10000"), w/8, .36f*h);
		f.assets.font.setColor(f.unlock>4?Color.WHITE:Color.LIGHT_GRAY);
		f.assets.font.draw(pic, "Tier 6-         "+(f.unlock>5?"Owned":"$50000"), w/8, .26f*h);
		f.assets.font.setColor(Color.WHITE);
		f.assets.font.draw(pic, "Your money: $"+f.money, w/8, .16f*h);
		
		Vector2 center = new Vector2();
		buy.getCenter(center);
		if(f.unlock<6){
		f.assets.font.draw(pic, "Buy", center.x-(f.assets.font.getBounds("Buy").width/2), center.y+(f.assets.font.getBounds("Buy").height/2));
		}
		pic.end();
		
		f.toolbar.render();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenY=(int) (h-screenY);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenY=(int) (h-screenY);
		Rectangle touch = new Rectangle(screenX,screenY,1,1);
		if(Intersector.overlaps(touch, buy)){
			switch(f.unlock){
			case 0: if(f.money>=100){f.unlock++;f.money-=100;}break;
			case 1: if(f.money>=500){f.unlock++;f.money-=500;}break;
			case 2: if(f.money>=1000){f.unlock++;f.money-=1000;}break;
			case 3: if(f.money>=5000){f.unlock++;f.money-=5000;}break;
			case 4: if(f.money>=10000){f.unlock++;f.money-=10000;}break;
			case 5: if(f.money>=50000){f.unlock++;f.money-=50000;}break;	
			}
			f.save();
		}
		
		if(screenX<w/10){f.toolbar.touch(touch);}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
