package com.jakebellotti.lang;

/**
 * 
 * @author Jake Bellotti
 * @date Apr 18, 2016
 *
 */
public enum Language {
	ENGLISH("English"), GERMAN("Deutsche"), ITALIAN("Italiano"), SPANISH("Español");

	/**
	 * What is used to identify this language (The name of the language in it's
	 * own language, e.g. German in the German language is Deutsche).
	 */
	private final String identifier;

	private Language(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}
}
