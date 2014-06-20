package com.DJG.units;

import com.DJG.fd.DisplayMessageActivity;
import com.DJG.fd.Wave;

public class Cthulu {
	 private Unit head;
	 private Unit arm1;
	 private Unit arm2;
	 private Unit rocketArm;
	 
	 static int height = DisplayMessageActivity.getScreenHeight();
	 static int width = DisplayMessageActivity.getScreenWidth();
	 
	 public Cthulu() {
		 head = new Unit("Cthulu","Cthulu Head",100,100, 0);
		 //arm1 = new Unit("Cthulu","Cthulu Lazer Arm",400,100, 0);
		// arm2 = new Unit("Cthulu","Cthulu Lazer Arm",400,400, 0);
		 rocketArm = new Unit("Cthulu","Cthulu Rocket Arm",100,400, 0);
	     Wave.addToCurrentWave(head);
		 Wave.addToCurrentWave(rocketArm);
		 //Wave.addToCurrentWave(arm1);
		 //Wave.addToCurrentWave(arm2);
	 }

	 public static void battleStart() {
		 Cthulu boss = new Cthulu();
	 }
	 
	 public Unit getHead() {
		 return head;
	 }
	 
	 public Unit getArm1() {
		 return arm1;
	 }
	 
	 public Unit getArm2() {
		 return arm2;
	 }
	 
	 public Unit getRocketArm() {
		 return rocketArm;
	 }
	 
}