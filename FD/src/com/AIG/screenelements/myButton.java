package com.AIG.screenelements;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.AIG.earthDefense.GameActivity;

public class myButton extends ScreenElement {
	
	private int duration = 1500;
	private double creationTime;
	private static ArrayList<myButton> myButtons = new ArrayList<myButton>();
	public static Object lock = new Object();
	
	private boolean forceColor = false;
	private float width;
	private float height;
	private boolean clickable = true;
	private boolean beingClicked = false;
	
	public myButton(String newText,float xSpawn,float ySpawn,float myWidth,float myHeight,String newActivity) {
		super("Drawn myButton",newText,xSpawn,ySpawn,myWidth,myHeight,newActivity);
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
		myPaint.setStyle(Paint.Style.STROKE);
		myPaint.setColor(Color.WHITE);
		myPaint.setStrokeWidth(1);
		canvas.drawRect(
				getX() - (width+1),
				getY() - (height+1),
				getX() + (width+1),
				getY() + (height+1),
				myPaint);
		myPaint.setStyle(Paint.Style.FILL);
		myPaint.setColor(Color.BLUE);
		if(!isClickable()) {
			myPaint.setColor(Color.RED);
		}
		if(forceColor) {
			myPaint.setColor(color);
		}
		canvas.drawRect(
				getX() - width,
				getY() - height,
				getX() + width,
				getY() + height,
				myPaint);
	}
	
	public void forceColor(int n) {
		forceColor = true;
		color = n;
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

	public boolean isClickable() {
		return clickable;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}

	public boolean isBeingClicked() {
		return beingClicked;
	}

	public void setBeingClicked(boolean beingClicked) {
		this.beingClicked = beingClicked;
	}
	
}