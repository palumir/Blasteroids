package com.DJG.planets;

public class Mars extends Planet {
	public Mars(String newName, String newType, float xSpawn, float ySpawn){
		super(newName, newType, xSpawn, ySpawn);
		gravity = 2.5f;
	}
}
