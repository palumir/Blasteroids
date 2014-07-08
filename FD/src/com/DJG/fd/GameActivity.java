package com.DJG.fd;

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
import com.DJG.planets.Earth;
import com.DJG.planets.Planet;
import com.DJG.screenelements.ScreenElement;
import com.DJG.secrets.JenkinsSecrets;
import com.DJG.units.Unit;
import com.DJG.units.UnitType;
import com.DJG.waves.Wave;

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
	static Random r = new Random();

	// Background
	private Bitmap background;
	private Canvas bgCanvas;

	// Shared data.
	static SharedPreferences prefs;
	public static int bgColor = Color.BLACK;

	// The current game thread.
	private static String mode = "Survival";
	private static long gameTime = 0;
	public static Context survContext;
	public static Thread gameThread;
	private volatile static boolean gameOver;

	// The current view.
	private static View currentView;
	private static int screenHeight;
	private static int screenWidth;

	// Some game buttons.
	private static ScreenElement pauseButton;

	// Text, that's all.
	public static String levelText;
	public static String castleHP;
	public static String highScoreText;
	public static String previousHighScoreText;

	// Is the game paused?
	public static boolean paused = false;

	// Has this guy lost yet?
	public static boolean lost;
	public static double lostTime;
	public static int loseDuration = 2500;

	// List of all units. This list is constantly redrawn.
	public static boolean doOnce = true;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		TouchEvent.respondToTouchEvent(event);
		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle b = getIntent().getExtras();
		int levelStart = 0;
		if (b != null) {
			levelStart = b.getInt("level");
		}
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		prefs = this.getSharedPreferences("flickOffGame", Context.MODE_PRIVATE);
		View v = new gameView(this);
		setContentView(v);
		currentView = v;

		if (doOnce) {
			survContext = this.getApplicationContext();
			gameOver = false;
			levelText = "Wave " + (Wave.getCurrentWaveNumber() + 1);
			highScoreText = "";
			previousHighScoreText = "";
			castleHP = "Health ";
			initGame(levelStart);
			playGame();
			doOnce = false;
		} else {
		}
	}

	void drawText(Canvas canvas, Paint myPaint) {
		myPaint.setStyle(Paint.Style.FILL);
		myPaint.setStrokeWidth(3);
		myPaint.setTextSize(50);
		myPaint.setColor(Color.WHITE);
		canvas.drawText(levelText, 50f, 50f, myPaint);
		canvas.drawText(highScoreText, 50f, 100f, myPaint);
		canvas.drawText(previousHighScoreText, 50f, 150f, myPaint);
		canvas.drawText(castleHP, 50f, (float) (screenHeight - 50), myPaint);
	}

	public static boolean isOffScreen(float x, float y) {
		if (x < 0 || x > getScreenWidth() || y < 0 || y > getScreenHeight()) {
			return true;
		}
		return false;
	}

	public static boolean isCloseOffScreen(float x, float y) {
		if (x < -50 || x > getScreenWidth() + 50 || y < -50
				|| y > getScreenHeight() + 50) {
			return true;
		}
		return false;
	}

	void drawBackground(Canvas canvas, Paint myPaint) {
		canvas.drawColor(bgColor);
		if (bgCanvas == null) {
			background = Bitmap.createBitmap(getScreenWidth(),
					getScreenHeight(), Bitmap.Config.ARGB_8888);
			bgCanvas = new Canvas(background);
			myPaint.setStrokeWidth(1);
			myPaint.setColor(Color.WHITE);
			int x = 0;
			while (x < getScreenWidth()) {
				int y = 0;
				int n = 0;
				while (y < getScreenHeight()) {
					if (r.nextInt(getScreenHeight()) == 0) {
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

	private class gameView extends View {

		public gameView(Context context) {
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			Paint myPaint = new Paint();

			// Draw background.
			drawBackground(canvas, myPaint);

			// Draw our text.
			drawText(canvas, myPaint);

			// Draw screen elements (buttons, etc.)
			ScreenElement.drawScreenElements(canvas, myPaint);

			// Draw earth.
			Unit.drawEarth(canvas, myPaint);

			// Draw ability slots/icons.
			Ability.drawAbilities(canvas, myPaint);

			// Draw all of our units.
			Unit.drawUnits(canvas, myPaint);

			// Draw abilities.
			Ability.drawAbilityAnimations(canvas, myPaint);
		}
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

	void initGame(int levelStart) {
		UnitType.initUnitTypes();
		// Put the Castle in the middle.
		Display display = getWindowManager().getDefaultDisplay();
		lost = false;
		lostTime = 0;
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();

		// Spawn pause button
		pauseButton = new ScreenElement("Pause", "Button", screenWidth - 40,
				40, 22);

		// Spawn the planet.
		Planet p = new Earth("Fortress", "Earth", screenWidth / 2,
				screenHeight / 2);
		p.setOnScreen();
		Wave.initWaves(levelStart);
		Ability.initAbilities();
	}

	void checkIfLost() {
		if (GameActivity.getLost()
				&& (gameTime
						- GameActivity.getTimeLost() > loseDuration)) {
			GameActivity.youLose();
		}
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
		gameOver = true;
		doOnce = true;
		levelText = "Wave " + (Wave.getCurrentWaveNumber() + 1)
				+ " defeated you.";
		int currHighScore = prefs.getInt("highScoreSurvival", 0);
		if ((Wave.getCurrentWaveNumber() + 1) > currHighScore) {
			Editor editor = prefs.edit();
			editor.putInt("highScoreSurvival",
					(int)(Wave.getCurrentWaveNumber() + 1));
			highScoreText = "New high score!";
			previousHighScoreText = "Previous: Wave " + currHighScore + ".";
			editor.commit();
		} else {
			highScoreText = "Current high score: Wave " + currHighScore + ".";
			previousHighScoreText = "";
		}
		Unit.destroyAllUnits(); // Don't request the lock because the caller is
								// already locking it.
		Wave.destroyWaves();
		ScreenElement.destroyAllScreenElements();
		Ability.clearAbilities();
	}
	
	public static long getGameTime() {
		return gameTime;
	}

	void updateAllStuff() {
		
		// If the game is unpaused...
		if(!paused) {
			gameTime = gameTime + 10;
			
			// Unleash the waves.
			if (lost == false) {
				Wave.sendWaves();
			}
	
			JenkinsSecrets.updateSecrets();
			
			// Update abilities.
			Ability.updateAbilities();
	
			// Update units.
			Unit.updateUnits();
		}
	}

}
