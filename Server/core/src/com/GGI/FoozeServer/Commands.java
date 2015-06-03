package com.GGI.FoozeServer;

import java.util.Date;
import java.util.Scanner;

public class Commands implements Runnable{

	public FoozeServer f;
	private Scanner scan = new Scanner(System.in);
	private String db = "Fooze Server: ";
	
public Commands(FoozeServer f){
	this.f=f;
}

@Override
public void run() {
	while(true){
		String str=scan.nextLine();
		System.out.println("Admin: " +str);
		Date d = new Date();
		if(str.equals("online")){
			System.out.println(db+"Time: "+d.toString()+"\n\t\t Connections: "+f.clients.size());
		}
		else{
			System.out.println(db+"No command found");
		}
	}
}
}
