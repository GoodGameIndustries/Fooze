package com.GGI.Fooze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.GGI.Fooze.Objects.Food;
import com.GGI.Fooze.Objects.Player;
import com.GGI.Fooze.Screens.MainScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class Fooze extends Game {

	public Socket sClient;
	public Socket rClient;
	public boolean nextScreen = false;
	public float gridx =0,gridy=0;
	public float size=0;
	public ArrayList<Food> food = new ArrayList<Food>();
	public ArrayList<Player> players = new ArrayList<Player>();
	public boolean update=false;
	public float massToAdd=0;
	public boolean die=false;
	public float sprint =50;
	public float sprintMax = 50;
	public float sprintMul = 1.5f;
	public Assets assets;
	public String name="";
	public ArrayList<String> messages = new ArrayList<String>();
	public int r = 0;
	public int ID = -1;
	public Color color = Color.GREEN;
	private ActionResolver actionResolver;
	public float xPos,yPos;
	public float mass=10;
	public int cState=0;
	public boolean isRender=false;
	public Fooze(ActionResolver androidLauncher){
		this.actionResolver=androidLauncher;
	}
	
	public void popup(){
		actionResolver.showOrLoadInterstital();
	}
	
	@Override
	public void create () {
		assets = new Assets(this);
		this.setScreen(new MainScreen(this));
	}

	public void send(String s){
		try {
			s+="\n";
			sClient.getOutputStream().write(s.getBytes());
			//System.out.println("sent: " + s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void connect(){
		cState=1;
		SocketHints hints = new SocketHints();
		sClient = Gdx.net.newClientSocket(Protocol.TCP, "52.11.36.209", 4443, hints);
		rClient = Gdx.net.newClientSocket(Protocol.TCP, "52.11.36.209", 4444, hints);
		try {
			sClient.getOutputStream().write("Connect\n".getBytes());
			String response = new BufferedReader(new InputStreamReader(rClient.getInputStream())).readLine();
			System.out.println(response);
		} catch (IOException e) {
			cState=2;
			System.out.println("an error occured");
		}
		
		new Thread(new Reader(this,rClient)).start();
	}
	

}
