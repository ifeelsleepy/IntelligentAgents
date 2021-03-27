package models;

public class gridWorld {

	private State[][] grid;

	public gridWorld(String[] greens, String[] oranges, String[] walls) {
		grid = new State[Data.ROWS][Data.COLS];

		for (int i = 0; i < Data.ROWS; i++) {
			for (int j = 0; j < Data.COLS; j++) {
				grid[i][j] = new State(Data.WHITE);
			}
		}
		for (String green : greens) {
			int gRow = Integer.parseInt(green.split(",")[0]);
			int gCol = Integer.parseInt(green.split(",")[1]);

			grid[gRow][gCol].setReward(Data.GREEN);
		}
		for (String orange : oranges) {
			int oRow = Integer.parseInt(orange.split(",")[0]);
			int oCol = Integer.parseInt(orange.split(",")[1]);

			grid[oRow][oCol].setReward(Data.ORANGE);
		}
		for (String wall : walls) {
			int wRow = Integer.parseInt(wall.split(",")[0]);
			int wCol = Integer.parseInt(wall.split(",")[1]);

			grid[wRow][wCol].setWall(true);
		}
	}
	
	
	public gridWorld() {
		grid = new State[Data.ROWS][Data.COLS];

		for (int i = 0; i < Data.ROWS; i++) {
			for (int j = 0; j < Data.COLS; j++) {
				grid[i][j] = new State(Data.WHITE);
			}
		}
		for (String green : Data.GREEN_LOCATIONS) {
			int gRow = Integer.parseInt(green.split(",")[0]);
			int gCol = Integer.parseInt(green.split(",")[1]);

			grid[gRow][gCol].setReward(Data.GREEN);
		}
		for (String orange : Data.ORANGE_LOCATIONS) {
			int oRow = Integer.parseInt(orange.split(",")[0]);
			int oCol = Integer.parseInt(orange.split(",")[1]);

			grid[oRow][oCol].setReward(Data.ORANGE);
		}
		for (String wall : Data.WALL_LOCATOINS) {
			int wRow = Integer.parseInt(wall.split(",")[0]);
			int wCol = Integer.parseInt(wall.split(",")[1]);

			grid[wRow][wCol].setWall(true);
		}
	}

	public State[][] getGrid() {
		return grid;
	}

	public void displayMaze() {

		StringBuilder maze = new StringBuilder();
		StringBuilder H_row = new StringBuilder();
		H_row.append("|");
		for (int i =0;i<Data.ROWS;i++) {
			H_row.append("----|");
		}

		maze.append("++++++++++Grid World+++++++++++\n");
		maze.append(H_row + "\n");
		for (int i = 0; i < Data.ROWS; i++) {
			maze.append("|");
			for (int j = 0; j < Data.COLS; j++) {
				if (grid[i][j].isWall()) {
					maze.append(" WW |");
				} else if (grid[i][j].getReward() == Data.GREEN) {
					maze.append(" +1 |");
				} else if (grid[i][j].getReward() == Data.ORANGE) {
					maze.append(" -1 |");
				} else {
					maze.append("    |");
				}
			}
			maze.append("\n" + H_row + "\n");
		}

		System.out.println(maze.toString());

	}

}
