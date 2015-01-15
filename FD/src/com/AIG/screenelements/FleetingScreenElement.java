package com.AIG.screenelements;

import java.util.ArrayList;

import com.AIG.blasteroids.GameActivity;

public class FleetingScreenElement extends ScreenElement {
	
	private int duration = 1500;
	private double creationTime;
	private static ArrayList<FleetingScreenElement> fleetingElements = new ArrayList<FleetingScreenElement>();
	public static Object lock = new Object();
	
	public FleetingScreenElement(
			String newText,
			float xSpawn,
			float ySpawn,
			String newActivity
			) {
		super("Text",newText,xSpawn,ySpawn,newActivity);
		creationTime = GameActivity.getGameTime();
		fleetingElements.add(this);
	}
	
	public static void updateFleetingScreenElements() {
		synchronized(lock) {
		for(int i = 0; i < fleetingElements.size(); i++) {
			FleetingScreenElement s = fleetingElements.get(i);
			if(GameActivity.getGameTime() - s.creationTime > s.duration) {
				killFleetingScreenElement(s);
			}
			else {
				s.y++;
			}
		}
		}
	}
	
	public static void killFleetingScreenElement(ScreenElement u) {
		synchronized(lock) {
		if(fleetingElements.size()!=0){
				int foundScreenElement = 0;
				for(int j = 0; j < fleetingElements.size(); j++) {
					FleetingScreenElement v =fleetingElements.get(j);
					if(u == v) {
						break;
					}
					foundScreenElement++;
				}
				if(foundScreenElement < fleetingElements.size()) {
					ScreenElement.killScreenElement(fleetingElements.get(foundScreenElement));
					fleetingElements.remove(foundScreenElement);
			}
		}
	}
	}
	
}