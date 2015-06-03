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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

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
	public SpriteBatch pic;
	
	
	//player vars
	public final double initMass=10;
	public final int speedMul=2;
	public float mass=10;
	public float massToAdd = 0;
	public float speed = 1;
	public float xPos,yPos;
	private int x=0;
	public boolean isSprint=false;
	public float theta=361;
	public boolean flip = false;
	
	public GameScreen(Fooze f){
		this.f=f;
		this.gridx=f.gridx;this.gridy=f.gridy;this.size=f.size;
		f.die=false;
		f.messages.clear();
		pic=new SpriteBatch();
		shape=new ShapeRenderer();
		
		view=getRadius(mass)*10;
		step = h/view;
		System.out.println(view);
		xPos=(float) (Math.random()*size*.8+.1*size);
		yPos=(float) (Math.random()*size*.8+.1*size);
		
		populate();
	}
	
	private float getRadius(float mass){
		return (float) Math.sqrt(10*mass/Math.PI);
	}
	
	private void addFood(float x,float y,float r,float g,float b) {
		f.food.add(new Food(new Color(r,g,b,1)));
		f.food.get(f.food.size()-1).x=x;
		f.food.get(f.food.size()-1).y=y;
	}
	private void addPlayer(String name, float x, float y,float mass) {
		f.players.add(new Player(name,mass));
		f.players.get(f.players.size()-1).x=x;
		f.players.get(f.players.size()-1).y=y;
	}
	

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		
		/**Read messages*/
		
		for(int i = 0; i < f.messages.size();i++){
			String[] breakdown = f.messages.get(i).split(":");
			if(breakdown[0].equals("Food")){f.food.clear();
			for(int j=1;j<breakdown.length;j++){
				String[] b2 = breakdown[j].split(",");
				addFood(Float.parseFloat(b2[0]),Float.parseFloat(b2[1]),Float.parseFloat(b2[2]),Float.parseFloat(b2[3]),Float.parseFloat(b2[4]));
			}
			f.update=true;
		}
		if(breakdown[0].equals("Players")){f.players.clear();
			for(int j=1;j<breakdown.length;j++){
				String[] b2 = breakdown[j].split(",");
				addPlayer(b2[0],Float.parseFloat(b2[1]),Float.parseFloat(b2[2]),Float.parseFloat(b2[3]));
			}
		}
		
		if(breakdown[0].equals("lose")){f.die=true;}
		if(breakdown[0].equals("addMass")){f.massToAdd=Float.parseFloat(breakdown[1]);}
	
		}
		f.messages.clear();
		
		/**end read messages*/
		
		
		if(f.die){f.setScreen(new MainScreen(f));}
		massToAdd+=f.massToAdd;f.massToAdd=0;
		players.clear();players.addAll(f.players);
		//System.out.println(players.size());
		//food.clear();food.addAll(f.food);
		//System.out.println(food.size());
		mass+=.1*massToAdd;massToAdd*=.9;
		if(mass>initMass){mass*=.9999;}
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		//Cam Update
		speed=(float) (speedMul/Math.pow(mass,(1/3)));
		view=getRadius(mass)*10;
		step = h/view;
		if(isSprint){
			if(f.sprint<=0){isSprint=false;}
			else{speed*=f.sprintMul;f.sprint--;}}
		else if(f.sprint<f.sprintMax){f.sprint+=.1f;}
		//System.out.println(f.sprint);
			/*if(xM){xPos-=speed;}
			if(xP){xPos+=speed;}
			if(yM){yPos-=speed;}
			if(yP){yPos+=speed;}
			*/
		if(theta!=361){
		if(flip){xPos-=speed*Math.cos(theta);
		yPos-=speed*Math.sin(theta);}
			
		else{xPos+=speed*Math.cos(theta);
		yPos+=speed*Math.sin(theta);}
		}
		
			f.send("Render:"+f.name+":"+xPos+":"+yPos+":"+mass);
		
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
		
		
		//shape.circle(xPos*step+xOff, yPos*step+yOff, getRadius(mass)*step);
		
		for(int i =0;i<players.size();i++){
			Player p = players.get(i);
			if(p!=null){
				
			shape.setColor(p.color.r*.8f,p.color.g*.8f,p.color.b*.8f,1);
			shape.circle(p.x*step+xOff, p.y*step+yOff, getRadius(p.mass)*step);
			shape.setColor(p.color);
			shape.circle(p.x*step+xOff, p.y*step+yOff, getRadius(p.mass)*step*.9f);
			
			
			}
		}
		
		if(isSprint){shape.setColor(Color.RED);}else{shape.setColor(Color.GREEN);}
		shape.rect(.925f*w, .15f*h, .025f*w, (f.sprint/f.sprintMax)*.8f*h);
		
		shape.setColor(Color.DARK_GRAY);
		shape.circle(.9375f*w, .08f*h, .05f*h);
		shape.setColor(Color.GRAY);
		shape.circle(.9375f*w, .08f*h, .04f*h);
		shape.end();
		
		pic.begin();
		for(int i =0;i<players.size();i++){
			Player p = players.get(i);
			if(p!=null&&p.mass!=0){
			f.assets.font.setScale(h*getRadius(p.mass)*step/500000);
			f.assets.font.setColor(Color.WHITE);
			
			f.assets.font.draw(pic, p.name, (p.x*step+xOff)-(f.assets.font.getBounds(p.name).width/2), (p.y*step+yOff)+(f.assets.font.getBounds(p.name).height/2));
			//System.out.println(p.name);
			
			}
		}
		pic.end();
		
		
		//eat food
		Circle t = new Circle(xPos*step+xOff, yPos*step+yOff, getRadius(mass)*step);
		for(int i = 0; i < food.size();i++){
			if(Intersector.overlaps(t, new Circle(food.get(i).x*step+xOff, food.get(i).y*step+yOff, getRadius(1)*step))){
				
				food.remove(i);
				massToAdd+=1f;
				addFood();
			}
		}
		//x++;
		//if(x>=2){
		
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
		isSprint=true;
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		isSprint=false;
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//flip=false;
		screenY=(int) (h-screenY);
		//System.out.println(pointer);
		//f.name=""+pointer;
		if(Intersector.overlaps(new Circle(.9375f*w, .08f*h, .05f*h), new Rectangle(screenX,screenY,1,1))){isSprint = true;}
		else{
		theta = (float) Math.atan((screenY-(h/2))/(screenX-(w/2)));
		if(screenX<w/2){flip=true;}else{flip=false;}
		}
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		isSprint=false;
		screenY=(int) (h-screenY);
		theta = 361;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		//flip=false;
		screenY=(int) (h-screenY);
		if(Intersector.overlaps(new Circle(.9375f*w, .08f*h, .05f*h), new Rectangle(screenX,screenY,1,1))){isSprint = true;}
		else{
		theta = (float) Math.atan((screenY-(h/2))/(screenX-(w/2)));
		if(screenX<w/2){flip=true;}else{flip=false;}
		}
		
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
	/*	screenY=(int) (h-screenY);
		
		xM=false;xP=false;yM=false;yP=false;
		
		if(screenX<w/3){xM=true;}
		if(screenX>(2*w/3)){xP=true;}
		if(screenY<h/3){yM=true;}
		if(screenY>(2*h/3)){yP=true;}
	*/
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void populate() {
		//add colors
		colors.add(new Color(205/255f,74/255f,74/255f,1));
		colors.add(new Color(204/255f,102/255f,102/255f,1));
		colors.add(new Color(188/255f,93/255f,88/255f,1));
		colors.add(new Color(255/255f,83/255f,73/255f,1));
		colors.add(new Color(253/255f,94/255f,83/255f,1));
		colors.add(new Color(253/255f,124/255f,110/255f,1));
		colors.add(new Color(255/255f,110/255f,74/255f,1));
		colors.add(new Color(255/255f,117/255f,56/255f,1));
		colors.add(new Color(255/255f,163/255f,67/255f,1));
		colors.add(new Color(255/255f,207/255f,72/255f,1));
		colors.add(new Color(252/255f,217/255f,117/255f,1));
		colors.add(new Color(252/255f,232/255f,131/255f,1));
		colors.add(new Color(240/255f,232/255f,145/255f,1));
		colors.add(new Color(197/255f,227/255f,132/255f,1));
		colors.add(new Color(178/255f,236/255f,93/255f,1));
		colors.add(new Color(168/255f,228/255f,160/255f,1));
		colors.add(new Color(118/255f,255/255f,122/255f,1));
		colors.add(new Color(28/255f,172/255f,120/255f,1));
		colors.add(new Color(69/255f,206/255f,162/255f,1));
		colors.add(new Color(128/255f,218/255f,235/255f,1));
		colors.add(new Color(28/255f,169/255f,201/255f,1));
		colors.add(new Color(25/255f,116/255f,210/255f,1));
		colors.add(new Color(31/255f,117/255f,254/255f,1));
		colors.add(new Color(93/255f,118/255f,203/255f,1));
		colors.add(new Color(115/255f,102/255f,189/255f,1));
		colors.add(new Color(116/255f,66/255f,200/255f,1));
		colors.add(new Color(255/255f,67/255f,164/255f,1));
		
		
		//populate food
		for(int i=0;i<size;i++){
			addFood();
		}
		System.out.println(food.size());
	}
	
	public void addFood() {
		food.add(new Food(colors.get((int) (Math.random()*colors.size())).cpy()));
		food.get(food.size()-1).x=(float) (Math.random()*size);
		food.get(food.size()-1).y=(float) (Math.random()*size);
	}

}
