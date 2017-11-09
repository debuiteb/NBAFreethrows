package com.nbaFtAnalysis.backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


// ### Class to read in and initialise the data ### //

public class DataOrganiser {

	private FreeThrow [] freeThrows;
	//private int numberOfFreeThrows;

	public DataOrganiser(String csvFile){


		long startTime = System.nanoTime();


		initialiseData(csvFile);
		readInData(csvFile);
		long endTime = System.nanoTime();

		long duration = ((endTime - startTime)/1000000);  //divide by 1000000 to get milliseconds.
		System.out.println("normal read in took " + duration +" ms");
	}

	public FreeThrow [] getFreeThrowArray(){
		return freeThrows;
	}

	private void initialiseData(String csvFile){
		int csvLength = 0;

		try{
			BufferedReader tempBr = new BufferedReader(new FileReader(csvFile));
			tempBr.readLine();

			while ((tempBr.readLine()) != null ) { // counts the number of lines in the csv file
				csvLength++;
			}
			//numberOfFreeThrows = csvLength;

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

		String[] ft = new String[11]; // number of columns

		try {
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine(); // skips the first row, skips the column titles

			FreeThrow f1;
			while ((line = br.readLine()) != null ) {

				// use comma as separator
				ft = line.split(cvsSplitBy);

				f1  = new FreeThrow(ft);
				freeThrows[y] = f1;

				y++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
