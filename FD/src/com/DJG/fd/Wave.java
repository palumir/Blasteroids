package com.DJG.fd;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
		
		// Start at what wave?
		currentWaveNumber = 0;
		sendWave(0);
	}

	static void sendWave(int waveNumber){
		Wave myWave = new Wave();
		HashMap<String, UnitPattern> unitMap = new HashMap<String, UnitPattern>();
		switch(waveNumber){
		case 0:
			unitMap.put("Ogre", new UnitPattern(3,"Random"));
			break;
		case 1:
			unitMap.put("Mage", new UnitPattern(10,"Random"));
			break;
		case 2:
			unitMap.put("Ogre", new UnitPattern(10,"Random"));
			unitMap.put("Mage", new UnitPattern(10,"Random"));
			break;
		case 3:
			unitMap.put("Demon", new UnitPattern(20,"Random"));
			break;
		case 4:
			unitMap.put("Cat", new UnitPattern(5,"Random"));
			break;
		case 5:
			unitMap.put("Ogre", new UnitPattern(15,"Random"));
			unitMap.put("Mage", new UnitPattern(15,"Random"));
			break;
		case 6:
			unitMap.put("Demon", new UnitPattern(40,"Random"));
			break;
		case 7:
			unitMap.put("Ogre", new UnitPattern(10,"Random"));
			unitMap.put("Mage", new UnitPattern(10,"Random"));
			unitMap.put("Demon", new UnitPattern(10,"Random"));
			break;
		case 8:
			unitMap.put("Cat", new UnitPattern(4,"Random"));
			unitMap.put("Cheetah", new UnitPattern(1,"Random"));
			break;
		case 9:
			unitMap.put("Cheetah", new UnitPattern(3,"Random"));
			break;
		case 10:
			unitMap.put("Ogre", new UnitPattern(35,"Random"));
			unitMap.put("Cat", new UnitPattern(3,"Random"));
			break;
		case 11:
			unitMap.put("Splitter Huge", new UnitPattern(2,"Random"));
			break;
		default:
			unitMap.put("Ogre", new UnitPattern(r.nextInt(3*waveNumber)/2 +1,"Random"));
			unitMap.put("Mage", new UnitPattern(r.nextInt(waveNumber+1),"Random"));
			unitMap.put("Demon", new UnitPattern(r.nextInt(waveNumber+1),"Random"));
			unitMap.put("Cat", new UnitPattern(r.nextInt(waveNumber/3+1),"Random"));
			unitMap.put("Healer", new UnitPattern(r.nextInt(waveNumber/4+1),"Random"));
			unitMap.put("Cheetah", new UnitPattern(r.nextInt(waveNumber/10+1),"Random"));
			unitMap.put("FullHealer",new UnitPattern(r.nextInt(waveNumber/15+1),"Random"));
			break;
		}
		addUnitsToWave(unitMap, myWave);
		currentWave = myWave;
	}
	
	//Given a hash of how many units of each type you want, adds them to the given wave
	static void addUnitsToWave(HashMap<String, UnitPattern> units, Wave wave){
		for(String type : units.keySet()){
			for(int i = 0; i<units.get(type).numUnits; i++){
				XY xy = getRandomXY();
				wave.add(new Unit("Any Name",type,xy.x,xy.y));
			}
		}
	}
	
	private static XY getRandomXY() {
		// Get the height, width, and a new random number generator.
		int screenWidth = DisplayMessageActivity.getScreenWidth();
		int screenHeight = DisplayMessageActivity.getScreenHeight();
		int xSpawn = -200;
		int ySpawn = -200;
	    int whatQuarter = r.nextInt(4) + 1;
	    
	    // ^ // NORTH // TOP
	    if(whatQuarter == 1) {
	    	xSpawn = r.nextInt(screenWidth);
	    	ySpawn = r.nextInt(300)*(-1)-50;
	    }
	    
	    // > // EAST // RIGHT
	    if(whatQuarter == 2) {
	    	xSpawn = screenWidth + r.nextInt(300) + 50;
	    	ySpawn = r.nextInt(screenHeight);
	    }
	    
	    // \/ // SOUTH // BOTTOM
	    if(whatQuarter == 3) {
	    	xSpawn = r.nextInt(screenWidth);
	    	ySpawn = screenHeight + r.nextInt(300) + 50;
	    }
	    
	    // < // WEST // LEFT
	    if(whatQuarter == 4) {
	    	xSpawn = r.nextInt(300)*(-1)-50;
	    	ySpawn = r.nextInt(screenHeight);
	    }
	    XY xy = new XY(xSpawn,ySpawn);
	    return xy;
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