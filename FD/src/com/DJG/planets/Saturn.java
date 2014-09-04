package com.DJG.planets;

import com.DJG.abilities.Bomb;

public class Saturn extends Planet {
	public Saturn(String newName, float xSpawn, float ySpawn){
		super(newName, "Saturn", xSpawn, ySpawn);
		gravity = 1f;
		defenderName = "Fire Defender Moon";
		radiusOfDefenders = 200;
		numOfDefenders = 1;
	}
	
	public void afterWave(){
		spawnDefenders();
	}
}
