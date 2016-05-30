package com.jakebellotti.scene.movie.add;

import java.io.File;

public class MovieDirectoryEntry {

	/**
	 * The folder/directory of the entry.
	 */
	private final File directory;
	/**
	 * Whether or not we should check this directory for new media on startup.
	 */
	private final boolean scanOnStartup;
	/**
	 * Whether or not this directory is removable media, to determine whether or
	 * not we should change the drive label to find the movies.
	 */
	private final boolean removableMedia;
	/**
	 * 
	 */
	private final boolean scanSubdirectories;

	public MovieDirectoryEntry(final File directory, final boolean scanOnStartup, final boolean removableMedia , final boolean scanSubdirectories) {
		this.directory = directory;
		this.scanOnStartup = scanOnStartup;
		this.removableMedia = removableMedia;
		this.scanSubdirectories = scanSubdirectories;
	}

	public File getDirectory() {
		return directory;
	}

	public boolean isScanOnStartup() {
		return scanOnStartup;
	}

	public boolean isRemovableMedia() {
		return removableMedia;
	}

	public boolean isScanSubdirectories() {
		return scanSubdirectories;
	}

}
