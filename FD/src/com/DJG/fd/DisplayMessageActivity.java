package com.DJG.fd;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class DisplayMessageActivity extends ActionBarActivity {
    
	// The current game thread.
	private Thread gameThread;
	private volatile static boolean gameOver = false;
	
	// Grabbed unit (with touch)
	private static Unit grabbedUnit = null;
	
	// The current view.
	private static View currentView;
	private static int screenHeight;
	private static int screenWidth;
	
	// List of all units. This list is constantly redrawn.
	public static ArrayList<Unit> allUnits = new ArrayList<Unit>();
	private final static Object allUnitsLock = new Object(); // A lock so we don't fuck up the allUnits
	public static boolean doOnce = true;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) { 
	    int action = MotionEventCompat.getActionMasked(event);
	    if(action == android.view.MotionEvent.ACTION_DOWN) {
	    	grabbedUnit = getUnitAt(event.getRawX(),event.getRawY());
	    }
	    else if(action == android.view.MotionEvent.ACTION_UP) {
	    	// WIP: THIS DOESN'T ACTUALLY SUFFICE FOR FLINGING. JUST FOR ALPHA.
	    	if(grabbedUnit != null && grabbedUnit.getKillable()) {
	    		grabbedUnit.die();
	    	}
	    }
	    return true;
	}
	
	
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		  
	         super.onCreate(savedInstanceState);
	         requestWindowFeature(Window.FEATURE_NO_TITLE);
	         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                                 WindowManager.LayoutParams.FLAG_FULLSCREEN);
	         View v = new gameView(this);
	         setContentView(v);
	         currentView = v;
			 if(doOnce) { 
				 initGame();
				 playGame();
				 doOnce = false;
			 }
			 else {
			 }
	  }
	 
	  private class gameView extends View {
	 
	      public gameView(Context context) {
	          super(context);
	      }
	 
	      @Override
	      protected void onDraw(Canvas canvas) {
	          Paint myPaint = new Paint();
	          myPaint.setStyle(Paint.Style.STROKE);
	          
	          // Draw all of our units (just one for now)
	          synchronized(allUnitsLock) {
	        	  for(Unit currentUnit : allUnits) {
	        	  	myPaint.setStrokeWidth(23);
	        	  	if(currentUnit.getName() == "Fortress") {
	        		  	myPaint.setStrokeWidth(10);
	        	  	}
	        	  	myPaint.setColor(currentUnit.color);
        		  	canvas.drawCircle(currentUnit.getX(), currentUnit.getY(), currentUnit.getRadius(), myPaint);
	          	}
	          }
	      }
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_display_message,
					container, false);
			return rootView;
		}
	}
	
	// Get the selected unit at the coordinates.
	public Unit getUnitAt(float x, float y) {
		for(Unit u : allUnits) {
			float yDistance = (u.getY() - y);
			float xDistance = (u.getX() - x);
			float distanceXY = (float)Math.sqrt(yDistance*yDistance + xDistance*xDistance);
			if(distanceXY <= 50 + u.getRadius() && u.getName() != "Fortress") {
				return u;
			}
		}
		return null;
	}
	
	// Get screen height and width.
	public static int getScreenHeight() {
		return screenHeight;
	}
	
	public static int getScreenWidth() {
		return screenWidth;
	}
	
	// Add a new unit to the list of all units to be drawn in animation.
	public static void addUnit(Unit newUnit) {
		synchronized(allUnitsLock) {
			allUnits.add(newUnit);
		}
	}
	
	public static Unit getUnit(String nameToSearch) {
		Unit foundUnit = null;
		for(Unit u : allUnits) {
			if(u.getName() == nameToSearch) {
				foundUnit = u;
				break;
			}
		}
		return foundUnit;
	}
	
	public static int getUnitPos(Unit thisUnit) {
		int foundUnit = 0;
		for(Unit u : allUnits) {
			if(u == thisUnit) {
				break;
			}
			foundUnit++;
		}
		return foundUnit;
	}
	
	public static void killUnit(Unit u) {
		synchronized(allUnitsLock) {
			int pos = getUnitPos(u);
			allUnits.remove(pos);
		}
	}
	
	void initGame() {
		UnitType.initUnitTypes();
		
		// Put the Castle in the middle.
		Display display = getWindowManager().getDefaultDisplay();
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();
	    Unit u = new Unit("Fortress","Castle",screenWidth/2,screenHeight/2,0,Color.BLACK);
	    Wave.initWaves();
	}
	
	void playGame() {
		gameThread=new Thread(new Runnable() {
			public void run() {
				while(!gameOver){
					updateAllUnits();
					currentView.postInvalidate();
					try {
						Thread.sleep(10);
					}
					catch (Throwable t) {
					}
				}
			}
		});
		gameThread.start();
	}
	
	 void youLose() {
		 gameOver = true;
		 AlertDialog ad = new AlertDialog.Builder(this).create();
		 ad.setCancelable(false); // This blocks the 'BACK' button 
		 ad.setMessage("You lose, FAGGOT."); 
		 ad.setButton("OK", new DialogInterface.OnClickListener() {  
			    @Override  
			    public void onClick(DialogInterface dialog, int which) {  
			        dialog.dismiss();                      
			    }  
			});  
			ad.show();  
	 }
	
	void updateAllUnits() {
		// Unleash the waves.
		synchronized(Wave.currentWaveLock) {
			Wave.sendWaves();
		}
		
		synchronized(allUnitsLock) {
			// Where is the castle?
			Unit castle = getUnit("Fortress");
			for(Unit u : allUnits) {
			    float castleY = 0;
			    float castleX = 0;
			    float castleRadius = 0;
			    if(castle!=null) {
			    	castleY = castle.getY();
			    	castleX = castle.getX();
			    	castleRadius = castle.getRadius();
			    }
				float yDistance = (castleY - u.getY());
				float xDistance = (castleX - u.getX());
				float distanceXY = (float)Math.sqrt(yDistance*yDistance + xDistance*xDistance);
				if(distanceXY <= castleRadius + u.getRadius() && u.getName() != "Fortress") {
					youLose();
				}
				else {
					u.moveUnit();
				}
			}
		}
	}

}
