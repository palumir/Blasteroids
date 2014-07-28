package com.DJG.planets;

public class Mars extends Planet {
	public Mars(String newName, float xSpawn, float ySpawn){
		super(newName, "Mars", xSpawn, ySpawn);
		gravity = 1f;
		defenderName = "Mars Moon";
		radiusOfDefenders = 250;
		numOfDefenders = 4;
	}
	
	public void afterWave(){
		spawnDefenders();
	}
}
