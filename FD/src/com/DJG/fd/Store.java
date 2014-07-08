package com.DJG.fd;

import com.DJG.abilities.Ability;
import com.DJG.units.Unit;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class Store extends ActionBarActivity {
	
	private static Thread storeThread;
	private static View currentView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		}
	}
	
	void initStore() {
	}
	
	void updateStuff() {}
	
	void runStore() {
			storeThread = new Thread(new Runnable() {
				public void run() {
						updateStuff();
						currentView.postInvalidate();
						try {
							Thread.sleep(10);
						} catch (Throwable t) {
						}
				}
			});
			storeThread.start();
		}

}
