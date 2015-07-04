package NetworkClasses;



import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

// This class is a convenient place to keep things common to both the client and server.
public class Network {
	static public final int port = 54555;
	static public final int udp = 54556;

	// This registers objects that are going to be sent over the network.
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Login.class);
		//kryo.register(RegistrationRequired.class);
		//kryo.register(Register.class);
		kryo.register(AddCharacter.class);
		kryo.register(UpdateCharacter.class);
		kryo.register(RemoveCharacter.class);
		kryo.register(Character.class);
		kryo.register(MoveCharacter.class);
		kryo.register(World.class);
		kryo.register(Lose.class);
		kryo.register(AddMass.class);
		kryo.register(UpdateAll.class);
	}

	
}
