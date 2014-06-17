package com.DJG.fd;

import java.util.ArrayList;

public class Ability {
	
	private ArrayList<Ability> equippedAbilities;
	
	// General information
	private String type;

	// Slot information.
	private int slot;
	private int x;
	private int y;
	
	public static void updateAbilities() {
		if(Bomb.getCurrentBomb() != null) {
			Bomb.getCurrentBomb().updateBomb();
		}
	}
}