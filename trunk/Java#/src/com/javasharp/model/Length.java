package com.javasharp.model;

public enum Length {
	WHOLE("Whole", 64),
	HALF("Half", 32),
	QUARTER("Quarter", 16),
	EIGHTH("Eighth", 8),
	SIXTEENTH("Sixteenth", 4),
	THIRTY_SECOND("Thirty-second", 2),
	SIXTY_FOURTH("Sixty-fourth", 1);
	
	private final String name;
	private final int length;
	
	private Length(String name, int length) {
		this.name = name;
		this.length = length;
	}
	
	public int getLength(int numDots) {
		int shiftedLength = length>>numDots;
		if(shiftedLength == 0) {
			throw new IllegalArgumentException("This length has too many dots.");
		}
		return 2*length-shiftedLength;
	}
	
	public String toString() {
		return name;
	}
}
