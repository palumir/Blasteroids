package com.DJG.abilities;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.DJG.fd.DisplayMessageActivity;
import com.DJG.units.Unit;

public class KnockBack {
	
	// Static information.
	private static ArrayList<KnockBack> allKnockBacks = new ArrayList<KnockBack>();
	public final static Object knockBacksLock = new Object(); // A lock so we don't fuck up the knockBackLock
	
	// General ability attributes. KnockBacks are static at the moment.
	private long startTime;
	private int duration = 5000;

	// Well, where is the ability?!
	private static float x;
	private static float y;
	private int radius;
	private int blastRadius;
	private int stroke;
	private int maxStroke;
	private int color;
	
	public KnockBack(float newX, float newY, int newBlastRadius, int newDuration) {
		x = newX;
		y = newY;
		color = Color.WHITE;
		maxStroke = 120;
		blastRadius = newBlastRadius;
		duration = newDuration;
		startTime = System.currentTimeMillis();
		synchronized(allKnockBacks) {
			addKnockBack(this);
		}
	}
	
	public void updateKnockBack(int knockBackPos) {
		synchronized(knockBacksLock) {
		if(this != null) {
			double percentDone = (double)(System.currentTimeMillis() - startTime)/(double)duration;
			radius = (int)(blastRadius*percentDone);
			stroke = (int)(maxStroke*(1 - percentDone));
			long currentTime = System.currentTimeMillis();
			if((int)(currentTime - startTime) > duration) {
					removeKnockBack(knockBackPos);
			}
		 }
		}
	}
	
	public static void updateknockBacks() {
		synchronized(KnockBack.knockBacksLock) {
			int knockBackPos = 0;
			for(int i = 0; i < allKnockBacks.size(); i++) {
				KnockBack b = allKnockBacks.get(i);
				b.updateKnockBack(knockBackPos);
				knockBackPos++;
			}
		}
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public static void clearKnockBacks() {
		synchronized(knockBacksLock) {
			allKnockBacks.clear();
		}
	}
	
	public static void addKnockBack(KnockBack b) {
		synchronized(knockBacksLock) {
			allKnockBacks.add(b);
		}
	}
	
	public static void removeKnockBack(int pos) {
		synchronized(knockBacksLock) {
			allKnockBacks.remove(pos);
		}
	}
	
	public static void checkIfHitKnockBack(Unit u) {
		float knockBackY = 0;
		float knockBackX = 0;
		float knockBackRadius = 0;
		synchronized(KnockBack.knockBacksLock) {
			for(int i = 0; i < KnockBack.getAllKnockBacks().size(); i++) {
				KnockBack b = KnockBack.getAllKnockBacks().get(i);
				knockBackY = b.getY();
				knockBackX = b.getX();
				knockBackRadius = b.getRadius();
				float yDistanceKnockBack = (knockBackY - u.getY());
				float xDistanceKnockBack = (knockBackX - u.getX());
				float distanceXYKnockBack = (float)Math.sqrt(yDistanceKnockBack*yDistanceKnockBack + xDistanceKnockBack*xDistanceKnockBack);
				if(distanceXYKnockBack <= knockBackRadius + u.getRadius() && !DisplayMessageActivity.isOffScreen(u.getX(),u.getY())) {
					
					u.knockBackFrom((int)x, (int)y, 70);
					break;
				}
			}
		}
	}
	
	public static void drawKnockBacks (Canvas canvas, Paint myPaint) {
        synchronized(KnockBack.knockBacksLock) {
  	        myPaint.setStyle(Paint.Style.STROKE);
			for(int i = 0; i < KnockBack.getAllKnockBacks().size(); i++) {
				KnockBack b = KnockBack.getAllKnockBacks().get(i);
        		myPaint.setColor(b.getColor());
	        	myPaint.setStrokeWidth(b.getStroke());
        		canvas.drawCircle(b.getX(),b.getY(),b.getRadius(), myPaint);
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
	
	public int getStroke() {
		return stroke;
	}
	
	public static ArrayList<KnockBack> getAllKnockBacks() {
		return allKnockBacks;
	}
}