package com.AIG.secrets;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.AIG.earthDefense.GameActivity;
import com.AIG.earthDefense.MainActivity;
import com.AIG.earthDefense.Options;

public class Secret {
	public static int secretNumber = 5;
	public static boolean pieActive = false;
	public static boolean fiveActive = false;
	
	public static boolean isPieSecret(float x, float y) {
		boolean pieSecret = (x < 120f && y < 120f) && (GameActivity.getGameTime()/1000 < 3.14 + .3 && GameActivity.getGameTime()/1000 > 3.14 - .3);
		return pieSecret;
	}
	
	public static boolean isFiveSecret() {
		boolean fiveSecret = Options.creditCount >= 5 && MainActivity.prefs.getInt("SurvivalhighScore", 0)/60 >= 5;
		return fiveSecret;
	}
	
	public static void activatePieSecret() {
		if(pieActive==false) {
			pieActive = true;
			SharedPreferences prefs = MainActivity.prefs;
			Editor editor = prefs.edit();
			if(prefs.getInt("pieSecret", 0) == 0) {
				editor.putInt("secretNumber",prefs.getInt("secretNumber", 0) + 1);
			}
			editor.putInt("pieSecret",1);
			editor.commit();
		}
	}
	
	public static void activateFiveSecret() {
		if(fiveActive==false) {
			fiveActive = true;
			SharedPreferences prefs = MainActivity.prefs;
			Editor editor = prefs.edit();
			if(prefs.getInt("fiveSecret", 0) == 0) {
				editor.putInt("secretNumber",prefs.getInt("secretNumber", 0) + 1);
			}
			editor.putInt("fiveSecret",1);
			editor.commit();
		}
	}
}