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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

/**
 * @author Emmett Deen
 *
 */
public class MainScreen implements InputProcessor,Screen {

	public Fooze f;
	public ShapeRenderer shape;
	public SpriteBatch pic;
	public float w = Gdx.graphics.getWidth(),h=Gdx.graphics.getHeight();
	private String u = "";
	private boolean type=false;
	private int select = 0;
	
	
	public MainScreen(Fooze f){
		this.f=f;
		f.die=false;
		f.nextScreen=false;
		shape = new ShapeRenderer();
		pic = new SpriteBatch();
		f.cState=0;
		
	}
	
	@Override
	public void render(float delta) {
		switch(f.toolbar.select){
		case 1:f.setScreen(new ColorScreen(f));break;
		case 2:f.setScreen(new ShopScreen(f));break;
		case 3:f.setScreen(new SettingsScreen(f));break;
		}
		
		//System.out.println(f.r);
		if(f.nextScreen){f.setScreen(new GameScreen(f));f.nextScreen=false;}
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		for(int i = 0; i < f.messages.size();i++){
			System.out.println(f.messages.get(i));
			String[] breakdown = f.messages.get(i).split(":");
			if(breakdown[0].equals("Online")){f.nextScreen=true;
			f.size=Float.parseFloat(breakdown[1]);
			f.gridx=Float.parseFloat(breakdown[2]);
			f.gridy=Float.parseFloat(breakdown[3]);
			f.ID=Integer.parseInt(breakdown[4]);
			}
		}
		
	f.messages.clear();
		
		shape.begin(ShapeType.Line);
		shape.setColor(Color.GRAY);
	for(int i = 0; i < 25;i++){
		
		shape.line((w/25)*i, 0, (w/25)*i, h);
		shape.line(0, (w/25)*i, w, (w/25)*i);
	}
		shape.end();
	
		shape.begin(ShapeType.Filled);
		shape.setColor(Color.GRAY);
		shape.rect(w/4-5, h/4-5, w/2+10, h/2+10);
		shape.setColor(Color.LIGHT_GRAY);
		shape.rect(w/4, h/4, w/2, h/2);
		shape.setColor(Color.BLUE);
		shape.circle(.7f*w, .7f*h, .02f*w);
		shape.setColor(Color.BLACK);
		shape.triangle(.6925f*w, .72f*h, .6925f*w, .68f*h, .7125f*w, .7f*h);
		
		shape.setColor(f.color);
		shape.rect(.618f*w, .51f*h, .05f*w, .05f*w);
		shape.end();
		/*shape.setColor(Color.BLACK);
		shape.rect(.268f*w, .5f*h, .05f*w, .05f*w);
		shape.setColor(Color.RED);
		shape.rect(.338f*w, .5f*h, .05f*w, .05f*w);
		shape.setColor(Color.GREEN);
		shape.rect(.408f*w, .5f*h, .05f*w, .05f*w);
		shape.setColor(Color.BLUE);
		shape.rect(.478f*w, .5f*h, .05f*w, .05f*w);
		shape.setColor(Color.PURPLE);
		shape.rect(.548f*w, .5f*h, .05f*w, .05f*w);
		shape.setColor(Color.YELLOW);
		shape.rect(.618f*w, .5f*h, .05f*w, .05f*w);
		shape.end();
		*/
		shape.begin(ShapeType.Line);
		shape.setColor(Color.BLACK);
		shape.rect(.268f*w,.66f*h,.4f*w,.08f*h);
		//shape.rect((.268f+(select *.07f))*w,.5f*h,.05f*w,.05f*w);
		shape.end();
		
		
		pic.begin();
		f.assets.font.setColor(Color.GRAY);
		f.assets.font.setScale(h/1000);
		f.assets.font.draw(pic,"Fooze",(w/2)-(f.assets.font.getBounds("Fooze").width/2),(float) (h-(f.assets.font.getBounds("Fooze").height*.1)));
		
		
		f.assets.font.setColor(Color.WHITE);
		f.assets.font.setScale(h/2000);
		u=u.replaceAll("\\p{Cntrl}","");
		f.assets.font.draw(pic, u, .269f*w, .74f*h);
		f.assets.font.draw(pic, "Selected Color- ", .269f*w, .6f*h);
		
		f.assets.font.setScale(h/3000);
		if(f.cState==1){
			f.assets.font.setColor(Color.WHITE);
			f.assets.font.draw(pic, "Connecting to server...", .269f*w, .4f*h);
		}
		else if(f.cState==2){
			f.assets.font.setColor(Color.RED);
			f.assets.font.draw(pic, "Error connecting to server...", .269f*w, .4f*h);
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
		if(type){
			if(character == ''&&u.length()>0){
				u=u.substring(0, u.length()-1);
			}
			else if((character == '\r' || character == '\n')){}
			else if(u.length()<10){
				u+=character;
			}
		}
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenY=(int) (h-screenY);
		Rectangle touch = new Rectangle(screenX,screenY,1,1);
		if(screenX<w/10){f.toolbar.touch(touch);}
		
		if(!type){
		
		//Color select
			/*
			if(Intersector.overlaps(touch, new Rectangle(.268f*w,.5f*h,.05f*w,.05f*w))){select=0;}
			else if(Intersector.overlaps(touch, new Rectangle(.338f*w,.5f*h,.05f*w,.05f*w))){select=1;}
			else if(Intersector.overlaps(touch, new Rectangle(.408f*w,.5f*h,.05f*w,.05f*w))){select=2;}
			else if(Intersector.overlaps(touch, new Rectangle(.478f*w,.5f*h,.05f*w,.05f*w))){select=3;}
			else if(Intersector.overlaps(touch, new Rectangle(.548f*w,.5f*h,.05f*w,.05f*w))){select=4;}
			else if(Intersector.overlaps(touch, new Rectangle(.618f*w,.5f*h,.05f*w,.05f*w))){select=5;}
			*/
			Rectangle name = new Rectangle(.268f*w,.66f*h,.4f*w,.08f*h);
		if(Intersector.overlaps(touch, name)){
			type = true;
			Gdx.input.setOnscreenKeyboardVisible(true);
			
		}}
		else{
			type = false;
			Gdx.input.setOnscreenKeyboardVisible(false);
		}
		if(Intersector.overlaps(new Circle(.7f*w, .7f*h, .02f*w), touch)){
		f.name=u;
		switch(select){
		case 0:f.color=Color.BLACK;break;
		case 1:f.color=Color.RED;break;
		case 2:f.color=Color.GREEN;break;
		case 3:f.color=Color.BLUE;break;
		case 4:f.color=Color.PURPLE;break;
		case 5:f.color=Color.YELLOW;break;
		}
		
		f.connect();
		for(int i = 0;i<100;i++){
		f.send("Connect");
		}
		
		
		}
		
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
