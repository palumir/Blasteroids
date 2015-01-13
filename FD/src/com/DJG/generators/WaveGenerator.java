package com.DJG.generators;
import java.util.ArrayList;
import java.util.Random;

import com.DJG.fd.GameActivity;
import com.DJG.generators.GeneratorInfo.spawnSystem;
import com.DJG.generators.GeneratorInfo.unitOrder;
import com.DJG.units.Unit;
import com.DJG.waves.Wave;


class XY {
	public int x;
	public int y;
	
	public XY(int xNew, int yNew) {
		y = yNew;
		x = xNew;
	}
}

public class WaveGenerator {
	public static double maxTimeOnWave;
	public static XY xy;
	public static float xySpeed;
	
	private static int screenWidth;// = DisplayMessageActivity.getScreenWidth();
	private static int screenHeight;// = DisplayMessageActivity.getScreenHeight();
	private static int xSpawn = -200;
	private static int ySpawn = -200;
	
	private static Random r = new Random();
	private int northTracker = 0;
	private int southTracker = 0;
	private int eastTracker = 0;
	private int westTracker = 0;
	private int spiralNumber = 30;
	private int circleRadius = 3;
	
	public WaveGenerator(){
		 maxTimeOnWave = 0;
		 screenWidth = GameActivity.getScreenWidth();
		 screenHeight = GameActivity.getScreenHeight();
	}

	public Wave generateWave(ArrayList<GeneratorInfo> genInfo){
		return generateWave(genInfo, unitOrder.Any);
	}
	
	public Wave generateWave(ArrayList<GeneratorInfo> genInfo, unitOrder order){
		reset();
		Wave w = new Wave();
		
		for(GeneratorInfo g : genInfo){
			int spinVal = g.spin;
			spawnSystem s = g.spawn;
			switch(s){
			case FullRandom:
				for (int i = 0; i<g.unitNumbers; i++){
					xy = getRandomXY(g.startingDifference);
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
				}
				break;
			case Spiral:
				for(int i =0; i<g.unitNumbers; i++){
					xy = spiralXY();
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
				}
				break;
			case Cardinal:
				for(int i = 0; i<g.unitNumbers; i+=4){
					 xy = northLine(g.startingDifference+GameActivity.getScreenWidth()/2);
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
					 xy = eastLine(g.startingDifference+GameActivity.getScreenWidth()/2);
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y,spinVal));
					 xy = southLine(g.startingDifference+GameActivity.getScreenWidth()/2);
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
					 xy = westLine(g.startingDifference+GameActivity.getScreenWidth()/2);
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
				}
				break;
			case SpinCardinal:
				for(int i = 0; i<g.unitNumbers; i+=4){
					 xy = northLine(g.startingDifference+GameActivity.getScreenWidth()/2);
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
					 xy = eastLine(g.startingDifference+GameActivity.getScreenWidth()/2);
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y,spinVal));
					 xy = southLine(g.startingDifference+GameActivity.getScreenWidth()/2);
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
					 xy = westLine(g.startingDifference+GameActivity.getScreenWidth()/2);
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
				}
				break;
			case Circle:
				buildCircle(w, g.unitNumbers, g.unitType, g.startingDifference, spinVal);
				break;
			case Bombardment:
				buildBombardment(w, g.unitNumbers, g.unitType, g.startingDifference+GameActivity.getScreenWidth()/2, spinVal);
				break;
			case LineFromNorth:
				for (int i = 0; i<g.unitNumbers; i++){
					xy = northLine(g.startingDifference+GameActivity.getScreenWidth()/2);
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
				}
				break;
			case LineFromEast:
				for (int i = 0; i<g.unitNumbers; i++){
					xy = eastLine(g.startingDifference+GameActivity.getScreenWidth()/2);
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
				}
				break;
			case LineFromSouth:
				for (int i = 0; i<g.unitNumbers; i++){
					xy = southLine(g.startingDifference+GameActivity.getScreenWidth()/2);
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y,spinVal));
				}
				break;
			case LineFromWest:
				for (int i = 0; i<g.unitNumbers; i++){
					xy = westLine(g.startingDifference+GameActivity.getScreenWidth()/2);
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y,spinVal));
				}
				break;
			default:
				for (int i = 0; i<g.unitNumbers; i++){
					 xy = getRandomXY(g.startingDifference);
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
				}
				break;
			}
			if(!w.isEmpty()) {
				float yDistance = (GameActivity.getScreenHeight()/2 - xy.y);
				float xDistance = (GameActivity.getScreenWidth()/2 - xy.x);
				float speed = (float) (w.get(w.size()-1).getMoveSpeed()*Wave.getSpeedFactor());
				float distanceXY = (float) Math.sqrt(yDistance * yDistance
						+ xDistance * xDistance) - GameActivity.getScreenWidth()/2;
				long timeToGetToEarth = (long) (distanceXY/speed)*10;
				long newTimeOnWave = GameActivity.getGameTime() + timeToGetToEarth;
				if(newTimeOnWave>maxTimeOnWave) { 
					maxTimeOnWave = newTimeOnWave;
				}
			}
		}
		
		return w;
	}
	
	
	public void reset(){
		northTracker = 0;
		southTracker = 0;
		eastTracker = 0;
		westTracker = 0;
		spiralNumber = 30;
		circleRadius = 0;
	}
	
	private XY spiralXY(){
		int x = screenWidth/2 + (int) (18 * spiralNumber *Math.cos(((double) spiralNumber)/3)); 
		int y = screenHeight/2  + (int) (18 * spiralNumber *Math.sin(((double) spiralNumber)/3));
		spiralNumber++;
		return new XY(x,y);
	}
	
	private XY northLine(int startingDifference){
		int x = screenWidth/2;
		int y = 150 - 75*northTracker - startingDifference;
		northTracker++;
		XY xy = new XY(x,y);
		return xy;
	}
	
	private XY eastLine(int startingDifference){
		int x = screenWidth + 75*eastTracker + startingDifference;
		int y =  screenHeight/2 +1;
		eastTracker++;
		return new XY(x,y);
	}
	
	private XY southLine(int startingDifference){
		int x = screenWidth/2;
		int y = -150 + screenHeight + 75*southTracker + startingDifference;
		southTracker++;
		return new  XY(x,y);
	}
	
	private XY westLine(int startingDifference){
		int x = 0 - 75*westTracker - startingDifference;
		int y = screenHeight/2;
		westTracker++;
		return new  XY(x,y);
	}
	
	private static XY getRandomXY(){
		return getRandomXY(0);
	}
	private static XY getRandomXY(int d) {
		// Get the height, width, and a new random number generator.
	    int radian = r.nextInt(360) + 1;
	    int radius = screenHeight/2 + 150 + r.nextInt(300);
	    xSpawn = (int) (screenWidth/2 + radius*Math.cos(Math.toRadians(radian))); 
	    ySpawn = (int) (screenHeight/2 + radius*Math.sin(Math.toRadians(radian)));
	    XY xy = new XY(xSpawn,ySpawn);
	    return xy;
	}
	
	public void buildCircle(Wave w, int n, String unitType, int startingDifference, int spinVal){
		int radius = circleRadius*150 + screenHeight/2 + startingDifference;
		double currentDegree = 0;
		double degreeChange = (double) 360/n;
		xy = new XY(0,0);
		for(int i = 0; i<n; i++){
			int x = (int) (screenWidth/2 + radius*Math.cos(Math.toRadians(currentDegree))); 
			int y = (int) (screenHeight/2 + radius*Math.sin(Math.toRadians(currentDegree)));
			xy.x = x;
			xy.y = y;
			currentDegree += degreeChange;
			w.add(new Unit("New name", unitType, x, y, spinVal));
		}
		circleRadius++;
	}
	
	public void buildBombardment(Wave w, int n, String unitType, int startingDifference, int spinVal){
		int radius = circleRadius*100 + startingDifference;
		xy = new XY(0,0);
		double currentDegree = 0;
		double degreeChange = (double) 360/n;
		for(int i = 0; i<n; i++){
			int x = (int) (screenWidth/2 + radius*Math.cos(Math.toRadians(currentDegree))); 
			int y = (int) (screenHeight/2 + radius*Math.sin(Math.toRadians(currentDegree)));
			xy.x = x;
			xy.y = y;
			currentDegree += degreeChange;
			w.add(new Unit("New name", unitType, x, y, spinVal));
		}
		circleRadius++;
	}
	
	
}
