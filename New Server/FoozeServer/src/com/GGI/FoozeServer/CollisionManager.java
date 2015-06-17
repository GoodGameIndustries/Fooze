/**
 * 
 */
package com.GGI.FoozeServer;

/**
 * @author Emmett Deen
 *
 */
public class CollisionManager implements Runnable{

	public FoozeServer s;
	
	public CollisionManager(FoozeServer s){
		this.s=s;
	}
	
	@Override
	public void run() {
		while(true){
			//System.out.println("Collide Check");
			try{
			System.out.print("");
		for(int i = 0; i < s.clients.size()-1;i++){
			//System.out.println("Checking");
			Reader c1 = s.clients.get(i);
			for(int j = i+1;j<s.clients.size();j++){
				Reader c2 = s.clients.get(j);
			//if(Intersector.overlapCircles(new Circle(c1.x,c1.y,getRadius(c1.mass)), new Circle(c2.x,c2.y,getRadius(c2.mass)))){
				
				if(s.dist(c1.x,c1.y,c2.x,c2.y)<s.getRadius(c1.mass)&&c1.mass>c2.mass){
					//System.out.println("Collide");
				for(int m = 0; m < 100;m++){
					c2.se.send("lose");
					c2.die=true;
				}
				c1.se.send("addMass:"+c2.mass);
				s.clients.remove(c2);}
				else if(s.dist(c1.x,c1.y,c2.x,c2.y)<s.getRadius(c2.mass)&&c2.mass>c1.mass){
					//System.out.println("Collide");
					for(int m = 0; m < 100;m++){
						c1.se.send("lose");
						c1.die=true;
					}
					c2.se.send("addMass:"+c1.mass);
					s.clients.remove(c1);}
			
			
			//}
				
				
			}
		}
		
		}
			catch(Exception e){
				System.out.println("Collison Issue");
			}
		
	}
	}
}
