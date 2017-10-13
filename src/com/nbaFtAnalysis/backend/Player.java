package com.nbaFtAnalysis.backend;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Player {

	private String name;
	private int numberOfFreeThrowsTaken;
	private double overallAverage;
	private Map<Object,Object>  seasonAverage;
	private ArrayList<FreeThrow> freeThrowsTaken;

	public Player(String name, FreeThrow freeThrow){
		this.name = name.toLowerCase();
		numberOfFreeThrowsTaken = 1;
		seasonAverage = new HashMap<Object,Object>();
		freeThrowsTaken = new ArrayList<FreeThrow>();
		freeThrowsTaken.add(freeThrow);
	}
	
	public void addFT(FreeThrow ft){
		freeThrowsTaken.add(ft);
		numberOfFreeThrowsTaken++;
	}
	
	public Player(String name){
		this.name = name.toLowerCase();
	}

	public String getName(){
		return name;
	}

	public double calcualteRegularSeasonAverage(){
		//TODO
		return 0;
	}
	public double calculatePlayoffAverage(){
		// TODO
		return 0;
	}
	
	public double calculateOverallAverageFromSinglePlayer(){
		// input of freeThrows is every single freeThrow in the dataset

		boolean shot_made;
		int made = 0;
		int total = 0;

		for(FreeThrow ft : freeThrowsTaken){
				shot_made = ft.getShotMade();
				total++;
				if(shot_made)
					made++;
		}
		System.out.println("made: " + made);
		System.out.println("total: " + total);

		return overallAverage=(double)made/total;
	}

	
	public double calculateNamedSeasonAverage(int season){
		// input of freeThrows is every single freeThrow in the dataset

		boolean shot_made;
		int made = 0;
		int total = 0;


		for(FreeThrow ft : freeThrowsTaken){
			if(ft.getSeason() == season){
				shot_made = ft.getShotMade();
				total++;
				if(shot_made){
					made++;
				}
			}
		}	
		if(total==0){ //if no freeThrows were found for that season
			return -1;
		}
		if(seasonAverage == null){
			seasonAverage = new HashMap<Object,Object>();
		}
		double percent = (double) made/total;
		seasonAverage.put(season, percent);
		return (double)made/total;
	}

	public int getNumberOfClutchFreeThrows(){
		//TODO
		return 0;
	}
	public ClutchFreeThrowPair clutchTimeAverage(){
		// clutch time = last 5 miuntes of 4th quarter or OT and score within 5
		
		ClutchFreeThrowPair ftPair;
		
		int made=0;
		int total=0;
		int scoreDif,score1=0,score2=0,index;
		String fullScore,score1Str,score2Str;
		for(FreeThrow ft : freeThrowsTaken){
			score1Str = "";
			score2Str = "";
			fullScore = ft.getScore();
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
				if(index>=fullScore.length())
					break;
			}
			score2 = Integer.parseInt(score2Str);
			
			
			scoreDif = Math.abs(score1 - score2);
			
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
		
		double percent = (double) made/total; 
		ftPair = new ClutchFreeThrowPair(percent , total);
		
		return ftPair;
	}

	public TreeMap<Integer,Double> getAllSeasons(){
		TreeMap<Integer,Double> seasons = new TreeMap<Integer,Double>();
		for(int year = 2007;year<=2016;year++){
			double percent = getSeasonAverage(year);
			if(percent!=-1)
				seasons.put(year, percent);
		}
		return seasons;
	}


	public double clutchDifferential(){
		return clutchTimeAverage().getPercentage() - overallAverage;
	}

	
	public double getSeasonAverage(int season){
		Integer x = Integer.valueOf(season);
		if(seasonAverage == null || !seasonAverage.containsKey(x)){
			return calculateNamedSeasonAverage(season);
		}
		return (double) seasonAverage.get(season);
	}
	
	public boolean checkIfPlayedSeason(int season){
		for(FreeThrow ft : freeThrowsTaken){
			if(ft.getSeason() == season){
				return true;
			}
		}
		return false;
	}
	
	public int getNumberOfTotalFTs(){
		return numberOfFreeThrowsTaken;
	}

}


