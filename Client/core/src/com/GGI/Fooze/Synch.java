/**
 * 
 */
package com.GGI.Fooze;

/**
 * @author Emmett Deen
 *
 */
public class Synch implements Runnable{

	public Fooze f;
	
	public Synch(Fooze f){
		this.f=f;
	}
	
	@Override
	public void run() {
		while(true){
		f.send("Render:"+f.name+":"+f.xPos+":"+f.yPos+":"+f.mass+":"+f.ID+":"+f.color.r+":"+f.color.g+":"+f.color.b);
		}
	}

}
