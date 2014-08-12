package com.DJG.units;

import java.util.ArrayList;
import java.util.Arrays;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.DJG.abilities.Ability;
import com.DJG.abilities.Bomb;
import com.DJG.abilities.Drop;
import com.DJG.abilities.Slow;
import com.DJG.fd.GameActivity;
import com.DJG.planets.Planet;
import com.DJG.waves.Wave;

public class Unit {
	// Global stuff.
	public static ArrayList<Unit> moons = new ArrayList<Unit>();
	public final static Object moonsLock = new Object();
	public static ArrayList<Unit> projectiles = new ArrayList<Unit>();
	public final static Object projectilesLock = new Object();
	public static ArrayList<Unit> allUnits = new ArrayList<Unit>();
	public static ArrayList<Unit> onScreenUnits = new ArrayList<Unit>();
	public final static Object onScreenUnitsLock = new Object();
	public final static Object allUnitsLock = new Object(); // A lock so we
															// don't fuck up the
															// allUnits

	// Unit General Information:
	private String name;
	private String type; // Chose not to simply store this as a UnitType incase
							// we want to modify individual
							// fields to be unique values. In other words, we
							// may want to change an Ogre to be
	// Position Information: // really big, but we don't want to have to add a
	// new UnitType every time we do that!
	private boolean onScreen = false;
	private float x;
	private float y;
	private float xNew;
	private float yNew;
	private int radius;
	private float moveSpeed;
	private float oldMoveSpeed;
	private float spinSpeed;
	private float oldSpinSpeed;
	private float xMomentum = 0;
	private float yMomentum = 0;
	private float fixedRadius = 0;

	// Projectile Information
	private Unit target = null;
	private boolean isAttacked = false;

	// Animator
	private UnitAnimation unitAnimation = null;

	// Combat information:
	private boolean suckedIn = false;
	private boolean killable = false;

	// Drawing information:
	private Bitmap frozenBMP;
	private String shape;
	private String metaType;
	public int color;
	private Bitmap bmp;

	// Freezing stats
	private Bitmap oldbmp;
	private long timeFrozen = 0;
	private boolean isFrozen = false;
	private long frozenDuration = 0;

	// Unit Stats
	private int currentHitPoints;
	private int maxHitPoints;
	private int damage;

	public Unit(String newName, String newType, float xSpawn, float ySpawn) {
		// Look up the UnitType and s et the values.
		UnitType u = UnitType.getUnitType(newType);
		radius = u.getRadius();
		type = u.getType();
		moveSpeed = u.getMoveSpeed();
		killable = u.getKillable();
		shape = u.getShape();
		spinSpeed = 0;
		bmp = u.getBMP();
		frozenBMP = u.getFrozenBMP();
		metaType = u.getMetaType();
		// Stats
		maxHitPoints = u.getMaxHitPoints();
		currentHitPoints = maxHitPoints;
		damage = u.getDamage();
		color = u.getColor();

		// Set it's coordinates.
		name = newName;
		type = newType;
		x = xSpawn;
		y = ySpawn;
		xNew = xSpawn;
		yNew = ySpawn;

		// Add it to the list of units to be drawn.
		synchronized(allUnitsLock) {
			addUnit(this);
		}
		if (metaType == "Projectile") {
			synchronized (projectilesLock) {
				projectiles.add(this);
			}
		}
	}

	public Unit(String newName, String newType, float xSpawn, float ySpawn,
			float spin) {
		// Look up the UnitType and set the values.
		UnitType u = UnitType.getUnitType(newType);
		radius = u.getRadius();
		type = u.getType();
		moveSpeed = u.getMoveSpeed();
		killable = u.getKillable();
		shape = u.getShape();
		spinSpeed = spin;
		bmp = u.getBMP();
		metaType = u.getMetaType();
		// Stats
		maxHitPoints = u.getMaxHitPoints();
		currentHitPoints = maxHitPoints;
		damage = u.getDamage();
		color = u.getColor();
		frozenBMP = u.getFrozenBMP();

		// Set it's coordinates.
		name = newName;
		type = newType;
		x = xSpawn;
		y = ySpawn;
		xNew = xSpawn;
		yNew = ySpawn;

		// Add it to the list of units to be drawn.
		synchronized(allUnitsLock) {
			addUnit(this);
		}
	}

	public void fixate(Unit u) {
		target = u;
	}

	public String getMetaType() {
		return metaType;
	}

	public void freeze(long time) {
		timeFrozen = GameActivity.getGameTime();
		frozenDuration = time;
		if (!isFrozen) {
			oldbmp = this.getBMP();
			bmp = this.frozenBMP;
		}
		isFrozen = true;
	}

	public void moveNormally(float xGo, float yGo) {
		xNew = xGo;
		yNew = yGo;
		// fix the radius, if it has no move speed
		if (moveSpeed == 0) {
			float yDistance = (yNew - y);
			float xDistance = (xNew - x);
			fixedRadius = (float) Math.sqrt(yDistance * yDistance + xDistance
					* xDistance);
		}
	}

	public void moveInstantly(float xGo, float yGo) {
		x = xGo;
		y = yGo;
		xNew = xGo;
		yNew = yGo;
	}

	public void setOnScreen() {
		synchronized (onScreenUnitsLock) {
			this.onScreen = true;
			onScreenUnits.add(this);
		}
	}

	public void moveUnit(Planet planet) {
		// If the unit is a projectile, follow the unit.
		if (target != null) {
			xNew = target.getX();
			yNew = target.getY();
		}
	
		float gravity = planet.getGravity();
		if (timeFrozen != 0
				&& GameActivity.getGameTime() - timeFrozen > frozenDuration) {
			isFrozen = false;
			this.bmp = this.oldbmp;
		}
		if (!isFrozen) {
			float yDistance = (yNew - y);
			float xDistance = (xNew - x);
			float step = moveSpeed * gravity;
			float distanceXY = (float) Math.sqrt(yDistance * yDistance
					+ xDistance * xDistance); // It should take this many frames
												// to get there.

			// The unit needs to be drawn if it's on screen.
			if (!GameActivity.isCloseOffScreen(x, y) && !onScreen) {
				setOnScreen();
			}

			// Log.d("On Screen",onScreenUnits.size()+"");
			// Log.d("All",allUnits.size()+"");

			// Move the unit.
			if (xNew != x || yNew != y) {

				if (xDistance < 0) {
					x = x + (-1) * Math.abs(xDistance / distanceXY) * step;
				} else {
					x = x + Math.abs(xDistance / distanceXY) * step;
				}

				// Deal with negatives.
				if (yDistance < 0) {
					y = y + (-1) * Math.abs(yDistance / distanceXY) * step;
				} else {
					y = y + Math.abs(yDistance / distanceXY) * step;
				}
			}
			// Spin the Unit
			if (spinSpeed != 0) {
				yDistance = (yNew - y);
				xDistance = (xNew - x);
				distanceXY = (float) Math.sqrt(yDistance * yDistance
						+ xDistance * xDistance);
				x = x + spinSpeed * yDistance / (distanceXY);
				y = y + spinSpeed * (0 - xDistance) / (distanceXY);
			}
			// Refix the radius
			if (fixedRadius != 0) {
				yDistance = (yNew - y);
				xDistance = (xNew - x);
				distanceXY = (float) Math.sqrt(yDistance * yDistance
						+ xDistance * xDistance);
				float ratio = fixedRadius / distanceXY;
				float goalX = ratio * xDistance;
				float goalY = ratio * yDistance;
				x = xNew - goalX;
				y = yNew - goalY;
			}

			// Just move it if it's close.
			if (Math.abs(yDistance) < step && Math.abs(xDistance) < step) {
				x = xNew;
				y = yNew;
			}
		}
		// Move based on Momentum, fozen units can move. Fixed radius not
		// affected for now
		if (fixedRadius == 0) {
			x += xMomentum;
			y += yMomentum;
			xMomentum -= xMomentum / 16;
			yMomentum -= yMomentum / 16;
		}

	
	}

	// Add a new unit to the list of all units to be drawn in animation.
	public static void addUnit(Unit newUnit) {
		synchronized (allUnitsLock) {
			allUnits.add(newUnit);
		}
	}

	public static Unit getUnit(String nameToSearch) {
		synchronized (onScreenUnitsLock) {
			Unit foundUnit = null;
			for (int j = 0; j < onScreenUnits.size(); j++) {
				Unit u = onScreenUnits.get(j);
				if (u.getName() == nameToSearch) {
					foundUnit = u;
					break;
				}
			}
			return foundUnit;
		}
	}

	public static ArrayList<Unit> getMoons() {
		return moons;
	}
	
	public static void addMoon(Unit u){
		synchronized (moonsLock) {
			moons.add(u);
		}
	}

	public static int getUnitPos(Unit thisUnit) {
		synchronized (allUnitsLock) {
			int foundUnit = 0;
			for (int j = 0; j < allUnits.size(); j++) {
				Unit u = allUnits.get(j);
				if (u == thisUnit) {
					break;
				}
				foundUnit++;
			}
			return foundUnit;
		}
	}

	public static void dontDrawUnit(Unit u) {
		if (onScreenUnits.size() != 0) {
			synchronized (onScreenUnitsLock) {
				int foundUnit = 0;
				for (int j = 0; j < onScreenUnits.size(); j++) {
					Unit v = onScreenUnits.get(j);
					if (u == v) {
						break;
					}
					foundUnit++;
				}
				if (foundUnit < onScreenUnits.size()) {
					onScreenUnits.remove(foundUnit);
				}
			}
		}
	}

	public static void killUnit(Unit u) {
		if (allUnits.size() != 0) {
			synchronized (allUnitsLock) {
				int foundUnit = 0;
				for (int j = 0; j < allUnits.size(); j++) {
					Unit v = allUnits.get(j);
					if (u == v) {
						break;
					}
					foundUnit++;
				}
				if (foundUnit < allUnits.size()) {
					allUnits.remove(foundUnit);
				}
			}
		}
	}

	public static void killProj(Unit u) {
		if (projectiles.size() != 0) {
			synchronized (projectilesLock) {
				int foundUnit = 0;
				for (int j = 0; j < projectiles.size(); j++) {
					Unit v = projectiles.get(j);
					if (u == v) {
						break;
					}
					foundUnit++;
				}
				if (foundUnit < projectiles.size()) {
					projectiles.get(foundUnit).target.isAttacked = false;
					projectiles.remove(foundUnit);
				}
			}
		}
	}
	
	public static void killMoon(Unit u) {
		if (moons.size() != 0) {
			synchronized (moonsLock) {
				int foundUnit = 0;
				for (int j = 0; j < moons.size(); j++) {
					Unit v = moons.get(j);
					if (u == v) {
						break;
					}
					foundUnit++;
				}
				if (foundUnit < moons.size()) {
					moons.remove(foundUnit);
				}
			}
		}
	}

	public static boolean isGreaterThan(String a, String b) {
		String[] order = { "Asteroid", "Ice Asteroid", "Cat", "Fire Asteroid" };
		int posA = Arrays.asList(order).indexOf(a);
		int posB = Arrays.asList(order).indexOf(b);
		return posA > posB;
	}

	// Get the selected unit at the coordinates.
	public static Unit getUnitAt(float x, float y) {
		synchronized (onScreenUnitsLock) {

			Unit closestUnit = null;
			Float closestDistance = 100000f;

			// Get all the close units.
			for (int j = 0; j < onScreenUnits.size(); j++) {
				Unit u = onScreenUnits.get(j);
				float yDistance = (u.getY() - y);
				float xDistance = (u.getX() - x);
				float distanceXY = (float) Math.sqrt(yDistance * yDistance
						+ xDistance * xDistance);
				if (distanceXY <= 60 + u.getRadius()
						&& u.getName() != "Fortress" && u.getRadius() <= 51) {
					if ((closestUnit == null)
							|| (distanceXY < closestDistance && u.getType() == closestUnit
									.getType())
							|| (isGreaterThan(u.getType(),
									closestUnit.getType()))) {
						closestUnit = u;
						closestDistance = distanceXY;
					}
				}
				if (distanceXY <= 10 + u.getRadius()
						&& u.getName() != "Fortress" && u.getRadius() > 51) {
					if ((closestUnit == null)
							|| (distanceXY < closestDistance && u.getType() == closestUnit
									.getType())
							|| (isGreaterThan(u.getType(),
									closestUnit.getType()))) {
						closestUnit = u;
						closestDistance = distanceXY;
					}
				}
			}

			return closestUnit;
		}
	}

	public void suckedIn(boolean tf) {
		suckedIn = tf;
	}

	public boolean getSuckedIn() {
		return suckedIn;
	}

	public static int numUnits() {
		return allUnits.size();
	}

	static void checkIfHitCastle(Unit castle, Unit u) {
		if (u.getMetaType() == "Unit") {
			float castleY = 0;
			float castleX = 0;
			float castleRadius = 0;
			if (castle != null) {
				castleY = castle.getY();
				castleX = castle.getX();
				castleRadius = castle.getRadius();
			}
			float yDistanceUnit = (castleY - u.getY());
			float xDistanceUnit = (castleX - u.getX());
			float distanceXYUnit = (float) Math.sqrt(yDistanceUnit
					* yDistanceUnit + xDistanceUnit * xDistanceUnit);
			if (distanceXYUnit <= castleRadius + u.getRadius()) {
				u.attacks(castle);
				u.die();
				GameActivity.castleHP = "" + castle.getHP();
				if (castle.isDead()) {
					GameActivity.setLost();
				}
				Planet p = (Planet) castle;
				p.onCollison();
			} else {
			}
		}
	}

	static void checkIfHitProjectile(Unit u) {
		synchronized (projectilesLock) {
			for (int j = 0; j < projectiles.size(); j++) {
				Unit currentProj = projectiles.get(j);
				float yDistanceUnit = (currentProj.getY() - u.getY());
				float xDistanceUnit = (currentProj.getX() - u.getX());
				float distanceXYUnit = (float) Math.sqrt(yDistanceUnit
						* yDistanceUnit + xDistanceUnit * xDistanceUnit);
				if (distanceXYUnit <= currentProj.getRadius() + u.getRadius()
						&& u.getMetaType() == "Unit"
						&& u.getName() != "Fortress") {
					currentProj.die();
					u.die();
				}
			}
		}
	}

	static void checkIfHitMoon(Unit u) {
		synchronized (moonsLock) {
			for (int j = 0; j < moons.size(); j++) {
				Unit m = moons.get(j);
				float yDistanceUnit = (m.getY() - u.getY());
				float xDistanceUnit = (m.getX() - u.getX());
				float distanceXYUnit = (float) Math.sqrt(yDistanceUnit
						* yDistanceUnit + xDistanceUnit * xDistanceUnit);
				if (distanceXYUnit <= m.getRadius() + u.getRadius()
						&& u.getMetaType() == "Unit"
						&& u.getName() != "Fortress") {
					m.die();
					u.die();
				}
			}
		}
	}
	
	public Boolean isMoon(){
		return getType().contains("Moon");
	}

	public static void destroyAllUnits() {
		allUnits.clear();
		onScreenUnits.clear();
	}

	public static void drawEarth(Canvas canvas, Paint myPaint) {
		synchronized (onScreenUnitsLock) {
			for (int j = 0; j < onScreenUnits.size(); j++) {
				Unit currentUnit = onScreenUnits.get(j);
				if (currentUnit.getName() == "Fortress"
						&& !GameActivity.getLost()) {
					myPaint.setStrokeWidth(1);
					myPaint.setStyle(Paint.Style.STROKE);
					myPaint.setColor(currentUnit.color);
					// Draw Earth!
					canvas.drawBitmap(currentUnit.getBMP(), currentUnit.getX()
							- currentUnit.getRadius(), currentUnit.getY()
							- currentUnit.getRadius(), null);
				}
			}
		}
	}

	public static void drawUnits(Canvas canvas, Paint myPaint) {
		synchronized (onScreenUnitsLock) {
			for (int j = 0; j < onScreenUnits.size(); j++) {
				Unit currentUnit = onScreenUnits.get(j);
				if (currentUnit.getName() != "Fortress") {
					// What shape do we draw?
					myPaint.setColor(currentUnit.color);
					if (currentUnit.getBMP() != null) {
						canvas.drawBitmap(currentUnit.getBMP(),
								currentUnit.getX() - currentUnit.getRadius(),
								currentUnit.getY() - currentUnit.getRadius(),
								null);
					} else if (currentUnit.getShape() == "Circle") {
						canvas.drawCircle(currentUnit.getX(),
								currentUnit.getY(), currentUnit.getRadius(),
								myPaint);
					} else if (currentUnit.getShape() == "Square") {
						canvas.drawRect(
								currentUnit.getX() - currentUnit.getRadius(),
								currentUnit.getY() - currentUnit.getRadius(),
								currentUnit.getX() + currentUnit.getRadius(),
								currentUnit.getY() + currentUnit.getRadius(),
								myPaint);
					} else if (currentUnit.getShape() == "Plus") {
						myPaint.setStrokeWidth(6);
						canvas.drawLine(
								currentUnit.getX() + currentUnit.getRadius()
										/ 2,
								currentUnit.getY() - currentUnit.getRadius()
										/ 2,
								currentUnit.getX() + currentUnit.getRadius()
										/ 2,
								currentUnit.getY() + currentUnit.getRadius()
										* 2 - currentUnit.getRadius() / 2,
								myPaint);
						canvas.drawLine(
								currentUnit.getX() - currentUnit.getRadius()
										/ 2,
								currentUnit.getY() + currentUnit.getRadius()
										/ 2,
								currentUnit.getX() + currentUnit.getRadius()
										* 2 - currentUnit.getRadius() / 2,
								currentUnit.getY() + currentUnit.getRadius()
										/ 2, myPaint);
					} else if (currentUnit.getShape() == "Triangle") {
						canvas.drawCircle(currentUnit.getX(),
								currentUnit.getY(), currentUnit.getRadius(),
								myPaint);
					}
				}
			}
		}
	}

	public static void destroyPlanet() {
		Bomb b = new Bomb(GameActivity.getScreenWidth() / 2,
				GameActivity.getScreenHeight() / 2,
				GameActivity.getScreenHeight() / 2 + 1,
				GameActivity.getLoseDuration());
	}

	public void setSpinSpeed(float s) {
		oldSpinSpeed = spinSpeed;
		spinSpeed = s;
	}

	public void setMoveSpeed(float f) {
		oldMoveSpeed = moveSpeed;
		moveSpeed = f;
	}

	public float getOldSpinSpeed() {
		return oldSpinSpeed;
	}

	public UnitAnimation getUnitAnimation() {
		if (unitAnimation == null) {
			return null;
		}
		return unitAnimation;
	}

	public float getOldMoveSpeed() {
		return oldMoveSpeed;
	}

	public void animate(String type) {
		this.unitAnimation = new UnitAnimation(this, type);
	}

	public void unAnimate() {
		this.unitAnimation = null;
	}

	public static void updateUnits() {
		// Where is the castle?
		Planet castle = GameActivity.getFortress();
		GameActivity.castleHP = "" + castle.getHP();

		synchronized (Unit.onScreenUnitsLock) {
			for (int j = 0; j < onScreenUnits.size(); j++) {
				Unit u = onScreenUnits.get(j);

				// Kill projectiles with dead units
				if (u.getMetaType() == "Projectile" && u.target.isDead()) {
					u.die();
				}

				// Animate?
				UnitAnimation.animateUnit(u);

				if (u.getName() != "Fortress") {
					checkIfHitCastle(castle, u);
				}
				if (u.getName() != "Fortress" && u.getMetaType() == "Unit") {
					// Check if we have hit any abilities.
					Ability.checkIfHitAbility(u);
				}
			}
		}

		synchronized (Unit.allUnits) {
			Planet fort = GameActivity.getFortress();
			for (int j = 0; j < allUnits.size(); j++) {
				Unit u = allUnits.get(j);
				if (u instanceof UnitSpawner) {
					((UnitSpawner) u).spawnNewUnits();
				}
				if (u.getName() != "Fortress") {
					// Check if we have hit the castle.
					u.moveUnit(fort);

					// Check if we hit a projectile.
					checkIfHitProjectile(u);
				}
				
				  if(!u.isMoon()){
					  checkIfHitMoon(u); // SO IS THIS. //Lol
				  }
			  }
			
		}
	}

	// Methods involving stats
	public Boolean isDead() {
		return currentHitPoints <= 0;
	}

	private void takeDamage(int damage) {
		currentHitPoints -= damage;
	}

	public void attacks(Unit u) {
		if (getDamage() > 0) {
			if (u.getHP() > 100) {
				u.currentHitPoints = 100;
				u.takeDamage(getDamage());
			} else if (u.getHP() - getDamage() < 0) {
				u.currentHitPoints = 0;
			} else if (u.getHP() <= 100 && u.getHP() > 0) {
				u.takeDamage(getDamage());
			}
		}
		if (getDamage() < 0) {
			if (u.getHP() - getDamage() > 100) {
				u.currentHitPoints = 100;
			}
			if (u.getHP() < 0) {
				currentHitPoints = 0;
			}
		}
	}

	public void hurt(int i) {
		this.currentHitPoints -= i;
		if (getHP() <= 0 && killable) {
			die();
		}
	}

	public void despawn() {
		// Kill the old unit.
		Bomb b = new Bomb(this.getX(), this.getY(), 50, 250, Color.RED, Color.YELLOW);
		dontDrawUnit(this);
		killUnit(this);
		Wave.killUnit(this);
	}

	public void die() {

		// Give an ability, if it's an ability drop dying.
		if (getMetaType() == "Ability Drop") {
			Drop.dropRespond(getType(), this.getX(), this.getY());
		}

		// Drop an ability maybe?
		Drop.potentiallyDropItem(this);

		// Do special things for special units.
		if (type.startsWith("Fire")) {
			Bomb b = new Bomb(this.getX(), this.getY(), 100, 500, Color.RED, Color.YELLOW);
		}
		if (type.equals("Bomb Bullet")) {
			Bomb b = new Bomb(this.getX(), this.getY(), 100, 500, Color.RED, Color.YELLOW);
		}
		if (type == "Ice Asteroid") {
			Slow s = new Slow(this.getX(), this.getY(), 200, 1500);
		}
		if (type == "MultiClicker 1") {
			Wave.addToCurrentWave(new Unit("Any Name", "MultiClicker 2", this
					.getX(), this.getY(), spinSpeed));
			Wave.currentWaveAttackCastle();
		}
		if (type == "MultiClicker 2") {
			Wave.addToCurrentWave(new Unit("Any Name", "MultiClicker 3", this
					.getX(), this.getY(), spinSpeed));
			Wave.currentWaveAttackCastle();
		}
		if (type == "MultiClicker 3") {
			Wave.addToCurrentWave(new Unit("Any Name", "MultiClicker 4", this
					.getX(), this.getY(), spinSpeed));
			Wave.currentWaveAttackCastle();
		}
		if (type == "MultiClicker 4") {
			Bomb b = new Bomb(this.getX(), this.getY(), 150, 500, Color.RED, Color.YELLOW);
		}
		
		if (type == "Splitter Huge") {
			Wave.addToCurrentWave(new Unit("Any Name", "Splitter Big", this
					.getX(), this.getY(), spinSpeed));
			Wave.addToCurrentWave(new Unit("Any Name", "Splitter Big", this
					.getX() + this.getRadius() / 2 + 5, this.getY(), spinSpeed));
			Wave.addToCurrentWave(new Unit("Any Name", "Splitter Big", this
					.getX(), this.getY() + this.getRadius() / 2 + 5, spinSpeed));
			Wave.addToCurrentWave(new Unit("Any Name", "Splitter Big", this
					.getX() + this.getRadius() / 2 + 5, this.getY()
					+ this.getRadius() / 2 + 5, spinSpeed));
			Wave.currentWaveAttackCastle();
		}
		if (type == "Splitter Big") {
			Wave.addToCurrentWave(new Unit("Any Name", "Splitter Medium", this
					.getX(), this.getY(), spinSpeed));
			Wave.addToCurrentWave(new Unit("Any Name", "Splitter Medium", this
					.getX() + this.getRadius() / 2 + 5, this.getY(), spinSpeed));
			Wave.addToCurrentWave(new Unit("Any Name", "Splitter Medium", this
					.getX(), this.getY() + this.getRadius() / 2 + 5, spinSpeed));
			Wave.addToCurrentWave(new Unit("Any Name", "Splitter Medium", this
					.getX() + this.getRadius() / 2 + 5, this.getY()
					+ this.getRadius() / 2 + 5, spinSpeed));
			Wave.currentWaveAttackCastle();
		}
		if (type == "Splitter Medium") {
			Wave.addToCurrentWave(new Unit("Any Name", "Splitter Small", this
					.getX(), this.getY(), spinSpeed));
			Wave.addToCurrentWave(new Unit("Any Name", "Splitter Small", this
					.getX() + this.getRadius() / 2 + 5, this.getY() + 5, spinSpeed));
			Wave.addToCurrentWave(new Unit("Any Name", "Splitter Small", this
					.getX(), this.getY() + this.getRadius() / 2 + 5, spinSpeed));
			Wave.addToCurrentWave(new Unit("Any Name", "Splitter Small", this
					.getX() + this.getRadius() / 2 + 5, this.getY()
					+ this.getRadius() / 2 + 5, spinSpeed));
			Wave.currentWaveAttackCastle();
		}

		if (metaType == "Projectile") {
			killProj(this);
		}
		if (isMoon()) {
			killMoon(this);
		}

		// Kill the old unit.
		dontDrawUnit(this);
		killUnit(this);
		Wave.killUnit(this);
		currentHitPoints = -1;
	}

	public void setMomentum(int x, int y) {
		xMomentum = x;
		yMomentum = y;
	}

	public void knockBackFrom(int xPos, int yPos, int vel) {

		float yDistance = (y - yPos);
		float xDistance = (x - xPos);
		float distanceXY = (float) Math.sqrt(yDistance * yDistance + xDistance
				* xDistance);
		xMomentum = vel * xDistance / distanceXY;
		yMomentum = vel * yDistance / distanceXY;

	}

	// Methods to get values
	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	// Stats
	public int getHP() {
		return currentHitPoints;
	}

	public int getCurrentHitPoitns() {
		return currentHitPoints;
	}

	public int getMaxHitPoints() {
		return maxHitPoints;
	}

	public int getDamage() {
		return damage;
	}

	public boolean getKillable() {
		return killable;
	}

	public Bitmap getBMP() {
		return bmp;
	}

	public String getShape() {
		return shape;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getXNew() {
		return xNew;
	}

	public float getYNew() {
		return yNew;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int newRadius) {
		radius = newRadius;
	}

	public boolean isAttacked() {
		return isAttacked;
	}

	public void setAttacked(boolean isAttacked) {
		this.isAttacked = isAttacked;
	}
}