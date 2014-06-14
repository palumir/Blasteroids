package com.DJG.fd;
import java.util.ArrayList;

import android.graphics.Color;

public class UnitType {
	
	// Global list of all Unit Types.
	private final static Object allUnitTypesLock= new Object(); // A lock so we don't fuck up the allUnits
	private static ArrayList<UnitType> allUnitTypes;
	
	public static void initUnitTypes() {
		allUnitTypes = new ArrayList<UnitType>();
		allUnitTypes.add(new UnitType("Ogre",30,1f, 20,true, Color.CYAN, 2, 10));
		allUnitTypes.add(new UnitType("Mage",25,0.8f, 20,true, Color.BLUE, 1, 20));
		allUnitTypes.add(new UnitType("Demon",30,0.7f,20,true, Color.RED, 2, 15));
		allUnitTypes.add(new UnitType("Cat",25,3f,20, true, Color.BLACK,1, 10));
		allUnitTypes.add(new UnitType("Cheetah",25,6f,20,true,Color.GREEN,1, 30));
		allUnitTypes.add(new UnitType("Castle",50,0f,0,false,Color.BLACK,500,0)); // CASTLES DON'T MOVE OKAY?
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
		color = Color.BLACK;
	}
	
	public UnitType(String newType, int newRadius, float newMoveSpeed, int newAS, boolean isKillable, int newColor, int newHP, int newDamage) {
		type = newType;
		radius = newRadius;
		moveSpeed = newMoveSpeed;
		attackSpeed = newAS;
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
	
	//Unit stats
	private int maxHitPoints;
	private int damage;
	private float moveSpeed;
	private int attackSpeed;
	
	
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
	
	public int getAttackSpeed(){
		return attackSpeed;
	}	
	
	public float getMoveSpeed() {
		return moveSpeed;
	}
}