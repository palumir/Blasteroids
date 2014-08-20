package com.DJG.fd.touchevents;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

import com.DJG.abilities.Ability;
import com.DJG.abilities.FireFingers;
import com.DJG.abilities.LazerFingers;
import com.DJG.fd.CampaignActivity;
import com.DJG.fd.GameActivity;
import com.DJG.screenelements.Combo;
import com.DJG.screenelements.ScreenElement;
import com.DJG.units.Unit;
import com.DJG.units.UnitType;

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
	
	public static void respondToStoreTouchEvent(MotionEvent event) {
		float pos1 = event.getX(event.findPointerIndex(event.getPointerId(0)));
		float pos2 = event.getY(event.findPointerIndex(event.getPointerId(0)));
    	int action = MotionEventCompat.getActionMasked(event);
    	Combo touchedCombo = Combo.getComboWithin(pos1,pos2);
    		if(action == android.view.MotionEvent.ACTION_DOWN) {
    			grabbedScreenElement = ScreenElement.getScreenElementAt(pos1,pos2);
    			if(touchedCombo!=null) {
    				touchedCombo.setOldX();
    				touchedCombo.startComboX = pos1;
    			}
    		}
    		else if(action == android.view.MotionEvent.ACTION_UP) {
    			ScreenElement touchedElement = ScreenElement.getScreenElementAt(pos1, pos2);
    			if(touchedElement == null) {
    				grabbedAbility = null;
    				grabbedPlanet = null;
    			}
    			else {
    				// Respond to an actual equip
    				if(grabbedScreenElement != null && grabbedScreenElement==touchedElement && grabbedScreenElement.getType().contains("Slot")) {
    					if(grabbedAbility!=null && !grabbedScreenElement.getType().equals("PlanetSlot")) {
	    					if(Ability.getPrefs().getInt(grabbedAbility.getType() + "_purchased", -99) == 1) {
	    						grabbedScreenElement.setName(grabbedAbility.getType());
	    					}
	    					grabbedAbility.equip(grabbedScreenElement.getType());
    					}
    					if(grabbedPlanet!=null && grabbedScreenElement.getType().equals("PlanetSlot")) {
	    					if(Ability.getPrefs().getInt(grabbedPlanet.getType() + "_purchased", -99) == 1) {
	    						grabbedScreenElement.setName(grabbedPlanet.getType());
	    					}
	    					grabbedPlanet.equip();
    					}
    				}
	    			// Respond to a purchase.
    				else if(grabbedScreenElement != null && grabbedScreenElement==touchedElement && grabbedScreenElement.getType() == "Button" && grabbedScreenElement.getName() == "Buy") {
	    				Ability attachedAbility = grabbedScreenElement.getAttachedAbility();
	    				if(attachedAbility!=null) {
	    					attachedAbility.buy();
	    				}
	    				UnitType attachedPlanet = grabbedScreenElement.getAttachedPlanet();
	    				if(attachedPlanet!=null) {
	    					attachedPlanet.buy();
	    				}
	    			}
	    			// Respond to equip click
	    			else if(grabbedScreenElement != null && grabbedScreenElement==touchedElement && grabbedScreenElement.getType() == "Button" && grabbedScreenElement.getName() == "Equip") {
	    				Ability attachedAbility = grabbedScreenElement.getAttachedAbility();
	    				grabbedAbility = attachedAbility;
	    				UnitType attachedPlanet = grabbedScreenElement.getAttachedPlanet();
	    				grabbedPlanet = attachedPlanet;
	    				
	    			}
    			}
    			if(touchedCombo!=null) {
	    			touchedCombo.startComboX = 0;
	    			touchedCombo.moveBy = 0;
	    			touchedCombo.curFingerPos = 0;
	    			touchedCombo.leftorright = 0;
    			}
    		}
    		else {
    			if(touchedCombo!=null) {
	    			touchedCombo.curFingerPos = pos1;
	    			if((pos1 - touchedCombo.startComboX) - touchedCombo.moveBy > 0) {
	    				touchedCombo.leftorright = 1;
	    			}
	    			else {
	    				touchedCombo.leftorright = -1;
	    			}
	    			touchedCombo.moveBy = pos1 - touchedCombo.startComboX;
	    		}
    		}
	}
	
	public static void respondToCampaignTouchEvent(MotionEvent event) {
		float pos1 = event.getX(event.findPointerIndex(event.getPointerId(0)));
		float pos2 = event.getY(event.findPointerIndex(event.getPointerId(0)));
    	int action = MotionEventCompat.getActionMasked(event);
    	Combo touchedCombo = Combo.getComboWithin(pos1,pos2);
    		if(action == android.view.MotionEvent.ACTION_DOWN) {
    			grabbedScreenElement = ScreenElement.getScreenElementAt(pos1,pos2);
    			if(touchedCombo!=null) {
    				touchedCombo.setOldX();
    				touchedCombo.startComboX = pos1;
    			}
    		}
    		else if(action == android.view.MotionEvent.ACTION_UP) {
    			ScreenElement touchedElement = ScreenElement.getScreenElementAt(pos1, pos2);
	    			// Respond to a purchase.
    				if(grabbedScreenElement != null && grabbedScreenElement==touchedElement && grabbedScreenElement.getType() == "Button") {
	    				int attachedNum = Integer.parseInt(grabbedScreenElement.getName());
	    				CampaignActivity.startGame(attachedNum*10);
	    			}
    			if(touchedCombo!=null) {
	    			touchedCombo.startComboX = 0;
	    			touchedCombo.moveBy = 0;
	    			touchedCombo.curFingerPos = 0;
	    			touchedCombo.leftorright = 0;
    			}
    		}
    		else {
    			if(touchedCombo!=null) {
	    			touchedCombo.curFingerPos = pos1;
	    			if((pos1 - touchedCombo.startComboX) - touchedCombo.moveBy > 0) {
	    				touchedCombo.leftorright = 1;
	    			}
	    			else {
	    				touchedCombo.leftorright = -1;
	    			}
	    			touchedCombo.moveBy = pos1 - touchedCombo.startComboX;
	    		}
    		}
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