package com.DJG.abilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

import com.DJG.fd.GameActivity;
import com.DJG.fd.R;
import com.DJG.fd.touchevents.TouchEvent;
import com.DJG.screenelements.ScreenElement;
import com.DJG.units.Unit;

public class LazerFingers {
	// General ability attributes.
	private static long startTime;
	private static long duration = 15000;
	private static int radius = 140;
	private static int explosionDuration = 550;
	public static ScreenElement timer;
	
	// Lazer stuff
	public static float lazerPoint1X;
	public static float lazerPoint1Y;
	public static float lazerPoint2X;
	public static float lazerPoint2Y;
	
	// Bitmap
	public static Bitmap lazerBMP = GameActivity.makeTransparent(BitmapFactory.decodeResource(GameActivity.gameContext.getResources(), R.drawable.satelite));
	
	public static void startLazerFingers(int newDuration) {
		TouchEvent.lazerFingers = true;
		startTime = GameActivity.getGameTime();
		setDuration(newDuration);
	}
	
	static void checkIfHitLazer(Unit u) {
		boolean tf = false;
	    final float EPSILON = 15f;
	    if (Math.abs(LazerFingers.lazerPoint1X - LazerFingers.lazerPoint2X) < EPSILON) {
	        // We've a vertical line, thus check only the x-value of the point.
	        tf = (Math.abs(u.getX() - LazerFingers.lazerPoint1X) < EPSILON);
	    } else {
	        float m = (LazerFingers.lazerPoint2Y - LazerFingers.lazerPoint1Y) / (LazerFingers.lazerPoint2X - LazerFingers.lazerPoint1X);
	        float b = LazerFingers.lazerPoint1Y - m * LazerFingers.lazerPoint1X;
	        tf = (Math.abs(u.getY() - (m * u.getX() + b)) < EPSILON);
	    }
		if(tf) {
			u.die();
		}
	}
	
	public static void updateLazerFingers() { 
		// End fire fingers!
		if(timer!=null) {
			if(timer.getColor() == Color.RED) {
				timer.setColor(Color.CYAN);
			}
			else {
				timer.setColor(Color.RED);
			}
			timer.setName("Lazer Fingers " + (getDuration() - (GameActivity.getGameTime() - startTime))/1000);
		}
		if(GameActivity.getGameTime() - startTime > getDuration()) {
			TouchEvent.lazerFingers = false;
			lazerPoint1X = -100;
			lazerPoint1Y = -100;
			lazerPoint2X = -100;
			lazerPoint2Y = -100;
		}
	}
	
	public static void setScreenElement(ScreenElement s) {
		timer = s;
	}
	
	public static void drawLazerFingers(Canvas canvas, Paint myPaint) {
		if(lazerPoint1X >= 0 && lazerPoint2X >= 0) {
			myPaint.setStrokeWidth(3);
			myPaint.setColor(Color.RED);
			canvas.drawLine(lazerPoint1X, lazerPoint1Y, lazerPoint2X, lazerPoint2Y, myPaint);
		}
	}
	
	public static void respondToTouch(MotionEvent event) {
		float pos1 = event.getX(event.findPointerIndex(event.getPointerId(0)));
		float pos2 = event.getY(event.findPointerIndex(event.getPointerId(0)));
    	int action = MotionEventCompat.getActionMasked(event);
		// Respond to a single touch event
	    if(event.getPointerCount() <= 1) {
	    	
	    	if(action == android.view.MotionEvent.ACTION_DOWN) {
	    	}
	    	if(action == android.view.MotionEvent.ACTION_UP) {
    			lazerPoint1X = -100;
    			lazerPoint1Y = -100;
    			lazerPoint2X = -100;
    			lazerPoint2Y = -100;
	    	}
	    }
	    
	    // Respond to a multitouch event.
	    if(event.getPointerCount() > 1) {
			float pos1Second = event.getX(event.findPointerIndex(event.getPointerId(1)));
			float pos2Second = event.getY(event.findPointerIndex(event.getPointerId(1)));
		    if(action == MotionEvent.ACTION_POINTER_UP) {
	    			lazerPoint1X = -100;
	    			lazerPoint1Y = -100;
	    			lazerPoint2X = -100;
	    			lazerPoint2Y = -100;
		    }
		    if(TouchEvent.lazerFingers){ 
    			lazerPoint1X = pos1;
    			lazerPoint1Y = pos2;
	    		lazerPoint2X = pos1Second;
	    		lazerPoint2Y = pos2Second;
		    }
	    }
	}

	public static long getDuration() {
		return duration;
	}

	public static void setDuration(long duration) {
		LazerFingers.duration = duration;
	}
}