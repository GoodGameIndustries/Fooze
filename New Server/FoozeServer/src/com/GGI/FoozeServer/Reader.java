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
	
	public Reader(FoozeServer s,Connection c,Sender se){
		this.s=s;
		this.c=c;
		this.se=se;
		br= new BufferedReader(new InputStreamReader(c.r.getInputStream()));
	}

	
	
	@Override
	public void run() {
		System.out.println("Reader Started");
		while(true){
			String message;
			try {
				
				message = br.readLine();
				messages.add(message);

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

