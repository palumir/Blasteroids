package com.DJG.abilities;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.DJG.fd.GameActivity;
import com.DJG.fd.R;
import com.DJG.secrets.Secret;
import com.DJG.units.Unit;

public class Bomb {
	
	// Static information.
	private static ArrayList<Bomb> allBombs = new ArrayList<Bomb>();
	
	// Bitmap
	public static Bitmap bombBMP = GameActivity.makeTransparent(BitmapFactory.decodeResource(GameActivity.gameContext.getResources(), R.drawable.bomb));
	
	// General ability attributes. Bombs are static at the moment.
	private long startTime;
	private int duration = 1050;
	private boolean deadly = true;

	// Well, where is the ability?!
	private float x;
	private float y;
	private int radius;
	private int blastRadius;
	private int stroke;
	private int maxStroke;
	private int color = -1;
	private int color2 = -1;
	private int color1 = -1;
	
	public Bomb(float newX, float newY, int newBlastRadius, int newDuration) {
		x = newX;
		y = newY;
		color = Color.YELLOW;
		maxStroke = 100;
		blastRadius = newBlastRadius;
		duration = newDuration;
		startTime = GameActivity.getGameTime();
		addBomb(this);
	}
	
	public Bomb(float newX, float newY, int newBlastRadius, int newDuration, int newColor) {
		x = newX;
		y = newY;
		color = newColor;
		maxStroke = 100;
		blastRadius = newBlastRadius;
		duration = newDuration;
		startTime = GameActivity.getGameTime();
		addBomb(this);
	}
	
	public Bomb(float newX, float newY, int newBlastRadius, int newDuration, int newColor, int newColor2) {
		x = newX;
		y = newY;
		color = newColor;
		color1 = newColor;
		color2 = newColor2;
		maxStroke = 100;
		blastRadius = newBlastRadius;
		duration = newDuration;
		startTime = GameActivity.getGameTime();
		addBomb(this);
	}
	
	public Bomb(float newX, float newY, int newBlastRadius, int newDuration, int newColor, int newColor2, boolean kill) {
		deadly = kill;
		x = newX;
		y = newY;
		color = newColor;
		color1 = newColor;
		color2 = newColor2;
		maxStroke = 100;
		blastRadius = newBlastRadius;
		duration = newDuration;
		startTime = GameActivity.getGameTime();
		addBomb(this);
	}
	
	public Bomb(float newX, float newY, int newBlastRadius, int newDuration, String setColor) {
		x = newX;
		y = newY;
		color = Color.YELLOW;
		if(setColor=="Red") {
			color = Color.RED;
		}
		if(setColor=="White") {
			color = Color.WHITE;
		}
		maxStroke = 100;
		blastRadius = newBlastRadius;
		duration = newDuration;
		startTime = GameActivity.getGameTime();
		addBomb(this);
	}

	public void updateBomb(int bombPos) {
		if(this != null) {
			if(color2 != -1) {
				if(color==color2) {
					color=color1;
				}
				else if(color==color1) {
					color=color2;
				}
			}
			
			double percentDone = (double)(GameActivity.getGameTime() - startTime)/(double)duration;
			radius = (int)(blastRadius*percentDone);
			stroke = (int)(maxStroke*(1 - percentDone));
			long currentTime = GameActivity.getGameTime();
			if((int)(currentTime - startTime) > duration) {
					removeBomb(bombPos);
			}
		 }
	}
	
	public static void updateBombs() {
			int bombPos = 0;
			for(int i = 0; i < allBombs.size(); i++) {
				Bomb b = allBombs.get(i);
				if(Secret.isPieSecret(b.x,b.y)) Secret.activatePieSecret();
				b.updateBomb(bombPos);
				bombPos++;
		}
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public static void clearBombs() {
		if(allBombs!=null) {
			allBombs.clear();
		}
	}
	
	public static void addBomb(Bomb b) {
		allBombs.add(b);
	}
	
	public static void removeBomb(int pos) {
		allBombs.remove(pos);
	}
	
	public static void checkIfHitBomb(Unit u) {
		float bombY = 0;
		float bombX = 0;
		float bombRadius = 0;
		for(int i = 0; i < Bomb.getAllBombs().size(); i++) {
			Bomb b = Bomb.getAllBombs().get(i);
			if(b.deadly){ 
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
        myPaint.setStyle(Paint.Style.STROKE);
		for(int i = 0; i < Bomb.getAllBombs().size(); i++) {
			Bomb b = Bomb.getAllBombs().get(i);
    		myPaint.setColor(b.getColor());
        	myPaint.setStrokeWidth(b.getStroke());
    		canvas.drawCircle(b.getX(),b.getY(),b.getRadius(), myPaint);
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