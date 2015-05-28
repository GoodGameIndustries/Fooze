/**
 * 
 */
package com.GGI.Fooze.Screens;

import com.GGI.Fooze.Fooze;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * @author Emmett Deen
 *
 */
public class GameScreen implements Screen ,InputProcessor{

	public Fooze f;
	public ShapeRenderer shape;
	public float gridx =100,gridy=100; 
	public float view = 7;
	public float step = 0;
	public float w=Gdx.graphics.getWidth(),h=Gdx.graphics.getHeight();
	public float xOff=0,yOff=0;
	public boolean xM=false,xP=false,yM=false,yP=false;
	
	//player vars
	public final int initMass=500;
	public final int speedMul=3;
	public int mass=500;
	public int speed = 3;
	
	public GameScreen(Fooze f){
		this.f=f;
		xOff=(float) (Math.random()*gridx*step);
		yOff=(float) (Math.random()*gridy*step);
		shape=new ShapeRenderer();
		
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		//Cam Update
		speed=(initMass/mass)*speedMul;
		view=mass/75;
		step = h/view;
		
		if(xM){xOff+=speed;}
		if(xP){xOff-=speed;}
		if(yM){yOff+=speed;}
		if(yP){yOff-=speed;}
		
		if(xOff>(w/2)-(mass*.001f)*step){xOff=(w/2)-(mass*.001f)*step;}
		if(yOff>(h/2)-(mass*.001f)*step){yOff=(h/2)-(mass*.001f)*step;}
		if(xOff<-((step*gridy)-(w/2)-(mass*.001f)*step)){xOff=-((step*gridy)-(w/2)-(mass*.001f)*step);}
		if(yOff<-((step*gridx)-(h/2)-(mass*.001f)*step)){yOff=-((step*gridx)-(h/2)-(mass*.001f)*step);}
		
		//Render
		shape.begin(ShapeType.Line);
		shape.setColor(Color.GRAY);
		for(int i = 0;i<=gridx;i++){
			shape.line(0+xOff, i*step+yOff, gridy*step+xOff, i*step+yOff);
		}
		for(int i = 0;i<=gridy;i++){
			shape.line(i*step+xOff, 0+yOff,i*step+xOff,  gridx*step+yOff);
		}
		shape.end();
		
		shape.begin(ShapeType.Filled);
		shape.setColor(Color.RED);
		shape.circle(w/2, h/2, (mass*.001f)*step);
		shape.end();
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		screenY=(int) (h-screenY);
		
		xM=false;xP=false;yM=false;yP=false;
		
		if(screenX<w/4){xM=true;}
		if(screenX>(3*w/4)){xP=true;}
		if(screenY<h/4){yM=true;}
		if(screenY>(3*h/4)){yP=true;}
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
