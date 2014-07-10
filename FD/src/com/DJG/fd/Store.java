package com.DJG.fd;

import android.content.Context;
import android.graphics.Canvas;
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

public class Store extends ActionBarActivity {
	
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
		View v = new storeView(this);
		setContentView(v);
		currentView = v;
		initStore();
		runStore();
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
		Combo.drawCombos(canvas, myPaint, "Store");
	}
	
	void initStore() {
		GameActivity.setContext(this.getApplicationContext());
		UnitType.initUnitTypes();
		// Put the Castle in the middle.
		Display display = getWindowManager().getDefaultDisplay();
		GameActivity.setScreenWidth(display.getWidth());
		GameActivity.setScreenHeight(display.getHeight());
		Ability.initAbilities();
		
		synchronized(Ability.allAbilitiesLock) {
			int seperation = 0;
			int top = GameActivity.getScreenHeight()/2 - 50;
			int bot = GameActivity.getScreenHeight()/2 + 300;
			Combo c = new Combo(top, bot);
			for(int j = 0; j < Ability.allAbilities.size(); j++){
				Ability a = Ability.allAbilities.get(j);
				ScreenElement abilityIcon = new ScreenElement(
						"Buy",
						"Button",
						GameActivity.getScreenWidth()/2 + seperation,
						GameActivity.getScreenHeight()/2,
						80,
						43,
						a.getBMP(),
						"Store"
						);
				ScreenElement buyButton = new ScreenElement(
						"Buy",
						"Button",
						GameActivity.getScreenWidth()/2 + seperation,
						GameActivity.getScreenHeight()/2 + 200,
						80,
						43,
						ScreenElement.buttonTest,
						"Store"
						);
				c.add(abilityIcon);
				c.add(buyButton);
				seperation = seperation + 300;
			}
		}
	}
	
	void updateStuff() {
		Combo.updateCombos();
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
