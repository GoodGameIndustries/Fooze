package com.GGI.FoozeServer.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Food extends Vector2{

	public Color color;
	
	public Food(Color color){
		this.color=color;
		
	}
	
	public String toString(){
		return x+","+y+","+color.r+","+color.g+","+color.b;
		
	}
	
}
