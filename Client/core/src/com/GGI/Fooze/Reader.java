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
	
	public Reader(Fooze f, Socket r){
		this.f=f;
		this.r=r;
	}
	
	
	
	@Override
	public void run() {
		while(true){
			String message;
			
			try {
				message = new BufferedReader(new InputStreamReader(r.getInputStream())).readLine();
				System.out.println(message);
				String[] breakdown = message.split(":");
				if(breakdown[0].equals("Online")){f.nextScreen=true;
				f.size=Float.parseFloat(breakdown[1]);
				f.gridx=Float.parseFloat(breakdown[2]);
				f.gridy=Float.parseFloat(breakdown[3]);
				}
				else{
					f.messages.add(message);
				}
				} catch (IOException e) {
				
			}
			
		}
		
	}

	

}
