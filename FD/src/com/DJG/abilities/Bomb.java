package com.DJG.abilities;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.DJG.fd.GameActivity;
import com.DJG.fd.R;
import com.DJG.units.Unit;

public class Bomb {
	
	// Static information.
	private static ArrayList<Bomb> allBombs = new ArrayList<Bomb>();
	public final static Object bombsLock = new Object(); // A lock so we don't fuck up the bombs
	
	// Bitmap
	public static Bitmap bombBMP = GameActivity.makeTransparent(BitmapFactory.decodeResource(GameActivity.survContext.getResources(), R.drawable.bomb));
	
	// General ability attributes. Bombs are static at the moment.
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
	
	public Bomb(float newX, float newY, int newBlastRadius, int newDuration) {
		x = newX;
		y = newY;
		color = Color.YELLOW;
		maxStroke = 100;
		blastRadius = newBlastRadius;
		duration = newDuration;
		startTime = GameActivity.getGameTime();
		synchronized(allBombs) {
			addBomb(this);
		}
	}
	
	public void updateBomb(int bombPos) {
		synchronized(bombsLock) {
		if(this != null) {
			double percentDone = (double)(GameActivity.getGameTime() - startTime)/(double)duration;
			radius = (int)(blastRadius*percentDone);
			stroke = (int)(maxStroke*(1 - percentDone));
			long currentTime = GameActivity.getGameTime();
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
	
	public static void clearBombs() {
		synchronized(bombsLock) {
			allBombs.clear();
		}
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
				if(distanceXYBomb <= bombRadius + u.getRadius() && !GameActivity.isOffScreen(u.getX(),u.getY())) {
					u.die();
					break;
				}
			}
		}
	}
	
	public static void drawBombs (Canvas canvas, Paint myPaint) {
        synchronized(Bomb.bombsLock) {
  	        myPaint.setStyle(Paint.Style.STROKE);
			for(int i = 0; i < Bomb.getAllBombs().size(); i++) {
				Bomb b = Bomb.getAllBombs().get(i);
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
	
	public static ArrayList<Bomb> getAllBombs() {
		return allBombs;
	}
}