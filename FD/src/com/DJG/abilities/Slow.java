package com.DJG.abilities;

import java.util.ArrayList;

import android.graphics.Color;
import android.util.Log;

import com.DJG.units.Unit;

public class Slow {
	
	// Static information.
	private static ArrayList<Slow> allSlows = new ArrayList<Slow>();
	public final static Object SlowsLock = new Object(); // A lock so we don't fuck up the Slows
	
	// General ability attributes. Slows are static at the moment.
	private long startTime;
	private int duration = 1050;

	// Well, where is the ability?!
	private float x;
	private float y;
	private int radius;
	private int blastRadius;
	private int stroke;
	private int maxStroke;
	private int color;
	
	public Slow(float newX, float newY, int newBlastRadius, int newDuration) {
		x = newX;
		y = newY;
		color = Color.BLUE;
		maxStroke = 100;
		blastRadius = newBlastRadius;
		duration = newDuration;
		startTime = System.currentTimeMillis();
		synchronized(allSlows) {
			addSlow(this);
		}
	}
	
	public void updateSlow(int SlowPos) {
		synchronized(SlowsLock) {
		if(this != null) {
			double percentDone = (double)(System.currentTimeMillis() - startTime)/(double)duration;
			radius = (int)(blastRadius*percentDone);
			stroke = (int)(maxStroke*(1 - percentDone));
			long currentTime = System.currentTimeMillis();
			if((int)(currentTime - startTime) > duration) {
					removeSlow(SlowPos);
			}
		 }
		}
	}
	
	public static void updateSlows() {
		synchronized(Slow.SlowsLock) {
			int SlowPos = 0;
			for(int i = 0; i < allSlows.size(); i++) {
				Slow b = allSlows.get(i);
				b.updateSlow(SlowPos);
				SlowPos++;
			}
		}
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public static void addSlow(Slow b) {
		synchronized(SlowsLock) {
			allSlows.add(b);
		}
	}
	
	public static void removeSlow(int pos) {
		synchronized(SlowsLock) {
			allSlows.remove(pos);
		}
	}
	
	public static void checkIfHitSlow(Unit u) {
		float SlowY = 0;
		float SlowX = 0;
		float SlowRadius = 0;
		synchronized(Slow.SlowsLock) {
			for(int i = 0; i < Slow.getAllSlows().size(); i++) {
				Slow b = Slow.getAllSlows().get(i);
				SlowY = b.getY();
				SlowX = b.getX();
				SlowRadius = b.getRadius();
				float yDistanceSlow = (SlowY - u.getY());
				float xDistanceSlow = (SlowX - u.getX());
				float distanceXYSlow = (float)Math.sqrt(yDistanceSlow*yDistanceSlow + xDistanceSlow*xDistanceSlow);
				if(distanceXYSlow <= SlowRadius + u.getRadius()) {
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
	
	public static void clearSlow() {
		synchronized(SlowsLock) {
			allSlows.clear();
		}
	}
	
	public int getStroke() {
		return stroke;
	}
	
	public static ArrayList<Slow> getAllSlows() {
		return allSlows;
	}
}