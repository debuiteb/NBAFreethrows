package com.nbaFtAnalysis.backend;

public class ClutchFreeThrowPair {
	
	private double percent;
	private int totalAttempts;
	
	public ClutchFreeThrowPair(double percent,int totalAttempts){
		this.percent = percent;
		this.totalAttempts = totalAttempts;
	}
	public double getPercentage(){
		return percent;
	}
	public int getTotalAttempts(){
		return totalAttempts;
	}
}
