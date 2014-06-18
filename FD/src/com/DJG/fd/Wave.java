package com.DJG.fd;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.DJG.generators.WaveGenerator;
import com.DJG.generators.GeneratorInfo;
import com.DJG.generators.GeneratorInfo.spawnSystem;
import com.DJG.unit.Unit;

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
	private static Wave currentWave;
	private static boolean isFirst;
	private static long waveEndedTime;
	public final static Object currentWaveLock = new Object(); // A lock so we don't fuck up the currentWave.
	private static int currentWaveNumber;
	private static boolean waveSent = false;
	private static Random r = new Random();
	
	public static void initWaves() {
		// Obviously we just started the game.
		waveSent = false;
		isFirst = true;
		waveGenerator = new WaveGenerator();
		// Start at what wave?
		currentWaveNumber = 5;
		sendWave(5);
	}

	static void sendWave(int waveNumber){
		Wave myWave = new Wave();
		HashMap<String, UnitPattern> unitMap = new HashMap<String, UnitPattern>();
		ArrayList<GeneratorInfo> genInfo = new ArrayList<GeneratorInfo>();
		switch(waveNumber){
		case 0:
			genInfo.add(new GeneratorInfo("Ogre", 4, spawnSystem.FullRandom));
			break;
		case 1:
			genInfo.add(new GeneratorInfo("Healer", 10,spawnSystem.LineFromNorth));
			break;
		case 2:
			genInfo.add(new GeneratorInfo("Ogre", 10,spawnSystem.LineFromEast));
			genInfo.add(new GeneratorInfo("Ogre", 10,spawnSystem.LineFromWest));
			break;
		case 3:
			genInfo.add(new GeneratorInfo("Demon", 20,spawnSystem.FullRandom));
			break;
		case 4:
			genInfo.add(new GeneratorInfo("Ogre", 12,spawnSystem.Circle));
			break;
		case 5:
			genInfo.add(new GeneratorInfo("Ogre", 15,spawnSystem.Spiral));
			genInfo.add(new GeneratorInfo("Mage", 15,spawnSystem.Spiral));
			break;
		case 6:
			genInfo.add(new GeneratorInfo("Demon", 10,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Demon", 10,spawnSystem.Circle));
			genInfo.add(new GeneratorInfo("Demon", 20,spawnSystem.Circle));
			break;
		case 7:
			genInfo.add(new GeneratorInfo("Cat", 10,spawnSystem.LineFromNorth));
			genInfo.add(new GeneratorInfo("Cat", 10,spawnSystem.LineFromSouth));
			break;
		case 8:
			genInfo.add(new GeneratorInfo("Cat", 4,spawnSystem.LineFromNorth));
			genInfo.add(new GeneratorInfo("Cheetah", 1,spawnSystem.FullRandom));
			break;
		case 9:
			genInfo.add(new GeneratorInfo("Cheetah", 1,spawnSystem.LineFromNorth));
			genInfo.add(new GeneratorInfo("Cheetah", 1,spawnSystem.Spiral));
			break;
		case 10:
			genInfo.add(new GeneratorInfo("Ogre", 24,spawnSystem.Circle));
			break;
		case 11:
			genInfo.add(new GeneratorInfo("Splitter Huge", 1,spawnSystem.FullRandom));
			break;
		default:
			genInfo.add(new GeneratorInfo("Ogre", r.nextInt(3*waveNumber)/2 +1,spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Mage", r.nextInt(waveNumber+1),spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Demon", r.nextInt(waveNumber+1),spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Cat", r.nextInt(waveNumber/5+1),spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Cat", r.nextInt(waveNumber/5+1),spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Healer", r.nextInt(waveNumber/4+1),spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("Cheetah", r.nextInt(waveNumber/10+1),spawnSystem.FullRandom));
			genInfo.add(new GeneratorInfo("FullHealer", r.nextInt(waveNumber/50+1),spawnSystem.FullRandom));
			break;
		}
		//addUnitsToWave(unitMap, myWave);
		
		currentWave = waveGenerator.generateWave(genInfo);
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
			if(currentWave.isEmpty() && System.currentTimeMillis() - waveEndedTime > 2000) {
				currentWaveNumber++;
				DisplayMessageActivity.levelText = "Wave " + (currentWaveNumber+1);
				sendWave(currentWaveNumber);
				waveSent = false;
				isFirst = true;
			}

			// Tell the wave to attack the castle.
			if(currentWave.getWaveSent() == false) {
				currentWave.attackCastle();
				waveSent = true; // Efficiency.
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