package com.sloverse.util;

import java.util.Random;

public class MathUtil {

	public static final Random RAND = new Random();
	
	public static final int clampInt(int value, int min, int max) {
		return (value > max) ? max : (value < min) ? min : value; 
	}
	
	public static final float clampFloat(float value, float min, float max) {
		return (value > max) ? max : (value < min) ? min : value;
	}
}
