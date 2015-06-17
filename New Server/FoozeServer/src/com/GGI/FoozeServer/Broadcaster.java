package com.GGI.FoozeServer;

public class Broadcaster implements Runnable{

	public FoozeServer s;
	
	public Broadcaster(FoozeServer s){
		this.s=s;
	}
	
	@Override
	public void run() {
		while(true){
			for(int i = 0; i < s.clients.size();i++){
				s.clients.get(i).se.send("Players"+s.listToString(s.clients));	
			}
			
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
