package com.GGI.Fooze.Screens;

import com.GGI.Fooze.Fooze;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class SettingsScreen implements Screen, InputProcessor{

	public Fooze f;
	public float w=Gdx.graphics.getWidth(),h=Gdx.graphics.getHeight();
	private ShapeRenderer shape;
	
	public SettingsScreen(Fooze f){
		this.f=f;
		shape = new ShapeRenderer();
	}
	
	@Override
	public void render(float delta) {
		switch(f.toolbar.select){
		case 0:f.setScreen(new MainScreen(f));break;
		case 1:f.setScreen(new ColorScreen(f));break;
		case 2:f.setScreen(new ShopScreen(f));break;
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
