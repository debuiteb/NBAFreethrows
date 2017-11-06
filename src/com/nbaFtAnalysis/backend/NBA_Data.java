package com.nbaFtAnalysis.backend;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class NBA_Data {

	private int numberOfFreeThrows;

	private PlayerFreeThrowList playerList;
	private FreeThrow [] freeThrows;
	private static NameSearchSet searchSet;

	public NBA_Data(String csvFile){
		DataOrganiser organiser = new DataOrganiser(csvFile);
		freeThrows = organiser.getFreeThrowArray();
		playerList = new PlayerFreeThrowList(freeThrows);
		searchSet = playerList.getSearchSet();
	}

	private void printFreeThrows(){
		for(FreeThrow ft : freeThrows)
			ft.printAllDetailsLineSpace();
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

	private HashMap<String,ClutchFreeThrowPair> getClutchList(){
		// map with amount of clutch time fts
		HashMap<String,ClutchFreeThrowPair> map = new HashMap<String,ClutchFreeThrowPair>();
		ArrayList<String> playerNames = searchSet.getAll();
		ClutchFreeThrowPair ftPair;
		int numAttempts;
		for(String player : playerNames){
			ftPair = getClutchTimeScore(player);
			numAttempts = ftPair.getTotalAttempts();
			if(numAttempts<70)
				continue;	
			map.put(player, ftPair);
		}

		return map;
	}

	private LinkedList[] clutchListLinkedListArray(){
		HashMap<String,ClutchFreeThrowPair> map = getClutchList();
		int size = map.size();
		LinkedList []  list = new LinkedList[size];
		String name;
		int attempts;
		double percent;

		int count = 0;
		for(Entry <String,ClutchFreeThrowPair> entry : map.entrySet()){
			name = entry.getKey();
			attempts = entry.getValue().getTotalAttempts();
			percent = entry.getValue().getPercentage();
			LinkedList<Comparable> tempList = new LinkedList();
			tempList.add(name);
			tempList.add(attempts);
			tempList.add(percent);
			list[count] = tempList;
			count++;
		}
		return list;
	}

	public LinkedList []  sortClutchListByAttempts(){
		LinkedList [] list = clutchListLinkedListArray();
		int length = list.length;
		for(int i=0;i<length;i++){
			for(int j=0;j<length;j++){
				//LinkedList l1 = list[i];
				//int at1 = (int) l1.get(1);
				if((int)list[i].get(1) < (int) list[j].get(1)){
					LinkedList<?> tempList = list[i];
					list[i] = list[j];
					list[j] = tempList;
				}
			}
		}
		return list;
	}
	
	public void printClutchFTsByAttempts(){
		LinkedList [] list = sortClutchListByAttempts();
		System.out.println(" attempts \t     percent \t  player");
		for(LinkedList l : list){
			System.out.print(l.getFirst() + "\t");
			System.out.print(l.get(1) + "\t");
			System.out.println(l.getLast() + "\t");
		}
	}

	// could have an array of linked lists of 3 elements to sort the cluctchFTlist

	public void printClutchFTs(){
		HashMap<String,ClutchFreeThrowPair> map = getClutchList();
		double percent;
		String name;
		int attempts;
		System.out.println("attempts \t     percent \t\t  player");
		for(Entry <String,ClutchFreeThrowPair>  entry : map.entrySet()){
			name = entry.getKey();
			ClutchFreeThrowPair pair = entry.getValue();
			percent = pair.getPercentage();
			attempts =pair.getTotalAttempts();

			System.out.println("  " + attempts + "\t\t" + percent +"\t" + name);
		}
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

		data.printClutchFTs();
		System.out.println("-----    -------    --------    --------");
		data.printClutchFTsByAttempts();

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
			while(!searchSet.doesContain(searchPlayer)){ // may need to change this to search the nameSearchTree
				ArrayList<String> allPlayerNames = searchSet.getAll();
				LevenshteinDistance leven = new LevenshteinDistance(4); // four is the arbitrary threshold so won't search go past four character changes
				// search for similar names


				HashMap<String,Integer> similarPlayerNames = new HashMap<String,Integer>();

				for(String str : allPlayerNames){
					int similarity = leven.apply(str, searchPlayer);
					//System.out.println("CHECK LEVEN HERE !!! " + leven.apply( str , searchPlayer));
					if(similarity <= 3 && similarity >= 0){
						System.out.println("similar player " + str +" sim: " + similarity);
						similarPlayerNames.put(str, similarity);
					}
				}
				String [] nameArray = new String [similarPlayerNames.size()];
				int [] simScoreArray = new int [similarPlayerNames.size()];
				int index = 0;
				for(Entry <String , Integer> e :  similarPlayerNames.entrySet()){
					nameArray[index] = e.getKey();
					simScoreArray[index] = e.getValue();
					index++;
				}
				String tempStr;
				int tempInt;
				// bubble sort fine as lists will be short, usually <5
				for(int i=0;i<nameArray.length;i++){
					for(int j=1;j<(nameArray.length); j++){
						if(simScoreArray[j] < simScoreArray[j-1]){
							// swap elements in both arrays
							tempStr = nameArray[j-1];
							tempInt = simScoreArray[j-1];
							simScoreArray[j-1] = simScoreArray[j];
							nameArray[j-1] = nameArray[j];
							simScoreArray[j] = tempInt;
							nameArray[j] = tempStr;
						}
					}
				}
				System.out.println("-----FINAL------");
				for(int x=0;x<similarPlayerNames.size();x++){
					System.out.println(nameArray[x] + "\tscore:" + simScoreArray[x]);
				}				

				if(nameArray.length==0){
					System.out.println("player not found, try again and watch yo spelling: ");
					System.out.println("enter player name to search: ");
					searchPlayer = scanner.nextLine().toLowerCase();
					if(scanner.nextLine().toLowerCase().equals("exit")){
						finish = true;
						break;
					}
				}
				else{
					System.out.println("-----------------------");
					System.out.println("Did you mean one of these guys?");
					System.out.println("Enter the corresponding number if you meant to search fot that player. If you want to search for none if these players, enter 'n' ");
					for(int in=0;in<nameArray.length;in++){
						System.out.println("["+ in +"]" + " " + nameArray[in]);
					}
					searchPlayer = scanner.nextLine().toLowerCase();
					if(!searchPlayer.equals("n")){
						int numEntered = Integer.valueOf(searchPlayer);
						if(numEntered < nameArray.length && numEntered >= 0){
							searchPlayer = nameArray[numEntered];
						}
					}
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
