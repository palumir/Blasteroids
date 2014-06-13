package com.DJG.fd;
import java.util.ArrayList;

public class UnitType {
	
	// Global list of all Unit Types.
	public static ArrayList<UnitType> allUnitTypes = new ArrayList<UnitType>();
	
	public static void initUnitTypes() {
		allUnitTypes.add(new UnitType("Ogre",12,1f,true));
		allUnitTypes.add(new UnitType("Mage",15,0.8f,true));
		allUnitTypes.add(new UnitType("Demon",20,0.7f,true));
		allUnitTypes.add(new UnitType("Cat",15,3f,true));
		allUnitTypes.add(new UnitType("Castle",50,0f,false)); // CASTLES DON'T MOVE OKAY?
	}
	
	public static UnitType getUnitType(String searchType) {
		UnitType foundUnitType = null;
		for(UnitType u : allUnitTypes) {
			if(u.getType() == searchType) {
				foundUnitType = u;
				break;
			}
		}
		return foundUnitType;
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