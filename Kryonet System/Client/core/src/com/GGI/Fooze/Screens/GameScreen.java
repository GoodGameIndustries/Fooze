/**
 * 
 */
package com.GGI.Fooze.Screens;

import java.util.ArrayList;

import NetworkClasses.Character;
import NetworkClasses.MoveCharacter;

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
	public ArrayList<Character> players = new ArrayList<Character>();
	public ArrayList<Color> colors = new ArrayList<Color>();
	public SpriteBatch pic;
	
	
	//player vars
	public final double initMass=10;
	public final double speedMul=3;
	
	public float massToAdd = 0;
	public float speed = 1;
	private int x=0;
	public boolean isSprint=false;
	public float theta=361;
	public boolean flip = false;
	public Thread synch;
	private int t=0;
	private int r=0;
	private int s=0;
	private boolean move=false;
	
	public GameScreen(Fooze f){
		this.f=f;
		this.gridx=f.gridx;this.gridy=f.gridy;this.size=f.size;
		f.die=false;
		f.messages.clear();
		pic=new SpriteBatch();
		shape=new ShapeRenderer();
		f.mass=10;
		view=getRadius(f.mass)*10;
		step = h/view;
		System.out.println(view);
		f.xPos=(float) (Math.random()*size*.8+.1*size);
		f.yPos=(float) (Math.random()*size*.8+.1*size);
		
		populate();
		//synch=new Thread(new Synch(f));
		//synch.start();
		//f.send("Render:"+f.name+":"+f.xPos+":"+f.yPos+":"+f.mass+":"+f.ID+":"+f.color.r+":"+f.color.g+":"+f.color.b);
		//f.send("Render:"+f.name+":"+f.xPos+":"+f.yPos+":"+f.mass+":"+f.ID+":"+f.color.r+":"+f.color.g+":"+f.color.b);
		//f.send("Render:"+f.name+":"+f.xPos+":"+f.yPos+":"+f.mass+":"+f.ID+":"+f.color.r+":"+f.color.g+":"+f.color.b);
		
		f.connect.move();
		
		//f.players.clear();
	}
	
	

	private float getRadius(float mass){
		return (float) Math.sqrt(12*mass/Math.PI);
	}
	
	private void addFood(float x,float y,float r,float g,float b) {
		f.food.add(new Food(new Color(r,g,b,1)));
		f.food.get(f.food.size()-1).x=x;
		f.food.get(f.food.size()-1).y=y;
	}
	
	

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		f.isRender=true;
		if(f.players.size()==0){f.connect.updateAll();s++;}
		//if(s>5){f.die=true;}
		
		for(int i = 0;i<f.players.size()-1;i++){
			for(int j=1;j<f.players.size();j++){
				if(f.players.get(i).id==f.players.get(j).id){f.players.remove(j);}
			}
		}
		
		
		//System.out.println(f.r);
		/**Read messages*/
		/*
		if(f.messages.size()>0){
		for(int i = 0; i <Math.round((f.messages.size()/10)+1);i++){
			if(f.messages.get(i)!=null){
			String[] breakdown = f.messages.get(i).split(":");
			if(breakdown[0].equals("Food")){f.food.clear();
			for(int j=1;j<breakdown.length;j++){
				String[] b2 = breakdown[j].split(",");
				addFood(Float.parseFloat(b2[0]),Float.parseFloat(b2[1]),Float.parseFloat(b2[2]),Float.parseFloat(b2[3]),Float.parseFloat(b2[4]));
			}
			f.update=true;
		}
		
		
		if(breakdown[0].equals("lose")){f.die=true;}
		if(breakdown[0].equals("addMass")){f.massToAdd=Float.parseFloat(breakdown[1]);f.money+=(int)(Float.parseFloat(breakdown[1]));f.save();}
			}
		f.messages.remove(i);
		}
		}
		*/
		/**end read messages*/
		
		
		
		for(int i = 0;i<f.players.size();i++){
			f.players.get(i).render();
		}
		
		
		try{
		for(int j =0;j<f.players.size();j++){
		for(int i =0;i<f.players.size()-1;i++){
			if(f.players.get(i+1).mass>f.players.get(i).mass){
				Character temp = f.players.get(i+1);
				f.players.remove(i+1);
				f.players.add(i, temp);
			}
		}
		}
		}
		catch(Exception e){
			
		}
		
		
		
		
		massToAdd+=f.massToAdd;f.massToAdd=0;
		players.clear();players.addAll(f.players);
		//System.out.println(players.size());
		//food.clear();food.addAll(f.food);
		//System.out.println(food.size());
		f.mass+=.1*massToAdd;massToAdd*=.9;
		if(f.mass>initMass){f.mass*=.9999;}
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		//Cam Update
		speed=(float) (speedMul/Math.pow(f.mass,(1/2.9)));
		view=getRadius(f.mass)*10;
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
		if(flip){f.xPos-=speed*Math.cos(theta);
		f.yPos-=speed*Math.sin(theta);}
			
		else{f.xPos+=speed*Math.cos(theta);
		f.yPos+=speed*Math.sin(theta);}
		
		//f.send("Render:"+f.name+":"+f.xPos+":"+f.yPos+":"+f.mass+":"+f.ID+":"+f.color.r+":"+f.color.g+":"+f.color.b);
		move=true;
		//System.out.println("Render");
		}
		
		if(move){
			if(r>5){
			f.connect.move();
			r=0;
			move=false;
			}else{r++;}
		}
		
		
		
	//	System.out.println("X: " + xPos+ " Y: "+yPos+" M: " + mass);
		
		xOff=-f.xPos*step+(w/2);yOff=-f.yPos*step+(h/2);
		
		//wall collision
		if(f.xPos>size-getRadius(f.mass)){f.xPos=size-getRadius(f.mass);}
		if(f.yPos>size-getRadius(f.mass)){f.yPos=size-getRadius(f.mass);}
		if(f.xPos<getRadius(f.mass)){f.xPos=getRadius(f.mass);}
		if(f.yPos<getRadius(f.mass)){f.yPos=getRadius(f.mass);}
		
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
		
		
		//System.out.println(players.size());
		for(int i =0;i<players.size();i++){
			Character p = players.get(i);
			if(p!=null&&p.id!=f.ID){
				
				Color color = new Color(p.r,p.g,p.b,1);
				
			shape.setColor(color.r*.8f,color.g*.8f,color.b*.8f,1);
			shape.circle(p.x*step+xOff, p.y*step+yOff, getRadius(p.mass)*step);
			shape.setColor(color);
			shape.circle(p.x*step+xOff, p.y*step+yOff, getRadius(p.mass)*step*.9f);
			
			
			}
			
			shape.setColor(f.color.r*.8f,f.color.g*.8f,f.color.b*.8f,1);
			shape.circle(f.xPos*step+xOff, f.yPos*step+yOff, getRadius(f.mass)*step);
			shape.setColor(f.color);
			shape.circle(f.xPos*step+xOff, f.yPos*step+yOff, getRadius(f.mass)*step*.9f);
		}
		
		if(isSprint){shape.setColor(Color.RED);}else{shape.setColor(Color.GREEN);}
		shape.rect(.925f*w, .15f*h, .025f*w, (f.sprint/f.sprintMax)*.7f*h);
		
		shape.setColor(Color.DARK_GRAY);
		shape.circle(.9375f*w, .08f*h, .05f*h);
		shape.setColor(Color.GRAY);
		shape.circle(.9375f*w, .08f*h, .04f*h);
		
		//shape.rect(0, 9*h/10, w, h/10);
		
		shape.end();
		
		pic.begin();
		for(int i =0;i<players.size();i++){
			Character p = players.get(i);
			if(p!=null&&p.mass!=0&&p.id!=f.ID){
			f.assets.font.setScale(h*getRadius(p.mass)*step/500000);
			f.assets.font.setColor(Color.WHITE);
			
			f.assets.font.draw(pic, p.name, (p.x*step+xOff)-(f.assets.font.getBounds(p.name).width/2), (p.y*step+yOff)+(f.assets.font.getBounds(p.name).height/2));
			//System.out.println(p.name);
			
			}
		}
		
		f.assets.font.setScale(h*getRadius(f.mass)*step/500000);
		f.assets.font.setColor(Color.WHITE);
		f.assets.font.draw(pic, f.name, (f.xPos*step+xOff)-(f.assets.font.getBounds(f.name).width/2), (f.yPos*step+yOff)+(f.assets.font.getBounds(f.name).height/2));
		
		try{
		f.assets.font.setScale(h/5000);
		f.assets.font.setColor(Color.BLACK);
		
		//System.out.println(f.players.size());
		f.assets.font.draw(pic, "Leaderboard:", 0, h);
		f.assets.font.setColor(Color.YELLOW);
		if(f.players.size()>0){f.assets.font.draw(pic, "1. "+f.players.get(0).name, 0, 19*h/20);}
		f.assets.font.setColor(Color.LIGHT_GRAY);
		if(f.players.size()>1){f.assets.font.draw(pic, "2. "+f.players.get(1).name, 0, 18*h/20);}
		f.assets.font.setColor(new Color(139f/255f,69f/255f,19f/255f,1));
		if(f.players.size()>2){f.assets.font.draw(pic, "3. "+f.players.get(2).name, 0, 17*h/20);}
		f.assets.font.setColor(Color.BLACK);
		if(f.players.size()>3){f.assets.font.draw(pic, "4. "+f.players.get(3).name, 0, 16*h/20);}
		if(f.players.size()>4){f.assets.font.draw(pic, "5. "+f.players.get(4).name, 0, 15*h/20);}
		if(f.players.size()>5){f.assets.font.draw(pic, "6. "+f.players.get(5).name, 0, 14*h/20);}
		}
		catch(Exception e){
			
		}
		pic.end();
		
		
		//eat food
		Circle t = new Circle(f.xPos*step+xOff, f.yPos*step+yOff, getRadius(f.mass)*step);
		for(int i = 0; i < food.size();i++){
			if(Intersector.overlaps(t, new Circle(food.get(i).x*step+xOff, food.get(i).y*step+yOff, getRadius(1)*step))){
				
				food.remove(i);
				massToAdd+=1f;
				f.money+=1;
				f.save();
				addFood();
			}
		}
		//x++;
		//if(x>=2){
		
		//x=0;
		//}
		
		
		if(f.die){f.popup();/*synch.destroy();*/
		Gdx.input.setInputProcessor(null);
		theta=361;
		 Gdx.gl.glEnable(GL30.GL_BLEND);
		    Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
		  // shape.setProjectionMatrix(camera.combined);
		   shape.begin(ShapeType.Filled);
		   shape.setColor(new Color(0, 0, 0, 0.5f));
		   shape.rect(0,0,w,h);
		   shape.end();
		   Gdx.gl.glDisable(GL30.GL_BLEND);
		if(this.t>50){
			pic.begin();
			f.assets.font.setColor(Color.RED);
		f.assets.font.setScale(w/5000);
			f.assets.font.draw(pic, "Wasted", (w/2)-(f.assets.font.getBounds("Wasted").width/2), (h/2)+(f.assets.font.getBounds("Wasted").height/2));
		pic.end();
		}
		if(this.t>200){f.setScreen(new MainScreen(f));}this.t++;}
		
		f.isRender=false;
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
