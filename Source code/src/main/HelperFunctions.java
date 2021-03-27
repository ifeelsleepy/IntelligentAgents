package main;

import models.Data;
import models.SquareData;
import models.State;

public class HelperFunctions {

	public static SquareData[][] estimateUtil(SquareData[][] curSqData, State[][] grid) {
		SquareData[][] curSqDataCopy = new SquareData[Data.ROWS][Data.COLS];
		for (int i = 0; i < Data.ROWS; i++) {
			for (int j = 0; j < Data.COLS; j++) {
				curSqDataCopy[i][j] = new SquareData(curSqData[i][j].getAction(), curSqData[i][j].getUtil());
			}
		}
		SquareData[][] newSqData = new SquareData[Data.ROWS][Data.COLS];
		for (int i = 0; i < Data.ROWS; i++) {
			for (int j = 0; j < Data.COLS; j++) {
				newSqData[i][j] = new SquareData();
			}
		}
		int count = 0;
		do {
			for (int i = 0; i < Data.ROWS; i++) {
				for (int j = 0; j < Data.COLS; j++) {
					if (grid[i][j].isWall()) {
						continue;
					}
					double newUtil = grid[i][j].getReward() + Data.DISCOUNT
							* HelperFunctions.utilityCalculator(grid, i, j, curSqDataCopy, curSqData[i][j].getAction());
					newSqData[i][j].setUtil(newUtil);

					newSqData[i][j].setAction(curSqData[i][j].getAction());

				}

			}
			for (int i1 = 0; i1 < Data.ROWS; i1++) {
				for (int j1 = 0; j1 < Data.COLS; j1++) {
					curSqDataCopy[i1][j1] = new SquareData(newSqData[i1][j1].getAction(), newSqData[i1][j1].getUtil());
				}
			}
			count++;

		} while (count < Data.K);

		return newSqData;

	}

	public static SquareData bestUtils(State[][] grid, int row, int col, SquareData[][] curSqData) {
		SquareData temp = max(grid, row, col, curSqData);
		double utility = grid[row][col].getReward() + Data.DISCOUNT * temp.getUtil();
		SquareData best = new SquareData(temp.getAction(), utility);
		return best;

	}

	public static double utilityCalculator(State[][] grid, int row, int col, SquareData[][] curSqData, String action) {
		String leftAction = null, rightAction = null;
		int[] intendedCoord, leftCoord, rightCoord;
		double sum;

		for (int i = 0; i < Data.ACTIONS.length; i++) {
			int index = i;
			if (Data.ACTIONS[i] == action) {
				if (index == 0) {
					leftAction = Data.ACTIONS[3];
					rightAction = Data.ACTIONS[1];
				} else if (index == Data.ACTIONS.length - 1) {
					leftAction = Data.ACTIONS[index - 1];
					rightAction = Data.ACTIONS[0];
				} else {
					leftAction = Data.ACTIONS[index - 1];
					rightAction = Data.ACTIONS[index + 1];
				}
			}
		}
		intendedCoord = getNewCoords(grid, action, row, col);
		leftCoord = getNewCoords(grid, leftAction, row, col);
		rightCoord = getNewCoords(grid, rightAction, row, col);

		sum = Data.INT_PROB * curSqData[intendedCoord[0]][intendedCoord[1]].getUtil()
				+ Data.LEFT_PROB * curSqData[leftCoord[0]][leftCoord[1]].getUtil()
				+ Data.RIGHT_PROB * curSqData[rightCoord[0]][rightCoord[1]].getUtil();

		double utility = sum;
		return utility;

	}

	// maximum function from bellman equation
	public static SquareData max(State[][] grid, int row, int col, SquareData[][] curUtils) {
		String intendedAction, leftAction, rightAction;
		int[] intendedCoord, leftCoord, rightCoord;
		double sum;
		double max = -10000;
		String action = null;

		for (int i = 0; i < Data.ACTIONS.length; i++) {
			int index = i;
			intendedAction = Data.ACTIONS[i];
			if (index == 0) {
				leftAction = Data.ACTIONS[3];
				rightAction = Data.ACTIONS[1];
			} else if (index == Data.ACTIONS.length - 1) {
				leftAction = Data.ACTIONS[index - 1];
				rightAction = Data.ACTIONS[0];
			} else {
				leftAction = Data.ACTIONS[index - 1];
				rightAction = Data.ACTIONS[index + 1];
			}
			intendedCoord = getNewCoords(grid, intendedAction, row, col);
			leftCoord = getNewCoords(grid, leftAction, row, col);
			rightCoord = getNewCoords(grid, rightAction, row, col);

			sum = Data.INT_PROB * curUtils[intendedCoord[0]][intendedCoord[1]].getUtil()
					+ Data.LEFT_PROB * curUtils[leftCoord[0]][leftCoord[1]].getUtil()
					+ Data.RIGHT_PROB * curUtils[rightCoord[0]][rightCoord[1]].getUtil();
			if (sum > max) {
				max = sum;
				action = intendedAction;
			}
		}
		SquareData action_value = new SquareData(action, max);
		return action_value;
	}

	// get new coordinates after action
	public static int[] getNewCoords(State[][] grid, String action, int row, int col) {
		int newRow = row, newCol = col;
		int[] newCoords = new int[2];

		switch (action) {
		case "UP":
			newRow = row - 1;
			newCol = col;
			break;
		case "DOWN":
			newRow = row + 1;
			newCol = col;
			break;
		case "LEFT":
			newRow = row;
			newCol = col - 1;
			break;
		case "RIGHT":
			newRow = row;
			newCol = col + 1;
			break;
		}
		// check if wall or out of bounds
		try {
			if (newRow >= Data.ROWS) {
				newRow = row;
			} else if (newRow < 0) {
				newRow = row;
			} else if (grid[newRow][newCol].isWall()) {
				newRow = row;
			}
		} catch (ArrayIndexOutOfBoundsException exception) {
			newRow = row;
		}

		if (newCol >= Data.COLS) {
			newCol = col;
		} else if (newCol < 0) {
			newCol = col;
		} else if (grid[newRow][newCol].isWall()) {
			newCol = col;
		}

		newCoords[0] = newRow;
		newCoords[1] = newCol;

		return newCoords;
	}

	public static void displayPolicy(SquareData[][] utils) {
		StringBuilder grid = new StringBuilder();
		StringBuilder H_row = new StringBuilder();
		H_row.append("|");
		for (int i =0;i<Data.ROWS;i++) {
			H_row.append("---|");
		}

		grid.append("++++++Optimal Policy+++++\n");
		grid.append(H_row + "\n");
		for (int i = 0; i < Data.ROWS; i++) {
			grid.append("|");
			for (int j = 0; j < Data.COLS; j++) {
				
				if (utils[i][j].getAction() == null) {
					grid.append(" W |");
				} else if (utils[i][j].getAction() == "UP") {
					grid.append(" ^ |");
				} else if (utils[i][j].getAction() == "DOWN") {
					grid.append(" v |");
				} else if (utils[i][j].getAction() == "LEFT") {
					grid.append(" < |");
				} else if (utils[i][j].getAction() == "RIGHT") {
					grid.append(" > |");
				}
			}
			grid.append("\n" + H_row + "\n");
		}

		System.out.println(grid.toString());
	}

	public static void returnUtils(SquareData[][] utils) {
		for (int i = 0; i < Data.ROWS; i++) {
			for (int j = 0; j < Data.COLS; j++) {
				System.out.printf("(%d,%d): %f\n", i, j, utils[i][j].getUtil());
			}
		}
	}

	public static void displayUtils(SquareData[][] utils) {
		StringBuilder grid = new StringBuilder();
		StringBuilder H_row = new StringBuilder();
		H_row.append("|");
		for (int i =0;i<Data.ROWS;i++) {
			H_row.append("-------|");
		}
		grid.append("++++++Optimal Policy+++++\n");
		grid.append(H_row + "\n");
		double util = 0;
		for (int i = 0; i < Data.ROWS; i++) {
			grid.append("|");
			for (int j = 0; j < Data.COLS; j++) {
				if (utils[i][j].getAction() == null) {
					grid.append("  WWW  |");
				} else if (utils[i][j].getUtil() < 10) {
					grid.append("  " + String.format("%.2f", utils[i][j].getUtil()) + " |");
				} else {
					grid.append(" " + String.format("%.2f", utils[i][j].getUtil()) + " |");					
				}
			}
			grid.append("\n" + H_row + "\n");
		}

		System.out.println(grid.toString());
	}

}
