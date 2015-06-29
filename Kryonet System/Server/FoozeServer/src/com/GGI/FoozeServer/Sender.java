package com.GGI.FoozeServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Sender{

	public FoozeServer s;
	public Connection c;
	
	public Sender(FoozeServer s,Connection c){
		this.s=s;
		this.c=c;
		
	}

	public boolean send(String s) {
		try {
			s+="\n";
			//System.out.println("sent");
			this.s.s++;
			c.s.getOutputStream().write(s.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			this.s.clients.remove(c);
			return true;
		}
		return false;
	}
	
}

