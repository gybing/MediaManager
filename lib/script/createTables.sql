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

/* A directory that contains movies*/
CREATE TABLE tblMovieDirectoryEntry (
	ID 					INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	directoryLocation	VARCHAR(255) NOT NULL UNIQUE,
	removableDirectory	BOOLEAN NOT NULL DEFAULT FALSE,
	scanOnStartup		BOOLEAN NOT NULL DEFAULT FALSE,
	scanSubdirectories	BOOLEAN NOT NULL DEFAULT FALSE
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
	FOREIGN KEY(tvSeriesEntryID) REFERENCES tblTVSeriesEntry(ID)
);

CREATE TABLE tblTVSeriesEpisodeDefinition(
	ID								INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)
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


/* 
INSERT DATA
*/
/* TV Series entry*/
INSERT INTO tblTVSeriesEntry(seriesName) VALUES ('My Name Is Earl');
INSERT INTO tblTVSeriesEntry(seriesName) VALUES ('Arrow');


/* TV Series seasons*/

INSERT INTO tblTVSeriesEntry(seriesName) 
VALUES ('Skins');

INSERT INTO tblTVSeriesSeason(seasonNumber, episodeCount, themoviedbID, posterURL, tvSeriesEntryID) 
VALUES(1, 9, 2596, 'https://image.tmdb.org/t/p/w185/uVvxlICxd0AFtgLgTqdtIA5SatG.jpg', 1);

INSERT INTO tblTVSeriesSeason(seasonNumber, episodeCount, themoviedbID, posterURL, tvSeriesEntryID) 
VALUES(2, 10, 2597, 'https://image.tmdb.org/t/p/w185/z3hWluGuywvvSckgyPPpRQJcXcs.jpg', 1);

INSERT INTO tblTVSeriesEpisode(fileLocation, episodeNumber, tvSeriesSeasonID) 
VALUES('Skins - S01-E01', 1, 1);
INSERT INTO tblTVSeriesEpisode(fileLocation, episodeNumber, tvSeriesSeasonID) 
VALUES('Skins - S01-E02', 2, 1);

INSERT INTO tblTVSeriesEpisode(fileLocation, episodeNumber, tvSeriesSeasonID) 
VALUES('Skins - S02-E01', 1, 2);
INSERT INTO tblTVSeriesEpisode(fileLocation, episodeNumber, tvSeriesSeasonID) 
VALUES('Skins - S02-E02', 2, 2);
