DROP TABLE tblMovieEntrySearch;
DROP TABLE tblMovieEntry;
DROP TABLE tblMovieDefinition;

CREATE TABLE tblMovieDefinition(
	ID 					INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	imdbID				VARCHAR(1000),
	title				VARCHAR(1000),
	releaseYear			VARCHAR(1000),
	rating				VARCHAR(1000),
	released			VARCHAR(1000),
	runtime				VARCHAR(1000),
	genre				VARCHAR(1000),
	director			VARCHAR(1000),
	writer				VARCHAR(1000),
	actors				VARCHAR(1000),
	movieLanguage		VARCHAR(1000),
	country				VARCHAR(1000),
	awards				VARCHAR(1000),
	poster				VARCHAR(1000),
	metascore			INTEGER,
	imdbRating			VARCHAR(1000),
	imdbVotes			VARCHAR(1000),
	plot				VARCHAR(10000)
);

CREATE TABLE tblMovieEntry(
	ID 							INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	fileLocation				VARCHAR(255) UNIQUE,
	extractedMovieName			VARCHAR(255) DEFAULT NULL,
	lastScrapeStatusCode		INTEGER DEFAULT 0,
	assignedMovieDefinitionID	INTEGER DEFAULT NULL,
	FOREIGN KEY(assignedMovieDefinitionID) REFERENCES tblMovieDefinition(ID)
);

CREATE TABLE tblMovieEntrySearch(
	ID 					INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	tblMovieEntryID		INTEGER NOT NULL,
	FOREIGN KEY(tblMovieEntryID) REFERENCES tblMovieEntry(ID)
);

/* A directory that contains movies*/
CREATE TABLE tblMovieDirectoryEntry (
	ID 					INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	directoryLocation	VARCHAR(255) NOT NULL UNIQUE,
	removableDirectory	BOOLEAN NOT NULL DEFAULT FALSE,
	scanOnStartup		BOOLEAN NOT NULL DEFAULT FALSE,
	scanSubdirectories	BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE tblTVSeriesDefinition(
	ID								INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	name							VARCHAR(255) DEFAULT NULL,
	firstAirDate					VARCHAR(255) DEFAULT NULL,
	lastAirDate						VARCHAR(255) DEFAULT NULL,
	homePageURL						VARCHAR(255) DEFAULT NULL,
	posterURL						VARCHAR(255) DEFAULT NULL,
	backdropURL						VARCHAR(255) DEFAULT NULL,
	episodeCount					INTEGER DEFAULT NULL,
	seasonCount						INTEGER DEFAULT NULL,
	overview						VARCHAR(10000) DEFAULT NULL
);

CREATE TABLE tblTVSeriesEntry (
	ID								INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	seriesName						VARCHAR(255) NOT NULL,
	assignedTVSeriesDefinitionID	INTEGER DEFAULT NULL
);

CREATE TABLE tblTVSeriesSeason(
	ID								INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	seasonNumber					INTEGER NOT NULL,
	episodeCount					INTEGER DEFAULT NULL,
	themoviedbID					INTEGER DEFAULT NULL,
	posterURL						VARCHAR(255) DEFAULT NULL,
	tvSeriesEntryID					INTEGER NOT NULL,
	overview						VARCHAR(10000) DEFAULT NULL,
	FOREIGN KEY(tvSeriesEntryID) REFERENCES tblTVSeriesEntry(ID)
);

CREATE TABLE tblTVSeriesEpisodeDefinition(
	ID								INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	name							VARCHAR(255) DEFAULT NULL,
	overview						VARCHAR(10000) DEFAULT NULL,
	themoviedbID					INTEGER DEFAULT NULL,
	stillImageURL					VARCHAR(255) DEFAULT NULL
);


CREATE TABLE tblTVSeriesEpisode(
	ID								INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	fileLocation					VARCHAR(255) NOT NULL,
	episodeNumber					INTEGER NOT NULL,
	tvSeriesSeasonID				INTEGER NOT NULL,
	tvSeriesEpisodeDefinitionID		INTEGER DEFAULT NULL,
	FOREIGN KEY(tvSeriesSeasonID) REFERENCES tblTVSeriesSeason(ID),
	FOREIGN KEY(tvSeriesEpisodeDefinitionID) REFERENCES tblTVSeriesEpisodeDefinition(ID)
);

CREATE TABLE tblMediaType(
	ID								VARCHAR(255) NOT NULL PRIMARY KEY
);

CREATE TABLE tblRecentMedia(
	ID								INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	entryID							INTEGER NOT NULL,
	mediaType						VARCHAR(255) NOT NULL,
	datePlayed						DATE NOT NULL,
	FOREIGN KEY(mediaType) REFERENCES tblMediaType(ID)
);


/* Populate the tables */
INSERT INTO tblMediaType(ID) VALUES('TVSeries');
INSERT INTO tblMediaType(ID) VALUES('Movie');
INSERT INTO tblMediaType(ID) VALUES('Music');

