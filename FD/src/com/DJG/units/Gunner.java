package com.DJG.units;

import com.DJG.fd.GameActivity;
import com.DJG.waves.Wave;

public class Gunner {
	
	private static boolean gunnerRange(Unit u) {
		int height = GameActivity.getScreenHeight();
		int width = GameActivity.getScreenWidth();
		float x = u.getX();
		float y = u.getY();
		int delta = width/4;
		return (x >= 0 + delta &&
				 x <= width - delta &&
				 y >= 0 + delta &&
				 y <= height - delta);
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