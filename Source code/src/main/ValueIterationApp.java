package main;

import java.util.ArrayList;

import models.Data;
import models.SquareData;
import models.State;

public class ValueIterationApp {

	public static ArrayList<SquareData[][]> valueIteration(State[][] grid) {
		
		SquareData[][] curSqData = new SquareData[Data.ROWS][Data.COLS];
		SquareData[][] newSqData = new SquareData[Data.ROWS][Data.COLS];
		ArrayList<SquareData[][]> sqDataList = new ArrayList<SquareData[][]>();

		for (int i = 0; i < Data.ROWS; i++) {
			for (int j = 0; j < Data.COLS; j++) {
				newSqData[i][j] = new SquareData();
			}
		}
		for (int i = 0; i < Data.ROWS; i++) {
			for (int j = 0; j < Data.COLS; j++) {
				curSqData[i][j] = new SquareData();
			}
		}

		int count = 0;
		double diff = 0;
		double reward;
		double max_utility;
		SquareData action_util;

		do {
			System.out.printf("Iteration %s\n", count);
			// copy array
			for (int i = 0; i < Data.ROWS; i++) {
				for (int j = 0; j < Data.COLS; j++) {
					curSqData[i][j].setAction(newSqData[i][j].getAction());
					curSqData[i][j].setUtil(newSqData[i][j].getUtil());
				}
			}
			diff = 0;
			// for each square in the grid
			for (int i = 0; i < Data.ROWS; i++) {
				for (int j = 0; j < Data.COLS; j++) {
					// skip if wall
					if (grid[i][j].isWall()) {
						continue;
					}
					reward = grid[i][j].getReward();
					// Bellman eqn
					// U(s) = R(s) + discount * max(a)sum(P(s'|s,a)U(s'))
					action_util = HelperFunctions.max(grid, i, j, curSqData);
					max_utility = action_util.getUtil();
					newSqData[i][j].setUtil(reward + Data.DISCOUNT * max_utility);
					newSqData[i][j].setAction(action_util.getAction());
					if (Math.abs(newSqData[i][j].getUtil() - curSqData[i][j].getUtil()) > diff) {
						diff = Math.abs(newSqData[i][j].getUtil() - curSqData[i][j].getUtil());
					}
				}
			}
			count++;
			SquareData[][] copyData = new SquareData[Data.ROWS][Data.COLS];
			for (int i = 0; i < Data.ROWS; i++) {
				for (int j = 0; j < Data.COLS; j++) {
					copyData[i][j] = new SquareData(curSqData[i][j].getAction(), curSqData[i][j].getUtil());
				}
			}
			sqDataList.add(copyData);
		} while (!(diff < Data.CONVERGENCE));

		return sqDataList;

	}

}
