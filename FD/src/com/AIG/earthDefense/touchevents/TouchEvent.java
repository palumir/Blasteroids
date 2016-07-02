package com.AIG.earthDefense.touchevents;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

import com.AIG.abilities.Ability;
import com.AIG.abilities.FireFingers;
import com.AIG.abilities.LazerFingers;
import com.AIG.earthDefense.GameActivity;
import com.AIG.earthDefense.MainActivity;
import com.AIG.screenelements.ScreenElement;
import com.AIG.units.Unit;
import com.AIG.units.UnitType;

public class TouchEvent {
	
	// What type of touch is it?
	public static boolean lazerFingers = false;
	public static boolean fireFingers = false;
	
	// Grabbed units (two, one for each hand);
	public static int action1 = 0;
	public static int action2 = 0;
	public static Ability grabbedAbility = null;
	public static UnitType grabbedPlanet = null;
	public static float grabbedAbilityX;
	public static float grabbedAbilityY;
	public static Ability secondGrabbedAbility = null;
	public static float secondGrabbedAbilityX;
	public static float secondGrabbedAbilityY;
	public static Unit grabbedUnit = null;
	public static Unit secondGrabbedUnit = null;
	public static ScreenElement grabbedScreenElement = null;
	public static ScreenElement secondGrabbedScreenElement = null;
	
	public static void respondToMainTouchevent(MotionEvent event) {
		float pos1 = event.getX(event.findPointerIndex(event.getPointerId(0)));
		float pos2 = event.getY(event.findPointerIndex(event.getPointerId(0)));
    	int action = MotionEventCompat.getActionMasked(event);
    		if(action == android.view.MotionEvent.ACTION_DOWN) {
    			grabbedScreenElement = ScreenElement.getScreenElementAt(pos1,pos2,"Main");
    		}
    		else if(action == android.view.MotionEvent.ACTION_UP) {
    			ScreenElement touchedElement = ScreenElement.getScreenElementAt(pos1, pos2,"Main");
	    			// Respond to a click on a button
    				if(grabbedScreenElement != null && grabbedScreenElement==touchedElement && grabbedScreenElement.getType().equals("Drawn myButton")) {
	    				String intentName = grabbedScreenElement.getName();
	    				MainActivity.startIntent(intentName);
	    			}
    		}
    		else {
    		}
	}
	
	public static void respondToTutorialTouchEvent(MotionEvent event) {
	}
	
	public static void respondToOptionsTouchEvent(MotionEvent event) {
	}

	
	public static void respondToGameTouchEvent(MotionEvent event) { 
		if(fireFingers || lazerFingers) {
			specialTouch(event);
		}
		respondToScreenElementTouch(event);
		if(!GameActivity.paused) {
			respondToAbilityTouch(event);
			respondToUnitTouch(event);
		}
	}
	
	static void specialTouch(MotionEvent event) {
		if(fireFingers) {
			FireFingers.respondToTouch(event);
		}
		if(lazerFingers) {
			LazerFingers.respondToTouch(event);
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
	    			grabbedScreenElement = ScreenElement.getScreenElementAt(pos1,pos2,"Game");
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
	    			secondGrabbedScreenElement = ScreenElement.getScreenElementAt(pos1,pos2,"Game");
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
		    		grabbedScreenElement = ScreenElement.getScreenElementAt(pos1,pos2,"Game");
		    	}
		    	if(event.getActionIndex() == event.getPointerId(1)) {
		    		secondGrabbedScreenElement = ScreenElement.getScreenElementAt(pos1Second,pos2Second,"Game");
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
	
	public static void purgeTouch() {
		// What type of touch is it?
		fireFingers = false;
		lazerFingers = false;
		
		// Grabbed units (two, one for each hand);
		action1 = 0;
		action2 = 0;
		grabbedAbility = null;
		grabbedAbilityX = -100;
		grabbedAbilityY = -100;
		secondGrabbedAbility = null;
		secondGrabbedAbilityX = -100;
	    secondGrabbedAbilityY = -100;
		grabbedUnit = null;
		secondGrabbedUnit = null;
		grabbedScreenElement = null;
		secondGrabbedScreenElement = null;
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
}