package com.DJG.units;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.DJG.abilities.Blackhole;
import com.DJG.abilities.Bomb;
import com.DJG.abilities.KnockBack;
import com.DJG.abilities.Slow;
import com.DJG.fd.DisplayMessageActivity;
import com.DJG.waves.Wave;

public class Unit {
	// Global stuff.
	public static ArrayList<Unit> allUnits = new ArrayList<Unit>();
	public static ArrayList<Unit> onScreenUnits = new ArrayList<Unit>();
	public final static Object onScreenUnitsLock = new ArrayList<Unit>();
	public final static Object allUnitsLock = new Object(); // A lock so we don't fuck up the allUnits
	
	// Unit General Information:
	private String name;
	private String type;     // Chose not to simply store this as a UnitType incase we want to modify individual
						     // fields to be unique values. In other words, we may want to change an Ogre to be
	// Position Information: // really big, but we don't want to have to add a new UnitType every time we do that!
	private boolean onScreen = false;
	private float x;     
	private float y;     
	private float xNew;
	private float yNew;
	private int radius;
	private float moveSpeed;
	private float oldMoveSpeed;
	private float spinSpeed;
	private float oldSpinSpeed;
	private float xMomentum=0;
	private float yMomentum=0;
	
	// Combat information:
	private boolean suckedIn = false;
	private boolean killable = false;
	
	// Drawing information:
	private String shape;
	public int color;
	private Bitmap bmp;
	
	// Freezing stats
	private Bitmap oldbmp;
	private long timeFrozen = 0;
	private boolean isFrozen = false;
	private long frozenDuration = 0;
	
	// Unit Stats
	private int currentHitPoints;
	private int maxHitPoints;
	private int damage;

	public Unit(String newName, String newType, float xSpawn, float ySpawn) {
		// Look up the UnitType and s	et the values.
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
		synchronized(allUnitsLock) {
			addUnit(this);
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
		synchronized(allUnitsLock) {
			addUnit(this);
		}
	}
	
	public void freeze(long time) {
		timeFrozen = System.currentTimeMillis();
		frozenDuration = time;
		if(!isFrozen) {
			oldbmp = this.getBMP();
			bmp = UnitType.frozenBMP;
		}
		isFrozen = true;
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

	public void setOnScreen() {
		synchronized(onScreenUnitsLock) {
			this.onScreen = true;
			onScreenUnits.add(this);
		}
	}
	
	public void moveUnit() {
			if(timeFrozen != 0 && System.currentTimeMillis() - timeFrozen > frozenDuration) {
				isFrozen = false;
				this.bmp = this.oldbmp;
			}
			if(!isFrozen) {
				float yDistance = (yNew - y);
				float xDistance = (xNew - x);
				float step = moveSpeed;
				float distanceXY = (float)Math.sqrt(yDistance*yDistance + xDistance*xDistance); // It should take this many frames to get there.
			
				// The unit needs to be drawn if it's on screen.
				if(!DisplayMessageActivity.isCloseOffScreen(x,y) && !onScreen) {
					setOnScreen();
				}
				
				//Log.d("On Screen",onScreenUnits.size()+"");
				//Log.d("All",allUnits.size()+"");
				
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
			//Move based on Momentum, fozen units can move
			x += xMomentum;
			y += yMomentum;
			xMomentum -= xMomentum/16;
			yMomentum -= yMomentum/16;
			
	}
	
	// Add a new unit to the list of all units to be drawn in animation.
		public static void addUnit(Unit newUnit) {
			synchronized(allUnitsLock) {
				allUnits.add(newUnit);
			}
		}
		
		public static Unit getUnit(String nameToSearch) {
			synchronized(onScreenUnitsLock) {
				Unit foundUnit = null;
				for(int j = 0; j < onScreenUnits.size(); j++) {
					Unit u = onScreenUnits.get(j);
					if(u.getName() == nameToSearch) {
						foundUnit = u;
						break;
					}
				}
				return foundUnit;
			}
		}
		
		public static int getUnitPos(Unit thisUnit) {
			synchronized(allUnitsLock) {
				int foundUnit = 0;
				for(int j = 0; j < allUnits.size(); j++) {
					Unit u = allUnits.get(j);
					if(u == thisUnit) {
						break;
					}
					foundUnit++;
				}
				return foundUnit;
			}
		}
		
		public static void dontDrawUnit(Unit u) {
			if(onScreenUnits.size()!=0){
				synchronized(onScreenUnitsLock) {
					int foundUnit = 0;
					for(int j = 0; j < onScreenUnits.size(); j++) {
						Unit v = onScreenUnits.get(j);
						if(u == v) {
							break;
						}
						foundUnit++;
					}
					if(foundUnit < onScreenUnits.size()) {
						onScreenUnits.remove(foundUnit);
					}
				}
			}
		}
		
		public static void killUnit(Unit u) {
			if(allUnits.size()!=0){
				synchronized(allUnitsLock) {
					int foundUnit = 0;
					for(int j = 0; j < allUnits.size(); j++) {
						Unit v = allUnits.get(j);
						if(u == v) {
							break;
						}
						foundUnit++;
					}
					if(foundUnit < allUnits.size()) {
						allUnits.remove(foundUnit);
					}
				}
			}
		}
		
	
	// Get the selected unit at the coordinates.
	public static Unit getUnitAt(float x, float y) {
		synchronized(onScreenUnitsLock) {
			
			ArrayList<Unit> closeUnits = new ArrayList<Unit>();
			
			// Get all the close units.
			for(int j = 0; j < onScreenUnits.size(); j++) {
				Unit u = onScreenUnits.get(j);
				float yDistance = (u.getY() - y);
				float xDistance = (u.getX() - x);
				float distanceXY = (float)Math.sqrt(yDistance*yDistance + xDistance*xDistance);
				if(distanceXY <= 50 + u.getRadius() && u.getName() != "Fortress" && u.getRadius() <= 50) {
					closeUnits.add(u);
				}
				if(distanceXY <= 10 + u.getRadius() && u.getName() != "Fortress" && u.getRadius() > 50) {
					closeUnits.add(u);
				}
			}
			
			// Kill the pressed one with highest preference
			for(int j = 0; j < closeUnits.size(); j++) {
				Unit u = closeUnits.get(j);
				if(u.getType() == "Fire Asteroid") {
					return u;
				}
			}
			for(int j = 0; j < closeUnits.size(); j++) {
				Unit u = closeUnits.get(j);
				if(u.getType() == "Cat") {
					return u;
				}
			}
			for(int j = 0; j < closeUnits.size(); j++) {
				Unit u = closeUnits.get(j);
				if(u.getType() == "Ice Asteroid") {
					return u;
				}
			}
			for(int j = 0; j < closeUnits.size(); j++) {
				Unit u = closeUnits.get(j);
				if(u.getType() == "Asteroid") {
					return u;
				}
			}
			if(closeUnits.size() != 0) {
				Unit defaultUnit = closeUnits.get(0);
				return defaultUnit;
			}
			
			return null;
		}
	}
	
	public void suckedIn(boolean tf) {
		suckedIn = tf;
	}
	
	public boolean getSuckedIn() {
		return suckedIn;
	}
	
	public static int numUnits() {
		return allUnits.size();
	}
	
	static void checkIfHitCastleOrMove(Unit castle, Unit u) {
		float castleY = 0;
		float castleX = 0;
		float castleRadius = 0;
		if(castle!=null) {
			castleY = castle.getY();
			castleX = castle.getX();
			castleRadius = castle.getRadius();
		}
		float yDistanceUnit = (castleY - u.getY());
		float xDistanceUnit = (castleX - u.getX());
		float distanceXYUnit = (float)Math.sqrt(yDistanceUnit*yDistanceUnit + xDistanceUnit*xDistanceUnit);
		if(distanceXYUnit <= castleRadius + u.getRadius()) {
			u.attacks(castle);
			u.die();
			DisplayMessageActivity.castleHP = "Health " + castle.getHP();
			if(castle.isDead()){
				DisplayMessageActivity.setLost();
			}
		}
		else {
			u.moveUnit();
		}
	}
	
	public static void destroyAllUnits() {
		allUnits.clear();
		onScreenUnits.clear();
	}
	
	public static void drawUnits(Canvas canvas, Paint myPaint) {
        synchronized(onScreenUnitsLock) {
			for(int j = 0; j < onScreenUnits.size(); j++) {
				Unit currentUnit = onScreenUnits.get(j);
  	        myPaint.setStyle(Paint.Style.FILL);
      	  	myPaint.setStrokeWidth(23);
      	  	if(currentUnit.getName() == "Fortress" && !DisplayMessageActivity.getLost()) {
      		  	myPaint.setStrokeWidth(1);
      		  	myPaint.setStyle(Paint.Style.STROKE);
      	  		myPaint.setColor(currentUnit.color);
    	        	// Draw Earth!
   	        	 canvas.drawBitmap(currentUnit.getBMP(), currentUnit.getX()-currentUnit.getRadius(), currentUnit.getY() - currentUnit.getRadius(), null);
      	  	}
      	  	else if(currentUnit.getName() != "Fortress") {
      	  		// What shape do we draw?
      	  		myPaint.setColor(currentUnit.color);
      	  		if(currentUnit.getBMP() != null) {
      	  			 canvas.drawBitmap(currentUnit.getBMP(), currentUnit.getX()-currentUnit.getRadius(), currentUnit.getY() - currentUnit.getRadius(), null);
      	  		}
      	  		else if(currentUnit.getShape() == "Circle") {
      	  			canvas.drawCircle(currentUnit.getX(), currentUnit.getY(), currentUnit.getRadius(), myPaint);
      	  		}
      	  		else if(currentUnit.getShape() == "Square") {
    	              canvas.drawRect(currentUnit.getX()-currentUnit.getRadius(), currentUnit.getY()-currentUnit.getRadius(), currentUnit.getX() + currentUnit.getRadius(), currentUnit.getY() + currentUnit.getRadius(), myPaint );
      	  		}
      	  		else if(currentUnit.getShape() == "Plus") {
  	        	  	myPaint.setStrokeWidth(6);
      	  			canvas.drawLine(currentUnit.getX() + currentUnit.getRadius()/2, currentUnit.getY() - currentUnit.getRadius()/2, currentUnit.getX() + currentUnit.getRadius()/2, currentUnit.getY() + currentUnit.getRadius()*2 - currentUnit.getRadius()/2, myPaint);
    	            	canvas.drawLine(currentUnit.getX() - currentUnit.getRadius()/2, currentUnit.getY() + currentUnit.getRadius()/2, currentUnit.getX() + currentUnit.getRadius()*2 - currentUnit.getRadius()/2, currentUnit.getY() + currentUnit.getRadius()/2, myPaint);
      	  		}
      	  		else if(currentUnit.getShape() == "Triangle") {
      	  			canvas.drawCircle(currentUnit.getX(), currentUnit.getY(), currentUnit.getRadius(), myPaint);
      	  		}
      	  	}
        	}
        }
	}
	
	public static void destroyPlanet() {
		Bomb b = new Bomb(DisplayMessageActivity.getScreenWidth()/2, DisplayMessageActivity.getScreenHeight()/2,DisplayMessageActivity.getScreenHeight()/2+1,DisplayMessageActivity.getLoseDuration());
	}
	
	public void setSpinSpeed(float s) {
		oldSpinSpeed = spinSpeed;
		spinSpeed = s;
	}
	
	public void setMoveSpeed(float f) {
		oldMoveSpeed = moveSpeed;
		moveSpeed = f;
	}
	
	public float getOldSpinSpeed() {
		return oldSpinSpeed;
	}
	
	public float getOldMoveSpeed() {
		return oldMoveSpeed;
	}
		
	public static void updateUnits() {
		// Where is the castle?
		Unit castle = getUnit("Fortress");
		DisplayMessageActivity.castleHP = "Health " + castle.getHP();
		
		synchronized(Unit.onScreenUnitsLock) {
			for(int j = 0; j < onScreenUnits.size(); j++) {
				Unit u = onScreenUnits.get(j);
				if(u.getName() != "Fortress") {
					// Check if we have hit a bomb.
					Bomb.checkIfHitBomb(u);
					Slow.checkIfHitSlow(u);
					Blackhole.checkIfHitBlackhole(u);
					KnockBack.checkIfHitKnockBack(u);
				}
			}
		}
		
		synchronized(Unit.allUnits) {
			for(int j = 0; j < allUnits.size(); j++) {
				Unit u = allUnits.get(j);
				if(u instanceof UnitSpawner){
					((UnitSpawner) u).spawnNewUnits();
				}
				if(u.getName() != "Fortress") {
					// Check if we have hit the castle.
					checkIfHitCastleOrMove(castle, u);
				}
			}
		}
	}
	
	//Methods involving stats
	public Boolean isDead(){
		return currentHitPoints<=0;
	}
	
	private void takeDamage(int damage){
		currentHitPoints -= damage;
	}
	
	public void attacks(Unit u) {
		if(getDamage() > 0) {
			if(u.getHP()>100) {
				u.currentHitPoints = 100;
				u.takeDamage(getDamage());
			}
			else if(u.getHP() - getDamage() < 0) {
				u.currentHitPoints = 0;
			}
			else if(u.getHP() <= 100 && u.getHP() > 0) {
				u.takeDamage(getDamage());
			}
		}
		if(getDamage() < 0) {
			if(u.getHP() - getDamage() > 100) {
				u.currentHitPoints = 100;
			}
			if(u.getHP()<0) {
				currentHitPoints = 0;
			}
		}
	}
	
	public void hurt(int i){
		this.currentHitPoints -=i;
		if(getHP()<=0 && killable){
			die();
		}
	}
	
	public void die() {
		
		// Do special things for special units.
		if(type=="Fire Asteroid") {
			Bomb b = new Bomb(this.getX(),this.getY(),100,500);
		}
		if(type=="Ice Asteroid") {
			Slow s = new Slow(this.getX(),this.getY(),200,1500);
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
		dontDrawUnit(this);
		killUnit(this);
		Wave.killUnit(this);
	}
	
	public void setMomentum(int x, int y){
		xMomentum = x;
		yMomentum = y;
	}
	
	public void knockBackFrom(int xPos, int yPos, int vel){
		
			float yDistance = (y - yPos);
	 		float xDistance = (x - xPos);
	 		float distanceXY = (float)Math.sqrt(yDistance*yDistance + xDistance*xDistance); 
	 		xMomentum = vel * xDistance/distanceXY;
	 		yMomentum = vel * yDistance/distanceXY;
		
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