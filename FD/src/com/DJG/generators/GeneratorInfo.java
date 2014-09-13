package com.DJG.generators;

import com.DJG.generators.WaveGenerator;

public class GeneratorInfo {
	public int unitNumbers;
	public String unitType;
	public int startingDifference;
	public int spin;
	public spawnSystem spawn;
	
	public enum unitOrder{
		InOrder, Random, Alternating, Any
	}
	
	public enum spawnSystem{
		FullRandom, Cardinal, LineFromNorth,LineFromEast, LineFromWest, LineFromSouth, Spiral, SpinCardinal, Circle, Spawner, WallFromEast, WallFromWest
	}
	
	public GeneratorInfo( String type, int unitNum, spawnSystem sSystem){
		unitNumbers = unitNum;
		unitType = type;
		spawn = sSystem;
		startingDifference = 0;
		spin = 0;
	}
	public GeneratorInfo( String type, int unitNum, spawnSystem sSystem, int spinSpeed){
		unitNumbers = unitNum;
		unitType = type;
		spawn = sSystem;
		startingDifference = 0;
		spin = spinSpeed;
	}
	
	public GeneratorInfo( String type, int unitNum, spawnSystem sSystem, int spinSpeed, int setDistance){
		unitNumbers = unitNum;
		unitType = type;
		spawn = sSystem;
		startingDifference = setDistance;
		spin = spinSpeed;
	}
}
