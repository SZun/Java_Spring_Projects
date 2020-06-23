USE HotelDB;

INSERT INTO Guests (
	FirstName,
    LastName,
    Address,
    City,
    StateAbbreviation,
    ZipCode,
    PhoneNumber)
VALUES(
	'Samuel',
    'Zun',
    '1234 Fake Street',
    'Chicago',
    'IL',
    '12345',
    '(847) 553-0508'
),
(
	'Mack',
    'Simmer',
    '379 Old Shore Street',
    'Council Bluffs',
    'IA',
    '51501',
    '(291) 553-050'
),
(
	'Bettyann',
    'Seery',
    '750 Wintergreen Dr.',
    'Wasilla',
    'AK',
    '99654',
    '(478) 277-9632'
),
(
	'Duane',
    'Cullison',
    '9662 Foxrun Lane',
    'Harlingen',
    'TX',
    '78552',
    '(308) 494-0198'
),
(
	'Karie',
    'Yang',
    '9378 W. Augusta Ave.',
    'West Deptford',
    'NJ',
    '08096',
    '(214) 730-0298'
),
(
	'Aurore',
    'Lipton',
    '762 Wild Rose Street',
    'Saginaw',
    'MI',
    '48601',
    '(377) 507-0974'
),
(
	'Zachery',
    'Luechtefeld',
    '7 Poplar Dr.',
    'Arvada',
    'CO',
    '80003',
    '(814) 485-2615'
),
(
	'Jeremiah',
    'Pendergrass',
    '70 Oakwood St.',
    'Zion',
    'IL',
    '60099',
    '(279) 491-0960'
),
(
	'Walter',
    'Holaway',
    '7556 Arrowhead St.',
    'Cumberland',
    'RI',
    '02864',
    '(446) 396-6785'
),
(
	'Wilfred',
    'Vise',
    '77 West Surrey Street',
    'Oswego',
    'NY',
    '13126',
    '(834) 727-1001'
),
(
	'Maritza',
    'Tilton',
    '939 Linda Rd.',
    'Burke',
    'VA',
    '22015',
    '(446) 351-6860'
),
(
	'Joleen',
    'Tison',
    '87 Queen St.',
    'Drexel Hill',
    'PA',
    '19026',
    '(231) 893-2755'
);

INSERT INTO Reservations(
    GuestID,
    StartDate,
    EndDate
) VALUES (
	2,
    '2023-02-02',
    '2023-02-04'
),
(
	2,
    '2023-09-16',
    '2023-09-17'
),
(
	2,
    '2023-11-22',
    '2023-11-25'
),
(
	3,
    '2023-02-05',
    '2023-02-10'
),
(
	3,
    '2023-07-28',
    '2023-07-29'
),
(
	3,
    '2023-08-30',
    '2023-09-01'
),
(
	4,
    '2023-02-22',
    '2023-02-24'
),
(
	4,
    '2023-11-22',
    '2023-11-25'
),
(
	5,
    '2023-03-06',
    '2023-03-07'
),
(
	5,
    '2023-09-13',
    '2023-09-15'
),
(
	1,
    '2023-03-17',
    '2023-03-20'
),
(
	1,
    '2023-06-28',
    '2023-07-02'
),
(
	6,
    '2023-03-18',
    '2023-03-23'
),
(
	6,
    '2023-06-17',
    '2023-06-18'
),
(
	7,
    '2023-03-29',
    '2023-03-31'
),
(
	8,
    '2023-03-31',
    '2023-04-05'
),
(
	9,
    '2023-04-09',
    '2023-04-13'
),
(
	9,
    '2023-07-13',
    '2023-07-14'
),
(
	10,
    '2023-04-23',
    '2023-04-24'
),
(
	10,
    '2023-07-18',
    '2023-07-21'
),
(
	11,
    '2023-05-30',
    '2023-06-02'
),
(
	11,
    '2023-12-24',
    '2023-12-28'
),
(
	12,
    '2023-06-10',
    '2023-06-14'
);

INSERT INTO Amenities(`Name`, Price)
VALUES('Microwave', NULL),('Refrigerator', NULL),('Jacuzzi', 25),('Oven', NULL);

INSERT INTO RoomTypes(
	`Type`,
    BasePrice,
	StandardOccupancy,
    MaxOccupancy,
    ExtraPersonFee
) VALUES(
	'Single',
    '149.99',
    2,
    2,
    NULL
),
(
	'Double',
    '174.99',
    2,
    4,
    10
),
(
	'Suite',
    '399.99',
    3,
    8,
    20
);

INSERT INTO Rooms(
	RoomNumber,
    RoomTypeID,
    AdaAccessible
) VALUES(201,2,0),(202,2,1),(203,2,0),(204,2,1),
				(205,1,0),(206,1,1),(207,1,0),(208,1,1),
                (301,2,0),(302,2,1),(303,2,0),(304,2,1),
                (305,1,0),(306,1,1),(307,1,0),(308,1,1),
                (401,3,1),(402,3,1);
   
INSERT INTO RoomsReservations(
	ReservationID,
    RoomNumber,
	Adults,
    Children,
    Cost
) VALUES
(1,308,1,0,'299.98'), (2,208,2,0,'149.99'),(3,206,2,0,'449.97'),(3,301,2,2,'599.97'),
(2,203,2,1,'999.95'),(5,303,2,1,'199.99'),(6,305,1,0,'349.98'),
(7,305,2,0,'349.98'),(8,401,2,2,'1199.97'),
(9,201,2,2,'199.99'),(10,203,2,2,'399.98'),
(11,307,1,1,'524.97'),(12,205,2,0,'699.96'),
(13,302,3,0,'924.95'),(14,304,3,0,'184.99'),
(15,202,2,2,'349.98'),
(16,304,2,0,'874.95'),
(17,301,1,0,'799.96'), (18,204,3,1,'184.99'),
(19,207,1,1,'174.99'),(20,401,4,2,'1259.97'),
(21,401,2,4,'1199.97'),(22,302,2,0,'699.96'),
(23,206,2,0,'599.96'),(23,208,1,0,'599.96');
                
INSERT INTO RoomsAmenities(RoomNumber,AmenitieID)
VALUES(201,1),(201,3),
(202,2),
(203,1),(203,3),
(204,2),
(205,1),(205,2),(205,3),
(206,1),(206,2),
(207,1),(207,2),(207,3),
(208,1),(208,2),
(301,1),(301,3),
(302,2),
(303,1),(303,3),
(304,2),
(305,1),(305,2),(305,3),
(306,1),(306,2),
(307,1),(307,2),(307,3),
(308,1),(308,2),
(401,1),(401,2),(401,4),
(402,1),(402,2),(402,4);

DELETE FROM RoomsReservations
WHERE ReservationID = (SELECT ReservationID FROM Reservations WHERE GuestID = 8);

DELETE FROM Reservations
WHERE GuestID = 8;

DELETE FROM Guests
WHERE GuestID = 8;

