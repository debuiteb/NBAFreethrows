package com.nbaFtAnalysis.backend;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerFreeThrowList {

	private HashMap <Player,ArrayList<FreeThrow>> playerList;
	private Player currentPlayer;

	public PlayerFreeThrowList(FreeThrow [] ftArray){

		playerList = new HashMap <Player,ArrayList<FreeThrow>>();

		for(FreeThrow currentFT : ftArray){
			String name = currentFT.getPlayerName();
			Player tempPlayer;
			if(playerAlreadyInitialised(name)){
				ArrayList<FreeThrow> tempList = playerList.get(currentPlayer);
				tempList.add(currentFT);
				currentPlayer.addFT(currentFT);
				playerList.put(currentPlayer, tempList);
			}
			else{
				tempPlayer = new Player(name,currentFT);
				ArrayList <FreeThrow> tempList = new ArrayList<FreeThrow>();
				tempList.add(currentFT);
				playerList.put(tempPlayer, tempList);

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
}
