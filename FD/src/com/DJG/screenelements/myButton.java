package com.DJG.screenelements;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.DJG.fd.GameActivity;

public class myButton extends ScreenElement {
	
	private int duration = 1500;
	private double creationTime;
	private static ArrayList<myButton> myButtons = new ArrayList<myButton>();
	public static Object lock = new Object();
	
	private float width;
	private float height;
	
	public myButton(String newText,float xSpawn,float ySpawn,float myWidth,float myHeight,String newActivity) {
		super("Drawn myButton",newText,xSpawn,ySpawn,newActivity);
		width = myWidth;
		height = myHeight;
		creationTime = GameActivity.getGameTime();
		myButtons.add(this);
	}
	
	public static void updatemyButtons() {
		synchronized(lock) {
		for(int i = 0; i < myButtons.size(); i++) {
			myButton s = myButtons.get(i);
			if(GameActivity.getGameTime() - s.creationTime > s.duration) {
				killmyButton(s);
			}
			else {
				s.y++;
			}
		}
		}
	}
	
	public void draw(Canvas canvas, Paint myPaint) {
		myPaint.setStyle(Paint.Style.FILL);
		myPaint.setColor(Color.RED);
		canvas.drawRect(
				getX() - width,
				getY() - height,
				getX() + width,
				getY() + height,
				myPaint);
	}
	
	public static void killmyButton(ScreenElement u) {
		synchronized(lock) {
		if(myButtons.size()!=0){
				int foundScreenElement = 0;
				for(int j = 0; j < myButtons.size(); j++) {
					myButton v =myButtons.get(j);
					if(u == v) {
						break;
					}
					foundScreenElement++;
				}
				if(foundScreenElement < myButtons.size()) {
					ScreenElement.killScreenElement(myButtons.get(foundScreenElement));
					myButtons.remove(foundScreenElement);
			}
		}
	}
	}
	
}