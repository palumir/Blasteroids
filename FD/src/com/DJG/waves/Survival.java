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
	
	public static ArrayList<Integer> spawnsUsed = new ArrayList<Integer>();

	static GeneratorInfo.spawnSystem getUniqueLine() {
		int x = r.nextInt(4);
		while(spawnsUsed.contains(x)) {
			x++;
			if(x>3) x = 0;
		}
		spawnsUsed.add(x);
		if(spawnsUsed.size()==4) spawnsUsed.clear();
		if(x==0) return spawnSystem.LineFromNorth;
		if(x==1) return spawnSystem.LineFromEast;
		if(x==2) return spawnSystem.LineFromSouth;
		if(x==3) return spawnSystem.LineFromWest;
		return spawnSystem.LineFromWest;
	}
	
	static int cap(int num, int cap) {
		if(num > cap) {
			return cap;
		}
		return num;
	}
	
	static int cap(int num, int min, int cap) {
		if(num < min) {
			return min;
		}
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
	
	static int interestingSpinCat() {
		int ran = r.nextInt(3);
		if(ran==0) {
			return 0;
		}
		if(ran==1) {
			return 4;
		}
		if(Wave.getCurrentWaveNumber()>10) {
			return 10;
		}
		return r.nextInt(3);
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
	
	static void sendSurvivalWave(double ddifficulty) {
		
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
		int difficulty = (int)ddifficulty;
		int x = 0;
		int dist = 0;
		int dist2 = 0;
		Wave myWave = new Wave();
		Wave.setCurrentWave(myWave);
		HashMap<String, UnitPattern> unitMap = new HashMap<String, UnitPattern>();
		GenInfo genInfo = new GenInfo();
		int numCases = 11;
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
					genInfo.add(new GeneratorInfo("Asteroid", cap(r.nextInt(difficulty*2+1)+1,difficulty,25),spawnSystem.FullRandom));
					x = 0;
					dist = 0;
					while(x < cap(difficulty+1,10)) { 
						dist = dist + 350;
						genInfo.add(new GeneratorInfo("Fire Asteroid", cap(r.nextInt(difficulty*2+1),difficulty,25),spawnSystem.Circle,interestingSpin(),dist));
						genInfo.add(new GeneratorInfo("Fire Asteroid", cap(r.nextInt(difficulty*2+1),difficulty,25),spawnSystem.Circle,interestingSpin(),dist));
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(difficulty/14+1),25),randomWave(), interestingSpinCat(), dist*2));
						x = x+10;
					}
				break;
		
				// Spiral wave!
				case 1:
					x = difficulty+ 2*r.nextInt(difficulty+1) + 1;
					dist = 200;
					while(x > 0) {
						if(300 - difficulty<100) {
							dist += 100;
						}
						else {
							dist += 300 - 2*difficulty;
						}
						if(difficulty >= 10) {
							genInfo.add(new GeneratorInfo("Splitter Medium", cap(r.nextInt(difficulty/8+2),6), randomWave(),interestingSpin(),dist+r.nextInt(15)));
						}
						genInfo.add(new GeneratorInfo(fireorice(), cap(x,difficulty,25),spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(difficulty/3+1)+1,25),randomWave(),interestingSpinCat(),(int)1.25*dist+100));
						x = x/2;
					}
				break;
				
				// Fire or ice circle with top and bottom bombardment.
				case 2:
					x = 0;
					dist = 0;
					while(x < cap(difficulty+1,10)) { 
						dist = dist + 350;
						genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(difficulty+1),difficulty,25),spawnSystem.Circle, r.nextInt(2)-1, dist));
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(difficulty/3+1),25),randomWave(),r.nextInt(2)-1,(int)1.25*dist+100));
						x = x+5;
					}
					genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(difficulty+1,difficulty,25),spawnSystem.LineFromNorth,r.nextInt(2)));
					genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(difficulty+1,difficulty,25),spawnSystem.LineFromSouth,r.nextInt(2)));	
					genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(difficulty+1,25),randomWave(),r.nextInt(2)));	
				break;
				
				// Four sides spiraling inward. 
				case 3:
					x=0;
					dist=200;
					while(x < cap(difficulty+1,10)) { 
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(2+x/4),25),randomLine(),interestingSpin(), (int)1.25*dist+100));
						if(difficulty >= 6) {
							genInfo.add(new GeneratorInfo("Splitter Medium", cap(r.nextInt(difficulty/6+2),6), randomWave(), r.nextInt(1),dist+r.nextInt(15)));
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(2+x/4),25),randomLine(),interestingSpinCat(), (int)1.25*dist+100));
						}
						if(300 - difficulty<100) {
							dist += 100;
						}
						else {
							dist += 300 - 2*difficulty;
						}
						
						x = x + 5;
					}
					genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(difficulty+1,difficulty,25),spawnSystem.LineFromNorth,r.nextInt(3)));
					genInfo.add(new GeneratorInfo(morethanlikelyice(), cap(difficulty+1,difficulty,25),spawnSystem.LineFromSouth,r.nextInt(3)));
					genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(difficulty+1,difficulty,25),spawnSystem.LineFromEast,r.nextInt(3)));
					genInfo.add(new GeneratorInfo(fireorice(), cap(difficulty+1,difficulty,25),spawnSystem.LineFromWest,r.nextInt(3)));
				break;
				
				// Mechanical Wave
				case 4:
					x=0;
					int n = 0;
					dist=200;
					while(x < cap((int)Math.ceil(difficulty/5),10)) { 
						if(n%2 == 0) {
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(3*difficulty/4+1)+1,difficulty,25),spawnSystem.LineFromNorth,interestingSpinNotFast(),(int)1.25*dist));
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(3*difficulty/4+1)+1,difficulty,25),spawnSystem.LineFromSouth,interestingSpinNotFast(),(int)1.25*dist+100));
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(3*difficulty/4+1)+1,difficulty,25),spawnSystem.LineFromEast,interestingSpinNotFast(),dist+difficulty*2));
								if(r.nextInt(2) == 1) {
									genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(r.nextInt(difficulty/2+1),difficulty,25),spawnSystem.LineFromWest,2,2*dist/3));
								}
								else { 
									genInfo.add(new GeneratorInfo(morethanlikelyice(), cap(r.nextInt(difficulty/2+1),difficulty,25),spawnSystem.LineFromEast,2,2*dist/3));
								}
						}
						else {
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(3*difficulty/4+1)+1,difficulty,25),spawnSystem.LineFromEast,interestingSpinNotFast(),dist));
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(3*difficulty/4+1)+1,difficulty,25),spawnSystem.LineFromWest,interestingSpinNotFast(),dist+difficulty*2));
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(3*difficulty/4+1)+1,difficulty,25),spawnSystem.LineFromNorth,interestingSpinNotFast(),dist+difficulty*2));
								if(r.nextInt(2) == 1) {
									genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(r.nextInt(difficulty/2+1),difficulty,25),spawnSystem.LineFromNorth,2,2*dist/3));
								}
								else { 
									genInfo.add(new GeneratorInfo(morethanlikelyice(),cap(r.nextInt(difficulty/2+1),difficulty,25),spawnSystem.LineFromSouth,2,2*dist/3));
								}
						}
						if(300 - difficulty<100) {
							dist += 100;
						}
						else {
							dist += 300 - 2*difficulty;
						}
						x = x + 1;
					}
					
				break;
				
				// Circle wave
				case 5:
					x=0;
					dist=200;
					while(x < cap(difficulty+1,10)) { 
						genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(difficulty*2+x+1)/3+1,difficulty,25),spawnSystem.Circle,r.nextInt(2), dist));
						genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(r.nextInt(difficulty*10+x+1),difficulty*2)/4+1,spawnSystem.Circle,r.nextInt(2), dist+175));
						genInfo.add(new GeneratorInfo(morethanlikelyice(), cap(r.nextInt(difficulty/3+x/5+1),difficulty,25),randomWave(),r.nextInt(4), dist));
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(2+x/3),25),randomWave(),interestingSpinNotFast(), (int)1.25*dist+100));
						if(300 - difficulty<100) {
							dist += 100;
						}
						else {
							dist += 300 - 2*difficulty;
						}
						x = x + 5;
					}
				break;
				// Spiral wave ice!
				case 6:
					x = difficulty+ 2*r.nextInt(difficulty+1) + 1;
					dist = 200;
					while(x > 0) {
						if(300 - difficulty<100) {
							dist += 100;
						}
						else {
							dist += 300 - 2*difficulty;
						}
						if(difficulty >= 6) {
							genInfo.add(new GeneratorInfo("Splitter Medium", cap(r.nextInt(difficulty/8+2),6), randomWave(), interestingSpin(),dist+r.nextInt(15)));
						}
						genInfo.add(new GeneratorInfo(morethanlikelyice(), cap(x,difficulty,25),spawnSystem.Spiral));
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(difficulty/3+1)+1,25),randomWave(),interestingSpinNotFast(),(int)1.25*dist+100));
						x = x/2;
					}
				break;
				// Mechanical Wave 2
				case 7:
					x=0;
					n = 0;
					dist=200;
					while(x < cap((int)Math.ceil(difficulty/5),50)) { 
						if(n%2 == 0) {
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(difficulty/2+1)+1,difficulty,25),spawnSystem.LineFromNorth,r.nextInt(4),(int)1.25*dist));
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(difficulty/2+1)+1,difficulty,25),spawnSystem.LineFromSouth,r.nextInt(4),(int)1.25*dist+100));
							genInfo.add(new GeneratorInfo("Cat Gunner", cap(r.nextInt(difficulty/10+1)+1,25),randomWave(),0,dist+difficulty*2));
								if(r.nextInt(2) == 1) {
									genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(r.nextInt(difficulty/2+1),difficulty,25),spawnSystem.LineFromWest,2,2*dist/3));
								}
								else { 
									genInfo.add(new GeneratorInfo(morethanlikelyice(), cap(r.nextInt(difficulty/2+1),difficulty,25),spawnSystem.LineFromEast,2,2*dist/3));
								}
						}
						else {
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(difficulty/2+1)+1,difficulty,25),spawnSystem.LineFromEast,r.nextInt(4),dist));
							genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(difficulty/2+1)+1,difficulty,25),spawnSystem.LineFromWest,r.nextInt(4),dist+difficulty*2));
							genInfo.add(new GeneratorInfo("Cat Gunner", cap(r.nextInt(difficulty/10+1)+1,25),randomWave(),0,dist+difficulty*2));
								if(r.nextInt(2) == 1) {
									genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(r.nextInt(difficulty/2+1),difficulty,25),spawnSystem.LineFromNorth,2,2*dist/3));
								}
								else { 
									genInfo.add(new GeneratorInfo(morethanlikelyice(),cap(r.nextInt(difficulty/2+1),difficulty,25),spawnSystem.LineFromSouth,2,2*dist/3));
								}
						}
						if(300 - difficulty<100) {
							dist += 100;
						}
						else {
							dist += 300 - 2*difficulty;
						}
						x = x + 1;
					}
					
				break;
				// Ice/Fire BIG bombardment
				case 8:
					x=0;
					dist=height/2;
					dist2 = dist;
					while(x < difficulty+1) { 
						genInfo.add(new GeneratorInfo(fireorice(), 10,spawnSystem.Bombardment,interestingSpinNotFast(), dist));
						if(difficulty>=12) {
							int y = 0;
							while(y*20<=difficulty) {
								genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(difficulty/5+x+1),25),randomWave(),interestingSpinCat(), dist2));
								y++;
							}
						}
						if(300 - difficulty<100) {
							dist2 += 100;
						}
						else {
							dist2 += 300 - 2*difficulty;
						}
						x = x + 2;
					}
				break;
				// Splitter wave
				case 9:
					x=0;
					dist=height/2;
					while(x < cap(difficulty+2,11)) { 
						int which = r.nextInt(100);
						String go = "Splitter Medium";
						if(which==Math.ceil(difficulty/7)) {
							go = "Splitter Big";
						}
						genInfo.add(new GeneratorInfo(go, cap(r.nextInt(5),1,4),spawnSystem.Bombardment,interestingSpin(), dist));
						if(difficulty>=14) {
							genInfo.add(new GeneratorInfo("Splitter Big", cap(r.nextInt(3),25),spawnSystem.Bombardment,interestingSpinCat(), (int) (dist*1.25)));
						}
						x = x + 2;
					}
				break;
				// Ice/Fire/Normal Lines Asteroid Wave
				case 10:
					x=0;
					dist=height/2;
					while(x < cap(difficulty*4 + 6*4, 50*4)) { 
						genInfo.add(new GeneratorInfo("Asteroid", cap(r.nextInt(4),1,4),getUniqueLine(),0, dist));
						genInfo.add(new GeneratorInfo("Fire Asteroid", cap(r.nextInt(4),1,4),getUniqueLine(),0, dist));
						genInfo.add(new GeneratorInfo("Ice Asteroid", cap(r.nextInt(4),1,4),getUniqueLine(),0, dist));
						genInfo.add(new GeneratorInfo("Asteroid", cap(r.nextInt(4),1,4),getUniqueLine(),0, dist));
						x = x + 2;
					}
				break;
				// Very random long wave
				case 11:
					x=0;
					dist=100;
					while(x < cap(difficulty+1,10)) { 
						genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(difficulty*2+x+1)/3+1,25),randomWave(),r.nextInt(2), dist));
						genInfo.add(new GeneratorInfo(morethanlikelyfire(), cap(r.nextInt(difficulty*2/4+x+1),25),randomWave(),interestingSpin(), dist));
						genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(difficulty/3+x+1),25),randomWave(),0, dist));
						genInfo.add(new GeneratorInfo("Cat", cap(r.nextInt(difficulty/6+1),25),randomWave(),r.nextInt(2), (int)2*dist+100));
						if(difficulty>=15) {
							int y = 0;
							while(y*15<=difficulty) {
								genInfo.add(new GeneratorInfo(fireorice(), cap(r.nextInt(difficulty/3+x+1),25),randomWave(),interestingSpin(), dist));
								y++;
							}
						}
						if(difficulty >= 8) {
							genInfo.add(new GeneratorInfo("Splitter Medium", cap(r.nextInt(difficulty/6+2),6), randomWave(), r.nextInt(1),dist+r.nextInt(15)));
						}
						if(difficulty >= 8) {
							genInfo.add(new GeneratorInfo("Splitter Big", cap(r.nextInt(difficulty/8+2),6), randomWave(), r.nextInt(1), (int)(1.25f*dist)+r.nextInt(15)));
						}
						if(difficulty >= 5) {
							genInfo.add(new GeneratorInfo("Cat Gunner", cap(r.nextInt(difficulty/8+1),25),randomWave(),0, (int)2*dist+100));
						}
						if(300 - difficulty<100) {
							dist += 100;
						}
						else {
							dist += 300 - 2*difficulty;
						}
						x = x + 5;
					}
				break;
				}
		}
			Wave.setCurrentWave(Wave.waveGenerator.generateWave(genInfo));
	}
	
}