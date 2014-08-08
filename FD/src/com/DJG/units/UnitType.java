package com.DJG.units;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.DJG.fd.GameActivity;
import com.DJG.fd.R;
import com.DJG.planets.Planet;

public class UnitType {
	
	// Global list of all Unit Types.
	private final static Object allUnitTypesLock= new Object(); // A lock so we don't fuck up the allUnits
	private static ArrayList<UnitType> allUnitTypes;
	
	// Frozen BMP
	private Bitmap frozenBMP;
	
	public static void initUnitTypes() {
		
		allUnitTypes = new ArrayList<UnitType>();
		
		// Basic Units
		getAllUnitTypes().add(new UnitType("Asteroid",30,1f, true, R.drawable.asteroid, R.drawable.frozen_asteroid, 1, 10,"Unit"));
		getAllUnitTypes().add(new UnitType("Fire Asteroid",30,1f,true, R.drawable.fire_asteroid, R.drawable.frozen_asteroid, 1, 10,"Unit"));
		getAllUnitTypes().add(new UnitType("Ice Asteroid",30,1f,true, R.drawable.ice_asteroid, R.drawable.frozen_asteroid, 1, 10,"Unit"));
		getAllUnitTypes().add(new UnitType("Cat",50,2f, true, R.drawable.satelite, 1, 10));
		getAllUnitTypes().add(new UnitType("Splitter Huge",200,1.5f,true,R.drawable.splitter_big, 1, 200));
		getAllUnitTypes().add(new UnitType("Splitter Big",100,1.25f,true,R.drawable.splitter_big, 1, 100));
		getAllUnitTypes().add(new UnitType("Splitter Medium",50,1f,true,R.drawable.splitter_medium, 1, 25));
		getAllUnitTypes().add(new UnitType("Splitter Small",25,0.25f,true,R.drawable.splitter_small, 1, 10));
		getAllUnitTypes().add(new UnitType("MultiClicker 1",30,1f, true, R.drawable.fasteroid, 1, 10,"Unit"));
		getAllUnitTypes().add(new UnitType("MultiClicker 2",30,1f, true, R.drawable.splitter_medium, 1, 10,"Unit"));
		getAllUnitTypes().add(new UnitType("MultiClicker 3",30,1f, true, R.drawable.fasteroid, 1, 10,"Unit"));
		getAllUnitTypes().add(new UnitType("MultiClicker 4",30,1f, true, R.drawable.splitter_medium, 1, 10,"Unit"));
		getAllUnitTypes().add(new UnitType("FullHealer",40,1f, true, Color.GREEN,"Plus", 2, -100));
		getAllUnitTypes().add(new UnitType("Healer",20,1.5f, true, Color.GREEN,"Plus", 2, -10));
		getAllUnitTypes().add(new UnitType("Spawner", 200, 0, true, Color.MAGENTA,"Square",2,0));
		
		// Projectile
		getAllUnitTypes().add(new UnitType("Bullet",30,10f, true, R.drawable.frozen_asteroid, R.drawable.frozen_asteroid, 1, 0,"Projectile"));
		
		// Cthulu
		getAllUnitTypes().add(new UnitType("Cthulu Head",100,1f,true,Color.RED,"Square", 1, 0));
		getAllUnitTypes().add(new UnitType("Cthulu Lazer Arm",100,1f,true,Color.RED,"Square", 1, 0));
		getAllUnitTypes().add(new UnitType("Cthulu Rocket Arm",100,1f,true,Color.RED,"Circle", 1, 0));
	
		//Planets
		Planet.initPlanetTypes();
		
		//Defensive units, Will add own class later
		getAllUnitTypes().add(new UnitType("Fire Defender Moon", 50, 0f,true, R.drawable.fire_asteroid, 1, 0));
		getAllUnitTypes().add(new UnitType("Mars Moon", 50, 0f, true, R.drawable.asteroid, 2, 0));
	}
	
	public static UnitType getUnitType(String searchType) {
		UnitType foundUnitType = null;
		synchronized(allUnitTypesLock) {
			for(UnitType u : getAllUnitTypes()) {
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
		metaType = "Unit";
		killable = isKillable;
		bitmap = GameActivity.makeTransparent(BitmapFactory.decodeResource(GameActivity.gameContext.getResources(), newBitMapLink));
		maxHitPoints = newHP;
		damage = newDamage;
	}
	
	public UnitType(String newType, int newRadius, float newMoveSpeed, boolean isKillable, int newBitMapLink, int newFrozenBMPLink, int newHP, int newDamage, String newMetaType) {
		type = newType;
		radius = newRadius;
		moveSpeed = newMoveSpeed;
		metaType = newMetaType;
		killable = isKillable;
		bitmap = GameActivity.makeTransparent(BitmapFactory.decodeResource(GameActivity.gameContext.getResources(), newBitMapLink));
		setFrozenBMP(GameActivity.makeTransparent(BitmapFactory.decodeResource(GameActivity.gameContext.getResources(), newFrozenBMPLink)));
		maxHitPoints = newHP;
		damage = newDamage;
	}
	
	public UnitType(String newType, int newRadius, float newMoveSpeed, boolean isKillable, int newBitMapLink, int newHP, int newDamage, String newMetaType) {
		type = newType;
		radius = newRadius;
		moveSpeed = newMoveSpeed;
		metaType = newMetaType;
		killable = isKillable;
		bitmap = GameActivity.makeTransparent(BitmapFactory.decodeResource(GameActivity.gameContext.getResources(), newBitMapLink));
		setFrozenBMP(bitmap);
		maxHitPoints = newHP;
		damage = newDamage;
	}
	
	public UnitType(String newType, String newMetaType, int newRadius, float newMoveSpeed, boolean isKillable, Bitmap newBitMap, int newHP, int newDamage) {
		setFrozenBMP(bitmap);
		type = newType;
		radius = newRadius;
		moveSpeed = newMoveSpeed;
		metaType = newMetaType;
		killable = isKillable;
		bitmap = newBitMap;
		maxHitPoints = newHP;
		damage = newDamage;
	}
	
	public UnitType(String newType, int newRadius, float newMoveSpeed, boolean isKillable, int newColor, String newShape, int newHP, int newDamage) {
		type = newType;
		radius = newRadius;
		metaType = "Unit";
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
	private String metaType;
	private String shape;
	private Bitmap bitmap;
	
	//Unit stats
	private int maxHitPoints;
	private int damage;
	private float moveSpeed;
	
	public static void addUnitType(UnitType u) {
		getAllUnitTypes().add(u);
	}
	
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
	
	public String getMetaType() {
		return metaType;
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

	public static ArrayList<UnitType> getAllUnitTypes() {
		return allUnitTypes;
	}

	public static void setAllUnitTypes(ArrayList<UnitType> allUnitTypes) {
		UnitType.allUnitTypes = allUnitTypes;
	}

	public Bitmap getFrozenBMP() {
		return frozenBMP;
	}

	public void setFrozenBMP(Bitmap frozenBMP) {
		this.frozenBMP = frozenBMP;
	}
}