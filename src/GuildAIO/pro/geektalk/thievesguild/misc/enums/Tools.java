package pro.geektalk.thievesguild.misc.enums;

public enum Tools {
	
	LOCKPICK(1523);
	private int id;
	
	Tools(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
