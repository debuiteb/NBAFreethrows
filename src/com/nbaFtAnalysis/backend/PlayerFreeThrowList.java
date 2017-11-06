package com.nbaFtAnalysis.backend;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class PlayerFreeThrowList {

	private HashMap <Player,ArrayList<FreeThrow>> playerList;
	private Player currentPlayer;
	private NameSearchSet searchSet; //this is a set of all the player names to allow quick search to see if a player is in the dataset

	
	// ###	This class represents a list of players, and each player points to a list of their free-throws ### //
	
	
	public PlayerFreeThrowList(FreeThrow [] ftArray){

		playerList = new HashMap <Player,ArrayList<FreeThrow>>();
		searchSet = new NameSearchSet();

		for(FreeThrow currentFT : ftArray){
			String name = currentFT.getPlayerName();
			Player tempPlayer;
			if(playerAlreadyInitialised(name)){
				ArrayList<FreeThrow> tempList = playerList.get(currentPlayer);
				tempList.add(currentFT);
				currentPlayer.addFT(currentFT);
				playerList.put(currentPlayer, tempList);
			}
			else{ // adding a player into the playerList
				tempPlayer = new Player(name,currentFT);
				ArrayList <FreeThrow> tempList = new ArrayList<FreeThrow>();
				tempList.add(currentFT);
				playerList.put(tempPlayer, tempList);
				searchSet.add(name);
			}
		}
	}

	private boolean playerAlreadyInitialised(String name){
		for(Player tempPlayer : playerList.keySet()){
			if(tempPlayer.getName().equals(name)){
				currentPlayer = tempPlayer;
				return true;
			}
		}
		return false;
	}

	public ArrayList<FreeThrow> getPlayersFreeThrows(String name){
		ArrayList<FreeThrow> ftList = new ArrayList<FreeThrow>();
		for(Player tempPlayer : playerList.keySet()){
			if(tempPlayer.getName().equals(name)){
				for(FreeThrow f : playerList.get(tempPlayer))
					ftList.add(f);
			}
		}
		return ftList;
	}

	public boolean playerExists(String name){
		for(Player tempPlayer : playerList.keySet()){
			if(tempPlayer.getName().equals(name.toLowerCase()))
				return true;
		}
		return false;

	}

	public Player getPlayer(String name){
		for(Player tempPlayer : playerList.keySet()){
			if(tempPlayer.getName().equals(name.toLowerCase()))
				return tempPlayer;
		}
		return null;

	}	

	public NameSearchSet getSearchSet (){
		return searchSet;
	}
}
