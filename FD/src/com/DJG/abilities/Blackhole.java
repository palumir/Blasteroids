package com.DJG.abilities;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.DJG.fd.GameActivity;
import com.DJG.fd.R;
import com.DJG.units.Unit;

public class Blackhole {
	
	// Static information.
	private static ArrayList<Blackhole> allBlackholes = new ArrayList<Blackhole>();
	public final static Object BlackholesLock = new Object(); // A lock so we don't fuck up the Blackholes
	
	// General ability attributes. Blackholes are static at the moment.
	private long startTime;
	private int duration = 3000;
	
	// Bitmap
	public static Bitmap BlackholeBMP = GameActivity.makeTransparent(BitmapFactory.decodeResource(GameActivity.gameContext.getResources(), R.drawable.blackhole));

	// Well, where is the ability?!
	private float x;
	private float y;
	private int radius;
	private int blastRadius;
	private int stroke;
	private int maxStroke;
	private int color;
	
	private int maxRadius;
	private int minRadius;
	private String upDown = "Up";
	
	public Blackhole(float newX, float newY, int newBlastRadius, int newDuration) {
		x = newX;
		y = newY;
		color = Color.MAGENTA;
		maxStroke = 30;
		blastRadius = newBlastRadius;
		minRadius = newBlastRadius;
		maxRadius = newBlastRadius + 10;
		duration = newDuration;
		startTime = GameActivity.getGameTime();
		synchronized(allBlackholes) {
			addBlackhole(this);
		}
	}
	
	public void updateBlackhole(int BlackholePos) {
		synchronized(BlackholesLock) {
		if(this != null) {
			double percentDone = (double)(GameActivity.getGameTime() - startTime)/(double)duration;
			if(radius<blastRadius) {
				radius += 10;
			}
			if(radius>=blastRadius) {
				if(upDown == "Up") {
					radius++;
				}
				if(upDown == "Down") {
					radius--;
				}
				if(radius==maxRadius) {
					upDown = "Down";
				}
				if(radius==minRadius) {
					upDown = "Up";
				}
			}
			stroke = 1;
			long currentTime = GameActivity.getGameTime();
			if((int)(currentTime - startTime) > duration) {
					removeBlackhole(BlackholePos);
			}
		 }
		}
	}
	
	public static void updateBlackholes() {
		synchronized(Blackhole.BlackholesLock) {
			int BlackholePos = 0;
			for(int i = 0; i < allBlackholes.size(); i++) {
				Blackhole b = allBlackholes.get(i);
				b.updateBlackhole(BlackholePos);
				BlackholePos++;
			}
		}
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public static void addBlackhole(Blackhole b) {
		synchronized(BlackholesLock) {
			allBlackholes.add(b);
		}
	}
	
	public static void removeBlackhole(int pos) {
		synchronized(BlackholesLock) {
			allBlackholes.get(pos).unSuckIn();
			allBlackholes.remove(pos);
		}
	}
	
	public static void clearBlackholes() {
		synchronized(BlackholesLock) {
			allBlackholes.clear();
		}
	}
	
	public static void drawBlackholes(Canvas canvas, Paint myPaint) {
        // Draw Blackholes.
        synchronized(Blackhole.BlackholesLock) {
	        myPaint.setStyle(Paint.Style.STROKE);
			for(int i = 0; i < Blackhole.getAllBlackholes().size(); i++) {
				Blackhole s = Blackhole.getAllBlackholes().get(i);
    		myPaint.setColor(s.getColor());
	        	myPaint.setStrokeWidth(s.getStroke());
    		canvas.drawCircle(s.getX(),s.getY(),s.getRadius(), myPaint);
    	  }
    	}
	}
	
	public static void checkIfHitBlackhole(Unit u) {
		float BlackholeY = 0;
		float BlackholeX = 0;
		float BlackholeRadius = 0;
		synchronized(Blackhole.BlackholesLock) {
			for(int i = 0; i < Blackhole.getAllBlackholes().size(); i++) {
				Blackhole b = Blackhole.getAllBlackholes().get(i);
				BlackholeY = b.getY();
				BlackholeX = b.getX();
				BlackholeRadius = b.getRadius();
				float yDistanceBlackhole = (BlackholeY - u.getY());
				float xDistanceBlackhole = (BlackholeX - u.getX());
				float distanceXYBlackhole = (float)Math.sqrt(yDistanceBlackhole*yDistanceBlackhole + xDistanceBlackhole*xDistanceBlackhole);
				if(!u.getSuckedIn() && distanceXYBlackhole <= BlackholeRadius + u.getRadius() && !GameActivity.isOffScreen(u.getX(), u.getY())) {
					b.suckIn(u);
					break;
				}
			}
		}
	}
	
	public void unSuckIn() {
		synchronized(Unit.onScreenUnitsLock) {
			for(int j = 0; j < Unit.onScreenUnits.size(); j++) {
				Unit u = Unit.onScreenUnits.get(j);
				if(u.getSuckedIn()) {
					u.suckedIn(false);
					u.moveNormally(GameActivity.getScreenWidth()/2, GameActivity.getScreenHeight()/2);
					u.setSpinSpeed(0);
					u.setMoveSpeed(u.getOldMoveSpeed());
				}
			}
		}
	}
	
	public void suckIn(Unit u) {
		u.suckedIn(true);
		u.setSpinSpeed(0.2f);
		u.setMoveSpeed(0.3f);
		u.moveNormally(this.getX(),this.getY()); // Don't suck them in for now!
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
	
	public static void clearBlackhole() {
		synchronized(BlackholesLock) {
			allBlackholes.clear();
		}
	}
	
	public int getStroke() {
		return stroke;
	}
	
	public static ArrayList<Blackhole> getAllBlackholes() {
		return allBlackholes;
	}
}