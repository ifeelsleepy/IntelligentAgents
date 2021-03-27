package models;

public class SquareData {
	
	private String action;
	private double util;
	
	public SquareData() {
		action=null;
		util=0;
	}
	
	public SquareData(String action, double util) {
		this.action=action;
		this.util=util;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public double getUtil() {
		return util;
	}
	public void setUtil(double util) {
		this.util = util;
	}

}
