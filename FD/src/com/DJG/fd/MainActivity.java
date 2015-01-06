package com.DJG.fd;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.DJG.fd.touchevents.TouchEvent;
import com.DJG.screenelements.Background;
import com.DJG.screenelements.ScreenElement;
import com.DJG.screenelements.myButton;
import com.DJG.secrets.Secret;
import com.DJG.units.UnitType;

public class MainActivity extends ActionBarActivity {

	public final static String EXTRA_MESSAGE = "com.DJG.fd.MESSAGE";
	
	public Thread mainThread;
	public static DisplayMetrics metrics;
	public View currentView;
	// create a matrix for the manipulation
	public static double defaultWidth = 4.226;
	public static double density = 1;
	public static Matrix matrix;
	// resize the bit map
	private boolean doOnce = true;
	private static Context currContext;
	public ScreenElement highScoreText;
	
	// Sound
	public static SoundPool soundPool;
	
	// Globals
	public static SharedPreferences prefs;
	
	public void startSurvival(View view) {
		if(GameActivity.gameContext==null) {
			GameActivity.gameContext =  this.getApplicationContext();
		}
		GameActivity.gameContext =  this.getApplicationContext();
		GameActivity.setMode("Survival");
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
	
	public void startCampaign(View view) {
		if(GameActivity.gameContext==null) {
			GameActivity.gameContext =  this.getApplicationContext();
		}
		Intent intent = new Intent(this, CampaignActivity.class);
		startActivity(intent);
	}
	
	public void openStore(View view) {
		Intent intent = new Intent(this, Store.class);
		startActivity(intent);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		View v = new mainView(this);
		setContentView(v);
		currentView = v;
		if(doOnce) {
	    	metrics = getResources().getDisplayMetrics();
			currContext = v.getContext();
			initMain();
			playMain();
			doOnce = false;
		}
    }
    
    public static void startIntent(String i) {
		if(GameActivity.gameContext==null) {
			GameActivity.gameContext =  currContext.getApplicationContext();
		}
		GameActivity.gameContext =  currContext.getApplicationContext();
		if(i=="Play") {
			GameActivity.setMode("Survival");
			Intent intent = new Intent(currContext, GameActivity.class);
			currContext.startActivity(intent);
		}
		if(i=="Campaign") {
			Intent intent = new Intent(currContext, CampaignActivity.class);
			currContext.startActivity(intent);
		}
		if(i=="Upgrades") {
			Intent intent = new Intent(currContext, Store.class);
			currContext.startActivity(intent);
		}
		if(i=="Tutorial") {
			Intent intent = new Intent(currContext, Tutorial.class);
		    currContext.startActivity(intent);
		}
		if(i=="Credits") {
			Intent intent = new Intent(currContext, Options.class);
		    currContext.startActivity(intent);
			Options.creditCount++;
		}

    }
    
    void initMain() {
		if(GameActivity.gameContext==null) {
			GameActivity.gameContext =  this.getApplicationContext();
		}
		Display display = getWindowManager().getDefaultDisplay();
		GameActivity.setScreenWidth(display.getWidth());
		GameActivity.setScreenHeight(display.getHeight());
		float height = display.getHeight();
		float width = display.getWidth();
		
		// Get the inches of the device.
		int widthPixels = metrics.widthPixels;
		int heightPixels = metrics.heightPixels;
		float widthDpi = metrics.xdpi;
		float heightDpi = metrics.ydpi;
		float widthInches = widthPixels / widthDpi;
		float heightInches = heightPixels / heightDpi;
		double diagonalInches = Math.sqrt(
			    (widthInches * widthInches) 
			    + (heightInches * heightInches));
		density = diagonalInches/defaultWidth;
		if(matrix==null) {
			matrix = new Matrix();
			matrix.postScale((float)density, (float)density);
		}
		ScreenElement title =  new ScreenElement("Text","Blasteroids",width/2 - 200,height/8,"Main");
		title.setTextSize(GameActivity.getScreenWidth()/9);
		myButton survival = new myButton("Play",width/2,height/5,(width - width/1.7f),height/20,"Main");
		ScreenElement survivalText =  new ScreenElement("Text","Play",width/2 - 65,height/4.7f,"Main");
		survivalText.setTextSize(GameActivity.getScreenWidth()/12);
		myButton tutorial = new myButton("Tutorial",width/2,height/5 + 3*height/20,(width - width/1.7f),height/20,"Main");
		ScreenElement tutorialText =  new ScreenElement("Text","Tutorial",width/2 - 100,height/4.7f + 3*height/20,"Main");
		tutorialText.setTextSize(GameActivity.getScreenWidth()/12);
		myButton options = new myButton("Credits",width/2,height/5 + 6*height/20,(width - width/1.7f),height/20,"Main");
		ScreenElement optionsText =  new ScreenElement("Text","Credits",width/2 - 100,height/4.7f + 6*height/20,"Main");
		optionsText.setTextSize(GameActivity.getScreenWidth()/12);
		prefs = this.getSharedPreferences("flickOffGame", Context.MODE_PRIVATE);
		highScoreText =  new ScreenElement("Text","High Score: " + GameActivity.toTime(prefs.getInt("SurvivalhighScore", 0)),width/7,height/4.7f + 9*height/20,"Main");
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		UnitType.initUnitTypes();
		Ability.initAbilities();
    }

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		TouchEvent.respondToMainTouchevent(event);
		return true;
	}
    
	void drawBackground(Canvas canvas, Paint myPaint) {
		Background.drawBackground(canvas,myPaint, "Main");
	}
	
	private class mainView extends View {

		public mainView(Context context) {
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			Paint myPaint = GameActivity.myPaint;

			// Draw background.
			drawBackground(canvas, myPaint);
			
			// Draw screen elements (buttons, etc.)
			ScreenElement.drawScreenElements(canvas, myPaint, "Main");

		}
	}
	
	void playMain() {
		mainThread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					currentView.postInvalidate();
					if(Secret.isFiveSecret()) Secret.activateFiveSecret();
					highScoreText.setName("High Score: " + GameActivity.toTime(prefs.getInt("SurvivalhighScore", 0)));
					try {
						Thread.sleep(10);
					} catch (Throwable t) {
					}
				}
			}
		});
		mainThread.start();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public static int getHighScore(String mode) {
		return prefs.getInt(mode + "highScore", 0);
	}

	/**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
