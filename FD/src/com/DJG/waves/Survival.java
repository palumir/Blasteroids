package com.DJG.waves;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.util.Log;

import com.DJG.fd.GameActivity;
import com.DJG.generators.GeneratorInfo;
import com.DJG.generators.GeneratorInfo.spawnSystem;
import com.DJG.secrets.Secret;

public class Survival {
	
	public static Random r = new Random();

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
	
	static int interestingSpin() {
		int ran = r.nextInt(2);
		if(ran==0) {
			return 0;
		}
		if(ran==1) {
			return 10;
		}
		return 10;
	}
	
	static int interestingSpinNotFast() {
		int ran = r.nextInt(2);
		if(ran==0) {
			return 0;
		}
		if(ran==1) {
			return 4;
		}
		return 10;
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
	
	static String strictlyfireorice() {
		int between = r.nextInt(1);
		if(between <= 0) {
			return "Ice Asteroid";
		}
		else {
			return "Fire Asteroid";
		}
	}
	
	static GeneratorInfo.spawnSystem randomLine() {
			int n = r.nextInt(4);
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
		return spawnSystem.FullRandom;
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
		
		class GenInfo extends ArrayList<GeneratorInfo> {
			int maxTime = 0;
			
			public GenInfo() {
				
			}
			
			public boolean add(GeneratorInfo g) {
				
				return super.add(g);
			}
		}
		
		int height = GameActivity.getScreenHeight();
		int width = GameActivity.getScreenWidth();
		int waveNumber = (int)dwaveNumber;
		int x = 0;
		int dist = 0;
		Wave myWave = new Wave();
		Wave.setCurrentWave(myWave);
		HashMap<String, UnitPattern> unitMap = new HashMap<String, UnitPattern>();
		GenInfo genInfo = new GenInfo();
		int numCases = 9;
		int randomNum = getMyRandom(r.nextInt(numCases), numCases);
		GameActivity.debug = randomNum+"";
		if(Secret.pieActive) {
			genInfo.add(new GeneratorInfo("Pie", 5,spawnSystem.Circle,0));
			genInfo.add(new GeneratorInfo("Pie", 5,spawnSystem.Circle,10));
			genInfo.add(new GeneratorInfo("Pie", 5,spawnSystem.Circle,0));
			genInfo.add(new GeneratorInfo("Pie", 5,spawnSystem.Circle,10));
			genInfo.add(new GeneratorInfo("Pie", 5,spawnSystem.Circle,0));
			Secret.pieActive = false;
		}
		else {
			switch(randomNum) {		
				// Explosive Circle Wave, probably most satisfying wave.
				case 0:
					genInfo.add(new GeneratorInfo("Asteroid", cap(r.nextInt(waveNumber*2+1)+1,75),spawnSystem.FullRandom));
					x = 0;
					dist = 0;
					while(x < waveNumber+1) { 
						dist = dist + 350;
						genInfo.add(new GeneratorInfo("Fire Asteroid", cap(r.nextInt(waveNumber*2+1),75),spawnSystem.Circle,interestingSpin(),dist));
						genInfo.add(new GeneratorInfo("Fire Asteroid", cap(r.nextInt(waveNumber*2+1),75),spawnSystem.Circle,interestingSpin(),dist));
						x = x+10;
					}
					genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/5+1),75),spawnSystem.FullRandom));
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
						if(waveNumber >= 10) {
							genInfo.add(new GeneratorInfo("Splitter Medium", cap(r.nextInt(waveNumber/16+2),6), randomWave(),interestingSpin(),dist+r.nextInt(15)));
						}
						genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(x,75),spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/3+1)+1,75),randomWave(),interestingSpin(),(int)1.25*dist+100));
						x = x/2;
					}
				break;
				
				// Fire or ice circle with top and bottom bombardment.
				case 2:
					x = 0;
					dist = 0;
					while(x < waveNumber+1) { 
						dist = dist + 350;
						genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(waveNumber+1),75),spawnSystem.Circle, r.nextInt(2)-1, dist));
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/3+1),75),randomWave(),r.nextInt(2)-1,(int)1.25*dist+100));
						x = x+5;
					}
					genInfo.add(new GeneratorInfo("Fire Asteroid", cap(waveNumber+1,75),spawnSystem.LineFromNorth,r.nextInt(2)));
					genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(waveNumber+1,75),spawnSystem.LineFromSouth,r.nextInt(2)));	
					genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(waveNumber+1,75),randomWave(),r.nextInt(2)));	
				break;
				
				// Four sides spiraling inward. 
				case 3:
					x=0;
					dist=200;
					while(x < waveNumber+1) { 
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(2+x/4),75),randomLine(),interestingSpin(), (int)1.25*dist+100));
						if(waveNumber >= 12) {
							genInfo.add(new GeneratorInfo("Splitter Medium", cap(r.nextInt(waveNumber/12+2),6), randomWave(), r.nextInt(1),dist+r.nextInt(15)));
						}
						if(300 - waveNumber<100) {
							dist += 100;
						}
						else {
							dist += 300 - 2*waveNumber;
						}
						
						x = x + 5;
					}
					genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(waveNumber+1,75),spawnSystem.LineFromNorth,r.nextInt(3)));
					genInfo.add(new GeneratorInfo(morethanlikelyice(), cap(waveNumber+1,75),spawnSystem.LineFromSouth,r.nextInt(3)));
					genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(waveNumber+1,75),spawnSystem.LineFromEast,r.nextInt(3)));
					genInfo.add(new GeneratorInfo(fireorice(), cap(waveNumber+1,75),spawnSystem.LineFromWest,r.nextInt(3)));
				break;
				
				// Mechanical Wave
				case 4:
					x=0;
					int n = 0;
					dist=200;
					while(x < Math.ceil(waveNumber/5)) { 
						if(n%2 == 0) {
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(3*waveNumber/4+1)+1,75),spawnSystem.LineFromNorth,interestingSpinNotFast(),(int)1.25*dist));
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(3*waveNumber/4+1)+1,75),spawnSystem.LineFromSouth,interestingSpinNotFast(),(int)1.25*dist+100));
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(3*waveNumber/4+1)+1,75),spawnSystem.LineFromEast,interestingSpinNotFast(),dist+75));
								if(r.nextInt(2) == 1) {
									genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(r.nextInt(waveNumber/2+1),75),spawnSystem.LineFromWest,2,dist));
								}
								else { 
									genInfo.add(new GeneratorInfo(morethanlikelyice(), cap(r.nextInt(waveNumber/2+1),75),spawnSystem.LineFromEast,2,dist));
								}
						}
						else {
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(3*waveNumber/4+1)+1,75),spawnSystem.LineFromEast,interestingSpinNotFast(),dist));
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(3*waveNumber/4+1)+1,75),spawnSystem.LineFromWest,interestingSpinNotFast(),dist+75));
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(3*waveNumber/4+1)+1,75),spawnSystem.LineFromNorth,interestingSpinNotFast(),dist+75));
								if(r.nextInt(2) == 1) {
									genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(r.nextInt(waveNumber/2+1),75),spawnSystem.LineFromNorth,2,dist));
								}
								else { 
									genInfo.add(new GeneratorInfo(morethanlikelyice(),cap(r.nextInt(waveNumber/2+1),75),spawnSystem.LineFromSouth,2,dist));
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
				
				// Circle wave
				case 5:
					x=0;
					dist=200;
					while(x < waveNumber+1) { 
						genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(waveNumber*2+x+1)/3+1,75),spawnSystem.Circle,r.nextInt(2), dist));
						genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(r.nextInt(waveNumber*10+x+1),75)/4+1,spawnSystem.Circle,r.nextInt(2), dist+175));
						genInfo.add(new GeneratorInfo(morethanlikelyice(), cap(r.nextInt(waveNumber/3+x/5+1),75),spawnSystem.FullRandom,r.nextInt(4), dist));
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(2+x/3),75),randomWave(),interestingSpinNotFast(), (int)1.25*dist+100));
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
						if(waveNumber >= 12) {
							genInfo.add(new GeneratorInfo("Splitter Medium", cap(r.nextInt(waveNumber/16+2),6), randomWave(), interestingSpin(),dist+r.nextInt(15)));
						}
						genInfo.add(new GeneratorInfo(morethanlikelyice(), cap(x,75),spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/3+1)+1,75),randomWave(),interestingSpinNotFast(),(int)1.25*dist+100));
						x = x/2;
					}
				break;
				// Mechanical Wave 2
				case 7:
					x=0;
					n = 0;
					dist=200;
					while(x < Math.ceil(waveNumber/5)) { 
						if(n%2 == 0) {
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/2+1)+1,75),spawnSystem.LineFromNorth,r.nextInt(4),(int)1.25*dist));
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/2+1)+1,75),spawnSystem.LineFromSouth,r.nextInt(4),(int)1.25*dist+100));
							genInfo.add(new GeneratorInfo("Cat Gunner", cap(r.nextInt(waveNumber/8+1)+1,75),spawnSystem.Spiral,0,dist+75));
								if(r.nextInt(2) == 1) {
									genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(r.nextInt(waveNumber/2+1),75),spawnSystem.LineFromWest,2,dist));
								}
								else { 
									genInfo.add(new GeneratorInfo(morethanlikelyice(), cap(r.nextInt(waveNumber/2+1),75),spawnSystem.LineFromEast,2,dist));
								}
						}
						else {
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/2+1)+1,75),spawnSystem.LineFromEast,r.nextInt(4),dist));
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/2+1)+1,75),spawnSystem.LineFromWest,r.nextInt(4),dist+75));
							genInfo.add(new GeneratorInfo("Cat Gunner", cap(r.nextInt(waveNumber/8+1)+1,75),spawnSystem.FullRandom,0,dist+75));
								if(r.nextInt(2) == 1) {
									genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(r.nextInt(waveNumber/2+1),75),spawnSystem.LineFromNorth,2,dist));
								}
								else { 
									genInfo.add(new GeneratorInfo(morethanlikelyice(),cap(r.nextInt(waveNumber/2+1),75),spawnSystem.LineFromSouth,2,dist));
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
				// Ice/Fire BIG bombardment
				case 8:
					x=0;
					dist=height/2;
					int dist2 = dist;
					while(x < waveNumber+1) { 
						genInfo.add(new GeneratorInfo(fireorice(), 10,spawnSystem.Bombardment,interestingSpin(), dist));
						if(waveNumber>=5) {
							int y = 0;
							while(y*20<=waveNumber) {
								genInfo.add(new GeneratorInfo("Asteroid", cap(r.nextInt(waveNumber/3+x+1),75),randomWave(),interestingSpin(), dist2));
								y++;
							}
						}
						if(300 - waveNumber<100) {
							dist2 += 100;
						}
						else {
							dist2 += 300 - 2*waveNumber;
						}
						x = x + 2;
					}
				break;
				// Very random long wave
				case 9:
					x=0;
					dist=100;
					while(x < waveNumber+1) { 
						genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(waveNumber*2+x+1)/3+1,75),randomWave(),r.nextInt(2), dist));
						genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(r.nextInt(waveNumber*2/4+x+1),75),randomWave(),interestingSpin(), dist));
						genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(waveNumber/3+x+1),75),randomWave(),0, dist));
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(waveNumber/6+1),75),randomWave(),r.nextInt(2), (int)2*dist+100));
						if(waveNumber>=15) {
							int y = 0;
							while(y*15<=waveNumber) {
								genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(waveNumber/3+x+1),75),randomWave(),interestingSpin(), dist));
								y++;
							}
						}
						if(waveNumber >= 8) {
							genInfo.add(new GeneratorInfo("Splitter Medium", cap(r.nextInt(waveNumber/8+2),6), randomWave(), r.nextInt(1),dist+r.nextInt(15)));
						}
						if(waveNumber >= 18) {
							genInfo.add(new GeneratorInfo("Splitter Big", cap(r.nextInt(waveNumber/24+2),6), randomWave(), r.nextInt(1), (int)(1.25f*dist)+r.nextInt(15)));
						}
						if(waveNumber >= 5) {
							genInfo.add(new GeneratorInfo("Cat Gunner", cap(r.nextInt(waveNumber/8+1),75),randomWave(),0, (int)2*dist+100));
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
				}
		}
			Wave.setCurrentWave(Wave.waveGenerator.generateWave(genInfo));
	}
	
}