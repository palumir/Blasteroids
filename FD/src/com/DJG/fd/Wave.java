package com.DJG.fd;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.DJG.generators.GeneratorInfo;
import com.DJG.generators.GeneratorInfo.spawnSystem;
import com.DJG.generators.WaveGenerator;
import com.DJG.units.Cthulu;
import com.DJG.units.Unit;

class XY {
	public int x;
	public int y;
	
	public XY(int xNew, int yNew) {
		y = yNew;
		x = xNew;
	}
}

class UnitPattern {
	public int numUnits;
	public String pattern;
	public UnitPattern(int num, String unitPattern) {
		numUnits = num;
		pattern = unitPattern;
	}
}

public class Wave extends ArrayList<Unit> {
	private static WaveGenerator waveGenerator;
	private static boolean isBossWave = false;
	private static Wave currentWave;
	private static boolean isFirst;
	private static long waveEndedTime;
	public final static Object currentWaveLock = new Object(); // A lock so we don't fuck up the currentWave.
	private static int currentWaveNumber;
	private static boolean waveSent = false;
	private static Random r = new Random();
	private static int waveWaitTime = 1000;
	
	public static void initWaves() {
		// Obviously we just started the game.
		waveSent = false;
		isFirst = true;
		waveGenerator = new WaveGenerator();
		// Start at what wave?
		int waveStart = 0;
		currentWaveNumber = waveStart;
		sendWave(waveStart);
	}
	
	public static void setWave(int n) {
		currentWaveNumber = n;
	}

	static void sendCampaignWave(int waveNumber) {
		Wave myWave = new Wave();
		currentWave = myWave;
		boolean isBoss = false;
		HashMap<String, UnitPattern> unitMap = new HashMap<String, UnitPattern>();
		ArrayList<GeneratorInfo> genInfo = new ArrayList<GeneratorInfo>();
		waveWaitTime = 1500;
		switch(waveNumber){
		case 0:
			genInfo.add(new GeneratorInfo("Cat", 4, spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Asteroid", 4, spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 4, spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Ice Asteroid", 4, spawnSystem.FullRandom));
			break;
		/*case 0: 
			Cthulu.battleStart();
			isBoss = true;
			break;*/
		case 1:
			genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.Circle));
			break;
		case 2:
			genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.LineFromEast));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 10,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.LineFromWest));
			break;
		case 3:
			genInfo.add(new GeneratorInfo("Ice Asteroid", 20,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Asteroid", 20,spawnSystem.Spiral));
			break;
		case 4:
			genInfo.add(new GeneratorInfo("Asteroid", 12,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Asteroid", 12,spawnSystem.Circle));
			break;
		case 5:
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
		case 6:
			genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.LineFromNorth));
			genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.LineFromSouth));
			break;
		case 7:
			genInfo.add(new GeneratorInfo("Asteroid", 8,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 15,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Asteroid", 8,spawnSystem.LineFromNorth));
			genInfo.add(new GeneratorInfo("Asteroid", 8,spawnSystem.LineFromSouth));
			break;
		case 8:
			genInfo.add(new GeneratorInfo("Asteroid", 30,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 15,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 15,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Fire Asteroid", 15,spawnSystem.Circle));
			break;
		case 9:
			genInfo.add(new GeneratorInfo("Cheetah", 1,spawnSystem.Circle));
			break;
		case 10:
			genInfo.add(new GeneratorInfo("Asteroid", 20,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Asteroid", 10,spawnSystem.Circle));
			break;
		case 11:
			genInfo.add(new GeneratorInfo("Splitter Huge", 1,spawnSystem.FullRandom));
			break;
		default:
			genInfo.add(new GeneratorInfo("Asteroid", r.nextInt(waveNumber) +1,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Fire Asteroid", r.nextInt(waveNumber*5+1)/4,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Cat", r.nextInt(waveNumber/4+1),spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Healer", r.nextInt(waveNumber/25+1),spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Cheetah", r.nextInt(waveNumber/9+1),spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("FullHealer", r.nextInt(waveNumber/50+1),spawnSystem.FullRandom));
			break;
		}
		isBossWave = isBoss;
		if(!isBoss) {
			currentWave = waveGenerator.generateWave(genInfo);
		}
	}
	
	static void sendSurvivalWave(int waveNumber) {
		Wave myWave = new Wave();
		currentWave = myWave;
		boolean isBoss = false;
		HashMap<String, UnitPattern> unitMap = new HashMap<String, UnitPattern>();
		ArrayList<GeneratorInfo> genInfo = new ArrayList<GeneratorInfo>();
		waveWaitTime = 1500;
			genInfo.add(new GeneratorInfo("Asteroid", r.nextInt(waveNumber+1) +1,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Fire Asteroid", r.nextInt(waveNumber*5+1)/4,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Cat", r.nextInt(waveNumber/4+1),spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Healer", r.nextInt(waveNumber/25+1),spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Cheetah", r.nextInt(waveNumber/9+1),spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("FullHealer", r.nextInt(waveNumber/50+1),spawnSystem.FullRandom));
		isBossWave = isBoss;
		if(!isBoss) {
			currentWave = waveGenerator.generateWave(genInfo);
		}
	}
	
	static void sendWave(int waveNumber){
		if(DisplayMessageActivity.getMode() == "Campaign") {
			sendCampaignWave(waveNumber);
		}
		else {
			sendSurvivalWave(waveNumber);
		}
	}
	
	public boolean getWaveSent() {
		return waveSent;
	}
	
	public static void addToCurrentWave(Unit u) {
		synchronized(currentWaveLock) {
			currentWave.add(u);
		}
	}
	
	public static Wave getCurrentWave() {
		return currentWave;
	}
	
	public static void sendWaves() {
		
		synchronized(currentWaveLock) {
			
			// Record when it first becomes empty.
			if(currentWave.isEmpty() && isFirst) {
				waveEndedTime = System.currentTimeMillis();
				isFirst = false;
			}
			
			// Send the next wave if the current one is empty and it has been two seconds!
			if(currentWave.isEmpty() && System.currentTimeMillis() - waveEndedTime > waveWaitTime) {
				currentWaveNumber++;
				DisplayMessageActivity.levelText = "Wave " + (currentWaveNumber+1);
				sendWave(currentWaveNumber);
				waveSent = false;
				isFirst = true;
			}
			
			if(!isBossWave) {
				// Tell the wave to attack the castle.
				if(currentWave.getWaveSent() == false) {
					currentWave.attackCastle();
					waveSent = true; // Efficiency.
				}
			}
		}
	}

	public static void currentWaveAttackCastle() {
			currentWave.attackCastle();
	}
	
	private void attackCastle() {
		// Get the height, width, and a new random number generator.
		int screenWidth = DisplayMessageActivity.getScreenWidth();
		int screenHeight = DisplayMessageActivity.getScreenHeight();
		synchronized(currentWaveLock) {
			for(Unit u : this) {
				u.moveNormally(screenWidth/2,screenHeight/2);
			}
		}
	}
	
	public static void destroyWaves() {
	currentWaveNumber = 0;
	 synchronized(Wave.currentWaveLock) {
		 Wave.getCurrentWave().clear();
	 }
	}
	
	public static int getUnitPos(Unit thisUnit) {
		int foundUnit = 0;
		synchronized(currentWaveLock) {
			for(Unit u : currentWave) {
				if(u == thisUnit) {
					break;
				}
				foundUnit++;
			}
			return foundUnit;
		}
	}
	
	public static int getCurrentWaveNumber() {
		return currentWaveNumber;
	}
	
	public int getWaitTime(){
		return waveWaitTime;
	}
	
	public static void killUnit(Unit u) {
			synchronized(currentWaveLock) {
				int foundUnit = 0;
					for(Unit v : currentWave) {
						if(v == u) {
							break;
						}
						foundUnit++;
					}
					if(currentWave.size() != 0 && foundUnit < currentWave.size()) {
						currentWave.remove(foundUnit);
					}
				}
	}
}