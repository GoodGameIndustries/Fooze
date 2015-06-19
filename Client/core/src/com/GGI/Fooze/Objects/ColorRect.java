package com.GGI.Fooze.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class ColorRect extends Rectangle{

	public Color color;
	
	public ColorRect(float x,float y,float width,float height,float r,float g, float b){
		super(x,y,width,height);
		color = new Color(r/255f,g/255f,b/255f,1);
	}
	
}
