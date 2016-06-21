package com.jakebellotti.model;

/**
 *
 * @author Jake Bellotti
 * @since Jun 21, 2016
 */
@FunctionalInterface
public interface LoadPluginHandler {
	
	public void loadPlugin(Class<?> plugin);

}
