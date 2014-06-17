package com.DJG.ability;
import java.util.ArrayList;

import android.util.Log;

import com.DJG.fd.DisplayMessageActivity;

public class Ability {
	
	// All abilities
	private static ArrayList<Ability> equippedAbilities;
	public final static Object abilitiesLock = new Object(); // A lock so we don't fuck up the allUnits
	
	// General information
	private String type;

	// Slot information.
	private int slot;
	private float x;
	private float y;
	private int radius;
	
	public Ability(String newType, int newSlot) {
		slot = newSlot;
		type = newType;
		switch(slot){
		case 0:
				x = DisplayMessageActivity.getScreenWidth()-100;
				y = 20;
				radius = 50;
			break;
		default:
			break;
		}
	}
	
	// Temporary
	public static void initAbilities() {
		equippedAbilities = new ArrayList<Ability>();
		equippedAbilities.add(new Ability("Bomb",0));
	}
	
	public static ArrayList<Ability> getEquippedAbilities() {
		return equippedAbilities;
	}
	
	public static void updateAbilities() {
		if(Bomb.getCurrentBomb() != null) {
			Bomb.getCurrentBomb().updateBomb();
		}
	}
	
	public String getType() {
		return type;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public static void clearAbilities() {
		synchronized(abilitiesLock) {
			Bomb.clearBomb();
			equippedAbilities.clear();
		}
	}
	
	public static int getAbilityPos(Ability thisAbility) {
		synchronized(abilitiesLock) {
			int foundAbility = 0;
			for(Ability a : equippedAbilities) {
				if(a == thisAbility) {
					break;
				}
				foundAbility++;
			}
			return foundAbility;
		}
	}
	// Get the selected unit at the coordinates.
		public static Ability getAbilityAt(float x, float y) {
			synchronized(abilitiesLock) {
				// Spare the plusses if possible.
				for(Ability u : equippedAbilities) {
					float yDistance = (u.getY() - y);
					float xDistance = (u.getX() - x);
					float distanceXY = (float)Math.sqrt(yDistance*yDistance + xDistance*xDistance);
					
					// If the unit is very small make it easier to press.
					if(distanceXY <= 50 + u.getRadius() && u.getRadius() <= 50) {
						return u;
					}
					// If the unit is big, don't make it get in the way of other things with a huge hitbox.
					if(distanceXY <= 10 + u.getRadius() && u.getRadius() > 50) {
						return u;
					}
				}
				
				return null;
			}
		}
}