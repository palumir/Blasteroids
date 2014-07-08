package com.DJG.units;

import java.util.ArrayList;

import com.DJG.fd.GameActivity;
import com.DJG.waves.Wave;




public class UnitSpawner extends Unit {

	public class SpawnData{
		float xOffset;
		float yOffset;
		int spinValue;
		String unitType;
		
		public SpawnData(float x, float y, int spin, String u){
			xOffset = x;
			yOffset = y;
			unitType = u;
			spinValue = spin;
		}
	}
	
	private ArrayList<SpawnData> unitsToSpawn = new ArrayList<SpawnData>();
	private Wave inWave;
	private int spawnCoolDown;
	private long lastSpawn;
	
	public UnitSpawner(String newName, String newType, float xSpawn, float ySpawn, Wave wave){
		super(newName, newType, xSpawn, ySpawn, 5f);
		ArrayList<UnitSpawner.SpawnData>units = new ArrayList<UnitSpawner.SpawnData>();
		units.add(new SpawnData(100, 0, 2, "Asteroid"));
		units.add(new SpawnData(-100, 0, 0, "Asteroid"));
		units.add(new SpawnData(0, 100, 2, "Asteroid"));
		units.add(new SpawnData(0, -100, -4, "Asteroid"));
		
		unitsToSpawn = units;
		spawnCoolDown = 800;
	}
	
	public UnitSpawner(String newName, String newType, float xSpawn, float ySpawn, Wave wave, ArrayList<SpawnData> units, int spawnTime){
		super(newName, newType, xSpawn, ySpawn);
		inWave = wave;
		unitsToSpawn = units;
		spawnCoolDown = spawnTime;
		lastSpawn = 0;	
	}
	
	public boolean canSpawnNewUnits(){
		return (GameActivity.getGameTime() - lastSpawn)> spawnCoolDown;		
	}
	
	public Boolean spawnNewUnits(){
		if(canSpawnNewUnits()){
			spawn();
			lastSpawn = GameActivity.getGameTime();
			return true;
		} else {
			return false;
		}
	}
	
	private void spawn(){
		for(SpawnData data : unitsToSpawn){
			float x = data.xOffset + getX();
			float y = data.yOffset + getY();
			Unit u = new Unit("Any name", data.unitType, x, y, data.spinValue);
			Wave.addToCurrentWave(u);
			Wave.currentWaveAttackCastle();
		}
	}
}
