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
	
	public Player(String name, float mass){
		this.name=name;
		this.mass=mass;
		x=0;
		y=0;
	}
	
}
