package main;

import java.util.ArrayList;
import java.util.Random;

import models.Data;
import models.SquareData;
import models.State;

public class PolicyIterationApp {

	public static ArrayList<SquareData[][]> policyIteration(State[][] grid) {
		Random r = new Random();
		ArrayList<SquareData[][]> sqDataList = new ArrayList<SquareData[][]>();

		// initialize a random policy
		SquareData[][] curSqData = new SquareData[Data.ROWS][Data.COLS];
		for (int i = 0; i < Data.ROWS; i++) {
			for (int j = 0; j < Data.COLS; j++) {
				curSqData[i][j] = new SquareData();
				if (!grid[i][j].isWall()) {
					int random_no = r.nextInt(3);
					curSqData[i][j].setAction(Data.ACTIONS[random_no]);
					;
				}
			}
		}

		int count = 0;
		boolean policyUnchanged = true;
		do {
			System.out.printf("Iteration %s\n", count);
			// policy estimation
			SquareData[][] policyData = HelperFunctions.estimateUtil(curSqData, grid);
			policyUnchanged = true;

			SquareData[][] copyData = new SquareData[Data.ROWS][Data.COLS];
			for (int i = 0; i < Data.ROWS; i++) {
				for (int j = 0; j < Data.COLS; j++) {
					copyData[i][j] = new SquareData(policyData[i][j].getAction(), policyData[i][j].getUtil());
				}
			}
			sqDataList.add(copyData);

			// improve policy based on each state
			for (int i = 0; i < Data.ROWS; i++) {
				for (int j = 0; j < Data.COLS; j++) {
					// skip if wall
					if (grid[i][j].isWall()) {
						continue;
					}
					SquareData bestData = HelperFunctions.max(grid, i, j, policyData);
					String policyAction = policyData[i][j].getAction();
					double policyUtil = HelperFunctions.utilityCalculator(grid, i, j, policyData, policyAction);
					if (bestData.getUtil() > policyUtil) {
						policyData[i][j].setAction(bestData.getAction());
						policyData[i][j].setUtil(bestData.getUtil());
						policyUnchanged = false; } } }
			for (int i = 0; i < Data.ROWS; i++) {
				for (int j = 0; j < Data.COLS; j++) {
					curSqData[i][j] = new SquareData(policyData[i][j].getAction(), policyData[i][j].getUtil());
				}
			}
			count++;
		} while (!policyUnchanged);

		return sqDataList;
	}
}