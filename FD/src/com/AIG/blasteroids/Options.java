package com.AIG.blasteroids;

import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.AIG.blasteroids.touchevents.TouchEvent;
import com.AIG.screenelements.Background;
import com.AIG.screenelements.Combo;
import com.AIG.screenelements.ScreenElement;
import com.AIG.units.UnitType;

public class Options extends ActionBarActivity {

SharedPreferences prefs;
	
	// Just do it once.
	public static boolean doOnce = true;

	// Just a random
	static Random r = new Random();
	
	// Thread
	private static Thread OptionsThread;
	private static View currentView;
	private static Context currContext;
	
	// Combo for unlocks
	public static Combo c1;
	public static int creditCount = 0;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		TouchEvent.respondToOptionsTouchEvent(event);
		return true;
	}
	
	public static void startGame(int level) {
			if(GameActivity.gameContext==null) {
				GameActivity.gameContext =  currContext.getApplicationContext();
			}
			GameActivity.gameContext =  currContext.getApplicationContext();
			GameActivity.setMode("Options");
			GameActivity.levelStart = level-10;
			Intent intent = new Intent(currContext, GameActivity.class);
			currContext.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		prefs = this.getSharedPreferences("flickOffGame", Context.MODE_PRIVATE);
		View v = new OptionsView(this);
		setContentView(v);
		currentView = v;
		if(doOnce) {
			currContext = v.getContext();
			initOptions();
			runOptions();
			doOnce = false;
		}
		SharedPreferences prefs = MainActivity.prefs;
		Editor editor = prefs.edit();
		if(prefs.getInt("fiveSecret", 0) == 1) {
			for(int i = 0; i < 5; i++) {
				UnitType u = UnitType.getUnitType("Pie");
				ScreenElement itemSymbol1 = new ScreenElement("UnitIcon" + u.getType(), "Button", 60f + u.getBMP().getWidth()/2 + i*GameActivity.getScreenWidth()/8, 7*GameActivity.getScreenHeight()/10, u.getBMP().getWidth(), u.getBMP().getHeight(), u.getBMP(), "Options");
				ScreenElement itemSymbol2 = new ScreenElement("UnitIcon" + u.getType(), "Button", 60f + u.getBMP().getWidth()/2 + i*GameActivity.getScreenWidth()/8, GameActivity.getScreenHeight()/10, u.getBMP().getWidth(), u.getBMP().getHeight(), u.getBMP(), "Options");
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main,
					container, false);
			return rootView;
		}
	}
	
	void drawBackground(Canvas canvas, Paint myPaint) {
		Background.drawBackground(canvas,myPaint, "Options");
	}
	
	private class OptionsView extends View {

		public OptionsView(Context context) {
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			Paint myPaint = GameActivity.myPaint;
			drawOptions(canvas, myPaint);
		}
	}
	
	void drawOptions(Canvas canvas, Paint myPaint) {
		drawBackground(canvas,myPaint);
		myPaint.setStyle(Paint.Style.FILL);
		myPaint.setColor(Color.WHITE);
		myPaint.setStrokeWidth(3);
		myPaint.setTextSize(GameActivity.getScreenWidth()/16);
		ScreenElement.drawScreenElements(canvas, myPaint, "Options");
	}
	
	void initOptions() {
		GameActivity.setContext(this.getApplicationContext());
		UnitType.initUnitTypes();
		// Put the Castle in the middle.
		Display display = getWindowManager().getDefaultDisplay();
		GameActivity.setScreenWidth(display.getWidth());
		GameActivity.setScreenHeight(display.getHeight());
		ScreenElement desc1 = new ScreenElement(
				"Text",
				"Game created by",
				GameActivity.getScreenWidth()/12,
				GameActivity.getScreenHeight()/10 + 1*GameActivity.getScreenHeight()/10,
				"Options"
				);
		desc1.setTextSize(GameActivity.getScreenWidth()/18);
		ScreenElement desc2 = new ScreenElement(
				"Text",
				"Ian H. Davidson",
				GameActivity.getScreenWidth()/12,
				GameActivity.getScreenHeight()/10 + 2*GameActivity.getScreenHeight()/10,
				"Options"
				);
		desc2.setTextSize(GameActivity.getScreenWidth()/18);
		ScreenElement desc3 = new ScreenElement(
				"Text",
				"Andrew Jenkins",
				GameActivity.getScreenWidth()/12,
				GameActivity.getScreenHeight()/10 + 3*GameActivity.getScreenHeight()/10,
				"Options"
				);
		desc3.setTextSize(GameActivity.getScreenWidth()/18);
		ScreenElement desc4 = new ScreenElement(
				"Text",
				"Thanks for playing!",
				GameActivity.getScreenWidth()/12,
				GameActivity.getScreenHeight()/10 + 5*GameActivity.getScreenHeight()/10,
				"Options"
				);
		desc4.setTextSize(GameActivity.getScreenWidth()/18);
	}
	
	void updateStuff() {
		Combo.updateCombos("Options");
	}
	
	void runOptions() {
			OptionsThread = new Thread(new Runnable() {
				public void run() {
					while(true) {
							updateStuff();
							currentView.postInvalidate();
							try {
								Thread.sleep(10);
							} catch (Throwable t) {
							}
					}
				}
			});
			OptionsThread.start();
		}
}
