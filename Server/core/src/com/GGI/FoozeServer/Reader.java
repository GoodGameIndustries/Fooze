package com.GGI.FoozeServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Reader implements Runnable{

	public FoozeServer s;
	public Connection c;
	public Sender se;
	
	//player attributes
	float mass = 0;
	float x=0;
	float y=0;
	
	public Reader(FoozeServer s,Connection c,Sender se){
		this.s=s;
		this.c=c;
		this.se=se;
	}

	@Override
	public void run() {
		System.out.println("Reader Started");
		while(true){
			String message;
			try {
				
				message = new BufferedReader(new InputStreamReader(c.r.getInputStream())).readLine();
				c.lastT=System.currentTimeMillis();
				if(se.send(" ")){break;}
				System.out.println("Reading");
				if(message!=null){
				System.out.println(message);
				String[] breakdown = message.split(":");
				if(breakdown[0].equals("Connect")){se.send("Online:"+s.size+":"+s.gridx+":"+s.gridy);}
				if(breakdown[0].equals("eatFood")){s.food.remove(Integer.parseInt(breakdown[1]));s.addFood();}
				if(breakdown[0].equals("Render")){
					x=Float.parseFloat(breakdown[1]);
					y=Float.parseFloat(breakdown[2]);
					mass=Float.parseFloat(breakdown[3]);
				se.send("Food"+s.listToString(s.food));	
				se.send("Players"+s.listToString(s.clients));	
				}
				
				
				}
				
			
				
				
				//send statement with error check:  if(se.send("Received")){break;}
			} catch (IOException e) {
				
			
			
		}
	
		//return;
		
	}
	}
	
	public String toString(){
		return x+","+y+","+mass;
	}
}

