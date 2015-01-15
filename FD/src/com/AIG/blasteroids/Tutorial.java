package com.AIG.blasteroids;

import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.AIG.abilities.Ability;
import com.AIG.blasteroids.touchevents.TouchEvent;
import com.AIG.screenelements.Background;
import com.AIG.screenelements.Combo;
import com.AIG.screenelements.ScreenElement;
import com.AIG.units.UnitType;

public class Tutorial extends ActionBarActivity {
SharedPreferences prefs;
	
	// Just do it once.
	public static boolean doOnce = true;

	// Just a random
	static Random r = new Random();
	
	// Thread
	private static Thread TutorialThread;
	private static View currentView;
	private static Context currContext;
	
	// Combo for unlocks
	public static Combo c1;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		TouchEvent.respondToTutorialTouchEvent(event);
		return true;
	}
	
	public static void startGame(int level) {
			if(GameActivity.gameContext==null) {
				GameActivity.gameContext =  currContext.getApplicationContext();
			}
			GameActivity.gameContext =  currContext.getApplicationContext();
			GameActivity.setMode("Tutorial");
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
		View v = new TutorialView(this);
		setContentView(v);
		currentView = v;
		if(doOnce) {
			currContext = v.getContext();
			initTutorial();
			runTutorial();
			doOnce = false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tutorial, menu);
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
		Background.drawBackground(canvas,myPaint, "Tutorial");
	}
	
	private class TutorialView extends View {

		public TutorialView(Context context) {
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			Paint myPaint = GameActivity.myPaint;
			drawTutorial(canvas, myPaint);
		}
	}
	
	void drawTutorial(Canvas canvas, Paint myPaint) {
		drawBackground(canvas,myPaint);
		myPaint.setStyle(Paint.Style.FILL);
		myPaint.setColor(Color.WHITE);
		myPaint.setStrokeWidth(3);
		myPaint.setTextSize(GameActivity.getScreenWidth()/16);
		ScreenElement.drawScreenElements(canvas, myPaint, "Tutorial");
	}
	
	void initTutorial() {
		GameActivity.setContext(this.getApplicationContext());
		UnitType.initUnitTypes();
		// Put the Castle in the middle.
		Display display = getWindowManager().getDefaultDisplay();
		GameActivity.setScreenWidth(display.getWidth());
		GameActivity.setScreenHeight(display.getHeight());
		ScreenElement units = new ScreenElement("Text","Enemies",50f,GameActivity.getScreenHeight()/14, "Tutorial");
		units.setTextSize(GameActivity.getScreenWidth()/12);
		int q = 0;
		for(int i=0; i < UnitType.getAllUnitTypes().size(); i++) {
			UnitType u = UnitType.getAllUnitTypes().get(i);
			if(u.displayTut) {
				q++;
				ScreenElement unitSymbol = new ScreenElement("UnitIcon" + u.getType(), "Button", 60f + u.getBMP().getWidth()/2, GameActivity.getScreenHeight()/12 + q*GameActivity.getScreenHeight()/12 , u.getBMP().getWidth(), u.getBMP().getHeight(), u.getBMP(), "Tutorial");
				ScreenElement unitDesc = new ScreenElement("Text",u.getDescription(),GameActivity.getScreenWidth()/6,GameActivity.getScreenHeight()/16 + q*GameActivity.getScreenHeight()/12, "Tutorial");
				unitDesc.setTextSize(GameActivity.getScreenWidth()/18);
			}
		}
		int w = 0;
		ScreenElement items = new ScreenElement("Text","Items",50f,GameActivity.getScreenHeight()/16 + (++q)*GameActivity.getScreenHeight()/11, "Tutorial");
		items.setTextSize(GameActivity.getScreenWidth()/12);
		q++;
		for(int i=0; i < Ability.upgradeableAbilities.size(); i++) {
			Ability a = Ability.upgradeableAbilities.get(i);
			if(a.getSlot()==0) {
				ScreenElement itemSymbol = new ScreenElement("AbilityIcon" + a.getType(), "Button", 60f + a.getBMP().getWidth()/2 + w*GameActivity.getScreenWidth()/8, GameActivity.getScreenHeight()/10 + q*GameActivity.getScreenHeight()/12 , a.getBMP().getWidth(), a.getBMP().getHeight(), a.getBMP(), "Tutorial");
				w++;
			}
		}
		ScreenElement itemDesc = new ScreenElement("Text","Items appear at random.",GameActivity.getScreenWidth()/11,GameActivity.getScreenHeight()/7 + q*GameActivity.getScreenHeight()/12, "Tutorial");
		itemDesc.setTextSize(GameActivity.getScreenWidth()/18);
		ScreenElement itemDesc2 = new ScreenElement("Text","Pick up, then drag and drop!",GameActivity.getScreenWidth()/11,GameActivity.getScreenHeight()/5 + q*GameActivity.getScreenHeight()/12, "Tutorial");
		itemDesc2.setTextSize(GameActivity.getScreenWidth()/18);
		w=0;
		for(int i=0; i < Ability.upgradeableAbilities.size(); i++) {
			Ability a = Ability.upgradeableAbilities.get(i);
			if(a.getSlot()==-1) {
				ScreenElement itemSymbol = new ScreenElement("UnitIcon" + a.getType(), "Button", 60f + a.getBMP().getWidth()/2 + w*GameActivity.getScreenWidth()/8, GameActivity.getScreenHeight()/4 + GameActivity.getScreenHeight()/40 + q*GameActivity.getScreenHeight()/12 , a.getBMP().getWidth(), a.getBMP().getHeight(), a.getBMP(), "Tutorial");
				w++;
			}
		}
		ScreenElement itemDesc3 = new ScreenElement("Text","These are rare, special items.",GameActivity.getScreenWidth()/11,GameActivity.getScreenHeight()/3 + q*GameActivity.getScreenHeight()/12 - GameActivity.getScreenHeight()/40, "Tutorial");
		itemDesc3.setTextSize(GameActivity.getScreenWidth()/18);
	}
	
	void updateStuff() {
		Combo.updateCombos("Tutorial");
	}
	
	void runTutorial() {
			TutorialThread = new Thread(new Runnable() {
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
			TutorialThread.start();
		}
}
