package net.stri.fdjava.views;

import lombok.Getter;

public enum Direction {

	NORD("N"),
	EST("E"),
	SUD("S"),
	OUEST("O");
	
	public static Direction getByTag(String tag) {
		for(Direction direction : Direction.values()) {
			if(direction.tag.equals(tag)) return direction;
		}
		return null;
	}
	
	@Getter
	private String tag;
	
	private Direction(String tag) {
		this.tag = tag;
	}

	public Direction opposite() {
		return Direction.values()[(this.ordinal()+2) %4];
	}
	
}