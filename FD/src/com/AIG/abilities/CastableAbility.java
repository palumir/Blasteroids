package com.AIG.abilities;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.AIG.earthDefense.GameActivity;
import com.AIG.units.Unit;

public class CastableAbility {
	// Static information.
	private static ArrayList<CastableAbility> allCastableAbilities;
	public final static Object CastableAbilitiesLock = new Object(); // A lock so we don't fuck up the CastableAbilities
	
	// General ability attributes. CastableAbilities are static at the moment.
	private long startTime;
	private int duration = 3000;
	
	// Bitmap
	public static Bitmap castableAbilityBMP;

	// Well, where is the ability?!
	private float x;
	private float y;
	private int radius;
	private int blastRadius;
	private int stroke;
	private int maxStroke;
	private int color;
	
	public CastableAbility(float newX, float newY, int newBlastRadius, int newDuration) {
		x = newX;
		y = newY;
		blastRadius = newBlastRadius;
		duration = newDuration;
		startTime = GameActivity.getGameTime();
	}
	
	public void updateCastableAbility(int CastableAbilityPos) {
		// This must be over-ridden. We don't know how to update a general ability.
	}
	
	public static void updateCastableAbilities() {
		// This must be over-ridden. We don't know how to update a general ability.
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public static void addCastableAbility(CastableAbility b) {
		// Must be over-ridden.
	}
	
	public static void removeCastableAbility(int pos) {
		// Must be over-ridden.
	}
	
	public static void clearCastableAbilities() {
		// Must be over-ridden.
	}
	
	public static void drawCastableAbilities(Canvas canvas, Paint myPaint) {
		// Must be over-ridden.
	}
	
	public static void checkIfHitCastableAbility(Unit u) {
		// Must be over-ridden.
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
	
	public static void clearCastableAbility() {
		// Must be over-ridden.
	}
	
	public int getStroke() {
		return stroke;
	}
	
	public static ArrayList<CastableAbility> getAllCastableAbilities() {
		// Must be over-ridden.
		return allCastableAbilities;
	}
}