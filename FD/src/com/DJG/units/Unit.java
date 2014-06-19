package com.DJG.units;

import android.graphics.Bitmap;
import android.util.Log;

import com.DJG.abilities.Bomb;
import com.DJG.fd.DisplayMessageActivity;
import com.DJG.fd.Wave;

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
	private float spinSpeed;
	
	// Combat information:
	private boolean killable;
	
	// Drawing information:
	private String shape;
	public int color;
	private Bitmap bmp;
	
	// Unit Stats
	private int currentHitPoints;
	private int maxHitPoints;
	private int damage;

	public Unit(String newName, String newType, float xSpawn, float ySpawn) {
		// Look up the UnitType and set the values.
		UnitType u = UnitType.getUnitType(newType);
		radius = u.getRadius();
		type = u.getType();
		moveSpeed = u.getMoveSpeed();
		killable = u.getKillable();
		shape = u.getShape();
		spinSpeed = 0;
		bmp = u.getBMP();
		// Stats
		maxHitPoints = u.getMaxHitPoints();
		currentHitPoints = maxHitPoints;
		damage = u.getDamage();
		color = u.getColor();
		
		// Set it's coordinates.
		name = newName;
		type = newType;
		x = xSpawn;
		y = ySpawn;
		xNew = xSpawn;
		yNew = ySpawn;
		
		// Add it to the list of units to be drawn.
		synchronized(DisplayMessageActivity.allUnitsLock) {
			DisplayMessageActivity.addUnit(this);
		}
	}
	
	public Unit(String newName, String newType, float xSpawn, float ySpawn, float spin) {
		// Look up the UnitType and set the values.
		UnitType u = UnitType.getUnitType(newType);
		radius = u.getRadius();
		type = u.getType();
		moveSpeed = u.getMoveSpeed();
		killable = u.getKillable();
		shape = u.getShape();
		spinSpeed = spin;
		bmp = u.getBMP();
		// Stats
		maxHitPoints = u.getMaxHitPoints();
		currentHitPoints = maxHitPoints;
		damage = u.getDamage();
		color = u.getColor();
		
		// Set it's coordinates.
		name = newName;
		type = newType;
		x = xSpawn;
		y = ySpawn;
		xNew = xSpawn;
		yNew = ySpawn;
		
		// Add it to the list of units to be drawn.
		synchronized(DisplayMessageActivity.allUnitsLock) {
			DisplayMessageActivity.addUnit(this);
		}
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
		
		//Spin the Unit
		if(spinSpeed!=0){
			yDistance = (yNew - y);
		 	xDistance = (xNew - x);
		 	distanceXY = (float)Math.sqrt(yDistance*yDistance + xDistance*xDistance); 
		 	x = x + spinSpeed*yDistance/(distanceXY);
		 	y = y + spinSpeed*(0-xDistance)/(distanceXY);
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
	
	private void takeDamage(int damage){
		currentHitPoints -= damage;
	}
	
	public void attacks(Unit u){
		u.takeDamage(getDamage());
	}
	
	public void die() {
		
		// Do special things for special units.
		if(type=="Fire Asteroid") {
			Bomb b = new Bomb(this.getX(),this.getY(),100,500);
		}
		if(type == "Splitter Huge") {
			Wave.addToCurrentWave(new Unit("Any Name","Splitter Big",this.getX(),this.getY()));
			Wave.addToCurrentWave(new Unit("Any Name","Splitter Big",this.getX()+this.getRadius()/2 + 5,this.getY()));
			Wave.addToCurrentWave(new Unit("Any Name","Splitter Big",this.getX(),this.getY()+this.getRadius()/2 + 5));
			Wave.addToCurrentWave(new Unit("Any Name","Splitter Big",this.getX()+this.getRadius()/2 + 5,this.getY()+this.getRadius()/2 + 5));
			Wave.currentWaveAttackCastle();
		}
		if(type == "Splitter Big") {
			Wave.addToCurrentWave(new Unit("Any Name","Splitter Medium",this.getX(),this.getY()));
			Wave.addToCurrentWave(new Unit("Any Name","Splitter Medium",this.getX()+this.getRadius()/2 + 5,this.getY()));
			Wave.addToCurrentWave(new Unit("Any Name","Splitter Medium",this.getX(),this.getY()+this.getRadius()/2 + 5));
			Wave.addToCurrentWave(new Unit("Any Name","Splitter Medium",this.getX()+this.getRadius()/2 + 5,this.getY()+this.getRadius()/2 + 5));
			Wave.currentWaveAttackCastle();
		}
		if(type == "Splitter Medium") {
			Wave.addToCurrentWave(new Unit("Any Name","Splitter Small",this.getX(),this.getY()));
			Wave.addToCurrentWave(new Unit("Any Name","Splitter Small",this.getX()+this.getRadius()/2 + 5,this.getY() + 5));
			Wave.addToCurrentWave(new Unit("Any Name","Splitter Small",this.getX(),this.getY()+this.getRadius()/2 + 5));
			Wave.addToCurrentWave(new Unit("Any Name","Splitter Small",this.getX()+this.getRadius()/2 + 5,this.getY()+this.getRadius()/2 + 5));
			Wave.currentWaveAttackCastle();
		}
		
		// Kill the old unit.
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
	
	public int getDamage(){
		return damage;
	}
	
	public boolean getKillable() {
		return killable;
	}
	
	public Bitmap getBMP() {
		return bmp;
	}
	
	public String getShape() {
		return shape;
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