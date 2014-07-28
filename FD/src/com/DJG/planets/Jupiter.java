package com.DJG.planets;

import com.DJG.abilities.Bomb;

public class Jupiter extends Planet {

	public Jupiter(String newName, float xSpawn, float ySpawn){
		super(newName, "Jupiter", xSpawn, ySpawn);
		gravity = 1f;
		numOfDefenders = 0;
	}
	
	public void onCollison(){
		synchronized(Bomb.bombsLock) {
			Bomb newBomb = new Bomb(getX(),getY(),400,1250,"Red");
		}
	}
}

