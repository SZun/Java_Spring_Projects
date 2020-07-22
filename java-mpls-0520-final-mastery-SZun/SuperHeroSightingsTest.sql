DROP DATABASE IF EXISTS SuperheroesTestDB;

CREATE DATABASE SuperheroesTestDB;

USE SuperheroesTestDB;

CREATE TABLE Powers(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    `Name` VARCHAR(15) NOT NULL
);

CREATE TABLE Organizations(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    `Name` VARCHAR(50) NOT NULL,
    Address VARCHAR(200) NOT NULL,
    Email VARCHAR(255) NOT NULL
);

CREATE TABLE Superheros(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    `Name` VARCHAR(30) NOT NULL,
    `Description` VARCHAR(255) NOT NULL,
    PowerId INT NOT NULL,
    FOREIGN KEY FK_Superheros_PowerId(PowerId)
		REFERENCES Powers(Id)
);

CREATE TABLE Locations(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    `Name` VARCHAR(30) NOT NULL,
     `Description` VARCHAR(255) NOT NULL,
     Address VARCHAR(200) NOT NULL,
     Latitude DECIMAL(9,7) NOT NULL,
     Longitude DECIMAL(10,7) NOT NULL
);

CREATE TABLE LocationsSuperheros(
	Id INT PRIMARY KEY AUTO_INCREMENT,
	SuperheroId INT NOT NULL,
    LocationId INT NOT NULL,
    SightingDate DATE NOT NULL,
    FOREIGN KEY FK_LocationsSuperheros_SuperheroId(SuperheroId)
		REFERENCES Superheros(Id),
	FOREIGN KEY FK_LocationsSuperheros_LocationId(LocationId)
		REFERENCES Locations(Id)
);

CREATE TABLE OrganizationsSuperheros(
	SuperheroId INT NOT NULL,
    OrganizationId INT NOT NULL,
    PRIMARY KEY(SuperheroId,OrganizationId),
    FOREIGN KEY FK_OrganizationsSuperheros_SuperheroId(SuperheroId)
		REFERENCES Superheros(Id),
	FOREIGN KEY FK_OrganizationsSuperheros_OrganizationId(OrganizationId)
		REFERENCES Organizations(Id)
);