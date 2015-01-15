package com.AIG.units;

import com.AIG.blasteroids.GameActivity;
import com.AIG.waves.Wave;

public class Gunner {
	
	private static boolean gunnerRange(Unit u) {
		int height = GameActivity.getScreenHeight();
		int width = GameActivity.getScreenWidth();
		float x = u.getX();
		float y = u.getY();
		float yDistance = (height/2 - y);
		float xDistance = (width/2 - x);
		float distanceXY = (float) Math.sqrt(yDistance * yDistance
				+ xDistance * xDistance);
		int delta = ((int) width/2 - 20) - GameActivity.getR().nextInt(50);
		return (distanceXY <= delta);
	}
	
	public static void updateGunner(Unit u) {
		if(gunnerRange(u)) {
			u.setInGunnerRange(true);
		}
		if(u.isInGunnerRange()) {
			if(u.getMoveSpeed() != 0) {
				u.setMoveSpeed(0);
			}
			if(GameActivity.getGameTime() - u.getLastAttackTime() > u.getAttackSpeed()) {
				u.setLastAttackTime(GameActivity.getGameTime());
				Unit bullet = new Unit("Any name", "Gunner Bullet", u.getX(), u.getY(), 0);
				Wave.addToCurrentWave(bullet);
				Wave.currentWaveAttackCastle();	
			}
		}
	}
}