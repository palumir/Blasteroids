package com.DJG.abilities;
import java.util.ArrayList;

import android.graphics.Color;
import android.media.MediaPlayer;

import com.DJG.fd.DisplayMessageActivity;
import com.DJG.fd.R;

public class Ability {
	
	// All abilities
	private static ArrayList<Ability> equippedAbilities;
	public final static Object abilitiesLock = new Object(); // A lock so we don't fuck up the abilities
	
	// General information
	private String type;

	// Cooldown information
	private int coolDown;
	
	// When was it last used?
	private long coolDownTime = 0;
	
	// Slot information.
	private int slot;
	private float x;
	private float y;
	private int radius;
	private MediaPlayer mpPlacement;
	private int uses;
	private String symbol;
	private int iconColor;
	
	public Ability(String newType, int newSlot, int newCoolDown, int newUses, int soundID, String newSymbol, int newIconColor) {
		coolDown = newCoolDown;
		if(soundID!=-1)  mpPlacement = MediaPlayer.create(DisplayMessageActivity.survContext, soundID); 
		slot = newSlot;
		type = newType;
		uses = newUses;
		symbol = newSymbol;
		iconColor = newIconColor;
		switch(slot){
		case 0:
			x = DisplayMessageActivity.getScreenWidth()-50;
			y = DisplayMessageActivity.getScreenHeight()-50;
			radius = 80;
			break;
		case 1:
			x = DisplayMessageActivity.getScreenWidth()-150;
			y = DisplayMessageActivity.getScreenHeight()-50;
			radius = 80;
			break;
		default:
			break;
		}
	}
	
	public static void initAbilities() {
		equippedAbilities = new ArrayList<Ability>();
		equippedAbilities.add(new Ability("Bomb",0,5000,5,R.raw.small_3_second_explosion,"B",Color.YELLOW));
		equippedAbilities.add(new Ability("Slow",1,5000,5,-1,"S",Color.BLUE));
	}
	
	public static ArrayList<Ability> getEquippedAbilities() {
		return equippedAbilities;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public int getIconColor() {
		return iconColor;
	}
	
	public static void updateAbilities() {
		Bomb.updateBombs();
		Slow.updateSlows();
	}
	
	public void playPlaceSound() {
		if(mpPlacement != null) {
			mpPlacement.start();
		}
	}
	
	public void useAbility(float xSpawn,float ySpawn) {
		if(this.isOffCoolDown() && uses>0) {
			// Well you just used it right, put in on CD!
			this.putOnCoolDown();
			uses--;
			
			// Play a sound of placing ability.
			this.playPlaceSound();
			
			if(this.getType() == "Bomb") {
				synchronized(Bomb.bombsLock) {
					Bomb newBomb = new Bomb(xSpawn,ySpawn,250,1500); // Default explosion for now. Make upgradable.
				}
			}
			if(this.getType() == "Slow") {
				synchronized(Slow.SlowsLock) {
					Slow newSlow = new Slow(xSpawn,ySpawn,250,1500); // Default slow.
				}
			}
		}
	} 
	
	public float getCDPercentRemaining() {
		if(coolDownTime == 0 || this.isOffCoolDown() || coolDown == 0) {
			return 1;
		}
		else {
			return (float)(System.currentTimeMillis() - coolDownTime)/coolDown;
		}
	}
	
	public void putOnCoolDown() {
		coolDownTime = System.currentTimeMillis();
	}
	
	public boolean isOffCoolDown() {
		return (System.currentTimeMillis() - coolDownTime > coolDown);
	}
	
	public int getCoolDown() {
		return coolDown;
	}
	
	public int getUses() {
		return uses;
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
					if(distanceXY <= 10 + u.getRadius()) {
						return u;
					}
				}
				
				return null;
			}
		}
}