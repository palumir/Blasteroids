package com.DJG.generators;

import com.DJG.generators.WaveGenerator;

public class GeneratorInfo {
	public int unitNumbers;
	public String unitType;
	//public unitOrder order;
	public int startingDist;
	public spawnSystem spawn;
	
	public enum unitOrder{
		InOrder, Random, Alternating, Any
	}
	
	public enum spawnSystem{
		FullRandom, Cardinal, LineFromNorth,LineFromEast, LineFromWest, LineFromSouth, Spiral, SpinCardinal, Circle
	}
	
	public GeneratorInfo( String type, int unitNum, spawnSystem sSystem){
		unitNumbers = unitNum;
		unitType = type;
		spawn = sSystem;
		startingDist = 0;
	}
	
	public GeneratorInfo( String type, int unitNum, spawnSystem sSystem, int startDistance){
		unitNumbers = unitNum;
		unitType = type;
		spawn = sSystem;
		startingDist = startDistance;
	}
}
