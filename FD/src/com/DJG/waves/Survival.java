package com.DJG.waves;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.util.Log;

import com.DJG.generators.GeneratorInfo;
import com.DJG.generators.GeneratorInfo.spawnSystem;

public class Survival {
	
	private static Random r = new Random();

// If the int has already used, don't use it again. If every int between
	// 0 and top have been used, reset Wave.usedWaves, and use the input as num.
	public static Integer getMyRandom(Integer num, Integer top) {
		Integer x = num;
		Integer n = 0;
		if(Wave.usedWaves == null) {
			Wave.usedWaves.add(num);
			return num;
		}
		while(Wave.usedWaves.contains(x)) {
			if(x<1) {
				x = top;
			}
			if(n>top+1) {
				Wave.usedWaves.clear();
				Wave.usedWaves.add(num);
				return num;
			}
			x--;
			n++;
		}
		Wave.usedWaves.add(x);
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
	
	static String morethanlikelyfire() {
		int between = r.nextInt(10); 
		String whatToSend = "Asteroid";
		if(between <= 3) {
			whatToSend = "Fire Asteroid";
		}
		if(between == 4) {
			whatToSend = "Ice Asteroid";
		}
		return whatToSend;
	}
	
	static String morethanlikelyice() {
		int between = r.nextInt(10); 
		String whatToSend = "Asteroid";
		if(between <= 3) {
			whatToSend = "Ice Asteroid";
		}
		if(between == 4) {
			whatToSend = "Fire Asteroid";
		}
		return whatToSend;
	}
	
	static GeneratorInfo.spawnSystem randomWave() {
		int n = r.nextInt(21);
		if(n<8) {
			return spawnSystem.Circle;
		}
		else if(n==9) {
			return spawnSystem.Spiral;
		}
		else if(n>9 && n<17) {
			n = r.nextInt(4);
			if(n==0) {
				return spawnSystem.LineFromEast;
			}
			if(n==1) {
				return spawnSystem.LineFromNorth;
			}
			if(n==2) {
				return spawnSystem.LineFromWest;
			}
			if(n==3) {
				return spawnSystem.LineFromSouth;
			}
		}
		else {
			return spawnSystem.FullRandom;
		}
		return spawnSystem.FullRandom;
	}
	
	static void sendSurvivalWave(double dwaveNumber) {
		int waveNumber = (int)dwaveNumber;
		int numCases = 7;
		int x = 0;
		int dist = 0;
		Integer randomNum = getMyRandom(r.nextInt(numCases),numCases-1);
		Wave myWave = new Wave();
		Wave.setCurrentWave(myWave);
		HashMap<String, UnitPattern> unitMap = new HashMap<String, UnitPattern>();
		ArrayList<GeneratorInfo> genInfo = new ArrayList<GeneratorInfo>();
		Wave.setWaitTime(1500);
		if(waveNumber%2 == 0) {
			x=0;
			dist=100;
			while(x < waveNumber+1) { 
				genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(waveNumber*2+x+1)/3+1,50),randomWave(),r.nextInt(2), dist));
				genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(r.nextInt(waveNumber/4+x+1),50),randomWave(),r.nextInt(2), dist));
				genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(waveNumber/3+x+1),50),randomWave(),0, dist));
				genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/6+1),50),randomWave(),r.nextInt(2), (int)2*dist+100));
				genInfo.add(new GeneratorInfo("Healer", cap(r.nextInt(waveNumber/25+1),50),spawnSystem.FullRandom,r.nextInt(2), dist));
				genInfo.add(new GeneratorInfo("FullHealer", cap(r.nextInt(waveNumber/50+1),50),spawnSystem.FullRandom,r.nextInt(2), dist));
				if(waveNumber >= 10) {
					genInfo.add(new GeneratorInfo("Splitter Medium", cap(r.nextInt(waveNumber/6+2),6), randomWave(), r.nextInt(1),dist+r.nextInt(15)));
				}
				if(waveNumber >= 20) {
					genInfo.add(new GeneratorInfo("Splitter Big", cap(r.nextInt(waveNumber/6+2),6), randomWave(), r.nextInt(1), (int)(1.2f*dist)+r.nextInt(15)));
				}
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
			Log.d("Go",randomNum+"");
		switch(randomNum) {		
			// Explosive Circle Wave, probably most satisfying wave.
			case 0:
				genInfo.add(new GeneratorInfo("Asteroid", cap(r.nextInt(waveNumber*2+1)+1,50),spawnSystem.FullRandom));
				x = 0;
				dist = 0;
				while(x < waveNumber+1) { 
					dist = dist + 350;
					genInfo.add(new GeneratorInfo("Fire Asteroid", cap(r.nextInt(waveNumber*2+1),50),spawnSystem.Circle,dist));
					genInfo.add(new GeneratorInfo("Fire Asteroid", cap(r.nextInt(waveNumber*2+1),50),spawnSystem.Circle,dist));
					x = x+10;
				}
				genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/5+1),50),spawnSystem.FullRandom));
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
					genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(x,50),spawnSystem.Spiral));
					genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/6+1)+1,50),spawnSystem.FullRandom,0,2*dist+100));
					x = x/2;
				}
			break;
			
			// Fire or ice circle with top and bottom bombardment.
			case 2:
				x = 0;
				dist = 0;
				while(x < waveNumber+1) { 
					dist = dist + 350;
					genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(waveNumber+1),50),spawnSystem.Circle, r.nextInt(2)-1, dist));
					genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/7+1),50),spawnSystem.FullRandom,r.nextInt(2)-1,2*dist+100));
					x = x+5;
				}
				genInfo.add(new GeneratorInfo("Fire Asteroid", cap(waveNumber+1,50),spawnSystem.LineFromNorth,r.nextInt(2)));
				genInfo.add(new GeneratorInfo("Fire Asteroid", cap(waveNumber+1,50),spawnSystem.LineFromSouth,r.nextInt(2)));	
				genInfo.add(new GeneratorInfo("Fire Asteroid", cap(waveNumber+1,50),randomWave(),r.nextInt(2)));	
			break;
			
			// Four sides spiraling inward. 
			case 3:
				x=0;
				dist=200;
				while(x < waveNumber+1) { 
					genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(2+x/5),50),randomWave(),r.nextInt(2), (int)2*dist+100));
					genInfo.add(new GeneratorInfo("Healer", cap(r.nextInt(waveNumber/25+1),50),spawnSystem.FullRandom,r.nextInt(2), dist));
					genInfo.add(new GeneratorInfo("FullHealer", cap(r.nextInt(waveNumber/50+1),50),spawnSystem.FullRandom,r.nextInt(2), dist));
					if(300 - waveNumber<100) {
						dist += 100;
					}
					else {
						dist += 300 - 2*waveNumber;
					}
					x = x + 5;
				}
				genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(waveNumber+1,50),spawnSystem.LineFromNorth,1));
				genInfo.add(new GeneratorInfo(morethanlikelyice(), cap(waveNumber+1,50),spawnSystem.LineFromSouth,1));
				genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(waveNumber+1,50),spawnSystem.LineFromEast,1));
				genInfo.add(new GeneratorInfo(fireorice(), cap(waveNumber+1,50),spawnSystem.LineFromWest,1));
			break;
			
			// Mechanical Wave
			case 4:
				x=0;
				int n = 0;
				dist=200;
				while(x < Math.ceil(waveNumber/5)) { 
					if(n%2 == 0) {
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/2+1)+1,50),spawnSystem.LineFromNorth,r.nextInt(2),2*dist));
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/2+1)+1,50),spawnSystem.LineFromSouth,r.nextInt(2),2*dist+100));
							if(r.nextInt(2) == 1) {
								genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(r.nextInt(waveNumber/2+1),50),spawnSystem.LineFromWest,2,dist));
							}
							else { 
								genInfo.add(new GeneratorInfo(morethanlikelyice(), cap(r.nextInt(waveNumber/2+1),50),spawnSystem.LineFromEast,2,dist));
							}
					}
					else {
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/2+1)+1,50),spawnSystem.LineFromEast,r.nextInt(2),2*dist));
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/2+1)+1,50),spawnSystem.LineFromWest,r.nextInt(2),2*dist+100));
							if(r.nextInt(2) == 1) {
								genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(r.nextInt(waveNumber/2+1),50),spawnSystem.LineFromNorth,2,dist));
							}
							else { 
								genInfo.add(new GeneratorInfo(morethanlikelyice(),cap(r.nextInt(waveNumber/2+1),50),spawnSystem.LineFromSouth,2,dist));
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
					genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(waveNumber*2+x+1)/3+1,50),spawnSystem.Circle,r.nextInt(2), dist));
					genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(r.nextInt(waveNumber*10+x+1),50)/4+1,spawnSystem.Circle,r.nextInt(2), dist+150));
					genInfo.add(new GeneratorInfo(morethanlikelyice(), cap(r.nextInt(waveNumber/3+x/5+1),50),spawnSystem.FullRandom,r.nextInt(4), dist));
					genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(2+x/5),50),spawnSystem.FullRandom,r.nextInt(2), (int)2*dist+100));
					genInfo.add(new GeneratorInfo("Healer", cap(r.nextInt(waveNumber/25+1),50),spawnSystem.FullRandom,r.nextInt(2), dist));
					genInfo.add(new GeneratorInfo("FullHealer", cap(r.nextInt(waveNumber/50+1),50),spawnSystem.FullRandom,r.nextInt(2), dist));
					if(300 - waveNumber<100) {
						dist += 100;
					}
					else {
						dist += 300 - 2*waveNumber;
					}
					x = x + 5;
				}
			break;
			// Spiral wave ice!
			case 6:
				x = waveNumber+ 2*r.nextInt(waveNumber+1) + 1;
				dist = 200;
				while(x > 0) {
					if(300 - waveNumber<100) {
						dist += 100;
					}
					else {
						dist += 300 - 2*waveNumber;
					}
					genInfo.add(new GeneratorInfo(morethanlikelyice(), cap(x,50),spawnSystem.Spiral));
					genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/6+1)+1,50),spawnSystem.FullRandom,0,2*dist+100));
					x = x/2;
				}
			break;
			}
		}
		
		Wave.setCurrentWave(Wave.waveGenerator.generateWave(genInfo));
	}
}