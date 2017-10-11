package com.nbaFtAnalysis.backend;

public class FreeThrow {

	private String end_result,game,player,score,time;
	private int period,season;

	private boolean playoffs,shot_made;

	public FreeThrow(String[] freeThrow){

		end_result = freeThrow[0];
		game = freeThrow[1];
		freeThrow[3] = freeThrow[3].substring(0, 1);
		period = Integer.parseInt(freeThrow[3]);	
		player = freeThrow[5].toLowerCase();
		playoffs = freeThrow[6].toLowerCase().equals("regular")? false : true;
		score = freeThrow[7];

		String se = freeThrow[8].substring(freeThrow[8].length()-4, freeThrow[8].length());
		
		if(!freeThrow[2].equals("400278346.0"))
			season = Integer.parseInt(freeThrow[8].substring(freeThrow[8].length()-4, freeThrow[8].length()));
		else
			season = 2013;
		
		shot_made = freeThrow[9].equals("1") ? true : false;
		time = freeThrow[10];
	}

	public String getPlayerName(){
		return player;
	}

	public String getTime(){
		return time;
	}
	public String getEndResult(){
		return end_result;
	}
	public String getScore(){
		return score;
	}
	public String getGame(){
		return game;
	}
	public boolean getShotMade(){
		return shot_made;
	}
	public int getPeriod(){
		return period;
	}
	public int getSeason(){
		return season;
	}
	public boolean getPlayoffs(){
		return playoffs;
	}
	
	public void printAllDetailsLineSpace(){
		System.out.println("end_result: " + end_result);
		System.out.println("game: " + game);
		System.out.println("period: " + period);
		System.out.println("player: " + player);
		System.out.println("playoffs: " + playoffs);
		System.out.println("score: " + score);
		System.out.println("season: " + season);
		System.out.println("shot made: " + shot_made);
		System.out.println("time: " + time);
		System.out.println();
	}
}
