package com.javasharp.model;

public interface Instructable {

	public void instruct(ScoreContext);
	
	public int getLength();
	
	public void onTimeSignatureChanged();
	
}
