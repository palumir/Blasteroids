package com.DJG.planets;

import com.DJG.abilities.Bomb;

public class Saturn extends Planet {
	public Saturn(String newName, float xSpawn, float ySpawn){
		super(newName, "Saturn", xSpawn, ySpawn);
		gravity = 2.5f;
		defenderName = "Fire Defender";
		radiusOfDefenders = 350;
		numOfDefenders = 4;
	}
	
	
	
	public void afterWave(){
		spawnDefenders();
	}
}
