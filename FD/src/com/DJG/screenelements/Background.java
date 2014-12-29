package com.DJG.screenelements;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.DJG.fd.GameActivity;

public class Background {
	// Background
	static Bitmap background;
	static Canvas bgCanvas;
	static int backX = 0;
	
	public static void drawBackground(Canvas canvas, Paint myPaint, String activity) {
		canvas.drawColor(GameActivity.bgColor);
		if (bgCanvas == null) {
			background = Bitmap.createBitmap(GameActivity.getScreenWidth(),
					GameActivity.getScreenHeight(), Bitmap.Config.ARGB_8888);
			bgCanvas = new Canvas(background);
			myPaint.setStrokeWidth(1);
			int x = 0;
			while (x < GameActivity.getScreenWidth()) {
				int y = 0;
				int n = 0;
				while (y < GameActivity.getScreenHeight()) {
					if (GameActivity.getR().nextInt(GameActivity.getScreenHeight()) == 0) {
						n++;
						int m = GameActivity.getR().nextInt(100);
						if(m==1) myPaint.setColor(Color.CYAN);
						else if(m==2) myPaint.setColor(Color.YELLOW);
						else if(m==3) myPaint.setColor(Color.RED);
						else if(m==4) myPaint.setColor(Color.RED);
						else myPaint.setColor(Color.WHITE);
						bgCanvas.drawPoint(x, y, myPaint);
					}
					if (n > 10) {
						break;
					}
					y++;
				}
				x++;
			}
		}
		// Move the background if it's not paused.
		if(!(GameActivity.paused  && activity.equals("Game"))) {
			backX++;
			if(backX == GameActivity.getScreenWidth()) {
				backX = 0;
			}
		}
		myPaint.setColor(Color.WHITE);
		myPaint.setStrokeWidth(1);
		canvas.drawBitmap(background, backX - GameActivity.getScreenWidth(),0,myPaint);
		canvas.drawBitmap(background, backX, 0, myPaint);
	}
}

