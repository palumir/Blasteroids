package com.DJG.fd;

import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
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

import com.DJG.abilities.Ability;
import com.DJG.units.Unit;
import com.DJG.units.UnitType;
import com.DJG.waves.Wave;

public class DisplayMessageActivity extends ActionBarActivity {
	
	// Convert red to transparent in a bitmap
	public static Bitmap makeTransparent(Bitmap bit) {
		int width =  bit.getWidth();
		int height = bit.getHeight();
		Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		int [] allpixels = new int [ myBitmap.getHeight()*myBitmap.getWidth()];
		bit.getPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(),myBitmap.getHeight());
		myBitmap.setPixels(allpixels, 0, width, 0, 0, width, height);

		for(int i =0; i<myBitmap.getHeight()*myBitmap.getWidth();i++){
		 if( allpixels[i] == Color.BLACK)

		             allpixels[i] = Color.alpha(Color.TRANSPARENT);
		 }

		  myBitmap.setPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
		  return myBitmap;
	}
	// Just a random
	static Random r = new Random();
	
	// Background
	private Bitmap background;
	private Canvas bgCanvas;
	
	// Shared data.
	static SharedPreferences prefs;
	public static int bgColor = Color.BLACK;
    
	// The current game thread.
	private static String mode = "Survival";
	public static Context survContext;
	private Thread gameThread;
	private volatile static boolean gameOver;
	
	// Grabbed units (two, one for each hand);
	public static Ability grabbedAbility = null;
	public static float grabbedAbilityX;
	public static float grabbedAbilityY;
	public static Ability secondGrabbedAbility = null;
	public static float secondGrabbedAbilityX;
	public static float secondGrabbedAbilityY;
	private static Unit grabbedUnit = null;
	private static Unit secondGrabbedUnit = null;
	
	// The current view.
	private static View currentView;
	private static int screenHeight;
	private static int screenWidth;
	
	// Text, that's all.
	public static String levelText;
	public static String castleHP;
	public static String highScoreText;
	public static String previousHighScoreText;
	
	// Has this guy lost yet?
	public static boolean lost;
	public static double lostTime;
	public static int loseDuration = 2500;
	
	// List of all units. This list is constantly redrawn.
	public static boolean doOnce = true;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) { 		
		
		float pos1 = event.getX(event.findPointerIndex(event.getPointerId(0)));
		float pos2 = event.getY(event.findPointerIndex(event.getPointerId(0)));
    	int action = MotionEventCompat.getActionMasked(event);
    	
		// Respond to a single touch event
	    if(event.getPointerCount() <= 1) {
	    	if(action == android.view.MotionEvent.ACTION_DOWN) {
	    		if(grabbedAbility == null) {
	    			grabbedAbility = Ability.getAbilityAt(pos1,pos2);
	    		}
	    		grabbedAbilityX = pos1;
	    		grabbedAbilityY = pos2;
	    		grabbedUnit = Unit.getUnitAt(pos1,pos2);
	    	}
	    	else if(action == android.view.MotionEvent.ACTION_UP) {
	    		if(grabbedAbility != null && grabbedAbility != Ability.getAbilityAt(pos1,pos2)) {
	    			grabbedAbility.useAbility(pos1,pos2);
	    			grabbedAbility = null;
	    		}
	    		if(grabbedUnit != null && grabbedUnit.getKillable()) {
	    			grabbedUnit.die();
	    		}
	    	}
	    	else if(grabbedAbility != null) {
	    		grabbedAbilityX = pos1;
	    		grabbedAbilityY = pos2;
	    	}
	    }
	    
	    // Respond to a multitouch event.
	    if(event.getPointerCount() > 1) {
			float pos1Second = event.getX(event.findPointerIndex(event.getPointerId(1)));
			float pos2Second = event.getY(event.findPointerIndex(event.getPointerId(1)));
			
			
		    if(action == android.view.MotionEvent.ACTION_POINTER_DOWN) {
		    	// First touch.
		    	if(grabbedAbility == null) {
		    		grabbedAbility = Ability.getAbilityAt(pos1,pos2);
		    	}
	    		grabbedAbilityX = pos1;
	    		grabbedAbilityY = pos2;
		    	grabbedUnit = Unit.getUnitAt(pos1,pos2);
		    	
		    	// Second touch.
		    	if(secondGrabbedAbility == null) {
		    		secondGrabbedAbility = Ability.getAbilityAt(pos1Second,pos2Second);
		    	}
	    		secondGrabbedAbilityX = pos1Second;
	    		secondGrabbedAbilityY = pos2Second;
		    	secondGrabbedUnit = Unit.getUnitAt(pos1Second,pos2Second);
		    }
		    else if(action == android.view.MotionEvent.ACTION_POINTER_UP) {
		    	
		    	// First touch.
		    	if(grabbedAbility != null && grabbedAbility != Ability.getAbilityAt(pos1,pos2)) {
		    		grabbedAbility.useAbility(pos1,pos2);
		    		grabbedAbility = null;
		    	}
		    	if(grabbedUnit != null && grabbedUnit.getKillable()) {
		    		grabbedUnit.die();
		    	}
		    	
		    	// Second touch.
	    		if(secondGrabbedAbility != null && secondGrabbedAbility != Ability.getAbilityAt(pos1Second,pos2Second)) {
		    		secondGrabbedAbility.useAbility(pos1Second,pos2Second);
		    		secondGrabbedAbility = null;
		    	}
	    		if(secondGrabbedUnit != null && secondGrabbedUnit.getKillable()) {
	    			secondGrabbedUnit.die();
	    		}
		    }
	    	else if(grabbedAbility != null) {
	    		grabbedAbilityX = pos1;
	    		grabbedAbilityY = pos2;
	    	}
	    	else if(secondGrabbedAbility != null) {
	    		secondGrabbedAbilityX = pos1Second;
	    		secondGrabbedAbilityY = pos2Second;
	    	}
	    }
	    return true;
	}
	
	
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		  	Bundle b = getIntent().getExtras();
		  	int levelStart = 0;
		  	if (b!=null){
		  		levelStart = b.getInt("level");
		  	}
	         super.onCreate(savedInstanceState);
	         requestWindowFeature(Window.FEATURE_NO_TITLE);
	         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                                 WindowManager.LayoutParams.FLAG_FULLSCREEN);
	         prefs = this.getSharedPreferences("flickOffGame", Context.MODE_PRIVATE);
	         View v = new gameView(this);
	         setContentView(v);
	         currentView = v;

			 if(doOnce) { 
				 survContext = this.getApplicationContext();
				 gameOver = false;
				 levelText = "Wave " + (Wave.getCurrentWaveNumber() + 1);
				 highScoreText = "";
				 previousHighScoreText = "";
				 castleHP = "Health ";
				 initGame(levelStart);
				 playGame();
				 doOnce = false;
			 }
			 else {
			 }
	  }
	  
	  void drawText(Canvas canvas, Paint myPaint) {
        	myPaint.setStyle(Paint.Style.FILL);
        	myPaint.setStrokeWidth(3);
        	myPaint.setTextSize(50);
        	myPaint.setColor(Color.WHITE);
        	canvas.drawText(levelText,50f,50f,myPaint);
        	canvas.drawText(highScoreText,50f,100f,myPaint);
        	canvas.drawText(previousHighScoreText,50f,150f,myPaint);
        	canvas.drawText(castleHP, 50f, (float)(screenHeight-50) ,myPaint );
	  }
	  
	  public static boolean isOffScreen(float x, float y) {
		  if(x < 0 || x > getScreenWidth() || y < 0|| y > getScreenHeight()) {
			  return true;
		  }
		  return false;
	  }
	  
	  public static boolean isCloseOffScreen(float x, float y) {
		  if(x < -50 || x > getScreenWidth() + 50 || y < -50 || y > getScreenHeight() + 50) {
			  return true;
		  }
		  return false;
	  }
	  
	  void drawBackground(Canvas canvas, Paint myPaint) {
          canvas.drawColor(bgColor);
          if(bgCanvas==null) {
        	  background = Bitmap.createBitmap(getScreenWidth(),getScreenHeight(),Bitmap.Config.ARGB_8888);
        	  bgCanvas = new Canvas(background);
	          myPaint.setStrokeWidth(1);
	          myPaint.setColor(Color.WHITE);
	          int x = 0;
	          while(x<getScreenWidth()) {
	        	  int y = 0;
	        	  int n = 0;
	        	  while(y<getScreenHeight()) {
	        		  if(r.nextInt(getScreenHeight()) == 0) {
	        			  n++;
	            		  bgCanvas.drawPoint(x,y,myPaint);
	        		  }
	        		  if(n>10) {
	        			 break;
	        		  }
	        		  y++;
	        	  }
	        	  x++;
	          }
          }
          canvas.drawBitmap(background,0,0,myPaint);
	  }
	 
	  private class gameView extends View {
	 
	      public gameView(Context context) {
	          super(context);
	      }
	 
	      @Override
	      protected void onDraw(Canvas canvas) {
	          Paint myPaint = new Paint();
	          
	          // Draw background.
	          	drawBackground(canvas, myPaint);
	          
	          // Draw our text.
	          drawText(canvas, myPaint);
  	        
     	      // Draw ability slots/icons.
       	      Ability.drawAbilities(canvas, myPaint);
  	        	
  	          // Draw all of our units.
    	      Unit.drawUnits(canvas, myPaint);
      	       
   	          // Draw abilities.
      	      Ability.drawAbilityAnimations(canvas, myPaint);
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
	
	// Set that they lost.
	public static void setLost() {
		lost = true;
		Unit.destroyPlanet();
		lostTime = System.currentTimeMillis();
	}
	
	public static double getTimeLost() {
		return lostTime;
	}
	
	public static boolean getLost() {
		return lost;
	}
	
	// Get screen height and width.
	public static int getScreenHeight() {
		return screenHeight;
	}
	
	public static int getScreenWidth() {
		return screenWidth;
	}
	
	public static String getMode() {
		return mode;
	}
	
	public static int getLoseDuration() {
		return loseDuration;
	}
	
	void initGame(int levelStart) {
		UnitType.initUnitTypes();
		// Put the Castle in the middle.
		Display display = getWindowManager().getDefaultDisplay();
		lost = false;
		lostTime = 0;
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();
	    Unit u = new Unit("Fortress","Castle",screenWidth/2,screenHeight/2);
	    u.setOnScreen();
	    Wave.initWaves(levelStart);
	    Ability.initAbilities();
	}
	
	void checkIfLost() {
		if(DisplayMessageActivity.getLost() && (System.currentTimeMillis() - DisplayMessageActivity.getTimeLost() > loseDuration)) {
			DisplayMessageActivity.youLose();
		}
	}
	
	void playGame() {
		gameThread=new Thread(new Runnable() {
			public void run() {
				while(!gameOver){
					updateAllStuff();
					checkIfLost();
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
	
	 public static void youLose() {
		 gameOver = true;
		 doOnce = true;
		 levelText = "Wave " + (Wave.getCurrentWaveNumber()+1) + " defeated you.";
		 int currHighScore = prefs.getInt("highScoreSurvival", 0);
		 if((Wave.getCurrentWaveNumber() + 1) > currHighScore)  {
			 Editor editor = prefs.edit();
			 editor.putInt("highScoreSurvival", (Wave.getCurrentWaveNumber() + 1));
			 highScoreText = "New high score!";
			 previousHighScoreText = "Previous: Wave " + currHighScore + ".";
			 editor.commit();
		 }
		 else {
			 highScoreText = "Current high score: Wave " + currHighScore + ".";
			 previousHighScoreText = "";
		 }
		 Unit.destroyAllUnits(); // Don't request the lock because the caller is already locking it.
		 Wave.destroyWaves();
		 Ability.clearAbilities();
	 }
	
	void updateAllStuff() {
		// Unleash the waves.
		if(lost==false) {
			Wave.sendWaves();
		}
		
		// Update abilities.
		Ability.updateAbilities();
		
		// Update units.
		Unit.updateUnits();
	}

}
