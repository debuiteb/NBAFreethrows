package com.nbaFtAnalysis.backend;

// ### Each object of this class has the two important instance variables of precentage in clutchtime and their total clutchtime attempts ### //

public class ClutchFreeThrowPair {

	private double percent;
	private int totalAttempts;

	public ClutchFreeThrowPair(double percent,int totalAttempts){
		this.percent = percent;
		this.totalAttempts = totalAttempts;
	}

	public ClutchFreeThrowPair(Player player){
		
		// TODO : finish this method and test to see if working

		int made=0;
		int total=0;
		int scoreDif,score1=0,score2=0,index;
		String fullScore,score1Str,score2Str;
		for(FreeThrow ft : player.getFreeThrowsTaken()){
			score1Str = "";
			score2Str = "";
			fullScore = ft.getScore();
			if(fullScore.equals("regular")) 
				continue;
			index=0;
			char c;
			while(fullScore.charAt(index)!=' '){
				c = fullScore.charAt(index);
				score1Str+=String.valueOf(c);

				index++;
			}
			score1 = Integer.parseInt(score1Str);

			while(fullScore.charAt(index)==' ' || fullScore.charAt(index)=='-'){
				index++;
			}
			while(fullScore.charAt(index)!=' '){
				c = fullScore.charAt(index);
				score2Str+=String.valueOf(c);

				index++;
				if(index >= fullScore.length())
					break;
			}
			score2 = Integer.parseInt(score2Str);


			scoreDif = Math.abs(score1 - score2); // difference in score at time of the fr

			index=0;
			String timeStr="";
			while(ft.getTime().charAt(index)!=':'){
				c = ft.getTime().charAt(index);
				timeStr += String.valueOf(c);
				index++;
			}
			int time = Integer.parseInt(timeStr);
			if(ft.getPeriod()==4 && time>=7 && scoreDif<=5){
				total++;
				if(ft.getShotMade())
					made++;
			}
		}
	}

	public double getPercentage(){
		return percent;
	}
	public int getTotalAttempts(){
		return totalAttempts;
	}
}
