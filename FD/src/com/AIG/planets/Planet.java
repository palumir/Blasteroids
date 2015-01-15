package com.AIG.planets;

import java.util.ArrayList;

import com.AIG.abilities.Ability;
import com.AIG.blasteroids.GameActivity;
import com.AIG.blasteroids.R;
import com.AIG.units.Unit;
import com.AIG.units.UnitType;

public abstract class Planet extends Unit {
	
	protected float gravity;
	private float multipler;
	protected String defenderName;
	protected int numOfDefenders;
	protected int radiusOfDefenders;
	
	private static ArrayList<UnitType> planetTypes;
	private static String currentPlanetName;
	private static Planet currentPlanet;
	
	public Planet(String newName, String newType, float xSpawn, float ySpawn){
		super(newName, newType, xSpawn, ySpawn);
		currentPlanetName = newType;
		currentPlanet = this;
		gravity = 1;
	}
	
	//Action to do after each wave
	//Currently set to 0
	public void afterWave(){
	}
	
	//Gravity. Increases how fast units move to the planet
	public float getGravity(){
		return gravity;
	}
	
	public double multipler(){
		return multipler;
	}
	

	public static void initPlanetTypes() {
		UnitType.addUnitType(new UnitType("Earth",50,0f,false,R.drawable.earth, 100,0,"Planet",0)); 
		UnitType.addUnitType(new UnitType("Mars",50,0f,false,R.drawable.mars, 100,0,"Planet",15));
		UnitType.addUnitType(new UnitType("Saturn", 50, 0f, false, R.drawable.saturn, 150, 0,"Planet",25));
		UnitType.addUnitType(new UnitType("Jupiter", 50, 0f, false, R.drawable.jupitier, 80, 0,"Planet",50));
		setPlanetTypes(new ArrayList<UnitType>());
		setPlanetTypes(UnitType.getAllOf("Planet"));
	}
	
	public static Planet getCurrentPlanet(float x, float y) {
		return new Earth("Fortress",x,y);
	}
	
	public static UnitType getCurrentPlanet() {
		String planet = Ability.getPrefs().getString("currentPlanet", "Earth");
		return UnitType.getUnitType(planet);
	}

	public void onCollison(){
		//Do nothing, override this
	}
	//A method to spawn defenders. Likely overriden
	public void spawnDefenders(){
	Unit fort = GameActivity.getFortress();
	float fortX= fort.getX()+fort.getRadius()/2;
	float fortY = fort.getY()+fort.getRadius()/2;
	float screenHeight = GameActivity.getScreenHeight();
	float screenWidth = GameActivity.getScreenWidth();
		int radius = radiusOfDefenders;
		double currentDegree = 0;
		double degreeChange = (double) 360/numOfDefenders;
		for(int i = 0; i<numOfDefenders; i++){
			int x = (int) (screenWidth/2 + radius*Math.cos(Math.toRadians(currentDegree))); 
			int y = (int) (screenHeight/2 + radius*Math.sin(Math.toRadians(currentDegree)));
			currentDegree += degreeChange;
		}
		
	}
	
	public float getMoonRadius(){
		return radiusOfDefenders;
	}

	public static ArrayList<UnitType> getPlanetTypes() {
		return planetTypes;
	}

	public static void setPlanetTypes(ArrayList<UnitType> planetTypes) {
		Planet.planetTypes = planetTypes;
	}
	
	
}
