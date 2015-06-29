package com.GGI.FoozeServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class Connector implements Runnable{

	public FoozeServer s;
	
	public Connector(FoozeServer s){
		this.s=s;
	}
	
	@Override
	public void run() {
		while(true){
			//listen for new connections
			try{
				SocketHints hints = new SocketHints();
				hints.tcpNoDelay=true;
				hints.trafficClass=0x10;
				Socket sClient = s.sServer.accept(hints);
				Socket rClient = s.rServer.accept(hints);
					try {
						String message = new BufferedReader(new InputStreamReader(rClient.getInputStream())).readLine();
						//System.out.println(message);
						if(message.equals("Connect")){
						sClient.getOutputStream().write("Connected\n".getBytes());
						
						Connection c =new Connection(rClient,sClient);
						s.clients.add(0,new Reader(s,c,new Sender(s,c)));
						
						
						new Thread(s.clients.get(0)).start();
						}
					} catch (IOException e) {
						System.out.println("an error occured");
					}
				}
				catch(Exception e){
					//System.out.println("Timeout");
					continue;
				}
		}
		
	}

}
