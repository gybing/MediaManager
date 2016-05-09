package com.jakebellotti.model.filenamecleanser;

import com.jakebellotti.model.FileNameCleanser;

/**
 * 
 * @author Jake Bellotti Date 9-May-2016
 *
 */
public class CapitalizationFileNameCleanser extends FileNameCleanser {

	@Override
	public String cleanseFileName(String toBeCapped) {
		String[] tokens = toBeCapped.split("\\s");
		toBeCapped = "";

		for (int i = 0; i < tokens.length; i++) {
			char capLetter = Character.toUpperCase(tokens[i].charAt(0));
			toBeCapped += " " + capLetter + tokens[i].substring(1);
		}
		toBeCapped = toBeCapped.trim();
		return toBeCapped;
	}

	@Override
	public String getFileCleanserName() {
		return "Capitalize each word";
	}

}
