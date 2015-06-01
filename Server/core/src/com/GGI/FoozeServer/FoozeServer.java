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
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		for(int i = 0; i < clients.size();i++){
			Reader c = clients.get(i);
			for(int j = 0; j < clients.get(i).messages.size();j++){
			String message = c.messages.get(j);
			if(message!=null){
				System.out.println(message);
				String[] breakdown = message.split(":");
				if(breakdown[0].equals("Connect")){c.se.send("Online:"+size+":"+gridx+":"+gridy);}
			//	if(breakdown[0].equals("eatFood")){s.food.remove(Integer.parseInt(breakdown[1]));s.addFood();}
				if(breakdown[0].equals("Render")){
					c.x=Float.parseFloat(breakdown[1]);
					c.y=Float.parseFloat(breakdown[2]);
					c.mass=Float.parseFloat(breakdown[3]);
				//se.send("Food"+s.listToString(s.food));	
				//se.send("Players"+s.listToString(s.clients));	
				}
				}
			c.messages.clear();
		}
		}
		for(int i = 0; i < clients.size();i++){
			clients.get(i).se.send("Players"+listToString(clients));	
		}
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
