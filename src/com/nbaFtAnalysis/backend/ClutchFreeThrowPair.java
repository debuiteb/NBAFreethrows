package com.nbaFtAnalysis.backend;

// ### Each object of this class has the two important instance variables of precentage in clutchtime and their total clutchtime attempts ### //

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
