package com.DJG.generators;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.util.Log;

import com.DJG.fd.*;
import com.DJG.generators.GeneratorInfo.unitOrder;
import com.DJG.generators.GeneratorInfo.spawnSystem;
import com.DJG.generators.GeneratorInfo;
import com.DJG.unit.Unit;
import com.DJG.fd.DisplayMessageActivity;


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
	
	public WaveGenerator(){
		 screenWidth = DisplayMessageActivity.getScreenWidth();
		 screenHeight = DisplayMessageActivity.getScreenHeight();
	}

	public Wave generateWave(ArrayList<GeneratorInfo> genInfo){
		return generateWave(genInfo, unitOrder.Any);
	}
	
	public Wave generateWave(ArrayList<GeneratorInfo> genInfo, unitOrder order){
		Wave w = new Wave();
		
		for(GeneratorInfo g : genInfo){
			spawnSystem s = g.spawn;
			XY xy;
			switch(s){
			case FullRandom:
				for (int i = 0; i<g.unitNumbers; i++){
					xy = getRandomXY();
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y));
				}
				break;
			case Cardinal:
				for(int i = 0; i<g.unitNumbers; i+=4){
					 xy = northLine();
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y));
					 xy = eastLine();
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y));
					 xy = southLine();
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y));
					 xy = westLine();
					 w.add(new Unit("Any Name",g.unitType,xy.x,xy.y));
				}
				break;
			case LineFromNorth:
				for (int i = 0; i<g.unitNumbers; i++){
					xy = northLine();
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y));
				}
				break;
			case LineFromEast:
				for (int i = 0; i<g.unitNumbers; i++){
						xy = eastLine();
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y));
				}
				break;
			case LineFromSouth:
				for (int i = 0; i<g.unitNumbers; i++){
					xy = southLine();
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y));
				}
				break;
			case LineFromWest:
				for (int i = 0; i<g.unitNumbers; i++){
					xy = westLine();
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y));
				}
				break;
			default:
				for (int i = 0; i<g.unitNumbers; i++){
					 xy = getRandomXY();
					w.add(new Unit("Any Name",g.unitType,xy.x,xy.y));
				}
				break;
			}
		}
		
		return w;
	}
	
	private XY northLine(){
		int x = screenWidth/2;
		int y = 150 - 100*northTracker;
		northTracker++;
		XY xy = new XY(x,y);
		return xy;
	}
	private XY eastLine(){
		int x = screenWidth + 100*eastTracker;
		int y =  screenHeight/2 +1;
		eastTracker++;
		return new XY(x,y);
	}
	private XY southLine(){
		int x = screenWidth/2;
		int y = -150 + screenHeight + 100*southTracker;
		southTracker++;
		return new  XY(x,y);
	}
	private XY westLine(){
		int x = 0 - 100*westTracker;
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
}
