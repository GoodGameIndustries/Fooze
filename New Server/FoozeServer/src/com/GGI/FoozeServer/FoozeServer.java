package com.GGI.FoozeServer;

import java.io.IOException;
import java.util.ArrayList;

import com.GGI.FoozeServer.Objects.Food;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
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
	public int s=0;
	public int count = 0;
	
	//world vars
	public float gridx =20,gridy=20;
	public float size=500;
	
	public ArrayList<Food> food = new ArrayList<Food>();
	public ArrayList<Color> colors = new ArrayList<Color>();
	public long lastTime = System.currentTimeMillis();
	public long begin = System.currentTimeMillis();
	public boolean broadcast = false;
	
	
	
	@Override
	public void create () {
		
		try {
			System.out.println("3");
			Thread.sleep(1000);
			System.out.println("2");
			Thread.sleep(1000);
			System.out.println("1");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Server Starting");
		rServer = Gdx.net.newServerSocket(Protocol.TCP, 4443, hints);
		sServer = Gdx.net.newServerSocket(Protocol.TCP, 4444, hints);
		System.out.println("	Server sockets created...");
		
		new Thread(new Connector(this)).start();
		new Thread(new Commands(this)).start();
		System.out.println("	Starting to listen for connections...");
		
		new Thread(new Broadcaster(this)).start();
		System.out.println("	Beginning broadcast...");
		new Thread(new CollisionManager(this)).start();
		System.out.println("	Hit detection initiated...");
		w =Gdx.graphics.getWidth();h=Gdx.graphics.getHeight();
		
	}

	@Override
	public void render () {
		//Gdx.gl.glClearColor(1,0,0,1);
		//Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//System.out.println(s);
		if(System.currentTimeMillis()-lastTime>60000||System.currentTimeMillis()-begin>1200000){
			for(int i = 0;i<clients.size();i++){
				clients.get(i).se.send("lose");
			}
			
			
			System.exit(0);
		}
		
		for(int i = 0; i < clients.size();i++){
			Reader c = clients.get(i);
			for(int j = 0; j < clients.get(i).messages.size();j++){
			String message = c.messages.get(j);
			if(message!=null){
				//System.out.println(message);
				//String[] breakdown = message.split(":");
				//	if(breakdown[0].equals("eatFood")){s.food.remove(Integer.parseInt(breakdown[1]));s.addFood();}
				
				}
			c.messages.clear();
		}
		}
		
		
		
		
	}
	
		public float dist(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
	}

		public float getRadius(float mass){
			return (float) Math.sqrt(10*mass/Math.PI);
		}
		
	public String listToString(ArrayList a){
		String result="";
		for(int i=0;i<a.size();i++){
			result+=":";
			result+=a.get(i).toString();
		}
		return result;
	}
	
	
}
