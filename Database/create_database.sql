CREATE DATABASE IF NOT EXISTS PTTK_GOT;

USE PTTK_GOT;

DROP TABLE IF EXISTS sections;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS mountain_groups;
DROP TABLE IF EXISTS mountain_ranges;

CREATE TABLE IF NOT EXISTS mountain_ranges(
	id_mountain_range INT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
	mountain_range_name VARCHAR(30) NOT NULL,
	
	PRIMARY KEY(mountain_range_name)
);

CREATE TABLE IF NOT EXISTS mountain_groups(
	id_mountain_group INT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
	mountain_group_name VARCHAR(30) NOT NULL,
	fk_mountain_range_id INT UNSIGNED NOT NULL,

	PRIMARY KEY(mountain_group_name),

	FOREIGN KEY(fk_mountain_range_id) REFERENCES mountain_ranges(id_mountain_range)
);

CREATE TABLE IF NOT EXISTS locations(
	id_location INT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
	location_name VARCHAR(30) NOT NULL UNIQUE,
	altitude SMALLINT UNSIGNED NOT NULL,

	CHECK(location_name<>''),

	PRIMARY KEY(location_name)
);

CREATE TABLE IF NOT EXISTS sections(
	id_section INT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
	points_altitude TINYINT UNSIGNED NOT NULL,
	points_distance TINYINT UNSIGNED NOT NULL,
	distance MEDIUMINT UNSIGNED NOT NULL,

	fk_location_one_id INT UNSIGNED NOT NULL,
	fk_location_two_id INT UNSIGNED NOT NULL,
	fk_mountain_group_id INT UNSIGNED NOT NULL,

	CHECK(fk_location_one_id<>fk_location_two_id),

	PRIMARY KEY(fk_location_one_id, fk_location_two_id),
	FOREIGN KEY(fk_location_one_id) REFERENCES locations(id_location),
	FOREIGN KEY(fk_location_two_id) REFERENCES locations(id_location),
	FOREIGN KEY(fk_mountain_group_id) REFERENCES mountain_groups(id_mountain_group)
);

INSERT INTO mountain_ranges (mountain_range_name) VALUES ("a");
INSERT INTO mountain_ranges (mountain_range_name) VALUES ("b");
INSERT INTO mountain_ranges (mountain_range_name) VALUES ("c");
INSERT INTO mountain_ranges (mountain_range_name) VALUES ("d");

INSERT INTO mountain_groups (mountain_group_name, fk_mountain_range_id) VALUES ("B", 2);
INSERT INTO mountain_groups (mountain_group_name, fk_mountain_range_id) VALUES ("C", 2);

INSERT INTO locations (location_name, altitude) VALUES ("A", 10);
INSERT INTO locations (location_name, altitude) VALUES ("B", 110);
INSERT INTO locations (location_name, altitude) VALUES ("C", 1230);
INSERT INTO locations (location_name, altitude) VALUES ("D", 110);
INSERT INTO locations (location_name, altitude) VALUES ("E", 120);
INSERT INTO locations (location_name, altitude) VALUES ("F", 150);

INSERT INTO sections (points_altitude, points_distance, distance, fk_location_one_id, fk_location_two_id, fk_mountain_group_id)
	VALUES (12, 12, 0, 1, 2, 2);

INSERT INTO sections (points_altitude, points_distance, distance, fk_location_one_id, fk_location_two_id, fk_mountain_group_id)
	VALUES (12, 12, 0, 1, 3, 2);

INSERT INTO sections (points_altitude, points_distance, distance, fk_location_one_id, fk_location_two_id, fk_mountain_group_id)
	VALUES (12, 12, 0, 1, 4, 2);

INSERT INTO sections (points_altitude, points_distance, distance, fk_location_one_id, fk_location_two_id, fk_mountain_group_id)
	VALUES (12, 12, 0, 1, 5, 2);

INSERT INTO sections (points_altitude, points_distance, distance, fk_location_one_id, fk_location_two_id, fk_mountain_group_id)
	VALUES (12, 12, 0, 5, 5, 2);