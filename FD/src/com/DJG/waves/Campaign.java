package com.DJG.waves;

import java.util.ArrayList;
import java.util.HashMap;

import com.DJG.generators.GeneratorInfo;
import com.DJG.generators.GeneratorInfo.spawnSystem;

public class Campaign {
	
	public static int campaignMax = 30;
	
	static void sendCampaignWave(double waveNumber) {
		Wave myWave = new Wave();
		Wave.setCurrentWave(myWave);
		boolean isBoss = false;
		HashMap<String, UnitPattern> unitMap = new HashMap<String, UnitPattern>();
		ArrayList<GeneratorInfo> genInfo = new ArrayList<GeneratorInfo>();
		Wave.setWaveWaitTime(1500);
		switch((int)waveNumber+1){
		
		//////////////////////////////////////////////////////////////////
		//// LEVEL ONE ///////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////
		case 1:
			genInfo.add(new GeneratorInfo("Cat", 4, spawnSystem.FullRandom,0,250));
			genInfo.add(new GeneratorInfo("Asteroid", 4, spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 4, spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Ice Asteroid", 4, spawnSystem.FullRandom));
			break;
		/*case 0: 
			Cthulu.battleStart();
			isBoss = true;
			break;*/
		case 2:
			genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.Circle));
			break;
		case 3:
			genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.LineFromEast));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.LineFromWest));
			break;
		case 4:
			genInfo.add(new GeneratorInfo("Ice Asteroid", 20,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Asteroid", 20,spawnSystem.Spiral));
			break;
		case 5:
			genInfo.add(new GeneratorInfo("Asteroid", 12,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Asteroid", 12,spawnSystem.Circle));
			break;
		case 6:
			genInfo.add(new GeneratorInfo("Asteroid", 15,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 15,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
			break;
		case 7:
			genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.LineFromNorth));
			genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.LineFromSouth));
			break;
		case 8:
			genInfo.add(new GeneratorInfo("Asteroid", 8,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 15,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Asteroid", 8,spawnSystem.LineFromNorth));
			genInfo.add(new GeneratorInfo("Asteroid", 8,spawnSystem.LineFromSouth));
			break;
		case 9:
			genInfo.add(new GeneratorInfo("Asteroid", 30,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 15,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 15,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 15,spawnSystem.Circle));
			break;
		case 10:
			genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Splitter Big", 1,spawnSystem.FullRandom));
			break;
		//////////////////////////////////////////////////////////////////
		//// LEVEL TWO ///////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////
		case 11:
			genInfo.add(new GeneratorInfo("Asteroid", 15,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Splitter Medium", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Splitter Medium", 2,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Splitter Medium", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Splitter Medium", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Splitter Medium", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Splitter Medium", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Splitter Medium", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Splitter Medium", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Splitter Medium", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Splitter Medium", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Splitter Medium", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Splitter Medium", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Asteroid", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Splitter Medium", 1,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.FullRandom,0,600));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.FullRandom,0,700));
			break;
		case 12:
			genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.Circle));	
			genInfo.add(new GeneratorInfo("Asteroid", 20,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Ice Asteroid", 20,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Asteroid", 20,spawnSystem.Circle));	
			genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Asteroid", 20,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Asteroid", 20,spawnSystem.Circle));	
			genInfo.add(new GeneratorInfo("Ice Asteroid", 20,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Asteroid", 20,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Asteroid", 20,spawnSystem.Circle));
			break;
		case 13:
			genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.LineFromEast,2));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Asteroid", 15,spawnSystem.LineFromWest,2));
			genInfo.add(new GeneratorInfo("Asteroid", 20,spawnSystem.LineFromNorth,2));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.FullRandom,0,200));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.FullRandom,0,400));
			genInfo.add(new GeneratorInfo("Asteroid", 25,spawnSystem.LineFromSouth,2));
			break;
		case 14:
			genInfo.add(new GeneratorInfo("Ice Asteroid", 20,spawnSystem.FullRandom,1,150));
			genInfo.add(new GeneratorInfo("Ice Asteroid", 20,spawnSystem.FullRandom,1,250));
			genInfo.add(new GeneratorInfo("Ice Asteroid", 20,spawnSystem.FullRandom,1,450));
			genInfo.add(new GeneratorInfo("Ice Asteroid", 20,spawnSystem.FullRandom,1,600));
			genInfo.add(new GeneratorInfo("Asteroid", 15,spawnSystem.Circle,0));
			genInfo.add(new GeneratorInfo("Asteroid", 15,spawnSystem.Circle,0));
			genInfo.add(new GeneratorInfo("Asteroid", 15,spawnSystem.Circle,0));
			genInfo.add(new GeneratorInfo("Asteroid", 15,spawnSystem.Circle,0));
			break;
		case 15:
			genInfo.add(new GeneratorInfo("Cat", 3,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Cat", 2,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Cat", 1,spawnSystem.Circle));
			break;
		case 16:
			genInfo.add(new GeneratorInfo("Splitter Big", 2,spawnSystem.FullRandom,1));
			break;
		case 17:
			genInfo.add(new GeneratorInfo("Cat",20,spawnSystem.Spiral));
			break;
		case 18:
			genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromNorth,2));
			genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromNorth,2,100));
			genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromNorth,2,200));
			genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromNorth,2,350));
			genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromNorth,2,600));
			genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromSouth,2));
			genInfo.add(new GeneratorInfo("Cat", 15,spawnSystem.LineFromEast,4,400));
			genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromSouth,2,100));
			genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromSouth,2,200));
			genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromSouth,2,350));
			genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromSouth,2,450));
			genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromSouth,2,600));
			break;
		case 19:
			genInfo.add(new GeneratorInfo("Fire Asteroid", 60,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.FullRandom,4,400));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.FullRandom,4,800));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.FullRandom,4,1200));
			break;
		case 20:
			genInfo.add(new GeneratorInfo("Cat", 14,spawnSystem.LineFromNorth,4));
			genInfo.add(new GeneratorInfo("Cat", 14,spawnSystem.LineFromSouth,4,100));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.Circle,2));
			genInfo.add(new GeneratorInfo("Ice Asteroid", 20,spawnSystem.Circle,2));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.Circle,2));
			genInfo.add(new GeneratorInfo("Ice Asteroid", 20,spawnSystem.Circle,2));
			genInfo.add(new GeneratorInfo("Asteroid", 20,spawnSystem.Circle,2));
			break;
			//////////////////////////////////////////////////////////////////
			//// LEVEL THREE /////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////
			case 21:
				genInfo.add(new GeneratorInfo("Splitter Medium", 10,spawnSystem.Circle));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.Circle));
				genInfo.add(new GeneratorInfo("Splitter Medium", 10,spawnSystem.Circle));
				genInfo.add(new GeneratorInfo("Splitter Medium", 10,spawnSystem.Circle));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.Circle));
				genInfo.add(new GeneratorInfo("Splitter Medium", 10,spawnSystem.Circle));
				genInfo.add(new GeneratorInfo("Splitter Medium", 10,spawnSystem.Circle));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.Circle));
				genInfo.add(new GeneratorInfo("Splitter Medium", 10,spawnSystem.Circle));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 12,spawnSystem.FullRandom,2,600));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 12,spawnSystem.FullRandom,2,700));
				break;
			case 22:
				genInfo.add(new GeneratorInfo("Fire Asteroid", 150,spawnSystem.FullRandom));	
				break;
			case 23:
				genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.FullRandom));
				genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.FullRandom,2));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.FullRandom));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 15,spawnSystem.LineFromWest,2));
				genInfo.add(new GeneratorInfo("Asteroid", 15,spawnSystem.LineFromWest,4));
				genInfo.add(new GeneratorInfo("Asteroid", 20,spawnSystem.LineFromNorth,2));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.FullRandom));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.FullRandom,0,200));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.FullRandom,0,400));
				genInfo.add(new GeneratorInfo("Asteroid", 25,spawnSystem.LineFromEast,2));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.LineFromEast,4));
				break;
			case 24:
				genInfo.add(new GeneratorInfo("Ice Asteroid", 40,spawnSystem.FullRandom,2,150));
				genInfo.add(new GeneratorInfo("Ice Asteroid", 40,spawnSystem.FullRandom,2,400));
				genInfo.add(new GeneratorInfo("Ice Asteroid", 40,spawnSystem.FullRandom,2,700));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 80,spawnSystem.Spiral,1,600));
				break;
			case 25:
				genInfo.add(new GeneratorInfo("Cat", 100,spawnSystem.Cardinal));
				break;
			case 26:
				genInfo.add(new GeneratorInfo("Splitter Medium", 30,spawnSystem.FullRandom));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.FullRandom));
				break;
			case 27:
				genInfo.add(new GeneratorInfo("Ice Asteroid",50,spawnSystem.Spiral));
				genInfo.add(new GeneratorInfo("Fire Asteroid",50,spawnSystem.Spiral,4));
				genInfo.add(new GeneratorInfo("Cat", 30,spawnSystem.LineFromNorth,2,200));
				genInfo.add(new GeneratorInfo("Cat", 30,spawnSystem.LineFromNorth,2,350));
				break;
			case 28:
				genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromNorth,2));
				genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromNorth,2,100));
				genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromNorth,2,200));
				genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromNorth,2,350));
				genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromNorth,2,600));
				genInfo.add(new GeneratorInfo("Cat", 7,spawnSystem.LineFromSouth,2));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 60,spawnSystem.Spiral));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.FullRandom,4,400));
				break;
			case 29:
				genInfo.add(new GeneratorInfo("Splitter Big", 4,spawnSystem.LineFromNorth));
				break;
			case 30:
				genInfo.add(new GeneratorInfo("Cat", 14,spawnSystem.Cardinal,4));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.Circle,2));
				genInfo.add(new GeneratorInfo("Ice Asteroid", 20,spawnSystem.Circle,2));
				genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.Circle,2));
				genInfo.add(new GeneratorInfo("Ice Asteroid", 20,spawnSystem.Circle,2));
				genInfo.add(new GeneratorInfo("Asteroid", 20,spawnSystem.Circle,2));
				break;
		default:
			genInfo.add(new GeneratorInfo("Asteroid", Wave.getR().nextInt((int)waveNumber) +1,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Fire Asteroid", Wave.getR().nextInt((int)waveNumber*5+1)/4,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Cat", Wave.getR().nextInt((int)waveNumber/4+1),spawnSystem.FullRandom));
			break;
		}
		Wave.setBossWave(isBoss);
		if(!isBoss) {
			Wave.setCurrentWave(Wave.waveGenerator.generateWave(genInfo));
		}
	}
}