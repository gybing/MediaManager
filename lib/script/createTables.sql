/*Describes the date of a movie. Does not generally have to refer to an imdb definition, as the user can add their own data in if need be.*/
DROP TABLE tblMovieDefinition;
CREATE TABLE tblMovieDefinition(
	ID 					INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	imdbID				VARCHAR(9),
	title				VARCHAR(50),
	releaseYear			VARCHAR(4),
	rating				VARCHAR(10),
	released			VARCHAR(20),
	runtime				VARCHAR(10),
	genre				VARCHAR(255)
);

/*Describes a movie file entry. Does not have to have any data assigned to it by default.*/
DROP TABLE tblMovieEntry;
CREATE TABLE tblMovieEntry(
	ID 					INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	fileLocation		VARCHAR(100),
	tblMovieDefinitionID INTEGER NOT NULL,
	FOREIGN KEY (tblMovieDefinitionID) REFERENCES tblMovieDefinition(ID)
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