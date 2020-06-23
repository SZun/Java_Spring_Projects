DROP DATABASE IF EXISTS HotelDB;

CREATE DATABASE HotelDB;

USE HotelDB;

CREATE TABLE Guests(
	GuestId INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(25) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Address VARCHAR(100) NOT NULL,
    City VARCHAR(30) NOT NULL,
    StateAbbreviation CHAR(2) NOT NULL,
    ZipCode CHAR(5) NOT NULL,
    PhoneNumber CHAR(14) NOT NULL
);

CREATE TABLE Reservations (
	ReservationID INT PRIMARY KEY AUTO_INCREMENT,
    GuestID INT NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    FOREIGN KEY FK_Reservations_GuestID(GuestID)
		REFERENCES Guests(GuestID)
);

CREATE TABLE Amenities (
	AmenitieID INT PRIMARY KEY AUTO_INCREMENT,
    `Name` VARCHAR(30) NOT NULL,
    Price INT NULL
);

CREATE TABLE RoomTypes(
    RoomTypeID INT PRIMARY KEY AUTO_INCREMENT,
    `Type` CHAR(6) NOT NULL,
    BasePrice DECIMAL(6,2) NOT NULL,
	StandardOccupancy INT NOT NULL,
    MaxOccupancy INT NOT NULL,
    ExtraPersonFee INT NULL
);

CREATE TABLE Rooms (
	RoomNumber INT PRIMARY KEY,
    RoomTypeID INT NOT NULL,
    AdaAccessible BOOL NOT NULL,
    FOREIGN KEY FK_Rooms_RoomTypeID(RoomTypeID)
		REFERENCES RoomTypes(RoomTypeID)
);

CREATE TABLE RoomsReservations(
	ReservationID INT NOT NULL,
    RoomNumber INT NOT NULL,
	Adults INT NOT NULL,
    Children INT NOT NULL,
    Cost DECIMAL(6,2) NOT NULL,
    PRIMARY KEY(ReservationID, RoomNumber),
    FOREIGN KEY FK_RoomsReservations_ReservationID(ReservationID)
		REFERENCES Reservations(ReservationID),
	FOREIGN KEY FK_RoomsReservations_RoomNumber(RoomNumber)
		REFERENCES Rooms(RoomNumber)
);

CREATE TABLE RoomsAmenities(
	AmenitieID INT NOT NULL,
    RoomNumber INT NOT NULL,
    PRIMARY KEY(AmenitieID,RoomNumber),
    FOREIGN KEY FK_RoomsAmenities_AmenitieID(AmenitieID)
		REFERENCES Amenities(AmenitieID),
	FOREIGN KEY FK_RoomsAmenities_RoomNumber(RoomNumber)
		REFERENCES Rooms(RoomNumber)
);


