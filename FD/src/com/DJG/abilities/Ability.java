package com.DJG.abilities;
import java.util.ArrayList;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.media.MediaPlayer;
import android.util.Log;

import com.DJG.fd.GameActivity;
import com.DJG.fd.MainActivity;
import com.DJG.fd.R;
import com.DJG.fd.touchevents.TouchEvent;
import com.DJG.units.Unit;

public class Ability {
	private static SharedPreferences prefs;
	private static Editor editor;
	
	// All abilitiesf
	private static ArrayList<Ability> equippedAbilities;
	public static Ability coinAbility;
	public static ArrayList<Ability> upgradeableAbilities;
	public static ArrayList<Ability> purchasedAbilities;
	public static ArrayList<Ability> aList;
	public static ArrayList<Ability> sList;
	public final static Object purchasedAbilitiesLock = new Object();
	public final static Object upgradeableAbilitiesLock = new Object();
	public final static Object abilitiesLock = new Object(); // A lock so we don't fuck up the abilities
	public final static Object allAbilitiesLock = new Object();
	
	// General information
	private String type;

	// Cooldown information
	private int coolDown;
	
	// When was it last used?
	private long coolDownTime = 0;
	
	// Slot information.
	public static int slotNumber = 0;
	private Bitmap bmp;
	private int slot;
	private float x;
	private float y;
	private int radius;
	private MediaPlayer mpPlacement;
	private int uses;
	private String symbol;
	private int iconColor;
	private boolean purchased = false;
	
	// Shop information
	private String description;
	private int cost;
	
	public Ability(String newType, int newSlot, int newCoolDown, int newUses, int soundID, Bitmap newBMP, int newRadius, String newDesc, int newCost) {
		coolDown = newCoolDown;
		setCost(newCost);
		setDescription(newDesc);
		if(soundID!=-1)  mpPlacement = MediaPlayer.create(GameActivity.gameContext, soundID); 
		setSlot(newSlot);
		type = newType;
		uses = newUses;
		iconColor = Color.WHITE;
		bmp = newBMP;
		bmp =  Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), MainActivity.matrix, true);
		/*purchased = (getPrefs().getInt(newType + "_equipped", 0) == 1);*/
		
		switch(getSlot()){
		case -2:
			// Don't show it on the screen.
			x = -1000;
			y = -1000;
			break;
		case -1:
			// Don't show it on the screen.
			x = -1000;
			y = -1000;
			break;
		default:
			x = GameActivity.getScreenWidth()-((slotNumber+1)*100);
			slotNumber = slotNumber + 1;
			break;
		}
		y = GameActivity.getScreenHeight()-100;
		radius = newRadius;
	}
	
	public static void initAbilities() {
		
		
		// If they have no abilities, give them a bomb!
		/*if(getPrefs().getInt("Bomb_purchased", -99) == -99) {
			getEditor().putInt("Bomb_purchased",1);
			getEditor().commit();
		}
		if(getPrefs().getString("Slot1","None").equals("None")) {
			getEditor().putString("Slot1", "Bomb");
			getEditor().commit();
		}*/
		
		// Load all abilities that you can upgrade
		upgradeableAbilities = new ArrayList<Ability>();
		upgradeableAbilities.add(new Ability("Bomb",0,1,3,R.raw.small_3_second_explosion,Bomb.bombBMP,32,"POW!",0));
		upgradeableAbilities.add(new Ability("Slow",0,1,0,-1,Slow.slowBMP,32,"Slows in a radius.",15));
		upgradeableAbilities.add(new Ability("Blackhole",0,1,0,-1,Blackhole.BlackholeBMP,32,"Sucks in asteroids.",25));
		upgradeableAbilities.add(new Ability("Turret",0,1,0,-1,Turret.TurretBMP,32,"Shoots stuff.",30));
		upgradeableAbilities.add(new Ability("Bomb Turret",0,1,0,-1,BombTurret.BombTurretBMP,32,"Shoots bombs.",30));
		upgradeableAbilities.add(new Ability("Fire Fingers",-1,1,3,-1,FireFingers.fireBMP,32,"Fire Fingers",0));
		upgradeableAbilities.add(new Ability("Lazer Fingers",-1,1,3,-1,LazerFingers.lazerBMP,32,"Lazer Fingers",0));
		upgradeableAbilities.add(new Ability("Nuke",-1,1,3,-1,Nuke.NukeBMP,32,"Nuke",0));
		
		// All droppable abilities/equippable abilities.
		//initPurchasedAbilities();
		initUserAbilities();
		Drop.initAbilityDrops();
		
		// aList and sList (equipped abilities vs special abilities)
		aList = new ArrayList<Ability>();
  		for(int i = 0; i < Ability.getEquippedAbilities().size(); i++) {
  			Ability a = Ability.getEquippedAbilities().get(i);
			if(a.slot>=0) {
				aList.add(a);
			}
		}
		sList = new ArrayList<Ability>();
  		for(int i = 0; i < Ability.getEquippedAbilities().size(); i++) {
  			Ability a = Ability.getEquippedAbilities().get(i);
			if(a.slot==-1) {
				sList.add(a);
			}
			if(a.slot==-2) {
				coinAbility = a;
			}
		}
		
		// Planet stuff
	/*	if(Ability.getPrefs().getInt("Earth_purchased", 0) == 0) {
			Ability.getEditor().putInt("Earth_purchased", 1);
			Ability.getEditor().commit();
		}*/
	}
	
	public static Ability getAbilityDrop(String dropType) {
		if(dropType.equals("Normal")) {
				if(aList.size()==1) {
					return aList.get(0);
				}
				else {
					return aList.get(Drop.getR().nextInt(aList.size()));
				}          
		}
		else if(dropType.equals("Special")) {
				if(sList.size()==1) {
					return sList.get(0);
				}
				else {
					return sList.get(Drop.getR().nextInt(sList.size()));
				}
		}
		else if(dropType.equals("Coin")) {
			return coinAbility;
		}
		return null;
	}
	
	public static int countNormals() {
		int count = 0;
		synchronized(upgradeableAbilitiesLock) {
      		for(int i = 0; i < Ability.upgradeableAbilities.size(); i++) {
      			Ability a = Ability.upgradeableAbilities.get(i);
				if(a.slot!=-1 || a.slot!=0 || a.slot!=-2) {
					count++;
				}
			}
		}
		return count;
	}
	
	public static int countSpecials() {
		int count = 0;
		synchronized(upgradeableAbilitiesLock) {
      		for(int i = 0; i < Ability.upgradeableAbilities.size(); i++) {
      			Ability a = Ability.upgradeableAbilities.get(i);
				if(a.slot==-1) {
					count++;
				}
			}
		}
		return count;
	}
	
	/*public void buy() {
		if(getPrefs().getInt(getType() + "_purchased", -99) == -99 && Coin.getCoins() >= getCost()) {
			getEditor().putInt(getType() + "_purchased",1);
			Coin.increaseCoins((-1)*getCost());
			Coin.coinsSave();
			getEditor().commit();
		}
		else if(!(getPrefs().getInt(getType() + "_purchased", -99) == -99)) {
			// You already purchased that!
		}
		else if(!(Coin.getCoins() >= getCost())) {
			// You don't have enough coins for that!
		}
	}*/
	
	/*public void equip(String equipSlot) {
		if(getPrefs().getInt(getType() + "_purchased", -99) == 1) {
			getEditor().putString(equipSlot, getType());
			getEditor().commit();
			initUserAbilities();
		}
		else {
			// You do not have that ability purchased.
		}
	}*/
	
	/*static void initPurchasedAbilities() {
		purchasedAbilities = new ArrayList<Ability>();
		synchronized(purchasedAbilitiesLock) {
			int curSlot = 0;
      		for(int i = 0; i < Ability.upgradeableAbilities.size(); i++) {
      			Ability a = Ability.upgradeableAbilities.get(i);
				purchasedAbilities.add(a);
			}
		}
	}*/
	
	static void initUserAbilities() {
		equippedAbilities = new ArrayList<Ability>();
		synchronized(upgradeableAbilitiesLock) {
      		for(int i = 0; i < Ability.upgradeableAbilities.size(); i++) {
      			Ability a = Ability.upgradeableAbilities.get(i);
				// The ability is passive!
				if(a.getSlot() <= -1 || a.getSlot() == 0) {
					equippedAbilities.add(a);
				}
			}
		}
	}
	
	public static ArrayList<Ability> getEquippedAbilities() {
		return equippedAbilities;
	}
	
	public void setSlot(int newSlot) {
		slot = newSlot;
		switch(slot){
		case -2:
			x = -1000;
			y = -1000;
			break;
		case -1:
			// Don't show it on the screen.
			x = -1000;
			y = -1000;
			break;
		case 0:
			x = GameActivity.getScreenWidth()-100;
			break;
		case 1:
			x = GameActivity.getScreenWidth()-200;
			break;
		case 2:
			x = GameActivity.getScreenWidth()-300;
			break;
		default:
			break;
		}
		y = GameActivity.getScreenHeight()-100;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public int getIconColor() {
		return iconColor;
	}
	
	public Bitmap getBMP() {
		return bmp;
	}
	
	public void increaseUses() {
		uses++;
	}
	
	public static void drawAbilities(Canvas canvas, Paint myPaint) {
        // Draw ability icons. 
      	myPaint.setStrokeWidth(3);
      	myPaint.setTextSize(50);
      	
      	// Draw the abilities on fingers.
      	if(TouchEvent.grabbedAbility != null && TouchEvent.grabbedAbility.getUses() > 0) {
			canvas.drawBitmap(TouchEvent.grabbedAbility.getBMP(), TouchEvent.grabbedAbilityX-TouchEvent.grabbedAbility.getRadius(), TouchEvent.grabbedAbilityY - TouchEvent.grabbedAbility.getRadius(), null);
      	}
      	if(TouchEvent.secondGrabbedAbility != null && TouchEvent.secondGrabbedAbility.getUses() > 0) {
			canvas.drawBitmap(TouchEvent.secondGrabbedAbility.getBMP(), TouchEvent.secondGrabbedAbilityX-TouchEvent.secondGrabbedAbility.getRadius(), TouchEvent.secondGrabbedAbilityY - TouchEvent.secondGrabbedAbility.getRadius(), null);
      	}
      	
      	if(TouchEvent.lazerFingers) {
      		LazerFingers.drawLazerFingers(canvas, myPaint);
      	}
      	synchronized(abilitiesLock) {
      		for(int i = 0; i < Ability.getEquippedAbilities().size(); i++) {
      			Ability a = Ability.getEquippedAbilities().get(i);
      			if(a.getBMP() != null && a.getUses() > 0) {
      				canvas.drawBitmap(a.getBMP(), a.getX()-a.getRadius(), a.getY() - a.getRadius(), null);
	  				myPaint.setColor(Color.WHITE);
	  				myPaint.setStyle(Style.FILL);
	  				myPaint.setStrokeWidth(1);
 	  				myPaint.setTextSize(35);
	  				canvas.drawText(
	  						Integer.toString(a.getUses()),a.getX()-a.getRadius()+20,a.getY() - a.getRadius() +100,myPaint);
      			}
      		}
      	}
      	
	}
	
	public static void drawAbilityAnimations(Canvas canvas, Paint myPaint) {
      	Bomb.drawBombs(canvas, myPaint);
      	Slow.drawSlows(canvas, myPaint);
      	Nuke.drawNukes(canvas,myPaint);
      	//Coin.drawCoins(canvas,myPaint);
      	Blackhole.drawBlackholes(canvas, myPaint);
      	Turret.drawTurrets(canvas,myPaint);
      	BombTurret.drawBombTurrets(canvas,myPaint);
      	KnockBack.drawKnockBacks(canvas, myPaint);
	}

	public static void updateAbilities() {
		Bomb.updateBombs();
		Slow.updateSlows();
		Nuke.updateNukes();
		//Coin.updateCoins();
		Blackhole.updateBlackholes();
		Turret.updateTurrets();
		BombTurret.updateBombTurrets();
		KnockBack.updateknockBacks();
		FireFingers.updateFireFingers();
		LazerFingers.updateLazerFingers();
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
					Bomb newBomb = new Bomb(xSpawn,ySpawn,400,1250, Color.RED, Color.YELLOW); // Default explosion for now. Make upgradable.
				}
			}
			if(this.getType() == "Slow") {
				synchronized(Slow.SlowsLock) {
					Slow newSlow = new Slow(xSpawn,ySpawn,600,850); // Default slow.
				}
			}
			if(this.getType() == "Nuke") {
				synchronized(Nuke.NukesLock) {
					Nuke nuke = new Nuke(xSpawn,ySpawn,2000,3000); // Default slow.
				}
			}
			if(this.getType() == "Blackhole") {
				synchronized(Blackhole.BlackholesLock) {
					Blackhole newBlackhole = new Blackhole(xSpawn,ySpawn,200,11000); // Default slow.
				}
			}
			if(this.getType() == "Turret") {
				synchronized(Turret.TurretsLock) {
					Turret newTurret = new Turret(xSpawn,ySpawn,150,20000); // Default slow.
				}
			}
			if(this.getType() == "Bomb Turret") {
				synchronized(BombTurret.BombTurretsLock) {
					BombTurret newBombTurret = new BombTurret(xSpawn,ySpawn,125,20000); // Default slow.
				}
			}
			if(this.getType() == "KnockBack") {
				synchronized (KnockBack.knockBacksLock) {
					KnockBack newKnockBack = new KnockBack(xSpawn, ySpawn, 450, 2000);
				}
			}
		}
	} 
	
	public float getCDPercentRemaining() {
		if(coolDownTime == 0 || this.isOffCoolDown() || coolDown == 0) {
			return 1;
		}
		else {
			return (float)(GameActivity.getGameTime() - coolDownTime)/coolDown;
		}
	}
	
	public void putOnCoolDown() {
		coolDownTime = GameActivity.getGameTime();
	}
	
	public boolean isOffCoolDown() {
		return (GameActivity.getGameTime() - coolDownTime > coolDown);
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
			Bomb.clearBombs();
			Slow.clearSlows();
			//Coin.clearCoins();
			Nuke.clearNukes();
			Blackhole.clearBlackholes();
			Turret.clearTurrets();
			BombTurret.clearBombTurrets();
			KnockBack.clearKnockBacks();
		}
	}
	
	public static void checkIfHitAbility(Unit u) {
		Bomb.checkIfHitBomb(u);
		Nuke.checkIfHitNuke(u);
		Slow.checkIfHitSlow(u);
		Blackhole.checkIfHitBlackhole(u);
		Turret.checkIfHitTurret(u);
		BombTurret.checkIfHitBombTurret(u);
		KnockBack.checkIfHitKnockBack(u);
		LazerFingers.checkIfHitLazer(u);
	}
	
	public static int getAbilityPos(Ability thisAbility) {
		synchronized(abilitiesLock) {
			int foundAbility = 0;
      		for(int i = 0; i < Ability.getEquippedAbilities().size(); i++) {
      			Ability a = Ability.getEquippedAbilities().get(i);
				if(a == thisAbility) {
					break;
				}
				foundAbility++;
			}
			return foundAbility;
		}
	}
	
	public static Ability getAbility(String name) {
		synchronized(upgradeableAbilitiesLock) {
			for(Ability a : upgradeableAbilities) {
				if(a.getType().equals(name)) {
					return a;
				}
			}
			return null;
		}
	}
	
	// Get the selected unit at the coordinates.
		public static Ability getAbilityAt(float x, float y) {
			synchronized(abilitiesLock) {
				// Spare the plusses if possible.
	      		for(int i = 0; i < Ability.getEquippedAbilities().size(); i++) {
	      			Ability u = Ability.getEquippedAbilities().get(i);
					float yDistance = (u.getY() - y);
					float xDistance = (u.getX() - x);
					float distanceXY = (float)Math.sqrt(yDistance*yDistance + xDistance*xDistance);
					
					// If the unit is very small make it easier to press.
					if(distanceXY <= 25 + u.getRadius()) {
						return u;
					}
				}
				
				return null;
			}
		}

		public static SharedPreferences getPrefs() {
			return prefs;
		}

		public static void setPrefs(SharedPreferences prefs) {
			Ability.prefs = prefs;
		}

		public static Editor getEditor() {
			return editor;
		}

		public static void setEditor(Editor editor) {
			Ability.editor = editor;
		}

		public int getCost() {
			return cost;
		}

		public void setCost(int cost) {
			this.cost = cost;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int getSlot() {
			return slot;
		}
}