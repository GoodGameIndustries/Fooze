package com.GGI.FoozeServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Reader implements Runnable{

	public FoozeServer s;
	public Connection c;
	public Sender se;
	public ArrayList<String> messages = new ArrayList<String>();
	
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
				messages.add(message);

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

