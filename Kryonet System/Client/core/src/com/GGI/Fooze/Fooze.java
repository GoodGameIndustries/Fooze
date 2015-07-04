package com.GGI.Fooze;

import java.io.IOException;
import java.util.ArrayList;

import NetworkClasses.AddCharacter;
import NetworkClasses.AddMass;
import NetworkClasses.Character;
import NetworkClasses.Login;
import NetworkClasses.Lose;
import NetworkClasses.MoveCharacter;
import NetworkClasses.Network;
import NetworkClasses.RemoveCharacter;
import NetworkClasses.UpdateCharacter;
import NetworkClasses.World;

import com.GGI.Fooze.Objects.Food;
import com.GGI.Fooze.Screens.MainScreen;
import com.GGI.Fooze.Screens.Toolbar;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.net.Socket;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

public class Fooze extends Game {

	public Socket sClient;
	public Socket rClient;
	public boolean nextScreen = false;
	public float gridx =0,gridy=0;
	public float size=0;
	public ArrayList<Food> food = new ArrayList<Food>();
	public ArrayList<Character> players = new ArrayList<Character>();
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
	public Toolbar toolbar;
	public int money =0;
	public int unlock = 0;
	public Connect connect;
	public int world=1;
	public Thread t=new Thread();
	
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
		toolbar=new Toolbar(this);
	}

	public void send(String s){
		try {
			s+="\n";
			sClient.getOutputStream().write(s.getBytes());
			//System.out.println("sent: " + s);
		} catch (Exception e) {
			die=true;
			//e.printStackTrace();
		}
		
	}
	
	public void connect(){
		connect = new Connect(this);
		t=new Thread(connect);
		t.start();
	}

	public void save() {
		FileHandle file = Gdx.files.local("Stats.txt");
		file.writeString(money+":"+color.r+":"+color.g+":"+color.b+":"+unlock, false);
		
	}
	
	
	
	public void setScreen(int i) {
		switch(i){
		case 1:nextScreen=true;break;
		}
		
	}
	
	
}
