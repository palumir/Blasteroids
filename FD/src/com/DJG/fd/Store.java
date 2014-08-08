package com.DJG.fd;

import java.util.Random;

import android.content.Context;
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
import com.DJG.abilities.Coin;
import com.DJG.fd.touchevents.TouchEvent;
import com.DJG.screenelements.Combo;
import com.DJG.screenelements.ScreenElement;
import com.DJG.units.UnitType;

public class Store extends ActionBarActivity {
	
	SharedPreferences prefs;
	
	// Just do it once.
	public static boolean doOnce = true;
	
	// Background
	private Bitmap background;
	private Canvas bgCanvas;
	
	// Coins
	private String coinsText = "0";
	
	// Just a random
	static Random r = new Random();
	
	// Thread
	private static Thread storeThread;
	private static View currentView;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		TouchEvent.respondToStoreTouchEvent(event);
		return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		prefs = this.getSharedPreferences("flickOffGame", Context.MODE_PRIVATE);
		View v = new storeView(this);
		setContentView(v);
		currentView = v;
		if(doOnce) {
			initStore();
			runStore();
			doOnce = false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.store, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_store,
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
	
	private class storeView extends View {

		public storeView(Context context) {
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			Paint myPaint = new Paint();
			drawStore(canvas, myPaint);
		}
	}
	
	void drawStore(Canvas canvas, Paint myPaint) {
		drawBackground(canvas,myPaint);
		Combo.drawCombos(canvas, myPaint, "Store");
	}
	
	void initStore() {
		GameActivity.setContext(this.getApplicationContext());
		UnitType.initUnitTypes();
		// Put the Castle in the middle.
		Display display = getWindowManager().getDefaultDisplay();
		GameActivity.setScreenWidth(display.getWidth());
		GameActivity.setScreenHeight(display.getHeight());
		Ability.initAbilities(prefs);
		
		Combo slots = new Combo(0,0);
		int slotsY = (int) ((int)GameActivity.getScreenHeight()*0.9);
		ScreenElement slot3 = new ScreenElement(
				"Slot3",
				Ability.getPrefs().getString("Slot3","Bomb"),
				GameActivity.getScreenWidth()/8+60,
				slotsY,
				"Store"
				);
		slot3.setWidth(70);
		slot3.setHeight(75);
		ScreenElement slot2 = new ScreenElement(
				"Slot2",
				Ability.getPrefs().getString("Slot2","None"),
				GameActivity.getScreenWidth()/8+180,
				slotsY,
				"Store"
				);
		slot2.setWidth(70);
		slot2.setHeight(75);
		ScreenElement slot1 = new ScreenElement(
				"Slot1",
				Ability.getPrefs().getString("Slot1","None"),
				GameActivity.getScreenWidth()/8+320,
				slotsY,
				"Store"
				);
		slot1.setWidth(70);
		slot1.setHeight(75);
		ScreenElement planetSlot = new ScreenElement(
				"PlanetSlot",
				Ability.getPrefs().getString("PlanetSlot","None"),
				GameActivity.getScreenWidth()*6/8,
				slotsY,
				"Store"
				);
		// Coin symbol
		ScreenElement coinSymbol = new ScreenElement("Coin", "Button", 60f,
				(GameActivity.getScreenHeight() - 68), 25, 25, Coin.CoinBMP, "Store");
		ScreenElement coinsTextSymbol = new ScreenElement(coinsText, "Text", 90f, (float) (GameActivity.getScreenHeight() - 50), "Store");
		planetSlot.setWidth(70);
		planetSlot.setHeight(75);
		slots.add(slot1);
		slots.add(slot2);
		slots.add(slot3);
		slots.add(coinSymbol);
		slots.add(planetSlot);
		
		synchronized(Ability.upgradeableAbilitiesLock) {
			int seperation = 0;
			int start = GameActivity.getScreenHeight()/5 - GameActivity.getScreenHeight()/16;
			int top = start - 50;
			int bot = start + 400;
			Combo c1 = new Combo(top, bot);
			
			// Abilities slider
			for(int j = 0; j < Ability.upgradeableAbilities.size(); j++){
				Ability a = Ability.upgradeableAbilities.get(j);
				if(a.getSlot() != -1) {
				ScreenElement abilityIcon = new ScreenElement(
						"Buy",
						"Icon",
						GameActivity.getScreenWidth()/2 + seperation,
						start,
						80,
						43,
						a.getBMP(),
						"Store"
						);
				ScreenElement coinIcon = new ScreenElement(
						"Coin",
						"Icon",
						GameActivity.getScreenWidth()/2 + seperation + 50,
						start,
						55,
						42,
						Coin.CoinBMP,
						"Store"
						);
				ScreenElement costButton = new ScreenElement(
						"Text",
						""+a.getCost(),
						GameActivity.getScreenWidth()/2 + seperation + 50,
						start,
						"Store"
						);
				ScreenElement descButton = new ScreenElement(
						"Text",
						""+a.getDescription(),
						GameActivity.getScreenWidth()/2 + seperation - 50,
						start+100,
						"Store"
						);
				descButton.setTextSize(35);
				ScreenElement buyButton = new ScreenElement(
						"Buy",
						"Button",
						GameActivity.getScreenWidth()/2 + seperation,
						start + 200,
						80,
						43,
						ScreenElement.buttonTest,
						"Store"
						);
				ScreenElement equipButton = new ScreenElement(
						"Equip",
						"Button",
						GameActivity.getScreenWidth()/2 + seperation,
						start + 300,
						80,
						43,
						ScreenElement.buttonTest,
						"Store"
						);
				equipButton.attachAbility(a);
				buyButton.attachAbility(a);
				c1.add(abilityIcon);
				c1.add(equipButton);
				c1.add(coinIcon);
				c1.add(costButton);
				c1.add(descButton);
				c1.add(buyButton);
				seperation = seperation + 300;
				}
			}
		}
		
		int seperation = 0;
		int start = 2*GameActivity.getScreenHeight()/5 + GameActivity.getScreenHeight()/14;
		int top = start - 50;
		int bot = start + 300;
		Combo c2 = new Combo(top, bot);
		// Planet slider
		for(int j = 0; j < UnitType.getAllUnitTypes().size(); j++){
			UnitType u = UnitType.getAllUnitTypes().get(j);
			if(u.getMetaType() == "Planet") { 
				ScreenElement planetIcon = new ScreenElement(
						"Buy",
						"Button",
						GameActivity.getScreenWidth()/2 + seperation,
						start,
						80,
						43,
						u.getBMP(),
						"Store"
						);
				ScreenElement buyButton = new ScreenElement(
						"Buy",
						"Button",
						GameActivity.getScreenWidth()/2 + seperation,
						start + 200,
						80,
						43,
						ScreenElement.buttonTest,
						"Store"
						);
				c2.add(planetIcon);
				c2.add(buyButton);
				seperation = seperation + 300;
			}
		}
	}
	
	void updateStuff() {
		Combo.updateCombos();
		coinsText = "" + Coin.getCoins();
	}
	
	void runStore() {
			storeThread = new Thread(new Runnable() {
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
			storeThread.start();
		}

}
