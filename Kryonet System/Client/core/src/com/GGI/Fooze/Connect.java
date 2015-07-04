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
import NetworkClasses.UpdateAll;
import NetworkClasses.UpdateCharacter;
import NetworkClasses.World;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

public class Connect implements Runnable{

	public Fooze f;
	public Client client;
	public Connect(Fooze f){
		this.f=f;
	}
	
@Override
public void run() {
	client = new Client();
	
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
			System.out.println("Something received");
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
			
			if (object instanceof AddCharacter) {
				AddCharacter msg = (AddCharacter)object;
				
				if(!f.players.contains(msg.character)){
					addCharacter(msg.character);
				}
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
			
			if (object instanceof RemoveCharacter) {
				RemoveCharacter msg = (RemoveCharacter)object;
				removeCharacter(msg.id);
				return;
			}

			if (object instanceof UpdateCharacter) {
				updateCharacter((UpdateCharacter)object);
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
		client.connect(5000, "52.27.71.7", Network.port,Network.udp);
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

public void removeCharacter (int id) {
	for(int i = 0;i<f.players.size();i++){
		if(f.players.get(i).id==id){
			f.players.remove(i);
		}
	}
		
		System.out.println("Player removed");
	}

public void move() {
	MoveCharacter m = new MoveCharacter();
	m.mass=f.mass;
	m.x=f.xPos;
	m.y=f.yPos;
	m.id=f.ID;
	m.world=f.world;
	client.sendTCP(m);
	
	System.out.println("Move");
	
}

public void updateAll(){
	f.players.clear();
	client.sendTCP(new UpdateAll());
}
}