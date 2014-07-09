package com.DJG.fd.touchevents;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

import com.DJG.abilities.Ability;
import com.DJG.abilities.FireFingers;
import com.DJG.fd.GameActivity;
import com.DJG.screenelements.ScreenElement;
import com.DJG.units.Unit;

public class TouchEvent {
	
	// What type of touch is it?
	private static String touchType = "Normal";
	
	// Grabbed units (two, one for each hand);
	public static int action1 = 0;
	public static int action2 = 0;
	public static Ability grabbedAbility = null;
	public static float grabbedAbilityX;
	public static float grabbedAbilityY;
	public static Ability secondGrabbedAbility = null;
	public static float secondGrabbedAbilityX;
	public static float secondGrabbedAbilityY;
	public static Unit grabbedUnit = null;
	public static Unit secondGrabbedUnit = null;
	public static ScreenElement grabbedScreenElement = null;
	public static ScreenElement secondGrabbedScreenElement = null;
	
	public static void respondToTouchEvent(MotionEvent event) { 
		if(touchType != "Normal") {
			specialTouch(event);
		}
		respondToScreenElementTouch(event);
		if(!GameActivity.paused) {
			respondToAbilityTouch(event);
			respondToUnitTouch(event);
		}
	}
	
	static void specialTouch(MotionEvent event) {
		if(touchType == "Fire Fingers") {
			FireFingers.respondToTouch(event);
		}
	}
	
	static void respondToScreenElementTouch(MotionEvent event) {
		float pos1 = event.getX(event.findPointerIndex(event.getPointerId(0)));
		float pos2 = event.getY(event.findPointerIndex(event.getPointerId(0)));
    	int action = MotionEventCompat.getActionMasked(event);
		// Respond to a single touch event
	    if(event.getPointerCount() <= 1) {
	    	if(!(grabbedScreenElement == null && secondGrabbedScreenElement != null)) {
	    		if(action == android.view.MotionEvent.ACTION_DOWN) {
	    			grabbedScreenElement = ScreenElement.getScreenElementAt(pos1,pos2);
	    		}
	    		else if(action == android.view.MotionEvent.ACTION_UP) {
	    			if(grabbedScreenElement != null) {
	    				grabbedScreenElement.respondToTouch();
	    				grabbedScreenElement = null;
	    			}
	    		}
	    	}
	    	else {
	    		if(action == android.view.MotionEvent.ACTION_DOWN) {
	    			secondGrabbedScreenElement = ScreenElement.getScreenElementAt(pos1,pos2);
	    		}
	    		else if(action == android.view.MotionEvent.ACTION_UP) {
	    			if(secondGrabbedScreenElement != null) {
	    				secondGrabbedScreenElement.respondToTouch();
	    				secondGrabbedScreenElement = null;
	    			}
	    		}
	    	}
	    }
	    
	    // Respond to a multitouch event.
	    if(event.getPointerCount() > 1) {
			float pos1Second = event.getX(event.findPointerIndex(event.getPointerId(1)));
			float pos2Second = event.getY(event.findPointerIndex(event.getPointerId(1)));
			
			
		    if(action == android.view.MotionEvent.ACTION_POINTER_DOWN) {
		    	if(event.getActionIndex() == event.getPointerId(0)){
		    		grabbedScreenElement = ScreenElement.getScreenElementAt(pos1,pos2);
		    	}
		    	if(event.getActionIndex() == event.getPointerId(1)) {
		    		secondGrabbedScreenElement = ScreenElement.getScreenElementAt(pos1Second,pos2Second);
		    	}
		    }
		    else if(action == android.view.MotionEvent.ACTION_POINTER_UP) {
		    	if(grabbedScreenElement != null && event.getActionIndex() == event.getPointerId(0)) {
		    		grabbedScreenElement.respondToTouch();
		    		grabbedScreenElement = null;
		    	}
	    		if(secondGrabbedScreenElement != null && event.getActionIndex() == event.getPointerId(1)) {
	    			secondGrabbedScreenElement.respondToTouch();
	    			secondGrabbedScreenElement = null;
	    		}
		    }
	    }
	}
	
	static void respondToAbilityTouch(MotionEvent event) {
		float pos1 = event.getX(event.findPointerIndex(event.getPointerId(0)));
		float pos2 = event.getY(event.findPointerIndex(event.getPointerId(0)));
    	int action = MotionEventCompat.getActionMasked(event);
    	
		// Respond to a single touch event
	    if(event.getPointerCount() <= 1) {
	    
	    	if(!(grabbedAbility == null && secondGrabbedAbility != null)) {
		    	if(action == android.view.MotionEvent.ACTION_DOWN) {
		    		if(grabbedAbility == null) {
		    			grabbedAbility = Ability.getAbilityAt(pos1,pos2);
		    		}
		    		grabbedAbilityX = pos1;
		    		grabbedAbilityY = pos2;
		    	}	
		    	else if(action == android.view.MotionEvent.ACTION_UP) {
		    		if(grabbedAbility != null && grabbedAbility != Ability.getAbilityAt(pos1,pos2)) {
		    			grabbedAbility.useAbility(pos1,pos2);
		    		}
		    		if(grabbedAbility != null) {
		    			grabbedAbility = null;	    			
		    		}
		    	}
		    	else if(grabbedAbility != null) {
		    		grabbedAbilityX = pos1;
		    		grabbedAbilityY = pos2;
		    	}
		    	}
	    	else {
		    	if(action == android.view.MotionEvent.ACTION_DOWN) {
		    		if(secondGrabbedAbility == null) {
		    			secondGrabbedAbility = Ability.getAbilityAt(pos1,pos2);
		    		}
		    		secondGrabbedAbilityX = pos1;
		    		secondGrabbedAbilityY = pos2;
		    	}
		    	else if(action == android.view.MotionEvent.ACTION_UP) {
		    		if(secondGrabbedAbility != null && secondGrabbedAbility != Ability.getAbilityAt(pos1,pos2)) {
		    			secondGrabbedAbility.useAbility(pos1,pos2);
		    		}
		    		if(secondGrabbedAbility != null) {
		    			secondGrabbedAbility = null;	    			
		    		}
		    	}
		    	else if(secondGrabbedAbility != null) {
		    		secondGrabbedAbilityX = pos1;
		    		secondGrabbedAbilityY = pos2;
		    	}
	    	}
	    }
	    
	    // Respond to a multitouch event.
	    if(event.getPointerCount() > 1) {
			float pos1Second = event.getX(event.findPointerIndex(event.getPointerId(1)));
			float pos2Second = event.getY(event.findPointerIndex(event.getPointerId(1)));
			
		    if(action == android.view.MotionEvent.ACTION_POINTER_DOWN) {
		    	// First touch.
		    	if(grabbedAbility == null && event.getActionIndex() == event.getPointerId(0)) {
		    		grabbedAbility = Ability.getAbilityAt(pos1,pos2);
		    	}
		    	if(event.getActionIndex() == event.getPointerId(0)) {
		    		grabbedAbilityX = pos1;
		    		grabbedAbilityY = pos2;
		    	}
		    	
		    	// Second touch.
		    	if(secondGrabbedAbility == null && event.getActionIndex() == event.getPointerId(1)) {
		    		secondGrabbedAbility = Ability.getAbilityAt(pos1Second,pos2Second);
		    	}
		    	if(event.getActionIndex() == event.getPointerId(1)) {
		    		secondGrabbedAbilityX = pos1Second;
		    		secondGrabbedAbilityY = pos2Second;
		    	}
		    }
		    else if(action == android.view.MotionEvent.ACTION_POINTER_UP) {
		    	
		    	// First touch.
		    	if(grabbedAbility != null && grabbedAbility != Ability.getAbilityAt(pos1,pos2) && event.getActionIndex() == event.getPointerId(0)) {
		    		grabbedAbility.useAbility(pos1,pos2);
		    	}
		    	if(grabbedAbility != null && event.getActionIndex() == event.getPointerId(0)) {
		    		grabbedAbility = null;
		    	}
		    	
		    	// Second touch.
	    		if(secondGrabbedAbility != null && secondGrabbedAbility != Ability.getAbilityAt(pos1Second,pos2Second) && event.getActionIndex() == event.getPointerId(1)) {
		    		secondGrabbedAbility.useAbility(pos1Second,pos2Second);
		    	}
	    		if(secondGrabbedAbility != null && event.getActionIndex() == event.getPointerId(1)) {
		    		secondGrabbedAbility = null;
	    		}
		    }
	    	if(grabbedAbility != null) {
	    		grabbedAbilityX = pos1;
	    		grabbedAbilityY = pos2;
	    	}
	    	if(secondGrabbedAbility != null) {
	    		secondGrabbedAbilityX = pos1Second;
	    		secondGrabbedAbilityY = pos2Second;
	    	}
	    }
	}
	
	static void respondToUnitTouch(MotionEvent event) {
		float pos1 = event.getX(event.findPointerIndex(event.getPointerId(0)));
		float pos2 = event.getY(event.findPointerIndex(event.getPointerId(0)));
    	int action = MotionEventCompat.getActionMasked(event);
		// Respond to a single touch event
	    if(event.getPointerCount() <= 1) {
	    	if(!(grabbedUnit == null && secondGrabbedUnit != null)) {
	    		if(action == android.view.MotionEvent.ACTION_DOWN) {
	    			grabbedUnit = Unit.getUnitAt(pos1,pos2);
	    		}
	    		else if(action == android.view.MotionEvent.ACTION_UP) {
	    			if(grabbedUnit != null && grabbedUnit.getKillable()) {
	    				grabbedUnit.hurt(1);
	    				grabbedUnit = null;
	    			}
	    		}
	    	}
	    	else {
	    		if(action == android.view.MotionEvent.ACTION_DOWN) {
	    			secondGrabbedUnit = Unit.getUnitAt(pos1,pos2);
	    		}
	    		else if(action == android.view.MotionEvent.ACTION_UP) {
	    			if(secondGrabbedUnit != null && secondGrabbedUnit.getKillable()) {
	    				secondGrabbedUnit.hurt(1);
	    				secondGrabbedUnit = null;
	    			}
	    		}
	    	}
	    }
	    
	    // Respond to a multitouch event.
	    if(event.getPointerCount() > 1) {
			float pos1Second = event.getX(event.findPointerIndex(event.getPointerId(1)));
			float pos2Second = event.getY(event.findPointerIndex(event.getPointerId(1)));
			
			
		    if(action == android.view.MotionEvent.ACTION_POINTER_DOWN) {
		    	if(event.getActionIndex() == event.getPointerId(0)){
		    		grabbedUnit = Unit.getUnitAt(pos1,pos2);
		    	}
		    	if(event.getActionIndex() == event.getPointerId(1)) {
		    		secondGrabbedUnit = Unit.getUnitAt(pos1Second,pos2Second);
		    	}
		    }
		    else if(action == android.view.MotionEvent.ACTION_POINTER_UP) {
		    	if(grabbedUnit != null && grabbedUnit.getKillable() && event.getActionIndex() == event.getPointerId(0)) {
		    		grabbedUnit.hurt(1);
		    		grabbedUnit = null;
		    	}
	    		if(secondGrabbedUnit != null && secondGrabbedUnit.getKillable() && event.getActionIndex() == event.getPointerId(1)) {
	    			secondGrabbedUnit.hurt(1);
	    			secondGrabbedUnit = null;
	    		}
		    }
	    }
	}
	
	public static String getTouchType() {
		return touchType;
	}
	
	public static void setTouchType(String s) {
		touchType = s;
	}
}