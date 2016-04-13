DROP TABLE tblMovieEntry;
DROP TABLE tblMovieDefinition;
DROP TABLE tblMovieEntrySearch;

CREATE TABLE tblMovieDefinition(
	ID 					INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	imdbID				VARCHAR(9),
	title				VARCHAR(50),
	releaseYear			VARCHAR(4),
	rating				VARCHAR(10),
	released			VARCHAR(20),
	runtime				VARCHAR(10),
	genre				VARCHAR(255),
	plot				VARCHAR(1000)
);

CREATE TABLE tblMovieEntry(
	ID 							INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	fileLocation				VARCHAR(100),
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