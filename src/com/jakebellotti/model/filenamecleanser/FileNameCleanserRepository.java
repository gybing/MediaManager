package com.jakebellotti.model.filenamecleanser;

import java.util.ArrayList;

import com.jakebellotti.model.FileNameCleanser;

/**
 * 
 * @author Jake Bellotti
 * @date Apr 18, 2016
 *
 */
public class FileNameCleanserRepository {
	
	private static final ArrayList<FileNameCleanser> cleansers = new ArrayList<>();

	static {
//		cleansers.add(new DefaultFileNameCleanser());
		//TODO add more cleansers
	}
	
	public static ArrayList<FileNameCleanser> getCleansers() {
		return cleansers;
	}

}
