package com.AIG.abilities;

import java.util.Random;

import android.graphics.Color;

import com.AIG.blasteroids.GameActivity;
import com.AIG.blasteroids.MainActivity;
import com.AIG.screenelements.FleetingScreenElement;
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
			if(getR().nextInt(250) == 1) {
				if(u.getMetaType() == "Unit") {
					abilityToDrop = Ability.getAbilityDrop("Normal");
				}
			}
			
			// Drop a special ability?
			if(getR().nextInt(400) == 1) {
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
					"Bomb Fingers " + (int)FireFingers.getDuration()/1000 + "\nTap everywhere!",
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
					"Lazer Fingers " + (int)LazerFingers.getDuration()/1000 + "\nUse both fingers!",
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
		else if(type == "Coin") {
			FleetingScreenElement newF = new FleetingScreenElement(
					"+1 Coin",
					x-125,
					y,
					"Game");
			newF.setColor(Color.YELLOW);
			Coin.increaseCoins();
		}
		else {
			MainActivity.soundPool.play(Ability.equipSound, 1, 1, 1, 0, 1f);
			for(int j = 0; j < Ability.getEquippedAbilities().size(); j++) {
				Ability a = Ability.getEquippedAbilities().get(j);
				if(a.getType() == type) {
					FleetingScreenElement newF = new FleetingScreenElement(
							"+1 " + a.getType(),
							x-125,
							y,
							"Game");
					newF.setColor(Color.WHITE);
					a.increaseUses();
				}
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