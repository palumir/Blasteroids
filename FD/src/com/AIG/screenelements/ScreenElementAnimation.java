package com.AIG.screenelements;
import com.AIG.blasteroids.GameActivity;

public class ScreenElementAnimation {
	
	// Animation Data
	private ScreenElement animatedScreenElement;
	private String type;
	private double animateStartTime;
	private int duration;
	
	// Bobbing Animation Info
	private float top;
	private float bot;
	private String direction;
	
	public ScreenElementAnimation(ScreenElement u, String myType, int newDuration) {
		animateStartTime = GameActivity.getGameTime();
		type = myType;
		animatedScreenElement = u;
		if(type == "Bob") {
			duration = newDuration;
			top = u.getY()+10;
			bot = u.getY();
			direction = "Up";
		}
	}
	
	public double getAnimateStartTime() {
		return animateStartTime;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public static void animateScreenElement(ScreenElement u) {
		ScreenElementAnimation currAn = u.getS();
		
		if(currAn != null) {
			
			if(GameActivity.getGameTime() - currAn.getAnimateStartTime() > currAn.getDuration() && currAn.getDuration() > 0) {
				u.despawn();
			}
			
			if(currAn.getType() == "Bob") {
				//  Move it up and down
				if(currAn.getDirection() == "Up") {
					u.moveInstantly(u.getX(), u.getY()+0.3f);
				}
				else {
					u.moveInstantly(u.getX(), u.getY()-0.3f);
				}
			
				// Swap the direction of bobbing?
				if(u.getY() >= currAn.getTop() || u.getY() <= currAn.getBot()) {
					currAn.switchDirection();
				}
			}
		}
	}
	
	public void switchDirection() {
		if(direction=="Up") {
			direction = "Down";
		}
		else {
			direction = "Up";
		}
	}
	
	public String getType() {
		return type;
	}
	
	public float getTop() {
		return top;
	}
	
	public float getBot() {
		return bot;
	}
	
	public String getDirection() {
		return direction;
	}
	
}