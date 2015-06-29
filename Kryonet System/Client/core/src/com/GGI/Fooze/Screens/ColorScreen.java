package com.GGI.Fooze.Screens;

import com.GGI.Fooze.Fooze;
import com.GGI.Fooze.Objects.ColorRect;
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

public class ColorScreen implements Screen, InputProcessor{
	
	public Fooze f;
	public float w=Gdx.graphics.getWidth(),h=Gdx.graphics.getHeight();
	private ShapeRenderer shape;
	private SpriteBatch pic;
	private ColorRect[][] colors=new ColorRect[6][7];
	
	public ColorScreen(Fooze f){
		this.f=f;
		shape = new ShapeRenderer();
		pic = new SpriteBatch();
		setupColors();
		//f.unlock=6;
	}

	private void setupColors() {
		//Done
		colors[0][0]=new ColorRect(.268f*w, .8f*h, .05f*w, .05f*w,0,0,0);
		colors[1][0]=new ColorRect(.338f*w, .8f*h, .05f*w, .05f*w,255,0,0); 
		colors[2][0]=new ColorRect(.408f*w, .8f*h, .05f*w, .05f*w,0,255,0);
		colors[3][0]=new ColorRect(.478f*w, .8f*h, .05f*w, .05f*w,0,0,255); 
		colors[4][0]=new ColorRect(.548f*w, .8f*h, .05f*w, .05f*w,115,51,128); 
		colors[5][0]=new ColorRect(.618f*w, .8f*h, .05f*w, .05f*w,250,250,55); 
		//Done
		colors[0][1]=new ColorRect(.268f*w, .7f*h, .05f*w, .05f*w,255,136,51);
		colors[1][1]=new ColorRect(.338f*w, .7f*h, .05f*w, .05f*w,95,167,119); 
		colors[2][1]=new ColorRect(.408f*w, .7f*h, .05f*w, .05f*w,0,149,183);
		colors[3][1]=new ColorRect(.478f*w, .7f*h, .05f*w, .05f*w,246,83,166); 
		colors[4][1]=new ColorRect(.548f*w, .7f*h, .05f*w, .05f*w,175,89,62); 
		colors[5][1]=new ColorRect(.618f*w, .7f*h, .05f*w, .05f*w,102,82,51); 
		//Done
		colors[0][2]=new ColorRect(.268f*w, .6f*h, .05f*w, .05f*w,254,111,94);
		colors[1][2]=new ColorRect(.338f*w, .6f*h, .05f*w, .05f*w,195,33,72); 
		colors[2][2]=new ColorRect(.408f*w, .6f*h, .05f*w, .05f*w,252,214,103);
		colors[3][2]=new ColorRect(.478f*w, .6f*h, .05f*w, .05f*w,147,223,184); 
		colors[4][2]=new ColorRect(.548f*w, .6f*h, .05f*w, .05f*w,118,215,234); 
		colors[5][2]=new ColorRect(.618f*w, .6f*h, .05f*w, .05f*w,0,102,204);
		//Done
		colors[0][3]=new ColorRect(.268f*w, .5f*h, .05f*w, .05f*w,118,110,200);
		colors[1][3]=new ColorRect(.338f*w, .5f*h, .05f*w, .05f*w,247,70,138); 
		colors[2][3]=new ColorRect(.408f*w, .5f*h, .05f*w, .05f*w,202,52,52);
		colors[3][3]=new ColorRect(.478f*w, .5f*h, .05f*w, .05f*w,158,91,64); 
		colors[4][3]=new ColorRect(.548f*w, .5f*h, .05f*w, .05f*w,230,190,138); 
		colors[5][3]=new ColorRect(.618f*w, .5f*h, .05f*w, .05f*w,201,192,187); 
		//Done
		colors[0][4]=new ColorRect(.268f*w, .4f*h, .05f*w, .05f*w,218,138,103);
		colors[1][4]=new ColorRect(.338f*w, .4f*h, .05f*w, .05f*w,200,200,205); 
		colors[2][4]=new ColorRect(.408f*w, .4f*h, .05f*w, .05f*w,253,14,53);
		colors[3][4]=new ColorRect(.478f*w, .4f*h, .05f*w, .05f*w,231,114,0); 
		colors[4][4]=new ColorRect(.548f*w, .4f*h, .05f*w, .05f*w,175,227,19); 
		colors[5][4]=new ColorRect(.618f*w, .4f*h, .05f*w, .05f*w,51,204,153); 
		//Done
		colors[0][5]=new ColorRect(.268f*w, .3f*h, .05f*w, .05f*w,0,204,204);
		colors[1][5]=new ColorRect(.338f*w, .3f*h, .05f*w, .05f*w,45,56,58); 
		colors[2][5]=new ColorRect(.408f*w, .3f*h, .05f*w, .05f*w,79,105,198);
		colors[3][5]=new ColorRect(.478f*w, .3f*h, .05f*w, .05f*w,101,45,193); 
		colors[4][5]=new ColorRect(.548f*w, .3f*h, .05f*w, .05f*w,252,116,253); 
		colors[5][5]=new ColorRect(.618f*w, .3f*h, .05f*w, .05f*w,135,66,31); 
		//Done
		colors[0][6]=new ColorRect(.268f*w, .2f*h, .05f*w, .05f*w,255,53,94);
		colors[1][6]=new ColorRect(.338f*w, .2f*h, .05f*w, .05f*w,255,96,55); 
		colors[2][6]=new ColorRect(.408f*w, .2f*h, .05f*w, .05f*w,204,255,0);
		colors[3][6]=new ColorRect(.478f*w, .2f*h, .05f*w, .05f*w,102,255,102); 
		colors[4][6]=new ColorRect(.548f*w, .2f*h, .05f*w, .05f*w,80,191,230); 
		colors[5][6]=new ColorRect(.618f*w, .2f*h, .05f*w, .05f*w,255,0,204); 
	}

	@Override
	public void render(float delta) {
		switch(f.toolbar.select){
		case 0:f.setScreen(new MainScreen(f));break;
		case 2:f.setScreen(new ShopScreen(f));break;
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
		
		/*shape.setColor(Color.BLACK);
		shape.rect(.268f*w, .8f*h, .05f*w, .05f*w);
		shape.setColor(Color.RED);
		shape.rect(.338f*w, .8f*h, .05f*w, .05f*w);
		shape.setColor(Color.GREEN);
		shape.rect(.408f*w, .8f*h, .05f*w, .05f*w);
		shape.setColor(Color.BLUE);
		shape.rect(.478f*w, .8f*h, .05f*w, .05f*w);
		shape.setColor(Color.PURPLE);
		shape.rect(.548f*w, .8f*h, .05f*w, .05f*w);
		shape.setColor(Color.YELLOW);
		shape.rect(.618f*w, .8f*h, .05f*w, .05f*w);
		*/
		
		for(int i =0;i<colors.length;i++){
			for(int j=0;j<=f.unlock;j++){
				shape.setColor(colors[i][j].color);
				shape.rect(colors[i][j].x, colors[i][j].y, colors[i][j].width, colors[i][j].height);
			}
		}
		
		shape.setColor(f.color);
		shape.rect(.75f*w, .4f*h,.2f*h,.2f*h); 
		
		shape.end();
		
		pic.begin();
		f.assets.font.setColor(Color.WHITE);
		f.assets.font.setScale(h/5000);
		f.assets.font.draw(pic, "Basic-", w/8, .86f*h);
		if(f.unlock>0){f.assets.font.draw(pic, "Tier 1-", w/8, .76f*h);}
		if(f.unlock>1){f.assets.font.draw(pic, "Tier 2-", w/8, .66f*h);}
		if(f.unlock>2){f.assets.font.draw(pic, "Tier 3-", w/8, .56f*h);}
		if(f.unlock>3){f.assets.font.draw(pic, "Tier 4-", w/8, .46f*h);}
		if(f.unlock>4){f.assets.font.draw(pic, "Tier 5-", w/8, .36f*h);}
		if(f.unlock>5){f.assets.font.draw(pic, "Tier 6-", w/8, .26f*h);}
		
		f.assets.font.draw(pic, "Your Color", (.75f*w+.1f*h)-(f.assets.font.getBounds("Your Color").width/2), .6f*h+(f.assets.font.getBounds("Your Color").height*1.2f));
		
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
		
		if(screenX<w/10){f.toolbar.touch(touch);}
		else{
			for(int i =0;i<colors.length;i++){
				for(int j=0;j<=f.unlock;j++){
					if(Intersector.overlaps(touch, colors[i][j])){f.color=colors[i][j].color;f.save();break;}
				}
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
