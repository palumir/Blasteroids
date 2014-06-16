package com.DJG.fd;
import java.util.ArrayList;

import android.graphics.Color;

public class UnitType {
	
	// Global list of all Unit Types.
	private final static Object allUnitTypesLock= new Object(); // A lock so we don't fuck up the allUnits
	private static ArrayList<UnitType> allUnitTypes;
	
	public static void initUnitTypes() {
		allUnitTypes = new ArrayList<UnitType>();
		allUnitTypes.add(new UnitType("Ogre",30,1f, true, Color.CYAN,"Circle", 2, 10));
		allUnitTypes.add(new UnitType("Mage",50,0.8f,true, Color.BLUE, "Square", 1, 10));
		allUnitTypes.add(new UnitType("Demon",30,0.7f,true, Color.RED, "Circle", 2, 15));
		allUnitTypes.add(new UnitType("Cat",25,3f, true, Color.WHITE, "Circle", 1, 10));
		allUnitTypes.add(new UnitType("Cheetah",45,6f,true,Color.GREEN,"Square", 1, 15));
		allUnitTypes.add(new UnitType("Splitter Huge",200,1f,true,Color.RED,"Square", 1, 200));
		allUnitTypes.add(new UnitType("Splitter Big",100,0.5f,true,Color.YELLOW,"Square", 1, 100));
		allUnitTypes.add(new UnitType("Splitter Medium",50,0.25f,true,Color.CYAN,"Square", 1, 25));
		allUnitTypes.add(new UnitType("Splitter Small",25,0.1f,true,Color.BLUE,"Square", 1, 10));
		allUnitTypes.add(new UnitType("Castle",50,0f,false,Color.WHITE,"Circle", 100,0)); // CASTLES DON'T MOVE OKAY?
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