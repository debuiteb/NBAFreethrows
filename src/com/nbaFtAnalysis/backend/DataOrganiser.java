package com.nbaFtAnalysis.backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataOrganiser {

	private FreeThrow [] freeThrows;
	private int numberOfFreeThrows;
	
	public DataOrganiser(String csvFile){
		initialiseData(csvFile);
		readInData(csvFile);
	}
	
	public FreeThrow [] getFreeThrowArray(){
		return freeThrows;
	}
	
	private void initialiseData(String csvFile){
		int csvLength = 0;


		try{
			BufferedReader tempBr = new BufferedReader(new FileReader(csvFile));
			tempBr.readLine();


			while ((tempBr.readLine()) != null ) {
				csvLength++;
			}
			numberOfFreeThrows = csvLength;

			tempBr.close();

			freeThrows = new FreeThrow[csvLength];
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void readInData(String csvFile){
		int y=0;

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		String[] ft = new String[11];



		try {
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();

			while ((line = br.readLine()) != null ) {

				// use comma as separator
				ft = line.split(cvsSplitBy);

				FreeThrow f1  = new FreeThrow(ft);
				
				freeThrows[y] = f1;


				y++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
