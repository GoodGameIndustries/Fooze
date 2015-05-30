/**
 * 
 */
package com.GGI.FoozeServer;

import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.Timer;

/**
 * @author Emmett Deen
 *
 */
public class Connection {

	public Socket r;
	public Socket s;
	public String name;
	public long lastT=System.currentTimeMillis();
	
	
	public Connection(Socket r,Socket s){
		this.r=r;
		this.s=s;
	}
	public void dispose(){
		r=null;
		s=null;
	}
	
}
