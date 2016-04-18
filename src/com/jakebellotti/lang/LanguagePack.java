package com.jakebellotti.lang;

/**
 * 
 * @author Jake Bellotti
 * @date Apr 18, 2016
 *
 */
public class LanguagePack {
	
	private final Language language;
	
	public LanguagePack(Language language) {
		this.language = language;
	}
	
	public String getTranslationFromEnglish(final String englishTag) {
		//TODO finish
		return englishTag;
	}

	public Language getLanguage() {
		return language;
	}

}
