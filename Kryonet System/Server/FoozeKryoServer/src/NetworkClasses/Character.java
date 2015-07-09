package NetworkClasses;

import java.awt.Color;





public class Character {
	public float mass=0;
	public String name = "";
	//public Color color = new Color(0, 0, 0,1);
	public float r=0,g=0,b=0;
	public float dX=0;
	public float dY=0;
	public float x;
	public float y;
	public float rX;
	public float rY;
	public boolean updated=true;
	public int id;
	public int world;
	public long last = System.currentTimeMillis();
	
	public void render(){
		x+=.1*(dX-x);
		y+=.1*(dY-y);
		
	}
}
