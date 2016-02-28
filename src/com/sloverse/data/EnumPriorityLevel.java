package com.sloverse.data;

public enum EnumPriorityLevel {

	//For data transmission like player look direction/position changed.
	LOW,

	//For data transmission like chat messages and images.
	MEDIUM, 
	
	//For data transmission like server broadcasts or commands.
	HIGH;
}
