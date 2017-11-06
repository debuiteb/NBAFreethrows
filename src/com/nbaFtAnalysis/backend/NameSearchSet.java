package com.nbaFtAnalysis.backend;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

// ### 	Class to allow quick searching to check if player is in the datset ###

public class NameSearchSet {


	private Set<String> lookupTable;
	public NameSearchSet(){
		lookupTable = new LinkedHashSet<String>();
	}

	public boolean doesContain(String playerName){
		return lookupTable.contains(playerName);
	}

	public static String shortenName(String longName){ // this is only called if a player's name is greater than 40 characters, eg, if the player name cell was mixed with another
		char [] charAr = longName.toCharArray();
		int spaceCount = 0;
		ArrayList <Character> charReturn = new ArrayList<Character> ();
		int count=0;
		for(char letter : charAr){
			if(count == 0 && letter ==' '){ //only for the first letter
				spaceCount++;
				count++;
				continue;
			}
			if(letter == ' ' && spaceCount==1) //first name finished
				spaceCount++;
			else if(letter == ' ' && spaceCount==2) // after second name
				return getStringRepresentation(charReturn);
			count++;
			charReturn.add(letter);
		}
		return getStringRepresentation(charReturn);
	}
	
	private static String getStringRepresentation(ArrayList<Character> list) // converts an arraylist of type character to a string
	{    
	    StringBuilder builder = new StringBuilder(list.size());
	    for(Character ch: list)
	    {
	        builder.append(ch);
	    }
	    return builder.toString();
	}


	public void add(String name){
		if(!lookupTable.contains(name)){
			if(name.length()<40)
				lookupTable.add(name);
			else{
				name = (String) shortenName(name);
				lookupTable.add(name);
			}
		}
	}
	public ArrayList<String> getAll(){ // returns all the names in the set as an arraylist
		ArrayList<String> list = new ArrayList();
		for(String s : lookupTable){
			list.add(s);
		}
		return list;
	}

}
