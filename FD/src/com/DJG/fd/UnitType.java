package com.DJG.fd;
import java.util.ArrayList;

public class UnitType {
	
	// Global list of all Unit Types.
	private final static Object allUnitTypesLock= new Object(); // A lock so we don't fuck up the allUnits
	public static ArrayList<UnitType> allUnitTypes = new ArrayList<UnitType>();
	
	public static void initUnitTypes() {
		allUnitTypes.add(new UnitType("Ogre",30,1f,true));
		allUnitTypes.add(new UnitType("Mage",25,0.8f,true));
		allUnitTypes.add(new UnitType("Demon",30,0.7f,true));
		allUnitTypes.add(new UnitType("Cat",25,3f,true));
		allUnitTypes.add(new UnitType("Cheetah",25,6f,true));
		allUnitTypes.add(new UnitType("Castle",50,0f,false)); // CASTLES DON'T MOVE OKAY?
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
	}
	
    // Unit Type fields. WIP: Seperate into sections when there's lots of values.
	private String type;
	private int radius;
	private float moveSpeed;
	private boolean killable;
	
	public boolean getKillable() {
		return killable;
	}
	
	public String getType() {
		return type;
	}
	
	public float getMoveSpeed() {
		return moveSpeed;
	}
	
	public int getRadius() {
		return radius;
	}
 
}