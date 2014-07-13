package com.DJG.screenelements;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.DJG.abilities.Coin;
import com.DJG.fd.GameActivity;
import com.DJG.fd.R;

public class ScreenElement {
	// Static button stuff
	public static Bitmap pauseBMP = GameActivity.makeTransparent(BitmapFactory.decodeResource(GameActivity.gameContext.getResources(), R.drawable.pause));
	public static Bitmap healthBMP = GameActivity.makeTransparent(BitmapFactory.decodeResource(GameActivity.gameContext.getResources(), R.drawable.health));
	public static Bitmap buttonTest = GameActivity.makeTransparent(BitmapFactory.decodeResource(GameActivity.gameContext.getResources(), R.drawable.buttontest));
	
	// Global stuff.
	public static ArrayList<ScreenElement> allScreenElements = new ArrayList<ScreenElement>();
	public static Object allScreenElementsLock = new Object();
	
	// ScreenElement General Information:
	private String activity = "Game";
	private String name;
	private String type;     // Chose not to simply store this as a ScreenElementType incase we want to modify individual
						     // fields to be unique values. In other words, we may want to change an Ogre to be
	// Position Information: // really big, but we don't want to have to add a new ScreenElementType every time we do that!
	private boolean onScreen = false;
	private float x;     
	private float y;     
	private float xNew;
	private float yNew;
	private int height;
	private int width;
	public float oldX;

	// Combat information:
	private boolean killable = false;
	
	// Drawing information:
	private String shape;
	public int color;
	private Bitmap bmp;
	
	// ScreenElement Stats
	private int currentHitPoints;
	private int maxHitPoints;
	private int damage;

	public ScreenElement(String newName, String newType, float xSpawn, float ySpawn, int newWidth, int newHeight, Bitmap newBMP) {
		
		// Set it's coordinates.
		name = newName;
		type = newType;
		x = xSpawn;
		y = ySpawn;
		xNew = xSpawn;
		yNew = ySpawn;
		width = newWidth;
		height = newHeight;
		bmp = newBMP;
		
		// Add it to the list of ScreenElements to be drawn.
		synchronized(allScreenElementsLock) {
			addScreenElement(this);
		}
	}
	
	public ScreenElement(String newName, String newType, float xSpawn, float ySpawn, int newWidth, int newHeight, Bitmap newBMP, String newActivity) {
		
		// Set it's coordinates.
		activity = newActivity;
		name = newName;
		type = newType;
		x = xSpawn;
		y = ySpawn;
		xNew = xSpawn;
		yNew = ySpawn;
		width = newWidth;
		height = newHeight;
		bmp = newBMP;
		
		// Add it to the list of ScreenElements to be drawn.
		synchronized(allScreenElementsLock) {
			addScreenElement(this);
		}
	}
	
	public void moveInstantly(float xGo, float yGo) {
		x = xGo;
		y = yGo;
		xNew = xGo;
		yNew = yGo;
	}

	
	// Add a new ScreenElement to the list of all ScreenElements to be drawn in animation.
		public static void addScreenElement(ScreenElement newScreenElement) {
			synchronized(allScreenElements) {
				allScreenElements.add(newScreenElement);
			}
		}
		
		public static ScreenElement getScreenElement(String nameToSearch) {
			synchronized(allScreenElementsLock) {
				ScreenElement foundScreenElement = null;
				for(int j = 0; j < allScreenElements.size(); j++) {
					ScreenElement u = allScreenElements.get(j);
					if(u.getName() == nameToSearch) {
						foundScreenElement = u;
						break;
					}
				}
				return foundScreenElement;
			}
		}
		
		
		public static int getScreenElementPos(ScreenElement thisScreenElement) {
			synchronized(allScreenElementsLock) {
				int foundScreenElement = 0;
				for(int j = 0; j < allScreenElements.size(); j++) {
					ScreenElement u = allScreenElements.get(j);
					if(u == thisScreenElement) {
						break;
					}
					foundScreenElement++;
				}
				return foundScreenElement;
			}
		}
		
		public static void killScreenElement(ScreenElement u) {
			if(allScreenElements.size()!=0){
				synchronized(allScreenElementsLock) {
					int foundScreenElement = 0;
					for(int j = 0; j < allScreenElements.size(); j++) {
						ScreenElement v = allScreenElements.get(j);
						if(u == v) {
							break;
						}
						foundScreenElement++;
					}
					if(foundScreenElement < allScreenElements.size()) {
						allScreenElements.remove(foundScreenElement);
					}
				}
			}
		}
		
		
		public void setOldX() {
			oldX = x;
		}
	
	// Get the selected ScreenElement at the coordinates.
	public static ScreenElement getScreenElementAt(float x, float y) {
		synchronized(allScreenElementsLock) {
			
			// Get all the close ScreenElements.
			for(int j = 0; j < allScreenElements.size(); j++) {
				ScreenElement u = allScreenElements.get(j);
				if(x > u.getX() - 50 && x < u.getX() + u.getWidth() + 50 && y > u.getY() - 50 && y < u.getY() + u.getHeight() + 50) {
					return u;
				}
			}
			return null;
		}
	}
	
	public static int numScreenElements() {
		return allScreenElements.size();
	}
	
	
	public static void destroyAllScreenElements() {
		allScreenElements.clear();
	}
	
	public void draw(Canvas canvas, Paint myPaint) {
		 canvas.drawBitmap(this.getBMP(), this.getX()-this.getWidth(), this.getY() - this.getHeight(), null);
	}
	
	public static void drawScreenElements(Canvas canvas, Paint myPaint, String activity) {
        synchronized(allScreenElementsLock) {
			for(int j = 0; j < allScreenElements.size(); j++) {
				ScreenElement currentScreenElement = allScreenElements.get(j);
      	  		// What shape do we draw?
      	  		myPaint.setColor(currentScreenElement.color);
      	  		if(currentScreenElement.getBMP() != null && currentScreenElement.getActivity() == activity) {
      	  			currentScreenElement.draw(canvas, myPaint);
      	  		}
        	}
        }
	}
	
	public static void updateScreenElements() {
		synchronized(ScreenElement.allScreenElements) {
			for(int j = 0; j < allScreenElements.size(); j++) {
				ScreenElement u = allScreenElements.get(j);
				
			}
		}
	}
	
	public void respondToTouch() {
		if(this.getName() == "Pause") {
			if(!GameActivity.paused) {
				try {
					GameActivity.paused = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					GameActivity.paused = false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// Methods to get values
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	// Stats
	public int getHP(){
		return currentHitPoints;
	}
	
	public int getCurrentHitPoitns(){
		return currentHitPoints;
	}
	
	public int getMaxHitPoints(){
		return maxHitPoints;
	}
	
	public int getDamage(){
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
	
	public String getActivity() {
		return activity;
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
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}

}