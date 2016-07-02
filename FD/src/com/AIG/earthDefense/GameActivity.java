package com.AIG.earthDefense;

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
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.AIG.abilities.Ability;
import com.AIG.abilities.Coin;
import com.AIG.earthDefense.touchevents.TouchEvent;
import com.AIG.planets.Planet;
import com.AIG.screenelements.Background;
import com.AIG.screenelements.ScreenElement;
import com.AIG.units.Unit;
import com.AIG.waves.Wave;

public class GameActivity extends ActionBarActivity {

	// Convert red to transparent in a bitmap
	public static Bitmap makeTransparent(Bitmap bit) {
		int width = bit.getWidth();
		int height = bit.getHeight();
		Bitmap myBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		int[] allpixels = new int[myBitmap.getHeight() * myBitmap.getWidth()];
		bit.getPixels(allpixels, 0, myBitmap.getWidth(), 0, 0,
				myBitmap.getWidth(), myBitmap.getHeight());
		myBitmap.setPixels(allpixels, 0, width, 0, 0, width, height);

		for (int i = 0; i < myBitmap.getHeight() * myBitmap.getWidth(); i++) {
			if (allpixels[i] == Color.BLACK)

				allpixels[i] = Color.alpha(Color.TRANSPARENT);
		}

		myBitmap.setPixels(allpixels, 0, myBitmap.getWidth(), 0, 0,
				myBitmap.getWidth(), myBitmap.getHeight());
		return myBitmap;
	}

	// Just a random
	private static Random r = new Random();
	
	// The castle of course
	private static Planet fortress;

	
	// Paint
	static Paint myPaint = new Paint();

	// Shared data.
	public static SharedPreferences prefs;
	public static int bgColor = Color.BLACK;
	public static int earthExplode;

	// The current game thread.
	private static String mode = "Campaign";
	private static long gameTime = 0;
	public static Context gameContext;
	public static Thread gameThread;
	private volatile static boolean gameOver;
	public static int levelStart = 0;

	// The current view.
	private static View currentView;
	private static int screenHeight;
	private static int screenWidth;

	// Some game buttons.
	private static ScreenElement pauseButton;
	private static ScreenElement healthSymbol;
	private static ScreenElement coinSymbol;

	// Text, that's all.
	public static String levelText;
	public static String castleHP;
	public static String highScoreText;
	public static String previousHighScoreText;
	public static String coinsText;

	// Is the game paused?
	public static boolean paused = false;

	// Has this guy lost yet?
	public static boolean lost;
	public static double lostTime;
	public static int loseDuration = 2500;

	// Just do it once.
	public static boolean doOnce = true;
	
	public static String toTime(int n) {
		int minutes = n/60;
		int seconds = n - minutes*60;
		if(minutes>0) {
			return minutes + " min " + seconds + " sec";
		}
		else {
			return seconds + " seconds";
		}
	}
	
	public static String toTimeDouble(double n) {
		int minutes = (int)(n/60d);
		double seconds = n - minutes*60d;
		if(minutes>0d) {
			return minutes + "m " + (Math.round(seconds*100.0)/100.0) + "s";
		}
		else {
			return seconds + "s";
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		TouchEvent.respondToGameTouchEvent(event);
		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		prefs = this.getSharedPreferences("flickOffGame", Context.MODE_PRIVATE);
		View v = new gameView(this);
		setContentView(v);
		currentView = v;
		TouchEvent.purgeTouch();

		if (doOnce) {
			gameContext = this.getApplicationContext();
			gameOver = false;
			levelText = "0";
			highScoreText = "";
			previousHighScoreText = "";
			castleHP = "";
			initGame();
			playGame();
			doOnce = false;
		}
	}

	void drawText(Canvas canvas, Paint myPaint) {
		myPaint.setStyle(Paint.Style.FILL);
		myPaint.setStrokeWidth(3);
		myPaint.setTextSize(screenWidth/16);
		myPaint.setColor(Color.WHITE);
		canvas.drawText(levelText, screenWidth/20, screenHeight/20, myPaint);
		canvas.drawText(highScoreText, screenWidth/20, screenHeight/20 + screenHeight/15, myPaint);
		canvas.drawText(previousHighScoreText, screenWidth/20, screenHeight/20 + 2*screenHeight/15, myPaint);
		canvas.drawText(castleHP, screenWidth/9, (float) (screenHeight - screenHeight/42), myPaint);
	}

	public static boolean isOffScreen(float x, float y) {
		if (x < 0 || x > getScreenWidth() || y < 0 || y > getScreenHeight()) {
			return true;
		}
		return false;
	}

	public static boolean isCloseOffScreen(float x, float y) {
		if (x < -200 || x > getScreenWidth() + 200 || y < -200
				|| y > getScreenHeight() + 200) {
			return true;
		}
		return false;
	}

	void drawBackground(Canvas canvas, Paint myPaint) {
		Background.drawBackground(canvas,myPaint, "Game");
	}

	private class gameView extends View {

		public gameView(Context context) {
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {

			// Draw background.
			drawBackground(canvas, myPaint);

			// Draw our text.
			drawText(canvas, myPaint);

			// Draw earth.
			Unit.drawEarth(canvas, myPaint);

			// Draw screen elements (buttons, etc.)
			ScreenElement.drawScreenElements(canvas, myPaint, "Game");
			
			// Draw ability slots/icons.
			Ability.drawAbilities(canvas, myPaint);

			// Draw all of our units.
			Unit.drawUnits(canvas, myPaint);

			// Draw abilities.
			Ability.drawAbilityAnimations(canvas, myPaint);
		}
	}
	
	public static void setContext(Context c) {
		gameContext = c;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_game,
					container, false);
			return rootView;
		}
	}

	// Set that they lost.
	public static void setLost() {
		lost = true;
		Unit.destroyPlanet();
		MainActivity.soundPool.play(earthExplode, 1, 1, 1, 0, 1f);
		lostTime = gameTime;
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

	void initGame() {
		// Put the Castle in the middle.
		lost = false;
		lostTime = 0;

		// Spawn pause button
		pauseButton = new ScreenElement("Pause", "Button", screenWidth - screenHeight/20, screenHeight/30, 22, 22, ScreenElement.pauseBMP, "Game");
		
		// Health symbol
		healthSymbol = new ScreenElement("Health", "Button", 70f,
				(screenHeight - screenHeight/25), 25, 25, ScreenElement.healthBMP, "Game");
		

		Wave.initWaves(levelStart);
		levelText = "0";
		// Spawn the planet.
		Planet p = Planet.getCurrentPlanet(screenWidth/2,screenHeight/2);
		setFortress(p);
		p.setOnScreen();
		p.spawnDefenders();
		Ability.initAbilities();
		earthExplode = MainActivity.soundPool.load(GameActivity.gameContext, R.raw.big_explosion, 1);
	}

	public static void setScreenWidth(int i) {
		screenWidth = i;
	}
	
	public static void setScreenHeight(int i) {
		screenHeight = i;
	}
	
	void checkIfLost() {
		if (GameActivity.getLost()
				&& (gameTime
						- GameActivity.getTimeLost() > loseDuration)) {
			GameActivity.youLose();
		}
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		pause();
	}


	@Override
	protected void onResume()
	{
		super.onResume();
		unPause();
	}


	void playGame() {
		gameThread = new Thread(new Runnable() {
			public void run() {
				while (!gameOver) {
					updateAllStuff();
					checkIfLost();
					currentView.postInvalidate();
					try {
						Thread.sleep(10);
					} catch (Throwable t) {
					}
				}
			}
		});
		gameThread.start();
	}

	public static void youLose() {
		Coin.coinsSave();
		gameOver = true;
		doOnce = true;
		levelText = "You survived " + toTime((int)Math.round(gameTime/1000))
				+ ".";
		castleHP = "";
		coinsText = "";
		int currHighScore = prefs.getInt(mode + "highScore", 0);
		if ((int)Math.round(gameTime/1000) > currHighScore) {
			Editor editor = prefs.edit();
			editor.putInt(mode + "highScore",
					(int)Math.round(gameTime/1000));
			highScoreText = "New high score!";
			previousHighScoreText = "Previous: " + toTime(currHighScore) + ".";
			editor.commit();
		} else {
			highScoreText = "High score: " + toTime(currHighScore) + ".";
			previousHighScoreText = "";
		}
		Unit.destroyAllUnits(); // Don't request the lock because the caller is
								// already locking it.
		Wave.destroyWaves();
		ScreenElement.destroyAllScreenElements("Game");
		Ability.clearAbilities();
	}
	
	public static long getGameTime() {
		return gameTime;
	}
	
	public static void pause() {
		GameActivity.paused = true;
	}
	
	public static void unPause() {
		GameActivity.paused = false;
	}

	void updateAllStuff() {
		
		// If the game is unpaused...
		if(!paused) {
			gameTime = gameTime + 10;
			levelText = toTimeDouble((double)gameTime/1000d);
			
			// Unleash the waves.
			if (lost == false) {
				Wave.sendWaves();
			}
	
			ScreenElement.updateScreenElements();
			
			// Update abilities.
			Ability.updateAbilities();
	
			// Update units.
			Unit.updateUnits();
		}
	}
	
	public static void reset() {
		gameTime = 0;
		Ability.slotNumber = 0;
		paused = false;
		gameOver = true;
		doOnce = true;
		Unit.destroyAllUnits(); // Don't request the lock because the caller is
		// already locking it.
		Wave.destroyWaves();
		Ability.clearAbilities();
	}

	public static Planet getFortress() {
		return fortress;
	}

	public void setFortress(Planet fortress) {
		this.fortress = fortress;
	}

	public static void setMode(String mode) {
		GameActivity.reset();
		GameActivity.mode = mode;
	}

	public static Random getR() {
		return r;
	}

	public static void setR(Random r) {
		GameActivity.r = r;
	}

}
