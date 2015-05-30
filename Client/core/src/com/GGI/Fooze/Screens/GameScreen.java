/**
 * 
 */
package com.GGI.Fooze.Screens;

import java.util.ArrayList;

import com.GGI.Fooze.Fooze;
import com.GGI.Fooze.Objects.Food;
import com.GGI.Fooze.Objects.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

/**
 * @author Emmett Deen
 *
 */
public class GameScreen implements Screen ,InputProcessor{

	public Fooze f;
	public ShapeRenderer shape;
	public float gridx =0,gridy=0;
	public float size=0;
	public float view = 0;
	public float step = 0;
	public float w=Gdx.graphics.getWidth(),h=Gdx.graphics.getHeight();
	public float xOff=0,yOff=0;
	public boolean xM=false,xP=false,yM=false,yP=false;
	public ArrayList<Food> food = new ArrayList<Food>();
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<Color> colors = new ArrayList<Color>();
	
	//player vars
	public final double initMass=10;
	public final int speedMul=2;
	public float mass=10;
	public float massToAdd = 0;
	public float speed = 1;
	public float xPos,yPos;
	private int x=0;
	
	public GameScreen(Fooze f){
		this.f=f;
		this.gridx=f.gridx;this.gridy=f.gridy;this.size=f.size;
		
		shape=new ShapeRenderer();
		
		view=getRadius(mass)*10;
		step = h/view;
		System.out.println(view);
		xPos=(float) (Math.random()*size*.8+.1*size);
		yPos=(float) (Math.random()*size*.8+.1*size);
		
		
	}
	
	private float getRadius(float mass){
		return (float) Math.sqrt(10*mass/Math.PI);
	}
	
	
	

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		players.clear();players.addAll(f.players);
		System.out.println(players.size());
		food.clear();food.addAll(f.food);
		//System.out.println(food.size());
		mass+=.1*massToAdd;massToAdd*=.9;
		if(mass>initMass){mass*=.9999;}
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		//Cam Update
		speed=(float) (speedMul/Math.pow(mass,(1/3)));
		view=getRadius(mass)*10;
		step = h/view;
		
		if(xM){xPos-=speed;}
		if(xP){xPos+=speed;}
		if(yM){yPos-=speed;}
		if(yP){yPos+=speed;}
	//	System.out.println("X: " + xPos+ " Y: "+yPos+" M: " + mass);
		
		xOff=-xPos*step+(w/2);yOff=-yPos*step+(h/2);
		
		//wall collision
		if(xPos>size-getRadius(mass)){xPos=size-getRadius(mass);}
		if(yPos>size-getRadius(mass)){yPos=size-getRadius(mass);}
		if(xPos<getRadius(mass)){xPos=getRadius(mass);}
		if(yPos<getRadius(mass)){yPos=getRadius(mass);}
		
		//Render
		shape.begin(ShapeType.Line);
		shape.setColor(Color.GRAY);
		for(int i = 0;i<=gridx;i++){
			shape.line(0+xOff, i*step*(size/gridx)+yOff, gridy*step*(size/gridx)+xOff, i*step*(size/gridx)+yOff);
		}
		for(int i = 0;i<=gridy;i++){
			shape.line(i*step*(size/gridx)+xOff, 0+yOff,i*step*(size/gridx)+xOff,  gridx*step*(size/gridx)+yOff);
		}
		shape.end();
		
		
		
		shape.begin(ShapeType.Filled);
		for(int i = 0; i<food.size();i++){
			shape.setColor(food.get(i).color);
			shape.circle(food.get(i).x*step+xOff, food.get(i).y*step+yOff, getRadius(1)*step);
		}
		
		shape.setColor(Color.RED);
		//shape.circle(xPos*step+xOff, yPos*step+yOff, getRadius(mass)*step);
		
		for(int i =0;i<players.size();i++){
			Player p = players.get(i);
			shape.circle(p.x*step+xOff, p.y*step+yOff, getRadius(p.mass)*step);
		}
		
		shape.end();
		
		//eat food
		Circle t = new Circle(xPos*step+xOff, yPos*step+yOff, getRadius(mass)*step);
		for(int i = 0; i < food.size();i++){
			if(Intersector.overlaps(t, new Circle(food.get(i).x*step+xOff, food.get(i).y*step+yOff, getRadius(1)*step))){
				f.send("eatFood:"+i);
				food.remove(i);
				massToAdd+=1f;

			}
		}
		//x++;
		//if(x>=2){
		f.send("Render:"+xPos+":"+yPos+":"+mass);
		//x=0;
		//}
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
		
		if(screenX<w/3){xM=true;}
		if(screenX>(2*w/3)){xP=true;}
		if(screenY<h/3){yM=true;}
		if(screenY>(2*h/3)){yP=true;}
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
