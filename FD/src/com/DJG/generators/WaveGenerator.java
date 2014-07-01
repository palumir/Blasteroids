package com.DJG.generators;
import java.util.ArrayList;
import java.util.Random;

import android.util.Log;

import com.DJG.fd.DisplayMessageActivity;
import com.DJG.generators.GeneratorInfo.spawnSystem;
import com.DJG.generators.GeneratorInfo.unitOrder;
import com.DJG.units.Unit;
import com.DJG.units.UnitSpawner;
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
	private int circleRadius = 0;
	
	public WaveGenerator(){
		 screenWidth = DisplayMessageActivity.getScreenWidth();
		 screenHeight = DisplayMessageActivity.getScreenHeight();
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
			XY xy;
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
					 xy = northLine(g.startingDifference);
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
					 xy = eastLine(g.startingDifference);
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y,spinVal));
					 xy = southLine(g.startingDifference);
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
					 xy = westLine(g.startingDifference);
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
				}
				break;
			case SpinCardinal:
				for(int i = 0; i<g.unitNumbers; i+=4){
					 xy = northLine(g.startingDifference);
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
					 xy = eastLine(g.startingDifference);
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y,spinVal));
					 xy = southLine(g.startingDifference);
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
					 xy = westLine(g.startingDifference);
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
				}
				break;
			case Circle:
				buildCircle(w, g.unitNumbers, g.unitType, g.startingDifference);
				break;
			case LineFromNorth:
				for (int i = 0; i<g.unitNumbers; i++){
					xy = northLine(g.startingDifference);
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
				}
				break;
			case LineFromEast:
				for (int i = 0; i<g.unitNumbers; i++){
					xy = eastLine(g.startingDifference);
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
				}
				break;
			case LineFromSouth:
				for (int i = 0; i<g.unitNumbers; i++){
					xy = southLine(g.startingDifference);
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y,spinVal));
				}
				break;
			case LineFromWest:
				for (int i = 0; i<g.unitNumbers; i++){
					xy = westLine(g.startingDifference);
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y,spinVal));
				}
				break;
			case Spawner:
				for(int i=0; i<g.unitNumbers; i++){
					xy = new XY(0, screenHeight/2);
					w.add(new UnitSpawner("Any name", g.unitType, xy.x, xy.y, w));
				}
				break;
			default:
				for (int i = 0; i<g.unitNumbers; i++){
					 xy = getRandomXY(g.startingDifference);
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y, spinVal));
				}
				break;
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
		int x = screenWidth/2 + (int) (18 * spiralNumber *Math.cos(((double) spiralNumber)/3)) ; 
		int y = screenHeight/2  + (int) (18 * spiralNumber *Math.sin(((double) spiralNumber)/3)) ;
		spiralNumber++;
		return new XY(x,y);
	}
	
	private XY northLine(int startingDifference){
		int x = screenWidth/2;
		int y = 150 - 100*northTracker - startingDifference;
		northTracker++;
		XY xy = new XY(x,y);
		return xy;
	}
	private XY eastLine(int startingDifference){
		int x = screenWidth + 100*eastTracker + startingDifference;
		int y =  screenHeight/2 +1;
		eastTracker++;
		return new XY(x,y);
	}
	private XY southLine(int startingDifference){
		int x = screenWidth/2;
		int y = -150 + screenHeight + 100*southTracker + startingDifference;
		southTracker++;
		return new  XY(x,y);
	}
	private XY westLine(int startingDifference){
		int x = 0 - 100*westTracker - startingDifference;
		int y = screenHeight/2;
		westTracker++;
		return new  XY(x,y);
	}
	
	private static XY getRandomXY(){
		return getRandomXY(0);
	}
	private static XY getRandomXY(int d) {
		// Get the height, width, and a new random number generator.
	    int whatQuarter = r.nextInt(4) + 1;
	    
	    // ^ // NORTH // TOP
	    if(whatQuarter == 1) {
	    	xSpawn = r.nextInt(screenWidth);
	    	ySpawn = r.nextInt(300)*(-1)-50-d;
	    }
	    
	    // > // EAST // RIGHT
	    if(whatQuarter == 2) {
	    	xSpawn = screenWidth + r.nextInt(300) + 50+d;
	    	ySpawn = r.nextInt(screenHeight);
	    }
	    
	    // \/ // SOUTH // BOTTOM
	    if(whatQuarter == 3) {
	    	xSpawn = r.nextInt(screenWidth);
	    	ySpawn = screenHeight + r.nextInt(300) + 50 +d;
	    }
	    
	    // < // WEST // LEFT
	    if(whatQuarter == 4) {
	    	xSpawn = r.nextInt(300)*(-1)-50-d;
	    	ySpawn = r.nextInt(screenHeight);
	    }
	    XY xy = new XY(xSpawn,ySpawn);
	    return xy;
	}
	
	public void buildCircle(Wave w, int n, String unitType, int startingDifference){
		int radius = circleRadius*150 + 400 + startingDifference;
		double currentDegree = 0;
		double degreeChange = (double) 360/n;
		for(int i = 0; i<n; i++){
			int x = (int) (screenWidth/2 + radius*Math.cos(Math.toRadians(currentDegree))); 
			int y = (int) (screenHeight/2 + radius*Math.sin(Math.toRadians(currentDegree)));
			currentDegree += degreeChange;
			w.add(new Unit("New name", unitType, x, y, 10));
		}
		circleRadius++;
	}
	
	
}
