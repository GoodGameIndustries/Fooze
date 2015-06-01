/**
 * 
 */
package com.GGI.Fooze.Objects;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Emmett Deen
 *
 */
public class Player extends Vector2{

	public float mass=0;
	
	public Player(float mass){
		this.mass=mass;
		x=0;
		y=0;
	}
	
}
