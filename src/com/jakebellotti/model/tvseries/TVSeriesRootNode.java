package com.jakebellotti.model.tvseries;

/**
 *
 * @author Jake Bellotti
 * @since Jun 1, 2016
 */
public class TVSeriesRootNode extends TVSeriesNode {
	
	private String name;
	
	public TVSeriesRootNode(String name) {
		setName(name);
	}

	@Override
	public String getDisplayName() {
		return getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
