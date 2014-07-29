package com.DJG.abilities;

import java.util.Random;

import android.graphics.Color;

import com.DJG.fd.GameActivity;
import com.DJG.screenelements.ScreenElement;
import com.DJG.units.Unit;
import com.DJG.units.UnitType;

public class Drop {
	private static Random r = new Random();
	
	public static void initAbilityDrops() {
  		for(Ability a : Ability.getEquippedAbilities()) {
  			UnitType.addUnitType(new UnitType(a.getType(),"Ability Drop",a.getRadius(),0, true, a.getBMP(), 1, 0));
  		}
	}
	
	public static void potentiallyDropItem(Unit u) {
		if(r.nextInt(65) == 1) {
			if(u.getMetaType() == "Unit") {
				Ability abilityToDrop = Ability.getEquippedAbilities().get(r.nextInt(Ability.getEquippedAbilities().size()));
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
					"Bomb Fingers " + (int)FireFingers.getDuration()/1000,
					x-200,
					y,
					"Game");
			newS.setColor(Color.RED);
			FireFingers.setScreenElement(newS);
			newS.animate("Bob", (int)FireFingers.getDuration());
		}
		if(type == "Nuke") {
			Nuke newNuke = new Nuke(GameActivity.getScreenWidth()/2,GameActivity.getScreenHeight()/2,GameActivity.getScreenHeight()*2,6000); // Default explosion for now. Make upgradable.
		}
		if(type == "Coin") {
			Coin.increaseCoins();
		}
		else {
			for(int j = 0; j < Ability.getEquippedAbilities().size(); j++) {
				Ability a = Ability.getEquippedAbilities().get(j);
				if(a.getType() == type) {
					a.increaseUses();
				}
			}
		}
	}
}