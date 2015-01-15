package com.AIG.screenelements;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.AIG.abilities.Ability;
import com.AIG.blasteroids.touchevents.TouchEvent;

public class Combo extends ArrayList<ScreenElement> {
	public static ArrayList<Combo> allCombos = new ArrayList<Combo>();
	
	public int comboTop;
	public int comboBot;
	public String activity;
	public int oldX = 0;
	public float startComboX = 0;
	public float moveBy = 0;
	public int leftorright = 0;
	public float curFingerPos = 0;
	
	public Combo(int top, int bot, String s) {
		activity = s;
		comboTop = top;
		comboBot = bot;
		allCombos.add(this);
	}
	
	public static Combo getComboWithin(float x, float y, String act) {
  		for(int i = 0; i < allCombos.size(); i++) {
  			Combo c = allCombos.get(i);
			if(y<c.getBot() && y>c.getTop() && c.activity.equals(act)) {
				return c;
			}
		}
		return null;
	}
	
	public void setOldX() {
  		for(int i = 0; i < allCombos.size(); i++) {
  			Combo c = allCombos.get(i);
  	  		for(int j = 0; j < c.size(); j++) {
  	  			ScreenElement s = c.get(j);
					s.setOldX();
			}
		}
	}
	
	public void moveHoriz(float m) {
	  		for(int j = 0; j < this.size(); j++) {
  	  			ScreenElement s = this.get(j);
			s.moveInstantly(s.getX() + m, s.getY());
		}
	}
	
	public static void drawCombos(Canvas canvas, Paint myPaint, String activity) {
  		for(int i = 0; i < allCombos.size(); i++) {
  			Combo c = allCombos.get(i);
  	  		for(int j = 0; j < c.size(); j++) {
  	  			ScreenElement s = c.get(j);
				if(s.getActivity() == activity) {
					s.draw(canvas, myPaint);
				}
			}
		}
	}
	
	public static void updateCombos(String currAct) {
			int step = 10;
	  		for(int i = 0; i < allCombos.size(); i++) {
	  			Combo c = allCombos.get(i);
	  	  		for(int j = 0; j < c.size(); j++) {
	  	  			ScreenElement s = c.get(j);
					if(c.startComboX != 0 && currAct.equals(c.activity)) {
						if(s.getX() >= s.oldX + c.moveBy && c.leftorright > 0) {
						}
						else if(s.getX() <= s.oldX + c.moveBy && c.leftorright < 0) {
						}
						else if(c.leftorright > 0){
							s.moveInstantly(s.getX() + step, s.getY());
						}
						else if(c.leftorright < 0){
							s.moveInstantly(s.getX() - step, s.getY());
						}
					}
				}
			}
		}
	
	public int getTop() {
		return comboTop;
	}
	
	public int getBot() {
		return comboBot;
	}
	
}