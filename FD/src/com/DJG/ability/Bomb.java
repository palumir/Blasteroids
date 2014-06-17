package com.DJG.ability;

import android.graphics.Color;

public class Bomb {
	
	// Static information.
	private static Bomb currentBomb = null;
	
	// General ability attributes.
	private long startTime;
	private int duration = 1500;

	// Well, where is the ability?!
	private float x;
	private float y;
	private int radius;
	private int blastRadius;
	private int stroke;
	private int color;
	
	public Bomb(float newX, float newY, int newBlastRadius) {
		x = newX;
		y = newY;
		color = Color.YELLOW;
		stroke = 100;
		blastRadius = newBlastRadius;
		startTime = System.currentTimeMillis();
		currentBomb = this;
	}
	
	public void updateBomb() {
		if(this != null) {
			radius = radius+2;
			stroke = stroke-1;
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
	
	public int getColor() {
		return color;
	}

	public float getY() {
		return y;
	}
	
	public int getBlastRadius() {
		return blastRadius;
	}
	
	public static void clearBomb() {
		currentBomb = null;
	}
	
	public int getStroke() {
		return stroke;
	}
	
	public static Bomb getCurrentBomb() {
		return currentBomb;
	}
}