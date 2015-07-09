package com.GGI.Fooze;

import java.io.IOException;

import NetworkClasses.AddCharacter;
import NetworkClasses.AddMass;
import NetworkClasses.Character;
import NetworkClasses.Login;
import NetworkClasses.Lose;
import NetworkClasses.MoveCharacter;
import NetworkClasses.Network;
import NetworkClasses.RemoveCharacter;
import NetworkClasses.World;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

public class Connect implements Runnable{

	public Fooze f;
	public Client client;
	public float lX=0,lY=0;
	public int s = 0;
	public Connect(Fooze f){
		this.f=f;
	}
	
@Override
public void run() {
	client = new Client(16384, 2048);
	
	System.out.println("Connecting");
	
	// For consistency, the classes to be sent over the network are
	// registered by the same method for both the client and server.
	Network.register(client);

	// ThreadedListener runs the listener methods on a different thread.
	client.addListener(new ThreadedListener(new Listener() {
		public void connected (Connection connection) {
			System.out.println("Connected");
		}

		public void received (Connection connection, Object object) {
			//System.out.println("Something received");
			if (object instanceof World) {
				World w = (World)object;
				f.gridx=w.gridx;
				f.gridy=w.gridy;
				f.size=w.size;
				f.ID=w.id;
				f.world=w.world;
				
				f.setScreen(1);
				return;
			}
			
			
			
			if (object instanceof Lose) {
				Lose l = (Lose)object;
				if(l.id==f.ID){f.die=true;}
				return;
			}
			
			if (object instanceof AddMass) {
				AddMass a = (AddMass)object;
				if(a.id==f.ID){f.massToAdd+=a.mass;}
				return;
			}
			
			if (object instanceof AddCharacter) {
				AddCharacter a = (AddCharacter)object;
				//if(a.character.world==f.world){f.players.add(a.character);}
				addCharacter(a.character);
				return;
			}
			
			if (object instanceof RemoveCharacter) {
				RemoveCharacter r = (RemoveCharacter)object;
				if(f.players.contains(r.character)){
					f.players.remove(r.character);
				}
				removeCharacter(r.character.id);
				return;
			}
			

			if (object instanceof MoveCharacter) {
				
				MoveCharacter m=((MoveCharacter)object);
				if(m.world==f.world){
					
					for(int i =0;i<f.players.size();i++){
						if(f.players.get(i).id==m.id){
							f.players.get(i).dX=m.x;
							f.players.get(i).dY=m.y;
							f.players.get(i).mass=m.mass;
						}
					}
				}
				
				for(int i = 0;i<f.players.size()-1;i++){
					for(int j=1;j<f.players.size();j++){
						if(f.players.get(i).id==f.players.get(j).id){f.players.remove(j);}
					}
				}
				
				if(s>10){
					s=0;
				try{
					for(int j =0;j<f.players.size();j++){
					for(int i =0;i<f.players.size()-1;i++){
						if(f.players.get(i+1).mass>f.players.get(i).mass){
							Character temp = f.players.get(i+1);
							f.players.remove(i+1);
							f.players.add(i, temp);
						}
					}
					}
					f.leaderboards=f.players;
					}
					catch(Exception e){
						
					}
				}
				
				s++;
				return;
			}

			
		}

		

		public void disconnected (Connection connection) {
			//setScreen(new MainScreen(f));
			System.out.println("Disconnected");
			f.die=true;
		}
	}));
	
	
	client.start();
	try {
		client.connect(5000, "52.27.71.7", Network.port);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	Login l = new Login();
	l.name=f.name;
	l.mass=f.mass;
	l.x=0;
	l.y=0;
	l.r=f.color.r;
	l.g=f.color.g;
	l.b=f.color.b;
	
	client.sendTCP(l);
	
}

public void addCharacter (Character character) {
	if(character.world==f.world&&!f.players.contains(character)){
		
	f.players.add( character);
	System.out.println(character.name + " added at " + character.x + ", " + character.y);
	}
	}
/*
public void updateCharacter (UpdateCharacter msg) {
	Character character=null;
	for(int i =0;i<f.players.size();i++){
	 if(f.players.get(i).id==msg.id){
		character= f.players.get(i);
	 }
	}
	if (character == null) return;
	
	character.dX = msg.x;
	character.dY = msg.y;
	character.mass=msg.mass;
	System.out.println(character.name + " moved to " + character.x + ", " + character.y);
}
*/
public void removeCharacter (int id) {
	for(int i = 0;i<f.players.size();i++){
		if(f.players.get(i).id==id){
			f.players.remove(i);
		}
	}
		
		System.out.println("Player removed");
	}

public void move() {
	
	if(dist(lX,lY,f.xPos,f.yPos)>20){
	MoveCharacter m = new MoveCharacter();
	m.mass=f.mass;
	m.x=f.xPos;
	m.y=f.yPos;
	m.id=f.ID;
	m.world=f.world;
	client.sendTCP(m);
	
	System.out.println("Move");
	}
}

/*public void updateAll(){
	f.players.clear();
	client.sendTCP(new UpdateAll());
}*/

public float dist(float x1, float y1, float x2, float y2) {
	return (float) Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
}
}