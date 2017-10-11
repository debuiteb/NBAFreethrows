package com.nbaFtAnalysis.backend;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PlayerFreeThrowList {

	//maybe an hash table/dictionary with the location being an arraylist of every free throw they've taken
	// then a separate class with a dictionary but with the clutch ratings and such asthe values/instance variables
	// so when a user seraches it just quesries this final class and accesses the member variable

	private HashMap <Player,ArrayList<FreeThrow>> playerList;
	private Player currentPlayer;

	public PlayerFreeThrowList(FreeThrow [] ftArray){

		playerList = new HashMap <Player,ArrayList<FreeThrow>>();

		for(FreeThrow currentFT : ftArray){
			String name = currentFT.getPlayerName();
			Player tempPlayer;// = new Player(name);
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
				System.out.println("We have a match");
				for(FreeThrow f : playerList.get(tempPlayer)){
					//f.printAllDetailsLineSpace();
					ftList.add(f);
				}
			}
		}
		return ftList;
	}

	public boolean playerExists(String name){
		for(Player tempPlayer : playerList.keySet()){
			if(tempPlayer.getName().equals(name.toLowerCase())){
				System.out.println(tempPlayer.getName());
				return true;
			}
		}
		System.out.println("not found");
		return false;

	}
	
	public Player getPlayer(String name){
		for(Player tempPlayer : playerList.keySet()){
			if(tempPlayer.getName().equals(name.toLowerCase())){
				System.out.println(tempPlayer.getName());
				return tempPlayer;
			}
		}
		System.out.println("here null");
		return null;

	}	
}
