package models;

public class Data {

	public static final int COLS = 6;
	public static final int ROWS = 6;

	public static final double WHITE = -0.04;
	public static final double GREEN = +1;
	public static final double ORANGE = -1;
	public static final double WALL = 0;

	public static final String[] GREEN_LOCATIONS = { "0,0", "0,2", "0,5", "1,3", "2,4", "3,5" };
	public static final String[] ORANGE_LOCATIONS = { "1,1", "1,5", "2,2", "3,3", "4,4" };
	public static final String[] WALL_LOCATOINS = { "0,1", "1,4", "4,1", "4,2", "4,3" };
	
	public static final String[] NEW_GREEN_LOCATIONS = { "0,0", "0,2", "0,5", "1,3", "2,4", "3,5" ,"4,6","0,7","2,6","3,9","8,3","11,10", "11,0","11,1","11,3"};
	public static final String[] NEW_ORANGE_LOCATIONS = { "1,1", "1,5", "2,2", "3,3", "4,4", "10,9","7,6","8,7","9,8","8,8"};
	public static final String[] NEW_WALL_LOCATOINS = { "0,1", "1,4", "4,1", "4,2", "4,3","8,2","8,4","9,0","3,7","5,6","7,3","10,10","9,10","8,10" ,"10,0","10,1","10,2"};

	public static final int INITIAL_ROW = 3;
	public static final int INITIAL_COL = 2;

	public static final double DISCOUNT = 0.99;

	public static final double INT_PROB = 0.8;
	public static final double LEFT_PROB = 0.1;
	public static final double RIGHT_PROB = 0.1;
	
	public static final int K = 50;

	public static final String[] ACTIONS = { "UP", "RIGHT", "DOWN", "LEFT" };
	
	public static final double ERROR = 10;
	public static final double CONVERGENCE = ERROR* (1-DISCOUNT)/DISCOUNT;
}
