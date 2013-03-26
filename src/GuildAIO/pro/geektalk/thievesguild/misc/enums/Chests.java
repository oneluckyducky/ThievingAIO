package pro.geektalk.thievesguild.misc.enums;

public enum Chests {
	
	NORTH(52297, 52296), SOUTH(52300, 52299);
	
	private int open, close;
	
	Chests(int open, int close) {
		this.open = open;
		this.close = close;
	}
	
	public int getOpenId() {
		return open;
	}
	
	public int getClosedId() {
		return close;
	}

}
