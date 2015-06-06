package com.GGI.Fooze;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStreamReader;

import com.GGI.Fooze.Objects.Food;
import com.GGI.Fooze.Objects.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.net.Socket;

public class Reader implements Runnable{

	public Socket r;
	public Fooze f;
	public BufferedReader br;
	
	public Reader(Fooze f, Socket r){
		this.f=f;
		this.r=r;
		br= new BufferedReader(new InputStreamReader(r.getInputStream()));
	}
	
	
	
	@Override
	public void run() {
		while(true){
			String message;
			
			try {
				message =br.readLine();
				//System.out.println(message);
				
					f.messages.add(message);
					f.r++;
				
				} catch (IOException e) {
				
			}
			
		}
		
	}

	

}
