


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import NetworkClasses.AddCharacter;
import NetworkClasses.AddMass;
import NetworkClasses.Character;
import NetworkClasses.Login;
import NetworkClasses.Lose;
import NetworkClasses.MoveCharacter;
import NetworkClasses.Network;
import NetworkClasses.RemoveCharacter;
import NetworkClasses.World;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class FoozeKryoServer {
	Server server;
	HashSet<Character> loggedIn = new HashSet();
	ArrayList<Character> players1 = new ArrayList<Character>();
	ArrayList<Character> players2 = new ArrayList<Character>();
	ArrayList<Character> players3 = new ArrayList<Character>();
	ArrayList<Character> players4 = new ArrayList<Character>();
	ArrayList<Character> players5 = new ArrayList<Character>();
	World world;
	UI ui;
	long last = System.currentTimeMillis();
	public FoozeKryoServer () throws IOException {
		world = new World();
		System.out.println("Server Staring...");
		ui=new UI(this);
		server = new Server(32768,4096) {
			
		
			protected Connection newConnection () {
				// By providing our own connection implementation, we can store per
				// connection state without a connection ID to state look up.
				return new CharacterConnection();
			}
		};
		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(server);

		server.addListener(new Listener() {
			public void received (Connection c, Object object) {
				
				last = System.currentTimeMillis();
				//System.out.println("Something received");
				// We know all connections for this server are actually CharacterConnections.
				CharacterConnection connection = (CharacterConnection)c;
				Character character = connection.character;
				
				ui.repaint();
				if (object instanceof Login) {
					if(character!=null){
						character.last=System.currentTimeMillis();}
					Login login = (Login)object;

					character = new Character();
					character.name = login.name;
					//character.mass= login.mass;
					character.r=login.r;
					character.g=login.g;
					character.b=login.b;
					//character.x = register.x;
					//character.y = register.y;
					world.nextId();
					world.world=bestWorld();
					character.world=world.world;
					character.id=world.id;
					try{
					c.sendTCP(world);
					}
					catch(Exception e){}
					ArrayList<Character> players = new ArrayList<Character>();
					switch(world.world){
					case 1:players=players1;break;
					case 2:players=players2;break;
					case 3:players=players3;break;
					case 4:players=players4;break;
					case 5:players=players5;break;
					}
					
					
					for(int i = 0;i<players.size();i++){
						AddCharacter a = new AddCharacter();
						a.character=players.get(i);
						c.sendTCP(a);
					}
					
					players.add(character);
					
					try{
						AddCharacter a = new AddCharacter();
						a.character=character;
						for(int i = 0;i<players.size();i++){
							server.sendToAllTCP(a);
						}
						
						
						}catch(Exception e){}

					
					//System.out.println(players1.size());
					//System.out.println("Logged In");
					return;
				}
				
				
				
				
				
				
				if (object instanceof MoveCharacter) {
					if(character!=null){
						character.last=System.currentTimeMillis();}
					MoveCharacter msg = (MoveCharacter)object;
					ArrayList<Character> players = new ArrayList<Character>();
					switch(msg.world){
					case 1:players=players1;break;
					case 2:players=players2;break;
					case 3:players=players3;break;
					case 4:players=players4;break;
					case 5:players=players5;break;
					}
					
					//System.out.println("Move");
					for(int i =0;i<players.size();i++){
						if(players.get(i).id==msg.id){character=players.get(i);}
						}
					
					if (character == null) return;

					

					// Ignore if invalid move.
					//if (Math.abs(msg.x) != 1 && Math.abs(msg.y) != 1) return;

					character.x =msg.x;
					character.y =msg.y;
					character.rX =msg.x;
					character.rY =msg.y;
					character.mass=msg.mass;
					character.world=msg.world;
					
					//System.out.println(character.name+" moved to: " + character.x+" , "+character.y);

					try{
						checkCollisions(character.world);
						}catch(Exception e){}
					
					/*UpdateCharacter update = new UpdateCharacter();
					update.id = character.id;
					update.mass = character.mass;
					update.x = character.x;
					update.y = character.y;
					server.sendToAllTCP(update);*/
					
					
					try{
					server.sendToAllTCP(msg);
					//System.out.println(c.getTcpWriteBufferSize());
					}catch(Exception e){}
					
					//System.out.println("Update sent");
					
					return;
				}
			}

			private int bestWorld() {
				int tr = 10;
				if(players1.size()<tr){return 1;}
				else if(players2.size()<tr){return 2;}
				else if(players3.size()<tr){return 3;}
				else if(players4.size()<tr){return 4;}
				else if(players5.size()<tr){return 5;}
				else{
				return (int)(Math.random()*5)+1;
				}
			}

			private boolean isValid (String value) {
				if (value == null) return false;
				value = value.trim();
				if (value.length() == 0) return false;
				return true;
			}

			public void disconnected (Connection c) {
				CharacterConnection connection = (CharacterConnection)c;
				if (connection.character != null) {
					ArrayList<Character> players = new ArrayList<Character>();
					switch(connection.character.world){
					case 1:players=players1;break;
					case 2:players=players2;break;
					case 3:players=players3;break;
					case 4:players=players4;break;
					case 5:players=players5;break;
					}
					players.remove(connection.character);

					//RemoveCharacter removeCharacter = new RemoveCharacter();
					//removeCharacter.id = connection.character.id;
					//server.sendToAllTCP(removeCharacter);
				}
				
			}
			
		});
		server.bind(Network.port);
		server.start();
	}



	

	// This holds per connection state.
	static class CharacterConnection extends Connection {
		public Character character;
		
		public CharacterConnection(){
			
		}
	}

	public static void main (String[] args) throws IOException {
		//Log.set(Log.LEVEL_DEBUG);
		new FoozeKryoServer();
	}
	
	public void checkCollisions(int world2){
		
		
		
		ArrayList<Character> players = new ArrayList<Character>();
		switch(world2){
		case 1:players=players1;break;
		case 2:players=players2;break;
		case 3:players=players3;break;
		case 4:players=players4;break;
		case 5:players=players5;break;
		}
		
		for(int i=0;i<players.size();i++){
			if(System.currentTimeMillis()-players.get(i).last>30000){
				//RemoveCharacter r = new RemoveCharacter();
				//r.id=players.get(i).id;
				//server.sendToAllTCP(r);
				players.remove(i);
			}
		}
		
		for(int i = 0; i < players.size()-1;i++){
			//SrYstem.out.println("Checking");
			Character c1 = players.get(i);
			
			
			for(int j = i+1;j<players.size();j++){
				Character c2 = players.get(j);
			//if(Intersector.overlapCircles(new Circle(c1.rX,c1.rY,getRadius(c1.mass)), new Circle(c2.rX,c2.rY,getRadius(c2.mass)))){
				
				if(dist(c1.rX,c1.rY,c2.rX,c2.rY)<getRadius(c1.mass)&&c1.mass>c2.mass){
					//SrYstem.out.println("Collide");
				for(int m = 0; m < 100;m++){
					Lose l = new Lose();
					l.id=c2.id;
					try{
						server.sendToAllTCP(l);
						}catch(Exception e){}
					//RemoveCharacter r= new RemoveCharacter();
					//r.id=c2.id;
					//server.sendToAllTCP(r);
					
					//c2.se.send("lose");
					//c2.die=true;
				}
				//c1.se.send("addMass:"+c2.mass);
				AddMass a = new AddMass();
				a.id=c1.id;
				a.mass=c2.mass;
				RemoveCharacter r = new RemoveCharacter();
				r.character=c2;
				try{
					server.sendToAllTCP(a);
					server.sendToAllTCP(r);
					}catch(Exception e){}
				players.remove(c2);}
				else if(dist(c1.rX,c1.rY,c2.rX,c2.rY)<getRadius(c2.mass)&&c2.mass>c1.mass){
					//SrYstem.out.println("Collide");
					for(int m = 0; m < 100;m++){
						Lose l = new Lose();
						l.id=c1.id;
						try{
							server.sendToAllTCP(l);
							}catch(Exception e){}
						//RemoveCharacter r= new RemoveCharacter();
						//r.id=c1.id;
						//server.sendToAllTCP(r);
						
						//c1.se.send("lose");
						//c1.die=true;
					}
					//c2.se.send("addMass:"+c1.mass);
					AddMass a = new AddMass();
					a.id=c2.id;
					a.mass=c1.mass;
					RemoveCharacter r = new RemoveCharacter();
					r.character=c1;
					try{
						server.sendToAllTCP(a);
						server.sendToAllTCP(r);
						}catch(Exception e){}
					players.remove(c1);
					
					
				}
				
				
					
				
			
			//}
				
				
			}
		}
	}
	public float dist(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
	}

		public float getRadius(float mass){
			return (float) Math.sqrt(10*mass/Math.PI);
		}
		
		
}
