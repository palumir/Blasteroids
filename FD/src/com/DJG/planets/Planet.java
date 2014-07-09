package com.DJG.planets;

import com.DJG.fd.DisplayMessageActivity;
import com.DJG.units.Unit;
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
	
	public void onCollison(){
		//Do nothing, override this
	}
	
	//A method to spawn defenders. Likely overriden
	protected void spawnDefenders(){
		int screenHeight = DisplayMessageActivity.getScreenHeight();
		int screenWidth = DisplayMessageActivity.getScreenWidth();
		int radius = radiusOfDefenders;
		double currentDegree = 0;
		double degreeChange = (double) 360/numOfDefenders;
		for(int i = 0; i<numOfDefenders; i++){
			int x = (int) (screenWidth/2 + radius*Math.cos(Math.toRadians(currentDegree))); 
			int y = (int) (screenHeight/2 + radius*Math.sin(Math.toRadians(currentDegree)));
			currentDegree += degreeChange;
			Wave.addToCurrentWave(new Unit("New name", defenderName, x, y, 5));
		}
		
	}
	
	
}
