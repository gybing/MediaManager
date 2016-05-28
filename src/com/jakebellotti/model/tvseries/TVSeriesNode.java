package com.jakebellotti.model.tvseries;

public class TVSeriesNode {
	
	private String name;
	
	public TVSeriesNode(final String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
