package com.nbaFtAnalysis.backend;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class NBA_Data {

	private int numberOfFreeThrows;

	private PlayerFreeThrowList playerList;
	private FreeThrow [] freeThrows;

	public NBA_Data(String csvFile){
		DataOrganiser organiser = new DataOrganiser(csvFile);
		freeThrows = organiser.getFreeThrowArray();
		playerList = new PlayerFreeThrowList(freeThrows);
	}
	
	private void printFreeThrows(){
		for(FreeThrow ft : freeThrows){
			ft.printAllDetailsLineSpace();
		}
		System.out.println();
	}


	private void printNumberOfFreeThrows(){
		System.out.println();
		System.out.println("number of freethrows: " + numberOfFreeThrows);
		System.out.println();
	}

	public void searchAndPrintPlayerFreeThrows(String name){
		PlayerFreeThrowList playerList = new PlayerFreeThrowList(freeThrows);
		playerList.getPlayersFreeThrows(name);
	}
	public double getPlayerOverallAverage(String name){
		//PlayerFreeThrowList playerList = new PlayerFreeThrowList(freeThrows);
		Player tempPlayer = playerList.getPlayer(name);
		return tempPlayer.calculateOverallAverageFromSinglePlayer(/*freeThrows*/);
	}

	public double getPlayerSeasonAverage(String name,int season){
		//PlayerFreeThrowList playerList = new PlayerFreeThrowList(freeThrows);
		Player tempPlayer = playerList.getPlayer(name);
		return tempPlayer.getSeasonAverage(/*freeThrows,*/ season);
	}

	public TreeMap<Integer, Double> getPlayerSeasons(String name){
		//PlayerFreeThrowList playerList = new PlayerFreeThrowList(freeThrows);
		Player tempPlayer = playerList.getPlayer(name);
		return tempPlayer.getAllSeasons(/*freeThrows*/);
	}

	public ClutchFreeThrowPair getClutchTimeScore(String name){
		Player tempPlayer = playerList.getPlayer(name);
		return tempPlayer.clutchTimeAverage();
	}

	public double getClutchDifferential(String name){
		Player tempPlayer = playerList.getPlayer(name);
		return tempPlayer.clutchDifferential();
	}

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

		long startTime = System.currentTimeMillis();

		String csvFile = "nba-free-throws/free_throws.csv";
		NBA_Data data = new NBA_Data(csvFile);
		data.printNumberOfFreeThrows();
		//data.printFreeThrows();

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);

		//data.serachAndPrintPlayerFreeThrows("Russell Westbrook");;

		String p1 = "Andre Drummond";
		int season = 2016;
		//data.searchAndPrintPlayerFreeThrows(p1);;

		//System.out.println("Player: " + p1 +" , "+ "Overall average: " + data.getPlayerOverallAverage(p1));

		//System.out.println("Player: " + p1 +" ,Season: " + season + " ,average: " + data.getPlayerSeasonAverage(p1,season));

		Scanner scanner = new Scanner(System.in);
		System.out.println();
		System.out.println("enter in 'exit' to finish the application, or press the enter key now to proceed");
		
		boolean finish = false;
		while(!scanner.nextLine().toLowerCase().equals("exit") &&!finish){
			System.out.println("enter player name to search: ");
			String searchPlayer = scanner.nextLine().toLowerCase();
			if(searchPlayer.equals("exit")){
				finish = true;
				break;
			}
			while(!data.playerList.playerExists(searchPlayer)){
				System.out.println("player not found, try again and watch yo spelling: ");
				System.out.println("enter player name to search: ");
				searchPlayer = scanner.nextLine().toLowerCase();
				if(scanner.nextLine().toLowerCase().equals("exit")){
					finish = true;
					break;
				}
			}

			System.out.println("Player: " + searchPlayer +" , "+ "Overall average: " + data.getPlayerOverallAverage(searchPlayer));


			System.out.println("enter season: ");
			int s1 = scanner.nextInt();
			while(!data.playerList.getPlayer(searchPlayer).checkIfPlayedSeason(s1)){
				System.out.println("player didn't have a free throw that season, try another season (remember the dataset is between 2007 & 2016 inclusive)");
				System.out.println("enter season: ");
				s1 = scanner.nextInt();
			}

			startTime = System.currentTimeMillis();

			System.out.println("-----------------------------------------------------------------------------------------------------------------");
			System.out.println("Player: " + searchPlayer +" ,Season: " + s1 + " ,average: " + data.getPlayerSeasonAverage(searchPlayer,s1));
			System.out.println("-----------------------------------------------------------------------------------------------------------------");

			stopTime = System.currentTimeMillis();
			elapsedTime = stopTime - startTime;
			System.out.println("after first search: " + elapsedTime);

			startTime = System.currentTimeMillis();
			System.out.println("-----------------------------------------------------------------------------------------------------------------");
			System.out.println("Player: " + searchPlayer +" ,Season: " + s1 + " ,average: " + data.getPlayerSeasonAverage(searchPlayer,s1));
			System.out.println("-----------------------------------------------------------------------------------------------------------------");

			stopTime = System.currentTimeMillis();
			elapsedTime = stopTime - startTime;
			System.out.println("after second search: " + elapsedTime);



			// nice print of multiple seasons
			TreeMap<Integer, Double> tm = data.getPlayerSeasons(searchPlayer);
			System.out.println("------------------------------------------------");
			for(Entry<Integer,Double> entry: tm.entrySet()){
				System.out.println("year:" + entry.getKey() + "\tft: " + round(entry.getValue()*100,2)+"%");
			}
			System.out.println("------------------------------------------------");

			//System.out.println("woo: "+ data.getPlayerSeasons(searchPlayer));
			System.out.println("clucth freethrow shooting:" + data.getClutchTimeScore(searchPlayer).getPercentage() + " on " + data.getClutchTimeScore(searchPlayer).getTotalAttempts()+" attempt(s)");

			System.out.println("clutch diuf: " + data.getClutchDifferential(searchPlayer));
		}
		scanner.close();
	}



}


