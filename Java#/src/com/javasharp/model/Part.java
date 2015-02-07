package com.javasharp.model;

public class Part implements Instructable {
	
	private Instrument instrument;
	private Clef clef;
	private PartContext partContext;
	private List<Instructions> measures;
	
	
	public Part() {
		
	}

	@Override
	public void instruct() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Length getLength() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onTimeSignatureChanged() {
		// TODO Auto-generated method stub
		
	}
	
	
}
