package com.DJG.screenelements;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Combo extends ArrayList<ScreenElement> {
	public static ArrayList<Combo> allCombos = new ArrayList<Combo>();
	
	public int comboTop;
	public int comboBot;
	public int oldX = 0;
	
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
	
	public boolean checkAllX(float p) {
		boolean b = true;
		for(ScreenElement s : this) {
			if(p<0) {
				b = (b & (s.oldX >= s.oldX + p));
			}
			else {
				b = (b & (s.oldX <= s.oldX + p));
			}
		}
		return b;
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
	
	public int getTop() {
		return comboTop;
	}
	
	public int getBot() {
		return comboBot;
	}
	
}