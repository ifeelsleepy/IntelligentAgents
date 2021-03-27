package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import models.Data;
import models.SquareData;
import models.gridWorld;

public class assignment {
	public static void main(String[] args) {

		//gridWorld grid = new gridWorld(Data.NEW_GREEN_LOCATIONS,Data.NEW_ORANGE_LOCATIONS,Data.NEW_WALL_LOCATOINS);
		gridWorld grid = new gridWorld();
		grid.displayMaze();
		ArrayList<SquareData[][]> viList = new ArrayList<SquareData[][]>();
		ArrayList<SquareData[][]> piList = new ArrayList<SquareData[][]>();
		viList = ValueIterationApp.valueIteration(grid.getGrid());
		piList = PolicyIterationApp.policyIteration(grid.getGrid());
		
		System.out.println();
		HelperFunctions.displayUtils(viList.get(viList.size() - 1));
		HelperFunctions.displayPolicy(viList.get(viList.size() - 1));
		System.out.println();
		HelperFunctions.displayUtils(piList.get(piList.size() - 1));
		HelperFunctions.displayPolicy(piList.get(piList.size() - 1));
		System.out.println(Data.CONVERGENCE);
//		for (int i=0;i<piList.size();i++) {
//			HelperFunctions.displayPolicy(piList.get(i));
//		}
//		writeToFile(viList, "valueIteration");
//		writeToFile(piList, "policyIteration");
	}

	public static void writeToFile(ArrayList<SquareData[][]> resultList, String fileName) {
		StringBuilder sb = new StringBuilder();
		String util;
		for (int k = 0; k < resultList.size(); k++) {
			for (int i = 0; i < Data.ROWS; i++) {
				for (int j = 0; j < Data.COLS; j++) {
					util = String.format("%.2f", resultList.get(k)[i][j].getUtil());
					sb.append(util + ",");
				}
			}
			sb.append("\n");
		}
		writeToFile(sb.toString().trim(), "src/" + fileName + ".csv");
	}

	public static void writeToFile(String content, String fileName) {
		try {
			FileWriter fw = new FileWriter(new File(fileName), false);

			fw.write(content);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
