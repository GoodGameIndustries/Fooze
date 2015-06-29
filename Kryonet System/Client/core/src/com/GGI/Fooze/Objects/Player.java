/**
 * 
 */
package com.GGI.Fooze.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Emmett Deen
 *
 */
public class Player extends Vector2{

	public float mass=0;
	public String name = "";
	public Color color = Color.RED;
	public int ID = -1;
	public float dX=0;
	public float dY=0;
	public boolean updated=true;
	
	public Player(String name, float mass, int ID, Color color){
		this.name=name;
		this.mass=mass;
		this.ID=ID;
		this.color=color;
		x=0;
		y=0;
	}
	
	public void render(){
		x+=.05*(dX-x);
		y+=.05*(dY-y);
	}
	
}
