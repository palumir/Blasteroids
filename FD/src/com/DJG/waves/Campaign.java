package com.DJG.waves;

import java.util.ArrayList;
import java.util.HashMap;

import com.DJG.generators.GeneratorInfo;
import com.DJG.generators.GeneratorInfo.spawnSystem;

public class Campaign {
	
	public static int campaignMax = 40;
	
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
			genInfo.add(new GeneratorInfo("Cat", 2, spawnSystem.FullRandom,0,250));
			genInfo.add(new GeneratorInfo("Asteroid", 2, spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 2, spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Ice Asteroid", 2, spawnSystem.FullRandom));
			break;
		/*case 0: 
			Cthulu.battleStart();
			isBoss = true;
			break;*/
		case 2:
			genInfo.add(new GeneratorInfo("Asteroid", 6,spawnSystem.Circle));
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
			genInfo.add(new GeneratorInfo("Cat Gunner", 5,spawnSystem.FullRandom,0));
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
			genInfo.add(new GeneratorInfo("Cat Gunner", 3,spawnSystem.FullRandom,0,100));
			genInfo.add(new GeneratorInfo("Cat Gunner", 7,spawnSystem.FullRandom,0,200));
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
			genInfo.add(new GeneratorInfo("Cat Gunner", 3,spawnSystem.FullRandom,0,0));
			genInfo.add(new GeneratorInfo("Cat Gunner", 3,spawnSystem.FullRandom,0,200));
			genInfo.add(new GeneratorInfo("Cat Gunner", 3,spawnSystem.FullRandom,0,300));
			genInfo.add(new GeneratorInfo("Cat", 3,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Cat", 2,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Cat", 1,spawnSystem.Circle));
			break;
		case 16:
			genInfo.add(new GeneratorInfo("Splitter Big", 2,spawnSystem.FullRandom,1));
			break;
		case 17:
			genInfo.add(new GeneratorInfo("Cat",32,spawnSystem.Spiral));
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
			genInfo.add(new GeneratorInfo("Fire Asteroid", 60,spawnSystem.Spiral,5));
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
					genInfo.add(new GeneratorInfo("Splitter Medium", 4,spawnSystem.Circle));
					genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.Circle));
					genInfo.add(new GeneratorInfo("Splitter Medium", 4,spawnSystem.Circle));
					genInfo.add(new GeneratorInfo("Splitter Medium", 4,spawnSystem.Circle));
					genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.Circle));
					genInfo.add(new GeneratorInfo("Splitter Medium", 4,spawnSystem.Circle));
					genInfo.add(new GeneratorInfo("Splitter Medium", 4,spawnSystem.Circle));
					genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.Circle));
					genInfo.add(new GeneratorInfo("Splitter Medium", 6,spawnSystem.Circle));
					genInfo.add(new GeneratorInfo("Fire Asteroid", 12,spawnSystem.FullRandom,2,600));
					genInfo.add(new GeneratorInfo("Fire Asteroid", 12,spawnSystem.FullRandom,2,700));
					break;
				case 22:
					genInfo.add(new GeneratorInfo("Fire Asteroid", 150,spawnSystem.FullRandom,10));	
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
					genInfo.add(new GeneratorInfo("Cat Gunner", 8,spawnSystem.FullRandom,0,2500));
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
					genInfo.add(new GeneratorInfo("Cat Gunner", 9,spawnSystem.FullRandom,0));
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
					genInfo.add(new GeneratorInfo("Asteroid", 60,spawnSystem.Spiral));
					genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.FullRandom,4,400));
					break;
				case 29:
					genInfo.add(new GeneratorInfo("Splitter Big", 4,spawnSystem.LineFromNorth));
					genInfo.add(new GeneratorInfo("Cat Gunner", 6,spawnSystem.FullRandom));
					break;
				case 30:
					genInfo.add(new GeneratorInfo("Cat", 30,spawnSystem.Cardinal,4));
					genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.Circle,2));
					genInfo.add(new GeneratorInfo("Ice Asteroid", 20,spawnSystem.Circle,2));
					genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.Circle,2));
					genInfo.add(new GeneratorInfo("Ice Asteroid", 20,spawnSystem.Circle,2));
					genInfo.add(new GeneratorInfo("Asteroid", 20,spawnSystem.Circle,2));
					break;
					//////////////////////////////////////////////////////////////////
					//// LEVEL Four /////////////////////////////////////////////////
					//////////////////////////////////////////////////////////////////
					case 31:
						genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.Circle));
						genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.Circle));
						genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.Circle));
						genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.Circle));
						genInfo.add(new GeneratorInfo("Asteroid",60,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Cat", 20,spawnSystem.LineFromNorth,3,2000));
						
						break;
					case 32:
						genInfo.add(new GeneratorInfo("Cat", 100,spawnSystem.Cardinal,5));	
						break;
					case 33:
						genInfo.add(new GeneratorInfo("Ice Asteroid", 40,spawnSystem.FullRandom,2,150));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 40,spawnSystem.FullRandom,2,400));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 40,spawnSystem.FullRandom,2,700));
						genInfo.add(new GeneratorInfo("Cat", 80,spawnSystem.Spiral,1,600));
						break;
					case 34:
						genInfo.add(new GeneratorInfo("Cat Gunner", 30,spawnSystem.FullRandom));
						break;
					case 35:
						genInfo.add(new GeneratorInfo("Cat", 50,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 25,spawnSystem.LineFromNorth,5,0));
						genInfo.add(new GeneratorInfo("Asteroid", 25,spawnSystem.LineFromSouth,5,0));
						genInfo.add(new GeneratorInfo("Cat", 25,spawnSystem.LineFromSouth,5,1750));
						genInfo.add(new GeneratorInfo("Cat", 25,spawnSystem.LineFromNorth,5,1750));
						break;
					case 36:  
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Ice Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 1,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 5,spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Asteroid", 20,spawnSystem.LineFromNorth,5,750));
						genInfo.add(new GeneratorInfo("Asteroid", 20,spawnSystem.LineFromEast,5,1000));
						break;
					case 37:
						genInfo.add(new GeneratorInfo("Ice Asteroid",20,spawnSystem.FullRandom,5,200));
						genInfo.add(new GeneratorInfo("Asteroid",50,spawnSystem.Spiral,4));
						genInfo.add(new GeneratorInfo("Fire Asteroid",50,spawnSystem.Circle,4));
						genInfo.add(new GeneratorInfo("Asteroid",20,spawnSystem.Circle,4));
						genInfo.add(new GeneratorInfo("Asteroid",20,spawnSystem.Circle,4));
						genInfo.add(new GeneratorInfo("Asteroid",20,spawnSystem.Circle,4));
						genInfo.add(new GeneratorInfo("Cat", 20,spawnSystem.LineFromNorth,2,1000));
						genInfo.add(new GeneratorInfo("Cat", 20,spawnSystem.LineFromEast,2,1500));
						break;
					case 38:
						int x=0;
						int dist =100;
						while(x < waveNumber+1) { 
							genInfo.add(new GeneratorInfo(Survival.fireorice(), Survival.cap(Survival.r.nextInt((int) (waveNumber*2+x+1))/3+1,50),Survival.randomWave(),Survival.r.nextInt(2), dist));
							genInfo.add(new GeneratorInfo(Survival.morethanlikelyfire(), Survival.cap(Survival.r.nextInt((int) (waveNumber/4+x+1)),50),Survival.randomWave(),Survival.r.nextInt(2), dist));
							genInfo.add(new GeneratorInfo(Survival.fireorice(), Survival.cap(Survival.r.nextInt((int) (waveNumber/3+x+1)),50),Survival.randomWave(),0, dist));
							genInfo.add(new GeneratorInfo("Cat", Survival.cap(Survival.r.nextInt((int) (waveNumber/6+1)),50),Survival.randomWave(),Survival.r.nextInt(2), (int)1.25*dist+100));
							if(waveNumber >= 12) {
								genInfo.add(new GeneratorInfo("Splitter Medium", Survival.cap(Survival.r.nextInt((int) (waveNumber/8+2)),6), Survival.randomWave(), Survival.r.nextInt(1),dist+Survival.r.nextInt(15)));
							}
							if(waveNumber >= 24) {
								genInfo.add(new GeneratorInfo("Splitter Big", Survival.cap(Survival.r.nextInt((int) (waveNumber/20+2)),6), Survival.randomWave(), Survival.r.nextInt(1), (int)(1.2f*dist)+Survival.r.nextInt(15)));
							}
							if(waveNumber >= 5) {
								genInfo.add(new GeneratorInfo("Cat Gunner", Survival.cap(Survival.r.nextInt((int) (waveNumber/8+1)),50),Survival.randomWave(),0, (int)1.25*dist+100));
							}
							if(300 - waveNumber<100) {
								dist += 100;
							}
							else {
								dist += 300 - 2*waveNumber;
							}
							x = x + 5;
						}
						break;
					case 39:
						genInfo.add(new GeneratorInfo("Splitter Big", 4,spawnSystem.Cardinal));
						genInfo.add(new GeneratorInfo("Splitter Big", 4,spawnSystem.Cardinal,1,300));
						genInfo.add(new GeneratorInfo("Splitter Big", 4,spawnSystem.Cardinal,2,600));
						genInfo.add(new GeneratorInfo("Splitter Big", 4,spawnSystem.Cardinal,3,900));
						
						break;
					case 40:
						genInfo.add(new GeneratorInfo("Cat", 30,spawnSystem.Spiral,4,250));
						genInfo.add(new GeneratorInfo("Fire Asteroid", 20,spawnSystem.Spiral,2));
						genInfo.add(new GeneratorInfo("Splitter Medium", 20,spawnSystem.Circle,2));
						genInfo.add(new GeneratorInfo("Splitter Medium", 20,spawnSystem.Circle,2));
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