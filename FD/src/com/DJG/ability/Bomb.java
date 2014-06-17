package com.DJG.ability;

public class Bomb {
	
	// Static information.
	private static Bomb currentBomb = null;
	
	// General ability attributes.
	private long startTime;
	private int duration;

	// Well, where is the ability?!
	private float x;
	private float y;
	private int radius;
	
	public Bomb(float newX, float newY, int newDuration) {
		x = newX;
		y = newY;
		duration = newDuration;
		startTime = System.currentTimeMillis();
		currentBomb = this;
	}
	
	public void updateBomb() {
		if(this != null) {
			radius = radius+2;
			long currentTime = System.currentTimeMillis();
			if((int)(currentTime - startTime) > duration) {
				currentBomb = null;
			}
		}
	}
	
	public long getStarTime() {
		return startTime;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public static void clearBomb() {
		currentBomb = null;
	}
	
	public static Bomb getCurrentBomb() {
		return currentBomb;
	}
}