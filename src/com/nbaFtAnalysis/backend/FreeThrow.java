package com.nbaFtAnalysis.backend;

public class FreeThrow {

	private String end_result,game,player,score,time;
	private int period,season;

	private boolean playoffs,shot_made;

	public FreeThrow(String[] freeThrow){

		// the columns skipped are ones that don't provide data relevenat to this apps
		end_result = freeThrow[0];
		game = freeThrow[1];
		freeThrow[3] = freeThrow[3].substring(0, 1);
		period = Integer.parseInt(freeThrow[3]);	
		player = freeThrow[5].toLowerCase();
		playoffs = freeThrow[6].toLowerCase().equals("regular")? false : true;
		score = freeThrow[7];
		
		// this handles some weird bad data from the 2013 season
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
		
		StringBuilder stringbuilder = new StringBuilder();
		String end = "\n";
		stringbuilder.append("end_result: ").append(end_result).append(end).append("game: ").append(game)
		.append(end).append("period: ").append(period).append(end).append("player: ").append(player)
		.append(end).append("playoffs: ").append(playoffs).append(end).append("score: ").append(score)
		.append(end).append("season: ").append(season).append(end).append("shot made: ").append(shot_made)
		.append(end).append("time: ").append(time);
		
		System.out.println(stringbuilder.toString());
		
		System.out.println();
	}
}
