package com.DJG.generators;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.DJG.waves.Survival;

public class GeneratorInfo {
	public int unitNumbers;
	public String unitType;
	public int startingDifference;
	public int spin;
	public spawnSystem spawn;
	
	public enum unitOrder{
		InOrder, Random, Alternating, Any
	}
	
	public static enum spawnSystem{
		FullRandom, Cardinal, LineFromNorth,LineFromEast, LineFromWest, LineFromSouth, Spiral, SpinCardinal, Circle, Bombardment, Spawner;
		
		private static final List<spawnSystem> VALUES =
		    Collections.unmodifiableList(Arrays.asList(values()));
		  private static final int SIZE = VALUES.size();
		  private static final Random RANDOM = Survival.r;

		  public static spawnSystem randomSpawnSystem()  {
		    return VALUES.get(RANDOM.nextInt(SIZE));
		  }
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
