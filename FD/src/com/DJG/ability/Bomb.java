package com.DJG.ability;

import java.util.ArrayList;

import android.graphics.Color;

import com.DJG.unit.Unit;

public class Bomb {
	
	// Static information.
	private static ArrayList<Bomb> allBombs = new ArrayList<Bomb>();
	public final static Object bombsLock = new Object(); // A lock so we don't fuck up the bombs
	
	// General ability attributes. Bombs are static at the moment.
	private long startTime;
	private int duration = 1050;

	// Well, where is the ability?!
	private float x;
	private float y;
	private int radius;
	private int blastRadius;
	private int stroke;
	private int color;
	
	public Bomb(float newX, float newY) {
		x = newX;
		y = newY;
		color = Color.YELLOW;
		stroke = 100;
		blastRadius = 500;
		startTime = System.currentTimeMillis();
		synchronized(allBombs) {
			addBomb(this);
		}
	}
	
	public void updateBomb(int bombPos) {
		synchronized(bombsLock) {
		if(this != null) {
			radius = radius+2;
			stroke = stroke-1;
			long currentTime = System.currentTimeMillis();
			if((int)(currentTime - startTime) > duration) {
					removeBomb(bombPos);
			}
		 }
		}
	}
	
	public static void updateBombs() {
		synchronized(Bomb.bombsLock) {
			int bombPos = 0;
			for(int i = 0; i < allBombs.size(); i++) {
				Bomb b = allBombs.get(i);
				b.updateBomb(bombPos);
				bombPos++;
			}
		}
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public static void addBomb(Bomb b) {
		synchronized(bombsLock) {
			allBombs.add(b);
		}
	}
	
	public static void removeBomb(int pos) {
		synchronized(bombsLock) {
			allBombs.remove(pos);
		}
	}
	
	public static void checkIfHitBomb(Unit u) {
		float bombY = 0;
		float bombX = 0;
		float bombRadius = 0;
		synchronized(Bomb.bombsLock) {
			for(int i = 0; i < Bomb.getAllBombs().size(); i++) {
				Bomb b = Bomb.getAllBombs().get(i);
				bombY = b.getY();
				bombX = b.getX();
				bombRadius = b.getRadius();
				float yDistanceBomb = (bombY - u.getY());
				float xDistanceBomb = (bombX - u.getX());
				float distanceXYBomb = (float)Math.sqrt(yDistanceBomb*yDistanceBomb + xDistanceBomb*xDistanceBomb);
				if(distanceXYBomb <= bombRadius + u.getRadius()) {
					u.die();
					break;
				}
			}
		}
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
		synchronized(bombsLock) {
			allBombs.clear();
		}
	}
	
	public int getStroke() {
		return stroke;
	}
	
	public static ArrayList<Bomb> getAllBombs() {
		return allBombs;
	}
}