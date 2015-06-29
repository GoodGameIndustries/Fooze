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
	
	private void addPlayer(String name, float x, float y,float mass,int ID,Color color) {
		
		f.players.add(new Player(name,mass,ID,color));
		f.players.get(f.players.size()-1).x=x;
		f.players.get(f.players.size()-1).y=y;
		
	}
	
	@Override
	public void run() {
		while(true){
			String message;
			
			try {
				message =br.readLine();
				//System.out.println(message);
				
					if(message!=null){
					String[] breakdown = message.split(":");
					
					if(breakdown[0].equals("Players")){
						if(!f.isRender){
						//f.players.clear();
						for(int i =0;i<f.players.size();i++){
							f.players.get(i).updated=false;
						}
					for(int j=1;j<breakdown.length;j++){
						boolean updated=false;
						String[] b2 = breakdown[j].split(",");
						try{
						//addPlayer(b2[0],Float.parseFloat(b2[1]),Float.parseFloat(b2[2]),Float.parseFloat(b2[3]),Integer.parseInt(b2[4]),new Color(Float.parseFloat(b2[5]),Float.parseFloat(b2[6]),Float.parseFloat(b2[7]),1));
					for(int i =0;i<f.players.size();i++){
						if(f.players.get(i).ID==Integer.parseInt(b2[4])){
							f.players.get(i).dX=Float.parseFloat(b2[1]);
							f.players.get(i).dY=Float.parseFloat(b2[2]);
							f.players.get(i).mass=Float.parseFloat(b2[3]);
							f.players.get(i).updated=true;
							updated=true;
						}
					}
					if(!updated){
						addPlayer(b2[0],Float.parseFloat(b2[1]),Float.parseFloat(b2[2]),Float.parseFloat(b2[3]),Integer.parseInt(b2[4]),new Color(Float.parseFloat(b2[5]),Float.parseFloat(b2[6]),Float.parseFloat(b2[7]),1));
					}
						
						}catch(Exception e){}
					}
					
					for(int i =0;i<f.players.size();i++){
						if(!f.players.get(i).updated){f.players.remove(i);}
					}
					
					}
				}
					else{
						f.messages.add(message);
					}
					f.r++;
					}
				} catch (IOException e) {
				
			}
			
		}
		
	}

	

}
