package com.GGI.FoozeServer;

public class Broadcaster implements Runnable{

	public FoozeServer s;
	
	public Broadcaster(FoozeServer s){
		this.s=s;
	}
	
	@Override
	public void run() {
		while(true){
			if(s.broadcast)
			try{
			for(int i = 0; i < s.clients.size();i++){
				s.clients.get(i).se.send("Players"+s.listToString(s.clients));	
			}
			//System.out.println("Broadcast");
			s.broadcast=false;
			}
			catch(Exception e){}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
