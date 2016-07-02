package com.AIG.abilities;

import java.util.Random;

import android.graphics.Color;

import com.AIG.earthDefense.GameActivity;
import com.AIG.screenelements.ScreenElement;
import com.AIG.units.Unit;
import com.AIG.units.UnitType;

public class Drop {
	private static Random r = new Random();
	
	public static void initAbilityDrops() {
  		for(int i = 0; i < Ability.getEquippedAbilities().size(); i++) {
  			Ability a = Ability.getEquippedAbilities().get(i);
  			UnitType.addUnitType(new UnitType(a.getType(),"Ability Drop",a.getRadius(),0, true, a.getBMP(), 1, 0));
  		}
	}
	
	public static void potentiallyDropItem(Unit u) {
		if(!u.getType().equals("Gunner Bullet")) {
			Ability abilityToDrop = null;
			
			// Drop a regular ability?
			if(getR().nextInt(20) == 1) {
				if(u.getMetaType() == "Unit") {
					abilityToDrop = Ability.getAbilityDrop("Normal");
				}
			}
			
			// Drop a special ability?
			if(getR().nextInt(100) == 1) {
				if(u.getMetaType() == "Unit") {
					abilityToDrop = Ability.getAbilityDrop("Special");
				}
			}
		
			if(abilityToDrop!=null) {
				Unit v = new Unit("Ability Drop",abilityToDrop.getType(),u.getX(),u.getY());
				v.animate("Bob");
				}
			}
	}
	
	public static void dropRespond(String type, float x, float y) {
		if(type == "Fire Fingers") {
			if(FireFingers.timer!=null) {
				FireFingers.timer.despawn();
			}
			FireFingers.startFireFingers((int)FireFingers.getDuration());
			ScreenElement newS = new ScreenElement(
					"Text",
					"Tap everywhere! " + (int)FireFingers.getDuration()/1000,
					x-200,
					y,
					"Game");
			newS.setColor(Color.RED);
			FireFingers.setScreenElement(newS);
			newS.animate("Bob", (int)FireFingers.getDuration());
			Ability.getAbility(type).playPlaceSound();
		}
		else if(type == "Lazer Fingers") {
			if(LazerFingers.timer!=null) {
				LazerFingers.timer.despawn();
			}
			LazerFingers.startLazerFingers((int)LazerFingers.getDuration());
			ScreenElement newF = new ScreenElement(
					"Text",
					"Use both fingers! " + (int)LazerFingers.getDuration()/1000,
					x-200,
					y,
					"Game");
			newF.setColor(Color.RED);
			LazerFingers.setScreenElement(newF);
			newF.animate("Bob", (int)LazerFingers.getDuration());
			Ability.getAbility(type).playPlaceSound();
		}
		else if(type == "Nuke") {
			Nuke newNuke = new Nuke(GameActivity.getScreenWidth()/2,GameActivity.getScreenHeight()/2,GameActivity.getScreenHeight()*2,6000); // Default explosion for now. Make upgradable.
			Ability.getAbility(type).playPlaceSound();
		}
		else if(type == "Bomb") {
			Ability.getAbility(type).playPlaceSound();
			Bomb newBomb = new Bomb(x,y,GameActivity.getScreenWidth(),1250, Color.RED, Color.YELLOW); // Default explosion for now. Make upgradable.
		}
		else if(type == "Slow") {
			synchronized(Slow.SlowsLock) {
				Ability.getAbility(type).playPlaceSound();
				Slow newSlow = new Slow(x,y,GameActivity.getScreenWidth()*2,850); // Default slow.
			}
		}
		else if(type == "Blackhole") {
			synchronized(Blackhole.BlackholesLock) {
				Blackhole newBlackhole = new Blackhole(x,y,GameActivity.getScreenWidth()*1/3,11000); // Default slow.
				Ability.getAbility(type).playPlaceSound();
			}
		}
		else if(type == "Turret") {
			synchronized(Turret.TurretsLock) {
				Turret newTurret = new Turret(x,y,GameActivity.getScreenWidth()*1/3,20000); // Default slow.
				Ability.getAbility(type).playPlaceSound();
			}
		}
		else if(type == "Bomb Turret") {
			synchronized(BombTurret.BombTurretsLock) {
				BombTurret newBombTurret = new BombTurret(x,y,GameActivity.getScreenWidth()*1/3,20000); // Default slow.
				Ability.getAbility(type).playPlaceSound();
			}
		}
	}

	public static Random getR() {
		return r;
	}

	public static void setR(Random r) {
		Drop.r = r;
	}
}