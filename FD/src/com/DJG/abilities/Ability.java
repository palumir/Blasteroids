package com.DJG.abilities;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
	private Bitmap bmp;
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
		case 2:
			x = DisplayMessageActivity.getScreenWidth()-250;
			y = DisplayMessageActivity.getScreenHeight()-50;
			radius = 80;
			break;
		default:
			break;
		}
	}
	
	public Ability(String newType, int newSlot, int newCoolDown, int newUses, int soundID, String newSymbol, Bitmap newBMP, int newRadius) {
		coolDown = newCoolDown;
		if(soundID!=-1)  mpPlacement = MediaPlayer.create(DisplayMessageActivity.survContext, soundID); 
		slot = newSlot;
		type = newType;
		uses = newUses;
		symbol = newSymbol;
		iconColor = Color.WHITE;
		bmp = newBMP;
		switch(slot){
		case 0:
			x = DisplayMessageActivity.getScreenWidth()-100;
			radius = newRadius;
			break;
		case 1:
			x = DisplayMessageActivity.getScreenWidth()-200;
			break;
		case 2:
			x = DisplayMessageActivity.getScreenWidth()-300;
			break;
		default:
			break;
		}
		y = DisplayMessageActivity.getScreenHeight()-100;
		radius = newRadius;
	}
	
	public static void initAbilities() {
		equippedAbilities = new ArrayList<Ability>();
		equippedAbilities.add(new Ability("Bomb",0,1,3,R.raw.small_3_second_explosion,"B",Bomb.bombBMP,32));
		equippedAbilities.add(new Ability("Slow",1,1,3,-1,"S",Slow.slowBMP,32));
		equippedAbilities.add(new Ability("Blackhole",2,1,3,-1,"S",Blackhole.BlackholeBMP,32));
		//equippedAbilities.add(new Ability("KnockBack", 2, 8000, 5, -1, "K", Color.WHITE));
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
	
	public Bitmap getBMP() {
		return bmp;
	}
	
	public static void drawAbilities(Canvas canvas, Paint myPaint) {
        // Draw ability icons. 
      	myPaint.setStrokeWidth(3);
      	myPaint.setTextSize(50);
      	
      	// Draw the abilities on fingers.
      	if(DisplayMessageActivity.grabbedAbility != null && DisplayMessageActivity.grabbedAbility.getUses() > 0) {
			canvas.drawBitmap(DisplayMessageActivity.grabbedAbility.getBMP(), DisplayMessageActivity.grabbedAbilityX-DisplayMessageActivity.grabbedAbility.getRadius(), DisplayMessageActivity.grabbedAbilityY - DisplayMessageActivity.grabbedAbility.getRadius(), null);
      	}
      	if(DisplayMessageActivity.secondGrabbedAbility != null && DisplayMessageActivity.secondGrabbedAbility.getUses() > 0) {
			canvas.drawBitmap(DisplayMessageActivity.secondGrabbedAbility.getBMP(), DisplayMessageActivity.secondGrabbedAbilityX-DisplayMessageActivity.secondGrabbedAbility.getRadius(), DisplayMessageActivity.secondGrabbedAbilityY - DisplayMessageActivity.secondGrabbedAbility.getRadius(), null);
      	}
      	
      	synchronized(abilitiesLock) {
      		for(Ability a : Ability.getEquippedAbilities()) {
      			if(a.getBMP() != null) {
      				canvas.drawBitmap(a.getBMP(), a.getX()-a.getRadius(), a.getY() - a.getRadius(), null);
	  				myPaint.setColor(Color.WHITE);
 	  				myPaint.setTextSize(35);
	  				canvas.drawText(a.getUses() + "",a.getX()-a.getRadius()+20,a.getY() - a.getRadius() +100,myPaint);
      			}
      			else {
    	  				myPaint.setColor(a.getIconColor());
    	  				myPaint.setStyle(Paint.Style.FILL);
    	  				canvas.drawRect(a.getX() - +a.getRadius(), a.getY() + ((float)a.getRadius())*(1-a.getCDPercentRemaining()) - +a.getRadius(), a.getX(), a.getY(), myPaint );
    	  				myPaint.setColor(Color.RED);
    	  				myPaint.setTextSize(23);
    	  				canvas.drawText(a.getUses() + "",a.getX()+4-a.getRadius(),a.getY()+22-a.getRadius(),myPaint);
    	  				myPaint.setColor(Color.WHITE);
    	  				myPaint.setTextSize(50);
    	  				canvas.drawText(a.getSymbol(),a.getX()+23-a.getRadius(),a.getY()-22,myPaint);
    	  				myPaint.setStyle(Paint.Style.STROKE);
    	  				canvas.drawRect(a.getX() - a.getRadius(), a.getY() - a.getRadius(), a.getX(), a.getY(), myPaint );
      			}
      		}
      	}
      	
	}
	
	public static void drawAbilityAnimations(Canvas canvas, Paint myPaint) {
      	Bomb.drawBombs(canvas, myPaint);
      	Slow.drawSlows(canvas, myPaint);
      	Blackhole.drawBlackholes(canvas, myPaint);
      	KnockBack.drawKnockBacks(canvas, myPaint);
	}

	public static void updateAbilities() {
		Bomb.updateBombs();
		Slow.updateSlows();
		Blackhole.updateBlackholes();
		KnockBack.updateknockBacks();
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
					Slow newSlow = new Slow(xSpawn,ySpawn,350,1000); // Default slow.
				}
			}
			if(this.getType() == "Blackhole") {
				synchronized(Blackhole.BlackholesLock) {
					Blackhole newBlackhole = new Blackhole(xSpawn,ySpawn,200,30000); // Default slow.
				}
			}
			if(this.getType() == "KnockBack"){
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
			Bomb.clearBombs();
			Slow.clearSlows();
			Blackhole.clearBlackholes();
			KnockBack.clearKnockBacks();
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
					if(distanceXY <= 25 + u.getRadius()) {
						return u;
					}
				}
				
				return null;
			}
		}
}