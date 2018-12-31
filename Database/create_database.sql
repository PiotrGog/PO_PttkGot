CREATE DATABASE IF NOT EXISTS PTTK_GOT;

USE PTTK_GOT;

DROP TABLE IF EXISTS sections;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS mounstain_groups;
DROP TABLE IF EXISTS mounstain_ranges;

CREATE TABLE IF NOT EXISTS mounstain_ranges(
	IdMountainRange INT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
	MountainRangeName VARCHAR(30) NOT NULL,
	
	PRIMARY KEY(MountainRangeName)
);

CREATE TABLE IF NOT EXISTS mounstain_groups(
	IdMountainGroup INT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
	MountainGroupName VARCHAR(30) NOT NULL,
	IdMountainRange INT UNSIGNED NOT NULL,

	PRIMARY KEY(MountainGroupName),

	FOREIGN KEY(IdMountainRange) REFERENCES mounstain_ranges(IdMountainRange)
);

CREATE TABLE IF NOT EXISTS locations(
	IdLocation INT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
	LocationName VARCHAR(30) NOT NULL UNIQUE,
	Altitude SMALLINT UNSIGNED NOT NULL,

	CHECK(LocationName<>''),

	PRIMARY KEY(LocationName)
);

CREATE TABLE IF NOT EXISTS sections(
	IdSection INT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
	PointsAltitude TINYINT UNSIGNED NOT NULL,
	PointsDistance TINYINT UNSIGNED NOT NULL,
	Distance MEDIUMINT UNSIGNED NOT NULL,

	LocationOneId INT UNSIGNED NOT NULL,
	LocationTwoId INT UNSIGNED NOT NULL,
	MountainGroupId INT UNSIGNED NOT NULL,

	CHECK(LocationOneId!=LocationTwoId),

	PRIMARY KEY(LocationOneId, LocationTwoId),
	FOREIGN KEY(LocationOneId) REFERENCES locations(IdLocation),
	FOREIGN KEY(LocationTwoId) REFERENCES locations(IdLocation)
	--FOREIGN KEY (PersonID) REFERENCES Persons(PersonID)
);

