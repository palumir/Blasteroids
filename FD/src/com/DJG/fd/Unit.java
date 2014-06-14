package com.DJG.fd;

import android.graphics.Color;
import android.util.Log;

public class Unit {
	// Unit General Information:
	private String name;
	private String type;     // Chose not to simply store this as a UnitType incase we want to modify individual
						     // fields to be unique values. In other words, we may want to change an Ogre to be
	// Position Information: // really big, but we don't want to have to add a new UnitType every time we do that!
	private float x;     
	private float y;     
	private float xNew;
	private float yNew;
	private int radius;
	private float moveSpeed;
	
	// Combat information:
	private boolean killable;
	
	// TEMPORARY COLOR STORE FOR COOLNESS
	public int color;
	
	// Unit Stats
	private int currentHitPoints;
	private int maxHitPoints;
	private int attackSpeed;
	private int damage;
	private int attackWait;
	
	// Wave Information:
	private int waveSpawnDelay; // How long to delay the next unit from spawning?
	

	public Unit(String newName, String newType, float xSpawn, float ySpawn, int newWaveSpawnDelay, int c) {
		// Look up the UnitType and set the values.
		UnitType u = UnitType.getUnitType(newType);
		radius = u.getRadius();
		type = u.getType();
		moveSpeed = u.getMoveSpeed();
		killable = u.getKillable();
		
		// Stats
		maxHitPoints = u.getMaxHitPoints();
		currentHitPoints = maxHitPoints;
		attackSpeed = u.getAttackSpeed();
		attackWait = attackSpeed;
		damage = u.getDamage();
		// TEMPORARY COLOR FOR COOLNESS
		color = c;
		
		// Set it's coordinates.
		name = newName;
		type = newType;
		x = xSpawn;
		y = ySpawn;
		xNew = xSpawn;
		yNew = ySpawn;
		waveSpawnDelay = newWaveSpawnDelay;
		
		// Add it to the list of units to be drawn.
		DisplayMessageActivity.addUnit(this);
	}
	
	public void moveNormally(float xGo, float yGo) {
		xNew = xGo;
		yNew = yGo;
	}
	
	public void moveInstantly(float xGo, float yGo) {
		x = xGo;
		y = yGo;
		xNew = xGo;
		yNew = yGo;
	}

	public void moveUnit() {
		float yDistance = (yNew - y);
		float xDistance = (xNew - x);
		float step = moveSpeed;
		float distanceXY = (float)Math.sqrt(yDistance*yDistance + xDistance*xDistance); // It should take this many frames to get there.
		
		// Move the unit.
		if(xNew != x || yNew != y) {
			if(xDistance != 0 && yDistance != 0) {
				// Deal with negatives.
				if(xDistance < 0) {
					x = x + (-1)*Math.abs(xDistance/distanceXY)*step;
				}
				else {
					x = x + Math.abs(xDistance/distanceXY)*step;
				}
				
				// Deal with negatives.
				if(yDistance < 0) {
					y = y + (-1)*Math.abs(yDistance/distanceXY)*step;
				}
				else {
					y = y + Math.abs(yDistance/distanceXY)*step;
				}
			}
			else if(xDistance == 0 && yDistance != 0) {
				y = yNew + step;
			}
			else if(yDistance == 0 && xDistance != 0) {
				x = xNew + step;
			}
			else {}
		}
		
		// Just move it if it's close.
		if(Math.abs(yDistance) < step && Math.abs(xDistance) < step) {
			x = xNew;
			y = yNew;
		}
	}
	
	//Methods involving stats
	public Boolean isDead(){
		return currentHitPoints<=0;
	}
	
	public void takeDamage(int damage){
		currentHitPoints -= damage;
	}
	
	public void attacks(Unit u){
		u.takeDamage(getDamage());
		attackWait = attackSpeed;
	}
	
	public void die() {
		DisplayMessageActivity.killUnit(this);
		Wave.killUnit(this);
	}
	
	
	// Methods to get values
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	// Stats
	public int getHP(){
		return currentHitPoints;
	}
	
	public int getCurrentHitPoitns(){
		return currentHitPoints;
	}
	
	public int getMaxHitPoints(){
		return maxHitPoints;
	}
	
	public int getAttackSpeed(){
		return attackSpeed;
	}
	
	public int getAttackWait(){
		return attackWait;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public boolean getKillable() {
		return killable;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getXNew() {
		return xNew;
	}
	
	public float getYNew() {
		return yNew;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int newRadius) {
		radius = newRadius;
	}
}