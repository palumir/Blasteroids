package com.DJG.abilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

import com.DJG.fd.GameActivity;
import com.DJG.fd.R;
import com.DJG.fd.touchevents.TouchEvent;
import com.DJG.screenelements.ScreenElement;

public class FireFingers {
	// General ability attributes.
	private static long startTime;
	private static long duration = 20000;
	private static int radius = 140;
	private static int explosionDuration = 550;
	public static ScreenElement timer;
	
	// Bitmap
	public static Bitmap fireBMP = GameActivity.makeTransparent(BitmapFactory.decodeResource(GameActivity.gameContext.getResources(), R.drawable.fire));
	
	public static void startFireFingers(int newDuration) {
		TouchEvent.fireFingers = true;
		startTime = GameActivity.getGameTime();
		setDuration(newDuration);
	}
	
	public static void updateFireFingers() {
		// End fire fingers!
		if(timer!=null) {
			if(timer.getColor() == Color.RED) {
				timer.setColor(Color.YELLOW);
			}
			else {
				timer.setColor(Color.RED);
			}
			timer.setName("Bomb Fingers " + (getDuration() - (GameActivity.getGameTime() - startTime))/1000);
		}
		if(GameActivity.getGameTime() - startTime > getDuration()) {
				TouchEvent.fireFingers = false;
		}
	}
	
	public static void setScreenElement(ScreenElement s) {
		timer = s;
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
		    	if(TouchEvent.fireFingers) {
					Bomb b = new Bomb(pos1,pos2,radius,explosionDuration, Color.RED, Color.YELLOW);
					Ability.getAbility("Bomb").playPlaceSound();
		    	}
	    	}
	    }
	    
	    // Respond to a multitouch event.
	    if(event.getPointerCount() > 1) {
			float pos1Second = event.getX(event.findPointerIndex(event.getPointerId(1)));
			float pos2Second = event.getY(event.findPointerIndex(event.getPointerId(1)));
		    if(action == MotionEvent.ACTION_POINTER_DOWN) {
		    }
		    else if(action == MotionEvent.ACTION_POINTER_UP) {
		    	if(TouchEvent.fireFingers) {
		    		if(event.getActionIndex() == event.getPointerId(0)) {
		    			Bomb b1 = new Bomb(pos1,pos2,radius,explosionDuration, Color.RED, Color.YELLOW);
		    			Ability.getAbility("Bomb").playPlaceSound();
		    		}
		    		if(event.getActionIndex() == event.getPointerId(1)) {
						Bomb b2 = new Bomb(pos1Second,pos2Second,radius,explosionDuration, Color.RED, Color.YELLOW);
						Ability.getAbility("Bomb").playPlaceSound();
		    		}
		    	}
		    }
	    }
	}

	public static long getDuration() {
		return duration;
	}

	public static void setDuration(long duration) {
		FireFingers.duration = duration;
	}
}