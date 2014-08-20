package com.DJG.fd;

import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import com.DJG.screenelements.Combo;
import com.DJG.screenelements.ScreenElement;
import com.DJG.units.UnitType;
import com.DJG.waves.Campaign;

public class CampaignActivity extends ActionBarActivity {

SharedPreferences prefs;
	
	// Just do it once.
	public static boolean doOnce = true;
	
	// Background
	private Bitmap background;
	private Canvas bgCanvas;

	// Just a random
	static Random r = new Random();
	
	// Thread
	private static Thread CampaignThread;
	private static View currentView;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		TouchEvent.respondToCampaignTouchEvent(event);
		return true;
	}
	
	public static void startGame(int level) {

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
		canvas.drawColor(GameActivity.bgColor);
		if (bgCanvas == null) {
			background = Bitmap.createBitmap(GameActivity.getScreenWidth(),
					GameActivity.getScreenHeight(), Bitmap.Config.ARGB_8888);
			bgCanvas = new Canvas(background);
			myPaint.setStrokeWidth(1);
			myPaint.setColor(Color.WHITE);
			int x = 0;
			while (x < GameActivity.getScreenWidth()) {
				int y = 0;
				int n = 0;
				while (y < GameActivity.getScreenHeight()) {
					if (r.nextInt(GameActivity.getScreenHeight()) == 0) {
						n++;
						bgCanvas.drawPoint(x, y, myPaint);
					}
					if (n > 10) {
						break;
					}
					y++;
				}
				x++;
			}
		}
		canvas.drawBitmap(background, 0, 0, myPaint);
	}
	
	private class CampaignView extends View {

		public CampaignView(Context context) {
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			Paint myPaint = new Paint();
			drawCampaign(canvas, myPaint);
		}
	}
	
	void drawCampaign(Canvas canvas, Paint myPaint) {
		drawBackground(canvas,myPaint);
		myPaint.setStyle(Paint.Style.FILL);
		myPaint.setColor(Color.WHITE);
		myPaint.setStrokeWidth(3);
		myPaint.setTextSize(50);
		Combo.drawCombos(canvas, myPaint, "Campaign");
	}
	
	void initCampaign() {
		GameActivity.setContext(this.getApplicationContext());
		UnitType.initUnitTypes();
		// Put the Castle in the middle.
		Display display = getWindowManager().getDefaultDisplay();
		GameActivity.setScreenWidth(display.getWidth());
		GameActivity.setScreenHeight(display.getHeight());
		Ability.initAbilities(prefs);
			int seperation = 0;
			int start = GameActivity.getScreenHeight()/5 - GameActivity.getScreenHeight()/16;
			int top = start - 50;
			int bot = start + 400;
			Combo c1 = new Combo(top, bot);
			
			// Campaign Slider
			for(int j = 0; j<(int)Campaign.campaignMax/10; j++){
				ScreenElement descButton = new ScreenElement(
						"Text",
						"Wave "+(j*10),
						GameActivity.getScreenWidth()/2 + seperation - 50,
						start+100,
						"Campaign"
						);
				descButton.setTextSize(35);
				ScreenElement goButton = new ScreenElement(
						"" + (j+1),
						"Button",
						GameActivity.getScreenWidth()/2 + seperation,
						start + 200,
						80,
						43,
						ScreenElement.buttonTest,
						"Campaign"
						);
				c1.add(goButton);
				c1.add(descButton);
				seperation = seperation + 300;
			}
	}
	
	void updateStuff() {
		Combo.updateCombos();
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
