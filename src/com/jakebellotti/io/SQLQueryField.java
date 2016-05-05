package com.jakebellotti.io;

/**
 * 
 * @author Jake Bellotti
 * @date 5-Apr-2016
 *
 */
public class SQLQueryField {
	
	private final String key;
	private final Object value;
	
	public SQLQueryField(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

}
