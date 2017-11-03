package com.nbaFtAnalysis.backend;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class NameSearchSet {

	// hashTree of all the players' names that will map the real player name to a position in the playerList
	private Set<String> lookupTable;
	public NameSearchSet(){
		lookupTable = new LinkedHashSet<String>();
	}
	
	public boolean doesContain(String playerName){
		return lookupTable.contains(playerName);
	}
	
	public void add(String name){
		if(!lookupTable.contains(name)){
			lookupTable.add(name);
		}
	}
	public ArrayList<String> getAll(){
		ArrayList<String> list = new ArrayList();
		for(String s : lookupTable){
			list.add(s);
		}
		return list;
	}
	
}
