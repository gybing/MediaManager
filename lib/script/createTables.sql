DROP TABLE tblMovieEntrySearch;
DROP TABLE tblMovieEntry;
DROP TABLE tblMovieDefinition;

CREATE TABLE tblMovieDefinition(
	ID 					INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	imdbID				VARCHAR(255),
	title				VARCHAR(255),
	releaseYear			VARCHAR(255),
	rating				VARCHAR(255),
	released			VARCHAR(255),
	runtime				VARCHAR(255),
	genre				VARCHAR(255),
	plot				VARCHAR(1000)
);

CREATE TABLE tblMovieEntry(
	ID 							INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	fileLocation				VARCHAR(255) UNIQUE,
	extractedMovieName			VARCHAR(255) DEFAULT NULL,
	assignedMovieDefinitionID	INTEGER DEFAULT NULL,
	FOREIGN KEY(assignedMovieDefinitionID) REFERENCES tblMovieDefinition(ID)
);

CREATE TABLE tblMovieEntrySearch(
	ID 					INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	tblMovieEntryID		INTEGER NOT NULL,
	FOREIGN KEY(tblMovieEntryID) REFERENCES tblMovieEntry(ID)
);


/*
fields:
	private final String genre;
	private final String director;
	private final String writer;
	private final String actors;
	private final String plot;
	private final String language;
	private final String country;
	private final String awards;
	private final String poster;
	private final int metascore;
	private final double imdbRating;
	private final String imdbVotes;
	private final String imdbID;
*/