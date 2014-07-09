package com.DJG.abilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

import com.DJG.fd.GameActivity;
import com.DJG.fd.R;
import com.DJG.fd.touchevents.TouchEvent;

public class FireFingers {
	// General ability attributes.
	private static long startTime;
	private static long duration = 3000;
	
	// Bitmap
	public static Bitmap fireBMP = GameActivity.makeTransparent(BitmapFactory.decodeResource(GameActivity.gameContext.getResources(), R.drawable.fire));
	
	public static void startFireFingers(int newDuration) {
		TouchEvent.setTouchType("Fire Fingers");
		startTime = GameActivity.getGameTime();
		duration = newDuration;
	}
	
	public static void updateFireFingers() {
		// End fire fingers!
		if(GameActivity.getGameTime() - startTime > duration) {
			TouchEvent.setTouchType("Normal");
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
		    	if(TouchEvent.getTouchType() == "Fire Fingers") {
					Bomb b = new Bomb(pos1,pos2,200,500);
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
		    	if(TouchEvent.getTouchType() == "Fire Fingers") {
		    		if(event.getActionIndex() == event.getPointerId(0)) {
		    			Bomb b1 = new Bomb(pos1,pos2,200,500);
		    		}
		    		if(event.getActionIndex() == event.getPointerId(1)) {
						Bomb b2 = new Bomb(pos1Second,pos2Second,200,500);
		    		}
		    	}
		    }
	    }
	}
}