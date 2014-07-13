package com.DJG.screenelements;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.DJG.fd.touchevents.TouchEvent;

public class Combo extends ArrayList<ScreenElement> {
	public static ArrayList<Combo> allCombos = new ArrayList<Combo>();
	
	public int comboTop;
	public int comboBot;
	public int oldX = 0;
	public float startComboX = 0;
	public float moveBy = 0;
	public int leftorright = 0;
	public float curFingerPos = 0;
	
	public Combo(int top, int bot) {
		comboTop = top;
		comboBot = bot;
		allCombos.add(this);
	}
	
	public static Combo getComboWithin(float x, float y) {
		for(Combo c : allCombos) {
			if(y<c.getBot() && y>c.getTop()) {
				return c;
			}
		}
		return null;
	}
	
	public void setOldX() {
		for(Combo c : allCombos) {
			for(ScreenElement s : c) {
					s.setOldX();
			}
		}
	}
	
	public void moveHoriz(float m) {
		for(ScreenElement s : this) {
			s.moveInstantly(s.getX() + m, s.getY());
		}
	}
	
	public static void drawCombos(Canvas canvas, Paint myPaint, String activity) {
		for(Combo c : allCombos) {
			for(ScreenElement s : c) {
				if(s.getActivity() == activity) {
					s.draw(canvas, myPaint);
				}
			}
		}
	}
	
	public static void updateCombos() {
			int step = 10;
			for(Combo c : allCombos) {
				for(ScreenElement s : c) {
					if(c.startComboX != 0) {
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