package com.javasharp.model;

public interface Instructable<T> {

	public void instruct(T context);
	
	public int getLength();
	
	public void onTimeSignatureChanged();
	
}