package com.DJG.waves;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.util.Log;

import com.DJG.fd.DisplayMessageActivity;
import com.DJG.generators.GeneratorInfo;
import com.DJG.generators.GeneratorInfo.spawnSystem;
import com.DJG.generators.WaveGenerator;
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
	private static ArrayList<Integer> usedWaves = new ArrayList<Integer>();
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
	public static void initWaves(int waveStartNumber){
		// Obviously we just started the game.
		waveSent = false;
		isFirst = true;
		waveGenerator = new WaveGenerator();
		// Start at what wave?
		currentWaveNumber = waveStartNumber;
		sendWave(waveStartNumber);
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
		int between = r.nextInt(20); 
		String whatToSend = "Asteroid";
		if(between == 1) {
			whatToSend = "Fire Asteroid";
		}
		if(between == 2) {
			whatToSend = "Ice Asteroid";
		}
		return whatToSend;
	}
	
	static void sendSurvivalWave(int waveNumber) {
		int numCases = 6;
		int x = 0;
		int dist = 0;
		Integer randomNum = getMyRandom(r.nextInt(numCases),numCases-1);
		Wave myWave = new Wave();
		currentWave = myWave;
		HashMap<String, UnitPattern> unitMap = new HashMap<String, UnitPattern>();
		ArrayList<GeneratorInfo> genInfo = new ArrayList<GeneratorInfo>();
		genInfo.add(new GeneratorInfo("Spawner", 1, spawnSystem.Spawner));
		waveWaitTime = 1500;
		if(waveNumber%2 == 0) {
			x=0;
			dist=100;
			while(x < waveNumber+1) { 
				genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(waveNumber*2+x+1)/3+1,100),spawnSystem.FullRandom,r.nextInt(2), dist));
				genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(waveNumber/4+x+1),100),spawnSystem.FullRandom,r.nextInt(2), dist));
				genInfo.add(new GeneratorInfo("Fire Asteroid", cap(r.nextInt(waveNumber/3+x+1),100),spawnSystem.FullRandom,r.nextInt(4), dist));
				genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/6+1),100),spawnSystem.FullRandom,r.nextInt(2), (int)2*dist+100));
				genInfo.add(new GeneratorInfo("Healer", cap(r.nextInt(waveNumber/25+1),100),spawnSystem.FullRandom,r.nextInt(2), dist));
				genInfo.add(new GeneratorInfo("FullHealer", cap(r.nextInt(waveNumber/50+1),100),spawnSystem.FullRandom,r.nextInt(2), dist));
				if(300 - waveNumber<100) {
					dist += 100;
				}
				else {
					dist += 300 - 2*waveNumber;
				}
				x = x + 5;
			}
		}
		else {
		switch(randomNum) {	
			// Explosive Circle Wave, probably most satisfying wave.
			case 0:
				
				genInfo.add(new GeneratorInfo("Asteroid", cap(r.nextInt(waveNumber*2+1)+1,100),spawnSystem.FullRandom));
				genInfo.add(new GeneratorInfo("Fire Asteroid", cap(r.nextInt(waveNumber*2+1),100),spawnSystem.Circle));
				genInfo.add(new GeneratorInfo("Fire Asteroid", cap(r.nextInt(waveNumber*2+1),100),spawnSystem.Circle));
				genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/5+1),100),spawnSystem.FullRandom));
			break;
	
			// Spiral wave!
			case 1:
				x = waveNumber+ 2*r.nextInt(waveNumber+1) + 1;
				dist = 200;
				while(x > 0) {
					if(300 - waveNumber<100) {
						dist += 100;
					}
					else {
						dist += 300 - 2*waveNumber;
					}
					genInfo.add(new GeneratorInfo(fireorice(), cap(x,100),spawnSystem.Spiral));
					genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/6+1)+1,100),spawnSystem.FullRandom,0,2*dist+100));
					x = x/2;
				}
			break;
			
			// Fire or ice circle with top and bottom bombardment.
			case 2:
				x = 0;
				dist = 0;
				while(x < waveNumber+1) { 
					dist = dist + 350;
					genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(waveNumber+1),100),spawnSystem.Circle, r.nextInt(2)-1, dist));
					genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/7+1),100),spawnSystem.FullRandom,r.nextInt(2)-1,2*dist+100));
					x = x+5;
				}
				genInfo.add(new GeneratorInfo("Fire Asteroid", cap(waveNumber+1,100),spawnSystem.LineFromNorth,r.nextInt(2)));
				genInfo.add(new GeneratorInfo("Fire Asteroid", cap(waveNumber+1,100),spawnSystem.LineFromSouth,r.nextInt(2)));	
			break;
			
			// Four sides spiraling inward. Coolest wave so far I think.
			case 3:
				x=0;
				dist=200;
				while(x < waveNumber+1) { 
					genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(2+x/5),100),spawnSystem.FullRandom,r.nextInt(2), (int)2*dist+100));
					genInfo.add(new GeneratorInfo("Healer", cap(r.nextInt(waveNumber/25+1),100),spawnSystem.FullRandom,r.nextInt(2), dist));
					genInfo.add(new GeneratorInfo("FullHealer", cap(r.nextInt(waveNumber/50+1),100),spawnSystem.FullRandom,r.nextInt(2), dist));
					if(300 - waveNumber<100) {
						dist += 100;
					}
					else {
						dist += 300 - 2*waveNumber;
					}
					x = x + 5;
				}
				genInfo.add(new GeneratorInfo(fireorice(), cap(waveNumber+1,100),spawnSystem.LineFromNorth,1));
				genInfo.add(new GeneratorInfo(fireorice(), cap(waveNumber+1,100),spawnSystem.LineFromSouth,1));
				genInfo.add(new GeneratorInfo(fireorice(), cap(waveNumber+1,100),spawnSystem.LineFromEast,1));
				genInfo.add(new GeneratorInfo(fireorice(), cap(waveNumber+1,100),spawnSystem.LineFromWest,1));
			break;
			
			// Mechanical Wave
			case 4:
				x=0;
				int n = 0;
				dist=200;
				while(x < Math.ceil(waveNumber/5)) { 
					if(n%2 == 0) {
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/2+1)+1,100),spawnSystem.LineFromNorth,r.nextInt(2),2*dist));
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/2+1)+1,100),spawnSystem.LineFromSouth,r.nextInt(2),2*dist+100));
							if(r.nextInt(2) == 1) {
								genInfo.add(new GeneratorInfo("Fire Asteroid", cap(r.nextInt(waveNumber/2+1),100),spawnSystem.LineFromWest,2,dist));
							}
							else { 
								genInfo.add(new GeneratorInfo("Fire Asteroid", cap(r.nextInt(waveNumber/2+1),100),spawnSystem.LineFromEast,2,dist));
							}
					}
					else {
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/2+1)+1,100),spawnSystem.LineFromEast,r.nextInt(2),2*dist));
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/2+1)+1,100),spawnSystem.LineFromWest,r.nextInt(2),2*dist+100));
							if(r.nextInt(2) == 1) {
								genInfo.add(new GeneratorInfo("Fire Asteroid", cap(r.nextInt(waveNumber/2+1),100),spawnSystem.LineFromNorth,2,dist));
							}
							else { 
								genInfo.add(new GeneratorInfo("Fire Asteroid",cap(r.nextInt(waveNumber/2+1),100),spawnSystem.LineFromSouth,2,dist));
							}
					}
					if(300 - waveNumber<100) {
						dist += 100;
					}
					else {
						dist += 300 - 2*waveNumber;
					}
					x = x + 1;
				}
				
			break;
			
			// Circle bombardment wave
			case 5:
				x=0;
				dist=200;
				while(x < waveNumber+1) { 
					genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(waveNumber*2+x+1)/3+1,100),spawnSystem.Circle,r.nextInt(2), dist));
					genInfo.add(new GeneratorInfo("Fire Asteroid", cap(r.nextInt(waveNumber*10+x+1),100)/4+1,spawnSystem.Circle,r.nextInt(2), dist+150));
					genInfo.add(new GeneratorInfo("Ice Asteroid", cap(r.nextInt(waveNumber/3+x/5+1),100),spawnSystem.FullRandom,r.nextInt(4), dist));
					genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(2+x/5),100),spawnSystem.FullRandom,r.nextInt(2), (int)2*dist+100));
					genInfo.add(new GeneratorInfo("Healer", cap(r.nextInt(waveNumber/25+1),100),spawnSystem.FullRandom,r.nextInt(2), dist));
					genInfo.add(new GeneratorInfo("FullHealer", cap(r.nextInt(waveNumber/50+1),100),spawnSystem.FullRandom,r.nextInt(2), dist));
					if(300 - waveNumber<100) {
						dist += 100;
					}
					else {
						dist += 300 - 2*waveNumber;
					}
					x = x + 5;
				}
			break;
			}
		}
		currentWave = waveGenerator.generateWave(genInfo);
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