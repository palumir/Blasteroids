package com.DJG.fd;
import java.util.ArrayList;
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
	
	public static void initWaves() {
		// Obviously we just started the game.
		sendWave(0);
	}
	
	static void sendWave(int waveNumber) {
		///////////////////////
		///////FIRST WAVE//////
		///////////////////////
		if(waveNumber == 0) {
			Wave firstWave = new Wave();
			int x = 0;
			while(x<10) {
				XY xy = getRandomXY();
				firstWave.add(new Unit("Any Name","Ogre",xy.x,xy.y,0,Color.CYAN));
				x++;
			}
			currentWave = firstWave;
		}
		///////////////////////
		//////SECOND WAVE//////
		///////////////////////
		if(waveNumber == 1) {
			Wave firstWave = new Wave();
			int x = 0;
			while(x<10) {
				XY xy = getRandomXY();
				firstWave.add(new Unit("Any Name","Mage",xy.x,xy.y,0,Color.BLUE));
				x++;
			}
			currentWave = firstWave;
		}
		///////////////////////
		///////THIRD WAVE//////
		///////////////////////
		if(waveNumber == 2) {
			Wave firstWave = new Wave();
			int x = 0;
			while(x<20) {
				XY xy = getRandomXY();
				firstWave.add(new Unit("Any Name","Demon",xy.x,xy.y,0,Color.RED));
				x++;
			}
			currentWave = firstWave;
		}
		///////////////////////
		//////FOURTH WAVE//////
		///////////////////////
		if(waveNumber == 3) {
			Wave firstWave = new Wave();
			int x = 0;
			while(x<5) {
				XY xy = getRandomXY();
				firstWave.add(new Unit("Any Name","Cat",xy.x,xy.y,0,Color.BLACK));
				x++;
			}
			currentWave = firstWave;
		}
		///////////////////////
		///FIRST SUPER WAVE////
		///////////////////////
		if(waveNumber == 4) {
			Wave firstWave = new Wave();
			int x = 0;
			while(x<20) {
				XY xy = getRandomXY();
				firstWave.add(new Unit("Any Name","Ogre",xy.x,xy.y,0,Color.CYAN));
				x++;
			}
			currentWave = firstWave;
		}
		///////////////////////
		///SECOND SUPER WAVE///
		///////////////////////
		if(waveNumber == 5) {
			Wave firstWave = new Wave();
			int x = 0;
			while(x<30) {
				XY xy = getRandomXY();
				firstWave.add(new Unit("Any Name","Mage",xy.x,xy.y,0,Color.BLUE));
				x++;
			}
			currentWave = firstWave;
		}
		///////////////////////
		///THIRD SUPER WAVE////
		///////////////////////
		if(waveNumber == 6) {
			Wave firstWave = new Wave();
			int x = 0;
			while(x<100) {
				XY xy = getRandomXY();
				firstWave.add(new Unit("Any Name","Demon",xy.x,xy.y,0,Color.RED));
				x++;
			}
			currentWave = firstWave;
		}
		///////////////////////
		//FOURTH SUPER WAVE////
		///////////////////////
		if(waveNumber == 7) {
			Wave firstWave = new Wave();
			int x = 0;
			while(x<50) {
				XY xy = getRandomXY();
				firstWave.add(new Unit("Any Name","Cat",xy.x,xy.y,0,Color.BLACK));
				x++;
			}
			currentWave = firstWave;
		}
	}
	
	private static XY getRandomXY() {
		// Get the height, width, and a new random number generator.
		int screenWidth = DisplayMessageActivity.getScreenWidth();
		int screenHeight = DisplayMessageActivity.getScreenHeight();
		
		Random r = new Random();
		int xSpawn = -200;
		int ySpawn = -200;
	    int whatQuarter = r.nextInt(3) + 1;
	    
	    // ^ //
	    if(whatQuarter == 1) {
	    	xSpawn = r.nextInt(screenWidth);
	    	ySpawn = r.nextInt(300)*(-1);
	    }
	    
	    // > //
	    if(whatQuarter == 2) {
	    	xSpawn = screenWidth + r.nextInt(300);
	    	ySpawn = r.nextInt(screenHeight);
	    }
	    
	    // \/ //
	    if(whatQuarter == 3) {
	    	xSpawn = r.nextInt(screenWidth);
	    	ySpawn = screenHeight + r.nextInt(300);
	    }
	    
	    // < //
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
		
		for(Unit u : this) {
			u.moveNormally(screenWidth/2,screenHeight/2);
		}
	}
	
	public static int getUnitPos(Unit thisUnit) {
		int foundUnit = 0;
		for(Unit u : currentWave) {
			if(u == thisUnit) {
				break;
			}
			foundUnit++;
		}
		return foundUnit;
	}
	
	public static void killUnit(Unit u) {
		synchronized(currentWaveLock) {
			int pos = getUnitPos(u);
			currentWave.remove(pos);
		}
	}
}