package com.DJG.abilities;

public class FireFingers {
	// General ability attributes.
	private long startTime;
	private int duration = 3000;
	
	public FireFingers(int newDuration) {
		startTime = System.currentTimeMillis();
		duration = newDuration;
	}
}