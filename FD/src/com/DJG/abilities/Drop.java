package com.DJG.abilities;

import java.util.ArrayList;
import java.util.Random;

import com.DJG.fd.R;
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
		if(r.nextInt(2) == 1) {
			if(u.getMetaType() == "Unit") {
				Ability abilityToDrop = Ability.getEquippedAbilities().get(r.nextInt(Ability.getEquippedAbilities().size()));
				Unit v = new Unit("Ability Drop",abilityToDrop.getType(),u.getX(),u.getY());
				v.animate("Bob");
			}
		}
	}
	
	public static void dropRespond(String type) {
		if(type == "Fire Fingers") {
			FireFingers.startFireFingers(30000);
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