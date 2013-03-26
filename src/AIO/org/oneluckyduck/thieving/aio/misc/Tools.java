package org.oneluckyduck.thieving.aio.misc;

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
