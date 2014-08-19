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

public class Turret {
	
	// Static information.
	private static ArrayList<Turret> allTurrets = new ArrayList<Turret>();
	public final static Object TurretsLock = new Object(); // A lock so we don't fuck up the Turrets
	
	// General ability attributes. Turrets are static at the moment.
	private long startTime;
	private int duration = 3000;
	private int attackCooldown = 100;
	private double lastAttackTime = 0;
	
	// Bitmap
	public static Bitmap TurretBMP = GameActivity.makeTransparent(BitmapFactory.decodeResource(GameActivity.gameContext.getResources(), R.drawable.turret));

	// Well, where is the ability?!
	private float x;
	private float y;
	private int radius;
	private int blastRadius;
	private int stroke;
	private int maxStroke;
	private int color;
	
	
	public Turret(float newX, float newY, int newBlastRadius, int newDuration) {
		x = newX;
		y = newY;
		color = Color.WHITE;
		maxStroke = 30;
		blastRadius = newBlastRadius;
		duration = newDuration;
		startTime = GameActivity.getGameTime();
		synchronized(allTurrets) {
			addTurret(this);
		}
	}
	
	public void updateTurret(int TurretPos) {
		synchronized(TurretsLock) {
		if(this != null) {
			double percentDone = (double)(GameActivity.getGameTime() - startTime)/(double)duration;
			if(radius<blastRadius) {
				radius += 10;
			}
			stroke = 1;
			long currentTime = GameActivity.getGameTime();
			if((int)(currentTime - startTime) > duration) {
					removeTurret(TurretPos);
			}
		 }
		}
	}
	
	public static void updateTurrets() {
		synchronized(Turret.TurretsLock) {
			int TurretPos = 0;
			for(int i = 0; i < allTurrets.size(); i++) {
				Turret b = allTurrets.get(i);
				b.updateTurret(TurretPos);
				TurretPos++;
			}
		}
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public static void addTurret(Turret b) {
		synchronized(TurretsLock) {
			allTurrets.add(b);
		}
	}
	
	public static void removeTurret(int pos) {
		synchronized(TurretsLock) {
			allTurrets.get(pos).unSuckIn();
			allTurrets.remove(pos);
		}
	}
	
	public static void clearTurrets() {
		synchronized(TurretsLock) {
			allTurrets.clear();
		}
	}
	
	public static void drawTurrets(Canvas canvas, Paint myPaint) {
        // Draw Turrets.
        synchronized(Turret.TurretsLock) {
	        myPaint.setStyle(Paint.Style.STROKE);
			for(int i = 0; i < Turret.getAllTurrets().size(); i++) {
				Turret s = Turret.getAllTurrets().get(i);
				myPaint.setColor(s.getColor());
	        	myPaint.setStrokeWidth(s.getStroke());
	        	canvas.drawCircle(s.getX(),s.getY(),s.getRadius(), myPaint);
				canvas.drawBitmap(Turret.TurretBMP, s.getX()-32, s.getY() - 32, null);
    	  }
    	}
	}
	
	public static void checkIfHitTurret(Unit u) {
		float TurretY = 0;
		float TurretX = 0;
		float TurretRadius = 0;
		synchronized(Turret.TurretsLock) {
			for(int i = 0; i < Turret.getAllTurrets().size(); i++) {
				Turret b = Turret.getAllTurrets().get(i);
				TurretY = b.getY();
				TurretX = b.getX();
				TurretRadius = b.getRadius();
				float yDistanceTurret = (TurretY - u.getY());
				float xDistanceTurret = (TurretX - u.getX());
				float distanceXYTurret = (float)Math.sqrt(yDistanceTurret*yDistanceTurret + xDistanceTurret*xDistanceTurret);
				if(b.attackIsOffCooldown() && distanceXYTurret < TurretRadius && !u.isAttacked()) {
					b.attack(u);
					break;
				}
			}
		}
	}
	
	public void attack(Unit u) {
		lastAttackTime = GameActivity.getGameTime();
		u.setAttacked(true);
		Unit m = new Unit("Any Name","Bullet",getX(),getY());
		m.fixate(u);
	}
	
	public boolean attackIsOffCooldown() {
		return (GameActivity.getGameTime() - lastAttackTime > attackCooldown);
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
	
	public static void clearTurret() {
		synchronized(TurretsLock) {
			allTurrets.clear();
		}
	}
	
	public int getStroke() {
		return stroke;
	}
	
	public static ArrayList<Turret> getAllTurrets() {
		return allTurrets;
	}
}