package com.DJG.planets;

import android.util.Log;

import com.DJG.fd.GameActivity;
import com.DJG.fd.R;
import com.DJG.units.Unit;
import com.DJG.units.UnitType;
import com.DJG.waves.Wave;

public abstract class Planet extends Unit {
	
	protected float gravity;
	private float multipler;
	protected String defenderName;
	protected int numOfDefenders;
	protected int radiusOfDefenders;
	
	public Planet(String newName, String newType, float xSpawn, float ySpawn){
		super(newName, newType, xSpawn, ySpawn);
		gravity = 1;
	}
	
	//Action to do after each wave
	//Currently set to 0
	public void afterWave(){
		spawnDefenders();
	}
	
	//Gravity. Increases how fast units move to the planet
	public float getGravity(){
		return gravity;
	}
	
	public double multipler(){
		return multipler;
	}
	

	public static void initPlanetTypes() {
		UnitType.addUnitType(new UnitType("Earth",50,0f,false,R.drawable.earth, 100,0,"Planet")); 
		UnitType.addUnitType(new UnitType("Mars",50,0f,false,R.drawable.mars, 100,0,"Planet"));
		UnitType.addUnitType(new UnitType("Saturn", 50, 0f, false, R.drawable.saturn, 150, 0,"Planet"));
		UnitType.addUnitType(new UnitType("Jupiter", 50, 0f, false, R.drawable.jupitier, 80, 0,"Planet"));
	}
	


	public void onCollison(){
		//Do nothing, override this
	}
	//A method to spawn defenders. Likely overriden
	protected void spawnDefenders(){
	Unit fort = GameActivity.getFortress();
	float fortX=fort.getX();
	float fortY = fort.getY();
	float screenHeight = GameActivity.getScreenHeight();
	float screenWidth = GameActivity.getScreenWidth();
		int radius = radiusOfDefenders;
		double currentDegree = 0;
		double degreeChange = (double) 360/numOfDefenders;
		for(int i = 0; i<numOfDefenders; i++){
			float x = (float) (screenWidth/2 + radius*Math.cos(Math.toRadians(currentDegree))); 
			float y = (float) (screenHeight/2 + radius*Math.sin(Math.toRadians(currentDegree)));
			currentDegree += degreeChange;
			Unit moon = new Unit("New name", defenderName, x, y, 5);
			moon.moveNormally(fortX, fortY);
			Unit.addMoon(moon);
			Log.i("moon created", ""+x + " " + y);
		}
		
	}
	
	public float getMoonRadius(){
		return radiusOfDefenders;
	}
	
	
}
