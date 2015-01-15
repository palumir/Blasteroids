package com.AIG.abilities;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.AIG.blasteroids.GameActivity;
import com.AIG.blasteroids.R;
import com.AIG.units.Unit;

public class Nuke {
	
	// Static information.
	private static ArrayList<Nuke> allNukes = new ArrayList<Nuke>();
	public final static Object NukesLock = new Object(); // A lock so we don't fuck up the Nukes
	private static Random r = new Random();
	
	// Bitmap
	public static Bitmap NukeBMP = GameActivity.makeTransparent(BitmapFactory.decodeResource(GameActivity.gameContext.getResources(), R.drawable.nuke));
	
	// General ability attributes. Nukes are static at the moment.
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
	
	public Nuke(float newX, float newY, int newBlastRadius, int newDuration) {
		x = newX;
		y = newY;
		color = Color.YELLOW;
		maxStroke = 0;
		blastRadius = newBlastRadius;
		duration = newDuration;
		Bomb b = new Bomb(GameActivity.getScreenWidth()/2,GameActivity.getScreenHeight()/2,GameActivity.getScreenHeight()/2,3000,Color.WHITE,Color.YELLOW);
		startTime = GameActivity.getGameTime();
		synchronized(allNukes) {
			addNuke(this);
		}
	}
	
	public void updateNuke(int NukePos) {
		synchronized(NukesLock) {
		if(this != null) {
			long currentTime = GameActivity.getGameTime();
			if((int)(currentTime - startTime) > duration) {
					removeNuke(NukePos);
			}
		 }
		}
	}
	
	public static void updateNukes() {
		synchronized(Nuke.NukesLock) {
			int NukePos = 0;
			for(int i = 0; i < allNukes.size(); i++) {
				Nuke b = allNukes.get(i);
				b.updateNuke(NukePos);
				NukePos++;
			}
		}
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public static void clearNukes() {
		synchronized(NukesLock) {
			if(allNukes!=null) {
			allNukes.clear();
			}
		}
	}
	
	public static void addNuke(Nuke b) {
		synchronized(NukesLock) {
			allNukes.add(b);
		}
	}
	
	public static void removeNuke(int pos) {
		synchronized(NukesLock) {
			allNukes.remove(pos);
		}
	}
	
	public static void checkIfHitNuke(Unit u) {
		float NukeY = 0;
		float NukeX = 0;
		float NukeRadius = 0;
		synchronized(Nuke.NukesLock) {
			for(int i = 0; i < Nuke.getAllNukes().size(); i++) {
				Nuke b = Nuke.getAllNukes().get(i);
				NukeY = b.getY();
				NukeX = b.getX();
				NukeRadius = b.getRadius();
				float yDistanceNuke = (NukeY - u.getY());
				float xDistanceNuke = (NukeX - u.getX());
				float distanceXYNuke = (float)Math.sqrt(yDistanceNuke*yDistanceNuke + xDistanceNuke*xDistanceNuke);
				if(distanceXYNuke <= NukeRadius + u.getRadius() && !GameActivity.isOffScreen(u.getX(),u.getY())) {
					u.die();
					break;
				}
			}
		}
	}
	
	public static void drawNukes (Canvas canvas, Paint myPaint) {
        synchronized(Nuke.NukesLock) {
  	        myPaint.setStyle(Paint.Style.STROKE);
			for(int i = 0; i < Nuke.getAllNukes().size(); i++) {
				Nuke b = Nuke.getAllNukes().get(i);
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
	
	public static ArrayList<Nuke> getAllNukes() {
		return allNukes;
	}
}