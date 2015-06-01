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
	
	private void addFood(float x,float y,float r,float g,float b) {
		f.food.add(new Food(new Color(r,g,b,1)));
		f.food.get(f.food.size()-1).x=x;
		f.food.get(f.food.size()-1).y=y;
	}
	private void addPlayer(float x, float y,float mass) {
		f.players.add(new Player(mass));
		f.players.get(f.players.size()-1).x=x;
		f.players.get(f.players.size()-1).y=y;
	}
	
	@Override
	public void run() {
		while(true){
			String message;
			try {
				message = new BufferedReader(new InputStreamReader(r.getInputStream())).readLine();
				//System.out.println(message);
				String[] breakdown = message.split(":");
				if(breakdown[0].equals("Online")){f.nextScreen=true;
				f.size=Float.parseFloat(breakdown[1]);
				f.gridx=Float.parseFloat(breakdown[2]);
				f.gridy=Float.parseFloat(breakdown[3]);
				}
				if(breakdown[0].equals("Food")){f.food.clear();
					for(int i=1;i<breakdown.length;i++){
						String[] b2 = breakdown[i].split(",");
						addFood(Float.parseFloat(b2[0]),Float.parseFloat(b2[1]),Float.parseFloat(b2[2]),Float.parseFloat(b2[3]),Float.parseFloat(b2[4]));
					}
					f.update=true;
				}
				if(breakdown[0].equals("Players")){f.players.clear();
					for(int i=1;i<breakdown.length;i++){
						String[] b2 = breakdown[i].split(",");
						addPlayer(Float.parseFloat(b2[0]),Float.parseFloat(b2[1]),Float.parseFloat(b2[2]));
					}
				}
				
				if(breakdown[0].equals("lose")){f.die=true;}
				if(breakdown[0].equals("addMass")){f.massToAdd=Float.parseFloat(breakdown[1]);}
			} catch (IOException e) {
				
			}
			
		}
		
	}

	

}
