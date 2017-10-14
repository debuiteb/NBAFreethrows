package com.nbaFtAnalysis.backend;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
		Player tempPlayer = playerList.getPlayer(name);
		return tempPlayer.calculateOverallAverageFromSinglePlayer();
	}

	public double getPlayerSeasonAverage(String name,int season){
		Player tempPlayer = playerList.getPlayer(name);
		return tempPlayer.getSeasonAverage(season);
	}

	public TreeMap<Integer, Double> getPlayerSeasons(String name){
		Player tempPlayer = playerList.getPlayer(name);
		return tempPlayer.getAllSeasons();
	}

	public ClutchFreeThrowPair getClutchTimeScore(String name){
		Player tempPlayer = playerList.getPlayer(name);
		return tempPlayer.clutchTimeAverage();
	}

	public double getClutchDifferential(String name){
		Player tempPlayer = playerList.getPlayer(name);
		return tempPlayer.clutchDifferential();
	}
	
	public double getRegSeasonAverage(String name){
		Player tempPlayer = playerList.getPlayer(name);
		return tempPlayer.getRegSeasonAverage();
	}
	public double getPlayoffAverage(String name){
		Player tempPlayer = playerList.getPlayer(name);
		return tempPlayer.getPlayoffAverage();
	}
	public double getPlayoffDifferential(String name){
		Player tempPlayer = playerList.getPlayer(name);
		return tempPlayer.getPlayoffDifferential();
	}

	public void printMultipleSeasons(String searchPlayer){
		// nice print of multiple seasons
		TreeMap<Integer, Double> tm = getPlayerSeasons(searchPlayer);
		System.out.println("------------------------------------------------");
		for(Entry<Integer,Double> entry: tm.entrySet()){
			System.out.println("year:" + entry.getKey() + "\tft: " + round(entry.getValue()*100,2)+"%");
		}
		System.out.println("------------------------------------------------");
	}

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}



	public static void main(String[] args) {

		String csvFile = "nba-free-throws/free_throws.csv";
		NBA_Data data = new NBA_Data(csvFile);
		data.printNumberOfFreeThrows();

		Scanner scanner = new Scanner(System.in);
		System.out.println();
		System.out.println("enter in 'exit' to finish the application, or press the enter key now to proceed");

		boolean finish = false;
		String searchPlayer;
		while(!scanner.nextLine().toLowerCase().equals("exit") && !finish){
			System.out.println("enter player name to search: ");
			searchPlayer = scanner.nextLine().toLowerCase();
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

			data.printMultipleSeasons(searchPlayer);

			System.out.println("Clutch freethrow shooting:" + data.getClutchTimeScore(searchPlayer).getPercentage() + " on " + data.getClutchTimeScore(searchPlayer).getTotalAttempts()+" attempt(s)");

			System.out.println("clutch differential: " + data.getClutchDifferential(searchPlayer));
			
			System.out.println("Playoff Differential: " + data.getPlayoffDifferential(searchPlayer));
		}
		System.out.println("------- Application Terminated --------");
		scanner.close();
	}

}
