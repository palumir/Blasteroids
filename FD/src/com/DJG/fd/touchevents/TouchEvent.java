package com.DJG.fd.touchevents;

import com.DJG.abilities.Ability;
import com.DJG.abilities.Bomb;
import com.DJG.units.Unit;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

public class TouchEvent {
	
	// What type of touch is it?
	public static String touchType = "Fire Fingers";
	
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
	
	public static void respondToTouchEvent(MotionEvent event) { 
		if(touchType != "Normal") {
			specialTouch(event);
		}
		respondToAbilityTouch(event);
		respondToUnitTouch(event);
	}
	
	static void specialTouch(MotionEvent event) {
		float pos1 = event.getX(event.findPointerIndex(event.getPointerId(0)));
		float pos2 = event.getY(event.findPointerIndex(event.getPointerId(0)));
    	int action = MotionEventCompat.getActionMasked(event);
		// Respond to a single touch event
	    if(event.getPointerCount() <= 1) {
	    	if(action == android.view.MotionEvent.ACTION_DOWN) {
	    	}
	    	if(action == android.view.MotionEvent.ACTION_UP) {
		    	if(touchType == "Fire Fingers") {
					Bomb b = new Bomb(pos1,pos2,100,500);
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
		    	if(touchType == "Fire Fingers") {
		    		if(event.getActionIndex() == event.getPointerId(0)) {
		    			Bomb b1 = new Bomb(pos1,pos2,100,500);
		    		}
		    		if(event.getActionIndex() == event.getPointerId(1)) {
						Bomb b2 = new Bomb(pos1Second,pos2Second,100,500);
		    		}
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
	    
	    // Respond to a multitouch event.
	    if(event.getPointerCount() > 1) {
			float pos1Second = event.getX(event.findPointerIndex(event.getPointerId(1)));
			float pos2Second = event.getY(event.findPointerIndex(event.getPointerId(1)));
			
		    if(action == android.view.MotionEvent.ACTION_POINTER_DOWN) {
		    	// First touch.
		    	if(grabbedAbility == null) {
		    		grabbedAbility = Ability.getAbilityAt(pos1,pos2);
		    	}
	    		grabbedAbilityX = pos1;
	    		grabbedAbilityY = pos2;
		    	
		    	// Second touch.
		    	if(secondGrabbedAbility == null) {
		    		secondGrabbedAbility = Ability.getAbilityAt(pos1Second,pos2Second);
		    	}
	    		secondGrabbedAbilityX = pos1Second;
	    		secondGrabbedAbilityY = pos2Second;
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
	    	else if(grabbedAbility != null) {
	    		grabbedAbilityX = pos1;
	    		grabbedAbilityY = pos2;
	    	}
	    	else if(secondGrabbedAbility != null) {
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
	    	if(action == android.view.MotionEvent.ACTION_DOWN) {
	    		grabbedUnit = Unit.getUnitAt(pos1,pos2);
	    	}
	    	else if(action == android.view.MotionEvent.ACTION_UP) {
	    		if(grabbedUnit != null && grabbedUnit.getKillable()) {
	    			grabbedUnit.hurt(1);
	    		}
	    	}
	    }
	    
	    // Respond to a multitouch event.
	    if(event.getPointerCount() > 1) {
			float pos1Second = event.getX(event.findPointerIndex(event.getPointerId(1)));
			float pos2Second = event.getY(event.findPointerIndex(event.getPointerId(1)));
			
		    if(action == android.view.MotionEvent.ACTION_POINTER_DOWN) {
		    	grabbedUnit = Unit.getUnitAt(pos1,pos2);
		    	secondGrabbedUnit = Unit.getUnitAt(pos1Second,pos2Second);
		    }
		    else if(action == android.view.MotionEvent.ACTION_POINTER_UP) {
		    	if(grabbedUnit != null && grabbedUnit.getKillable() && event.getActionIndex() == event.getPointerId(0)) {
		    		grabbedUnit.hurt(1);
		    	}
	    		if(secondGrabbedUnit != null && secondGrabbedUnit.getKillable() && event.getActionIndex() == event.getPointerId(1)) {
	    			secondGrabbedUnit.hurt(1);
	    		}
		    }
	    }
	}
	
}