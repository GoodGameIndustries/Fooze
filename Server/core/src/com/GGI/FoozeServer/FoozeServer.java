package com.GGI.FoozeServer;

import java.util.ArrayList;

import com.GGI.FoozeServer.Objects.Food;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;

public class FoozeServer extends ApplicationAdapter {
	
	public ArrayList<Reader> clients = new ArrayList<Reader>();
	public ServerSocketHints hints = new ServerSocketHints();
	public ServerSocket rServer;
	public ServerSocket sServer;
	public BitmapFont fnt;
	public SpriteBatch pic;
	public float w,h;
	public long gameTime = System.currentTimeMillis()+30000;
	public long pauseTime;
	public boolean isPaused = false;
	
	//world vars
	public float gridx =20,gridy=20;
	public float size=2000;
	
	public ArrayList<Food> food = new ArrayList<Food>();
	public ArrayList<Color> colors = new ArrayList<Color>();
	
	@Override
	public void create () {
		System.out.println("Server Starting");
		rServer = Gdx.net.newServerSocket(Protocol.TCP, 4443, hints);
		sServer = Gdx.net.newServerSocket(Protocol.TCP, 4444, hints);
		System.out.println("	Server sockets created...");
		
		new Thread(new Connector(this)).start();
		new Thread(new Commands(this)).start();
		System.out.println("	Starting to listen for connections...");
		
		w =Gdx.graphics.getWidth();h=Gdx.graphics.getHeight();
		populate();
	}

	@Override
	public void render () {
		
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
	}
	
	public String listToString(ArrayList a){
		String result="";
		for(int i=0;i<a.size();i++){
			result+=":";
			result+=a.get(i).toString();
		}
		return result;
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
		for(int i=0;i<size/8;i++){
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
