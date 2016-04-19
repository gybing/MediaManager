package com.jakebellotti.model.filenamecleanser;

import com.jakebellotti.model.FileNameCleanser;

/**
 * 
 * @author Jake Bellotti
 * @date Apr 18, 2016
 *
 */
public class DefaultFileNameCleanser extends FileNameCleanser {

	@Override
	public String cleanseFileName(String fileName) {
		String after = new String(fileName);
		
		int fileExtensionIndex = after.lastIndexOf(".");
		after = after.substring(0, fileExtensionIndex);

		for (int i = 1970; i < 2015; i++) {
			if (!after.startsWith("" + i)) {
				if (after.endsWith("" + i)) {
					after = after.substring(0, after.length() - 4);
				} else if (after.contains("" + i)) {
					int start = after.lastIndexOf("" + i);
					after = after.substring(0, start - 1);
				}
			}
		}

		after = after.replace("6.0", "").replace("5.9", "").replace("7.5", "").replace("7.1", "").replace("7.2", "")
				.replace("6.6", "").replace("6.8", "").replace("7.7", "").replace("7.0", "");

		after = after.replace("Devils", "Devil's");

		after = after.replace("_", " ");

		if (after.endsWith(",The") || after.endsWith(",the")) {
			after = after.replace(",The", "").replace(",the", "");
			after = "The " + after;
		}

		if (after.contains("CD1") || after.contains("cd1")) {
			after = after.replace("CD1", "").replace("cd1", "");
		}

		if (after.contains("CD2") || after.contains("cd2")) {
			after = after.replace("CD2", "").replace("cd2", "");
		}

		if (after.contains("DivX")) {
			after = after.replace("DivX", "");
		}

		if (!after.contains("A.D.") || after.contains("F.I.S.T.")) {
			if (after.contains(".")) {
				after = after.replace(".", " ");
			}
		}

		if (after.contains("xvid")) {
			after = after.replace("xvid", " ");
		}

		if (after.contains("XviD")) {
			after = after.substring(0, after.indexOf("XviD") - 1);
		}

		if (after.contains("-")) {
			after = after.replace("-", " ");
		}

		if (after.contains("  ")) {
			after = after.replace("  ", "");
		}

		if (after.contains("[") & after.contains("]")) {
			String newAfter = after.substring(0, after.indexOf("["));
			after = newAfter;
		}

		if (after.startsWith("copy") || after.startsWith("Copy")) {
			for (int i = 0; i < 10; i++) {
				after = after.replace("Copy (" + i + ") of ", "").replace("Copy of", "");
			}

		} else {
			if (after.contains("(") & after.contains(")")) {
				String newAfter = after.substring(0, after.indexOf("("));
				after = newAfter;
			}
		}

		if (after.contains("[20")) {
			String newAfter = after.substring(0, after.indexOf("[20"));
			after = newAfter;
		}

		after = after.trim();
		return after;
	}

	@Override
	public String getFileCleanserName() {
		return "Default name cleanser";
	}

}
