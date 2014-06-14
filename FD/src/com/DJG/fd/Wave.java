package com.DJG.fd;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;



import android.graphics.Color;

class XY {
	public int x;
	public int y;
	
	public XY(int xNew, int yNew) {
		y = yNew;
		x = xNew;
	}
}

public class Wave extends ArrayList<Unit> {
	public static Wave currentWave;
	public final static Object currentWaveLock = new Object(); // A lock so we don't fuck up the currentWave.
	public static int currentWaveNumber;
	private static boolean waveSent = false;
	private static Random r = new Random();
	
	public static void initWaves() {
		// Obviously we just started the game.
		sendWave(0);
	}

	static void sendWave(int waveNumber){
		Wave myWave = new Wave();
		HashMap<String, Integer> unitMap = new HashMap<String, Integer>();
		switch(waveNumber){
		case 0:
			unitMap.put("Ogre", 10);
			break;
		case 1:
			unitMap.put("Mage", 15);
			break;
		case 2:
			unitMap.put("Ogre", 10);
			unitMap.put("Mage", 10);
			break;
		case 3:
			unitMap.put("Demon", 20);
			break;
		case 4:
			unitMap.put("Cat", 5);
			break;
		case 5:
			unitMap.put("Ogre", 15);
			unitMap.put("Mage", 15);
			break;
		case 6:
			unitMap.put("Demon", 30);
			break;
		case 7:
			unitMap.put("Ogre", 10);
			unitMap.put("Mage", 10);
			unitMap.put("Demon", 15);
			break;
		case 8:
			unitMap.put("Cat", 9);
			break;
		case 9:
			unitMap.put("Cheetah", 1);
			break;
		case 10:
			unitMap.put("Ogre", 25);
			unitMap.put("Cat", 3);
			break;
		default:
			unitMap.put("Ogre", r.nextInt(3*waveNumber)/2 +1);
			unitMap.put("Mage", r.nextInt(waveNumber+1));
			unitMap.put("Demon", r.nextInt(waveNumber+1));
			unitMap.put("Cat", r.nextInt(waveNumber/3+1));
			unitMap.put("Cheetah", r.nextInt(waveNumber/10+1));
			break;
		}
		addUnitsToWave(unitMap, myWave);
		currentWave = myWave;
	}
/*	
	static void sendWave(int waveNumber) {
		Wave myWave = new Wave();
		int x = 0;
		////////////////////////
		///////FIRST WAVE///////
		////////////////////////
		if(waveNumber == 0) {
			while(x<10) {
				XY xy = getRandomXY();
				myWave.add(new Unit("Any Name","Ogre",xy.x,xy.y,0,Color.CYAN));
				x++;
			}
		}
		///////////////////////
		//////SECOND WAVE//////
		///////////////////////
		if(waveNumber == 1) {
			while(x<10) {
				XY xy = getRandomXY();
				myWave.add(new Unit("Any Name","Mage",xy.x,xy.y,0,Color.BLUE));
				x++;
			}
		}
		///////////////////////
		///////THIRD WAVE//////
		///////////////////////
		if(waveNumber == 2) {
			while(x<20) {
				XY xy = getRandomXY();
				myWave.add(new Unit("Any Name","Demon",xy.x,xy.y,0,Color.RED));
				x++;
			}
		}
		///////////////////////
		//////FOURTH WAVE//////
		///////////////////////
		if(waveNumber == 3) {
			while(x<5) {
				XY xy = getRandomXY();
				myWave.add(new Unit("Any Name","Cat",xy.x,xy.y,0,Color.BLACK));
				x++;
			}
		}
		///////////////////////
		///FIRST SUPER WAVE////
		///////////////////////
		if(waveNumber == 4) {
			while(x<20) {
				XY xy = getRandomXY();
				myWave.add(new Unit("Any Name","Ogre",xy.x,xy.y,0,Color.CYAN));
				x++;
			}
		}
		///////////////////////
		///SECOND SUPER WAVE///
		///////////////////////
		if(waveNumber == 5) {
			while(x<30) {
				XY xy = getRandomXY();
				myWave.add(new Unit("Any Name","Mage",xy.x,xy.y,0,Color.BLUE));
				x++;
			}
		}
		///////////////////////
		///THIRD SUPER WAVE////
		///////////////////////
		if(waveNumber == 6) {
			while(x<40) {
				XY xy = getRandomXY();
				myWave.add(new Unit("Any Name","Demon",xy.x,xy.y,0,Color.RED));
				x++;
			}
		}
		///////////////////////
		//FOURTH SUPER WAVE////
		///////////////////////
		if(waveNumber == 7) {
			while(x<7) {
				XY xy = getRandomXY();
				myWave.add(new Unit("Any Name","Cat",xy.x,xy.y,0,Color.BLACK));
				x++;
			}
		}
		///////////////////////
		//FIRST ULTRA WAVE/////
		///////////////////////
		if(waveNumber == 8) {
			while(x<1) {
				XY xy = getRandomXY();
				myWave.add(new Unit("Any Name","Cheetah",xy.x,xy.y,0,Color.BLUE));
				x++;
			}
		}
		currentWave = myWave;
	}*/
	
	
	//Given a hash of how many units of each type you want, addes them to the given wave
	static void addUnitsToWave(HashMap<String, Integer> units, Wave wave){
		for(String type : units.keySet()){
			int c = UnitType.getUnitType(type).getColor();
			for(int i = 0; i<units.get(type); i++){
				XY xy = getRandomXY();
				wave.add(new Unit("Any Name",type,xy.x,xy.y,0,c));
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
	    	ySpawn = r.nextInt(300)*(-1);
	    }
	    
	    // > // EAST // RIGHT
	    if(whatQuarter == 2) {
	    	xSpawn = screenWidth + r.nextInt(300);
	    	ySpawn = r.nextInt(screenHeight);
	    }
	    
	    // \/ // SOUTH // BOTTOM
	    if(whatQuarter == 3) {
	    	xSpawn = r.nextInt(screenWidth);
	    	ySpawn = screenHeight + r.nextInt(300);
	    }
	    
	    // < // WEST // LEFT
	    if(whatQuarter == 4) {
	    	xSpawn = r.nextInt(300)*(-1);
	    	ySpawn = r.nextInt(screenHeight);
	    }
	    XY xy = new XY(xSpawn,ySpawn);
	    return xy;
	}
	
	public boolean getWaveSent() {
		return waveSent;
	}
	
	public static Wave getCurrentWave() {
		return currentWave;
	}
	
	public static void sendWaves() {
		
		// Send the next wave if the current one is empty!
		if(currentWave.isEmpty()) {
			currentWaveNumber++;
			DisplayMessageActivity.levelText = "Wave " + currentWaveNumber;
			synchronized(currentWaveLock) {
				sendWave(currentWaveNumber);
			}
			waveSent = false;
		}
		// Tell the wave to attack the castle.
		if(currentWave.getWaveSent() == false) {
			currentWave.attackCastle();
			waveSent = true; // Efficiency.
		}
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
	
	public static void killUnit(Unit u) {
		synchronized(currentWaveLock) {
			int pos = getUnitPos(u);
			currentWave.remove(pos);
		}
	}
}