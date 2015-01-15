package com.AIG.units;

import com.AIG.blasteroids.GameActivity;

public class UnitAnimation {
	
	// Animation Data
	private Unit animatedUnit;
	private String type;
	private double animateStartTime;
	private int duration;
	
	// Bobbing Animation Info
	private float top;
	private float bot;
	private String direction;
	
	public UnitAnimation(Unit u, String myType) {
		animateStartTime = GameActivity.getGameTime();
		type = myType;
		animatedUnit = u;
		if(type == "Bob") {
			duration = 10000;
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
	
	public static void animateUnit(Unit u) {
		UnitAnimation currAn = u.getUnitAnimation();
		
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