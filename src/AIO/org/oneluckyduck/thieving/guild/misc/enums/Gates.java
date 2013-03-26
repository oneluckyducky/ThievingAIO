package org.oneluckyduck.thieving.guild.misc.enums;

public enum Gates {
	NORTH(52303, 52302), SOUTH(52305, 52304);

	private int open, close;

	Gates(int open, int close) {
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
