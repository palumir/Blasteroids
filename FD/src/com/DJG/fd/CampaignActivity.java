package com.DJG.fd;

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
import com.DJG.screenelements.Combo;
import com.DJG.screenelements.ScreenElement;
import com.DJG.screenelements.myButton;
import com.DJG.units.UnitType;
import com.DJG.waves.Campaign;

public class CampaignActivity extends ActionBarActivity {

SharedPreferences prefs;
	
	// Just do it once.
	public static boolean doOnce = true;

	// Just a random
	static Random r = new Random();
	
	// Thread
	private static Thread CampaignThread;
	private static View currentView;
	private static Context currContext;
	
	// Combo for unlocks
	public static Combo c1;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		TouchEvent.respondToCampaignTouchEvent(event);
		return true;
	}
	
	public static void startGame(int level) {
			if(GameActivity.gameContext==null) {
				GameActivity.gameContext =  currContext.getApplicationContext();
			}
			GameActivity.gameContext =  currContext.getApplicationContext();
			GameActivity.setMode("Campaign");
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
		View v = new CampaignView(this);
		setContentView(v);
		currentView = v;
		if(doOnce) {
			currContext = v.getContext();
			initCampaign();
			runCampaign();
			doOnce = false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.campaign, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_campaign,
					container, false);
			return rootView;
		}
	}
	
	void drawBackground(Canvas canvas, Paint myPaint) {
		Background.drawBackground(canvas,myPaint, "Campaign");
	}
	
	private class CampaignView extends View {

		public CampaignView(Context context) {
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			Paint myPaint = GameActivity.myPaint;
			drawCampaign(canvas, myPaint);
		}
	}
	
	void drawCampaign(Canvas canvas, Paint myPaint) {
		drawBackground(canvas,myPaint);
		myPaint.setStyle(Paint.Style.FILL);
		myPaint.setColor(Color.WHITE);
		myPaint.setStrokeWidth(3);
		myPaint.setTextSize(GameActivity.getScreenWidth()/16);
		Combo.drawCombos(canvas, myPaint, "Campaign");
	}
	
	void initCampaign() {
		GameActivity.setContext(this.getApplicationContext());
		UnitType.initUnitTypes();
		// Put the Castle in the middle.
		Display display = getWindowManager().getDefaultDisplay();
		GameActivity.setScreenWidth(display.getWidth());
		GameActivity.setScreenHeight(display.getHeight());
			int seperationX = 0;
			int seperationY = 0;
			int start = GameActivity.getScreenHeight()/5 - GameActivity.getScreenHeight()/16;
			int top = start - 50;
			int bot = start + 400;
			c1 = new Combo(0, 0,"Campaign");
			
			// Campaign Slider
			ScreenElement title =  new ScreenElement("Text","Select a Wave",GameActivity.getScreenWidth()/2 - 200,GameActivity.getScreenHeight()/8,"Campaign");
			title.setTextSize(GameActivity.getScreenWidth()/12);
			c1.add(title);
			int i = 0;
			for(int j = 0; j<=(int)MainActivity.getHighScore("Campaign")/10; j++){
				myButton goButton = new myButton(Integer.toString(j+1),GameActivity.getScreenWidth()/6 + seperationX,start + 200 + seperationY,80,43,"Campaign");
				ScreenElement descButton = new ScreenElement(
						"Text",
						Integer.toString(j*10+1),
						GameActivity.getScreenWidth()/6 + seperationX - 22,
						start + 210 + seperationY,
						"Campaign"
						);
				descButton.setTextSize(35);
				c1.add(goButton);
				c1.add(descButton);
				seperationX = seperationX + 225;
				i=j;
				if(GameActivity.getScreenWidth()/3 + seperationX > GameActivity.getScreenWidth()) {
					seperationX = 0;
					seperationY = seperationY + 100;
				}
			}
			for(int j = i+1; j<=(int)Campaign.campaignMax/10; j++){
				myButton goButton = new myButton(Integer.toString(j+1),GameActivity.getScreenWidth()/6 + seperationX,start + 200 + seperationY,80,43,"Campaign");
				goButton.setClickable(false);
				ScreenElement descButton = new ScreenElement(
						"Text",
						Integer.toString(j*10+1),
						GameActivity.getScreenWidth()/6 + seperationX - 22,
						start + 210 + seperationY,
						"Campaign"
						);
				descButton.setTextSize(35);
				c1.add(goButton);
				c1.add(descButton);
				seperationX = seperationX + 225;
				i=j;
				if(GameActivity.getScreenWidth()/3 + seperationX > GameActivity.getScreenWidth()) {
					seperationX = 0;
					seperationY = seperationY + 100;
				}
			}
	}
	
	void updateStuff() {
		Combo.updateCombos("Campaign");
		checkIfUnlockedLevels();
	}
	
	void checkIfUnlockedLevels() {
		for(int i=0; i < c1.size(); i++) {
			ScreenElement s = c1.get(i);
			if(s.getType().equals("Drawn myButton")) {
				myButton u = (myButton)c1.get(i);
				if(MainActivity.getHighScore(GameActivity.getMode()) >= 10*(Integer.parseInt(s.getName()) - 1)) {
					u.setClickable(true);
				}
			}
		}
	}
	
	void runCampaign() {
			CampaignThread = new Thread(new Runnable() {
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
			CampaignThread.start();
		}
}
