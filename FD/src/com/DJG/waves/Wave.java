package com.DJG.waves;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.util.Log;

import com.DJG.fd.GameActivity;
import com.DJG.generators.GeneratorInfo;
import com.DJG.generators.GeneratorInfo.spawnSystem;
import com.DJG.generators.WaveGenerator;
import com.DJG.planets.Planet;
import com.DJG.planets.Saturn;
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
	public static ArrayList<Integer> usedWaves = new ArrayList<Integer>();
	public static WaveGenerator waveGenerator;
	private static boolean isBossWave = false;
	private static Wave currentWave;
	private static boolean isFirst;
	private static long waveEndedTime;
	public final static Object currentWaveLock = new Object(); // A lock so we don't fuck up the currentWave.
	private static double currentWaveNumber;
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
	
	public static double getWaveNumber() {
		return currentWaveNumber;
	}
	
	public static void setWave(int n) {
		currentWaveNumber = n;
	}

	public static void setCurrentWave(Wave w) {
		currentWave = w;
	}
	
	public static void setWaitTime(int n) {
		setWaveWaitTime(n);
	}
	
	public static void initWaves(int waveStartNumber){
		// Obviously we just started the game.
		waveSent = false;
		isFirst = true;
		waveGenerator = new WaveGenerator();
		// Start at what wave?
		waveStartNumber = 0;
		currentWaveNumber = waveStartNumber;
		sendWave(waveStartNumber);
	}
	
	// If the int has already used, don't use it again. If every int between
	// 0 and top have been used, reset usedWaves, and use the input as num.
	static Integer getMyRandom(Integer num, Integer top) {
		Integer x = num;
		Integer n = 0;
		if(usedWaves == null) {
			usedWaves.add(num);
			return num;
		}
		while(usedWaves.contains(x)) {
			if(x<0) {
				x = top;
			}
			if(n>top+1) {
				usedWaves.clear();
				usedWaves.add(num);
				return num;
			}
			x--;
			n++;
		}
		usedWaves.add(x);
		return x;
	}
	
	static int cap(int num, int cap) {
		if(num > cap) {
			return cap;
		}
		return num;
	}
	
	static String fireorice() {
		int between = getR().nextInt(20); 
		String whatToSend = "Asteroid";
		if(between == 1) {
			whatToSend = "Fire Asteroid";
		}
		if(between == 2) {
			whatToSend = "Ice Asteroid";
		}
		return whatToSend;
	}
	
	static void sendWave(double waveNumber){
		if(GameActivity.getMode() == "Campaign") {
			Campaign.sendCampaignWave(waveNumber);
		}
		else {
			Survival.sendSurvivalWave(waveNumber);
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
				waveEndedTime = GameActivity.getGameTime();
				isFirst = false;

			}
			
			// Send the next wave if the current one is empty and it has been two seconds!
			if(currentWave.isEmpty() && GameActivity.getGameTime() - waveEndedTime > getWaveWaitTime()) {
				currentWaveNumber++;
				GameActivity.levelText = "Wave " + (int)(currentWaveNumber+1);
				sendWave(currentWaveNumber);
				Planet planet = (Planet) Unit.getUnit("Fortress");
				planet.afterWave();
				Log.i("Moons:", "" + Unit.moons.size());
				waveSent = false;
				isFirst = true;
				
			}
			
			if(!isBossWave()) {
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
		int screenWidth = GameActivity.getScreenWidth();
		int screenHeight = GameActivity.getScreenHeight();
		synchronized(currentWaveLock) {
			for(Unit u : this) {
				u.moveNormally(screenWidth/2,screenHeight/2);
			}
		}
	}
	
	public static void destroyWaves() {
	currentWaveNumber = 0;
	 synchronized(Wave.currentWaveLock) {
		 if(Wave.getCurrentWave() != null) {
			 Wave.getCurrentWave().clear();
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
	
	public static double getCurrentWaveNumber() {
		return currentWaveNumber;
	}
	
	public int getWaitTime(){
		return getWaveWaitTime();
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

	public static int getWaveWaitTime() {
		return waveWaitTime;
	}

	public static void setWaveWaitTime(int waveWaitTime) {
		Wave.waveWaitTime = waveWaitTime;
	}

	public static boolean isBossWave() {
		return isBossWave;
	}

	public static void setBossWave(boolean isBossWave) {
		Wave.isBossWave = isBossWave;
	}

	public static Random getR() {
		return r;
	}

	public static void setR(Random r) {
		Wave.r = r;
	}
}