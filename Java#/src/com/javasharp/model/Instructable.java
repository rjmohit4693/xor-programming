package com.javasharp.model;

public interface Instructable {

	public void instruct(ScoreContext);
	
	public Length getLength();
	
	public void onTimeSignatureChanged();
	
}
