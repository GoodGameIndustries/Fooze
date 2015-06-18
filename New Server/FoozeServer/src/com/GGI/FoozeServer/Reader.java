package com.GGI.FoozeServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;

public class Reader implements Runnable{

	public FoozeServer s;
	public Connection c;
	public Sender se;
	public ArrayList<String> messages = new ArrayList<String>();
	
	//player attributes
	float mass = 0;
	float x=0;
	float y=0;
	public String name="";
	public BufferedReader br;
	public int ID=-1;
	public Color color = Color.GRAY;
	public long lastTime = System.currentTimeMillis();
	public boolean connected = false;
	public boolean die=false;
	public Reader(FoozeServer s,Connection c,Sender se){
		this.s=s;
		this.c=c;
		this.se=se;
		br= new BufferedReader(new InputStreamReader(c.r.getInputStream()));
	}

	
	
	@Override
	public void run() {
		System.out.println("Reader Started");
		
		if(!connected){for(int m = 0;m<10;m++){se.send("Online:"+s.size+":"+s.gridx+":"+s.gridy+":"+s.count);}s.count++;if(s.count>100000){s.count=0;} System.out.println(s.count);connected=true;}
		while(true){
			String message;
			try {
				
				if(die){
					break;
				}
				
				message = br.readLine();
				if(message!=null){
				lastTime = System.currentTimeMillis();
				String[] breakdown=message.split(":");
				if(breakdown[0].equals("Render")){
					
					try{
					s.lastTime = System.currentTimeMillis();
					name=breakdown[1];
					x=Float.parseFloat(breakdown[2]);
					y=Float.parseFloat(breakdown[3]);
					mass=Float.parseFloat(breakdown[4]);
					ID=Integer.parseInt(breakdown[5]);
					color=new Color(Float.parseFloat(breakdown[6]),Float.parseFloat(breakdown[7]),Float.parseFloat(breakdown[8]),1);
					}
					
					catch(Exception e){
						//System.out.println("Render issue");
					}
					s.broadcast=true;
					
					//se.send("Food"+s.listToString(s.food));	
				//se.send("Players"+s.listToString(s.clients));	
				}
				else if(breakdown[0].equals("Connect")&&!connected){for(int m = 0;m<10;m++){se.send("Online:"+s.size+":"+s.gridx+":"+s.gridy+":"+s.count);}s.count++;if(s.count>100000){s.count=0;} System.out.println(s.count);connected=true;}
				
				else{messages.add(message);}
				}
				
				if(lastTime>0){
					if(System.currentTimeMillis()-lastTime>2000&&!connected){
						s.clients.remove(this);
						break;
					}
					if(System.currentTimeMillis()-lastTime>30000){
						s.clients.remove(this);
						break;
					}
				}
				//send statement with error check:  if(se.send("Received")){break;}
			} catch (IOException e) {
				
			
			
		}
	
		//return;
		
	}
	}
	
	public String toString(){
		return name+","+x+","+y+","+mass+","+ID+","+color.r+","+color.g+","+color.b;
	}
}

