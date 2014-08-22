package com.DJG.fd;
import com.DJG.abilities.Ability;
import com.DJG.screenelements.ScreenElement;
import com.DJG.units.Unit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.EditText;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends ActionBarActivity {

	public final static String EXTRA_MESSAGE = "com.DJG.fd.MESSAGE";
	
	public Thread mainThread;
	public View currentView;
	private boolean doOnce = true;
	
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
			initMain();
			playMain();
			doOnce = false;
		}
    }
    
    void initMain() {
		Display display = getWindowManager().getDefaultDisplay();
		GameActivity.setScreenWidth(display.getWidth());
		GameActivity.setScreenHeight(display.getHeight());
    }

	void drawBackground(Canvas canvas, Paint myPaint) {
		canvas.drawColor(GameActivity.bgColor);
		if (GameActivity.bgCanvas == null) {
			GameActivity.background = Bitmap.createBitmap(GameActivity.getScreenWidth(),
					GameActivity.getScreenHeight(), Bitmap.Config.ARGB_8888);
			GameActivity.bgCanvas = new Canvas(GameActivity.background);
			myPaint.setStrokeWidth(1);
			myPaint.setColor(Color.WHITE);
			int x = 0;
			while (x < GameActivity.getScreenWidth()) {
				int y = 0;
				int n = 0;
				while (y < GameActivity.getScreenHeight()) {
					if (GameActivity.r.nextInt(GameActivity.getScreenHeight()) == 0) {
						n++;
						GameActivity.bgCanvas.drawPoint(x, y, myPaint);
					}
					if (n > 10) {
						break;
					}
					y++;
				}
				x++;
			}
		}
		canvas.drawBitmap(GameActivity.background, 0, 0, myPaint);
	}
	
	private class mainView extends View {

		public mainView(Context context) {
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			Paint myPaint = new Paint();

			// Draw background.
			drawBackground(canvas, myPaint);

		}
	}
	
	void playMain() {
		mainThread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					currentView.postInvalidate();
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
