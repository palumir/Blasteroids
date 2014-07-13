package com.DJG.waves;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
			if(x<0) {
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
	
	static void sendSurvivalWave(double dwaveNumber) {
		int waveNumber = (int)dwaveNumber;
		int numCases = 6;
		int x = 0;
		int dist = 0;
		Integer randomNum = getMyRandom(r.nextInt(numCases),numCases-1);
		Wave myWave = new Wave();
		Wave.setCurrentWave(myWave);
		HashMap<String, UnitPattern> unitMap = new HashMap<String, UnitPattern>();
		ArrayList<GeneratorInfo> genInfo = new ArrayList<GeneratorInfo>();
		Wave.setWaitTime(1500);
		/*if(waveNumber%2 == 0) {
			x=0;
			dist=100;
			if(waveNumber > 10) {
				genInfo.add(new GeneratorInfo("Splitter Big", cap(r.nextInt(waveNumber/12+1),2), spawnSystem.FullRandom, 0, r.nextInt(waveNumber*100)));
			}
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
		}*/
		
		x=0;
		dist=200;
		while(x < waveNumber+1) { 
			genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(waveNumber*2+x+1)/3+1,100),spawnSystem.Circle,r.nextInt(2), dist));
			genInfo.add(new GeneratorInfo("Fire Asteroid", cap(r.nextInt(waveNumber*10+x+1),100)/4+1,spawnSystem.Circle,r.nextInt(2), dist+150));
			genInfo.add(new GeneratorInfo("Ice Asteroid", cap(r.nextInt(waveNumber/3+x/5+1),100),spawnSystem.FullRandom,r.nextInt(4), dist));
			genInfo.add(new GeneratorInfo("Splitter Medium", cap(r.nextInt(2+x/5),100),spawnSystem.FullRandom,r.nextInt(2), (int)dist/2+100));
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
		
		Wave.setCurrentWave(Wave.waveGenerator.generateWave(genInfo));
	}
}