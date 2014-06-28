package com.DJG.units;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

import com.DJG.fd.DisplayMessageActivity;
import com.DJG.fd.R;

public class UnitType {
	
	// Global list of all Unit Types.
	private final static Object allUnitTypesLock= new Object(); // A lock so we don't fuck up the allUnits
	private static ArrayList<UnitType> allUnitTypes;
	
	// Frozen BMP
	public static Bitmap frozenBMP;
	
	public static void initUnitTypes() {
		frozenBMP = DisplayMessageActivity.makeTransparent(BitmapFactory.decodeResource(DisplayMessageActivity.survContext.getResources(), R.drawable.frozen_asteroid));
		allUnitTypes = new ArrayList<UnitType>();
		
		// Basic Units
		allUnitTypes.add(new UnitType("Asteroid",30,1f, true, R.drawable.asteroid, 1, 10));
		allUnitTypes.add(new UnitType("Fire Asteroid",30,1f,true, R.drawable.fire_asteroid, 1, 20));
		allUnitTypes.add(new UnitType("Ice Asteroid",30,1f,true, R.drawable.ice_asteroid, 1, 10));
		allUnitTypes.add(new UnitType("Cat",50,3f, true, R.drawable.satelite, 1, 10));
		allUnitTypes.add(new UnitType("Cheetah",25,6f,true,Color.YELLOW,"Square", 1, 15));
		allUnitTypes.add(new UnitType("Splitter Huge",200,1f,true,Color.RED,"Square", 1, 200));
		allUnitTypes.add(new UnitType("Splitter Big",100,0.5f,true,Color.YELLOW,"Square", 1, 100));
		allUnitTypes.add(new UnitType("Splitter Medium",50,0.25f,true,Color.CYAN,"Square", 1, 25));
		allUnitTypes.add(new UnitType("Splitter Small",25,0.1f,true,Color.BLUE,"Square", 1, 10));
		allUnitTypes.add(new UnitType("FullHealer",40,1f, true, Color.GREEN,"Plus", 2, -100));
		allUnitTypes.add(new UnitType("Healer",20,1.5f, true, Color.GREEN,"Plus", 2, -10));
		allUnitTypes.add(new UnitType("Spawner", 200, 0, true, Color.MAGENTA,"Square",2,0));
		
		// Cthulu
		allUnitTypes.add(new UnitType("Cthulu Head",100,1f,true,Color.RED,"Square", 1, 0));
		allUnitTypes.add(new UnitType("Cthulu Lazer Arm",100,1f,true,Color.RED,"Square", 1, 0));
		allUnitTypes.add(new UnitType("Cthulu Rocket Arm",100,1f,true,Color.RED,"Circle", 1, 0));
		
		// The Earth. Called Castle for... some reason.
		allUnitTypes.add(new UnitType("Castle",50,0f,false,R.drawable.earth, 100,0)); // CASTLES DON'T MOVE OKAY?
	}
	
	public static UnitType getUnitType(String searchType) {
		UnitType foundUnitType = null;
		synchronized(allUnitTypesLock) {
			for(UnitType u : allUnitTypes) {
				if(u.getType() == searchType) {
					foundUnitType = u;
					break;
				}
			}
			return foundUnitType;
		}
	}
	
	// UnitType constructor.
	public UnitType(String newType, int newRadius, float newMoveSpeed, boolean isKillable) {
		type = newType;
		radius = newRadius;
		moveSpeed = newMoveSpeed;
		killable = isKillable;
		color = Color.WHITE;
	}
	
	public UnitType(String newType, int newRadius, float newMoveSpeed, boolean isKillable, int newBitMapLink, int newHP, int newDamage) {
		type = newType;
		radius = newRadius;
		moveSpeed = newMoveSpeed;
		killable = isKillable;
		bitmap = DisplayMessageActivity.makeTransparent(BitmapFactory.decodeResource(DisplayMessageActivity.survContext.getResources(), newBitMapLink));
		maxHitPoints = newHP;
		damage = newDamage;
	}
	
	public UnitType(String newType, int newRadius, float newMoveSpeed, boolean isKillable, int newColor, String newShape, int newHP, int newDamage) {
		type = newType;
		radius = newRadius;
		moveSpeed = newMoveSpeed;
		shape = newShape;
		killable = isKillable;
		color = newColor;
		maxHitPoints = newHP;
		damage = newDamage;
	}
	
    // Unit Type fields. WIP: Seperate into sections when there's lots of values.
	private String type;
	private int radius;
	private boolean killable;
	
	//UI stuff
	private int color;
	private String shape;
	private Bitmap bitmap;
	
	//Unit stats
	private int maxHitPoints;
	private int damage;
	private float moveSpeed;
	
	
	public boolean getKillable() {
		return killable;
	}
	
	public String getType() {
		return type;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public int getColor(){
		return color;
	}
 
	public int getMaxHitPoints(){
		return maxHitPoints;
	}
	
	public Bitmap getBMP() {
		return bitmap;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public String getShape() {
		return shape;
	}
	
	public float getMoveSpeed() {
		return moveSpeed;
	}
}