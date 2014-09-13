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

public class BombTurret {
	
	// Static information.
	private static ArrayList<BombTurret> allBombTurrets = new ArrayList<BombTurret>();
	public final static Object BombTurretsLock = new Object(); // A lock so we don't fuck up the BombTurrets
	
	// General ability attributes. BombTurrets are static at the moment.
	private long startTime;
	private int duration = 3000;
	private int attackCooldown = 400;
	private double lastAttackTime = 0;
	
	// Bitmap
	public static Bitmap BombTurretBMP = GameActivity.makeTransparent(BitmapFactory.decodeResource(GameActivity.gameContext.getResources(), R.drawable.bomb_turret));

	// Well, where is the ability?!
	private float x;
	private float y;
	private int radius;
	private int blastRadius;
	private int stroke;
	private int maxStroke;
	private int color;
	
	
	public BombTurret(float newX, float newY, int newBlastRadius, int newDuration) {
		x = newX;
		y = newY;
		color = Color.WHITE;
		maxStroke = 30;
		blastRadius = newBlastRadius;
		duration = newDuration;
		startTime = GameActivity.getGameTime();
		synchronized(allBombTurrets) {
			addBombTurret(this);
		}
	}
	
	public void updateBombTurret(int BombTurretPos) {
		synchronized(BombTurretsLock) {
		if(this != null) {
			double percentDone = (double)(GameActivity.getGameTime() - startTime)/(double)duration;
			if(radius<blastRadius) {
				radius += 10;
			}
			stroke = 1;
			long currentTime = GameActivity.getGameTime();
			if((int)(currentTime - startTime) > duration) {
					removeBombTurret(BombTurretPos);
			}
		 }
		}
	}
	
	public static void updateBombTurrets() {
		synchronized(BombTurret.BombTurretsLock) {
			int BombTurretPos = 0;
			for(int i = 0; i < allBombTurrets.size(); i++) {
				BombTurret b = allBombTurrets.get(i);
				b.updateBombTurret(BombTurretPos);
				BombTurretPos++;
			}
		}
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public static void addBombTurret(BombTurret b) {
		synchronized(BombTurretsLock) {
			allBombTurrets.add(b);
		}
	}
	
	public static void removeBombTurret(int pos) {
		synchronized(BombTurretsLock) {
			allBombTurrets.get(pos).unSuckIn();
			allBombTurrets.remove(pos);
		}
	}
	
	public static void clearBombTurrets() {
		synchronized(BombTurretsLock) {
			if(allBombTurrets!=null) {
				allBombTurrets.clear();
			}
		}
	}
	
	public static void drawBombTurrets(Canvas canvas, Paint myPaint) {
        // Draw BombTurrets.
        synchronized(BombTurret.BombTurretsLock) {
	        myPaint.setStyle(Paint.Style.STROKE);
			for(int i = 0; i < BombTurret.getAllBombTurrets().size(); i++) {
				BombTurret s = BombTurret.getAllBombTurrets().get(i);
				myPaint.setColor(s.getColor());
	        	myPaint.setStrokeWidth(s.getStroke());
	        	canvas.drawCircle(s.getX(),s.getY(),s.getRadius(), myPaint);
				canvas.drawBitmap(BombTurret.BombTurretBMP, s.getX()-32, s.getY() - 32, null);
    	  }
    	}
	}
	
	public static void checkIfHitBombTurret(Unit u) {
		float BombTurretY = 0;
		float BombTurretX = 0;
		float BombTurretRadius = 0;
		synchronized(BombTurret.BombTurretsLock) {
			for(int i = 0; i < BombTurret.getAllBombTurrets().size(); i++) {
				BombTurret b = BombTurret.getAllBombTurrets().get(i);
				BombTurretY = b.getY();
				BombTurretX = b.getX();
				BombTurretRadius = b.getRadius();
				float yDistanceBombTurret = (BombTurretY - u.getY());
				float xDistanceBombTurret = (BombTurretX - u.getX());
				float distanceXYBombTurret = (float)Math.sqrt(yDistanceBombTurret*yDistanceBombTurret + xDistanceBombTurret*xDistanceBombTurret);
				if(b.attackIsOffCooldown() && distanceXYBombTurret < BombTurretRadius && !u.isAttacked()) {
					b.attack(u);
					break;
				}
			}
		}
	}
	
	public void attack(Unit u) {
		lastAttackTime = GameActivity.getGameTime();
		u.setAttacked(true);
		Unit m = new Unit("Any Name","Bomb Bullet",getX(),getY());
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
	
	public static void clearBombTurret() {
		synchronized(BombTurretsLock) {
			allBombTurrets.clear();
		}
	}
	
	public int getStroke() {
		return stroke;
	}
	
	public static ArrayList<BombTurret> getAllBombTurrets() {
		return allBombTurrets;
	}
}