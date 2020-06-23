USE HotelDB;

-- 1. Write a query that returns a list of reservations that end in July 2023, including the name of the guest, the room number(s), and the reservation dates.
SELECT r.*, CONCAT(g.FirstName, ' ', g.LastName) GuestName, rm.RoomNumber
FROM Guests g
INNER JOIN Reservations r ON r.GuestID = g.GuestID
INNER JOIN RoomsReservations rr ON rr.ReservationID = r.ReservationID
INNER JOIN Rooms rm ON rm.RoomNumber = rr.RoomNumber
WHERE r.EndDate BETWEEN '2023-07-01' AND '2023-07-31';
-- 4 rows

-- 2. Write a query that returns a list of all reservations for rooms with a jacuzzi, displaying the guest's name, the room number, and the dates of the reservation.
SELECT r.*, CONCAT(g.FirstName, ' ', g.LastName) GuestName, rm.RoomNumber
FROM Guests g
INNER JOIN Reservations r ON r.GuestID = g.GuestID
INNER JOIN RoomsReservations rr ON rr.ReservationID = r.ReservationID
INNER JOIN Rooms rm ON rm.RoomNumber = rr.RoomNumber
INNER JOIN RoomsAmenities ra ON ra.RoomNumber = rm.RoomNumber
WHERE  ra.AmenitieID = 3;
-- 11 rows

-- 3. Write a query that returns all the rooms reserved for a specific guest, including the guest's name, the room(s) reserved, the starting date of the reservation, 
-- and how many people were included in the reservation. (Choose a guest's name from the existing data.)
SELECT rm.*, CONCAT(g.FirstName, ' ', g.LastName) GuestName, r.StartDate, rr.Adults + Children Total_Guests
FROM Guests g
INNER JOIN Reservations r ON r.GuestID = g.GuestID
INNER JOIN RoomsReservations rr ON rr.ReservationID = r.ReservationID
INNER JOIN Rooms rm ON rm.RoomNumber = rr.RoomNumber
WHERE CONCAT(g.FirstName, ' ', g.LastName) = 'Samuel Zun';
-- 2 rows for Samuel Zun

-- 4. Write a query that returns a list of rooms, reservation ID, and per-room cost for each reservation. 
-- The results should include all rooms, whether or not there is a reservation associated with the room.
SELECT rm.*, r.ReservationID, rr.Cost
FROM Rooms rm
LEFT OUTER JOIN RoomsReservations rr ON rm.RoomNumber = rr.RoomNumber
LEFT JOIN Reservations r ON rr.ReservationID = r.ReservationID;
-- 26 rows, 2 rooms have nulls for Reservations.ReservationID and Reservations.Cost

-- Write a query that returns all the rooms accommodating at least three guests and that are reserved on any date in April 2023.
SELECT rm.*
FROM Rooms rm
INNER JOIN RoomTypes rt ON rt.RoomTypeID = rm.RoomTypeID
INNER JOIN RoomsReservations rr ON rm.RoomNumber = rr.RoomNumber
INNER JOIN Reservations r ON rr.ReservationID = r.ReservationID
WHERE rt.MaxOccupancy >= 3 AND (r.StartDate <= '2023-04-30' AND r.EndDate >= '2023-04-01' );
-- 1 row

-- Write a query that returns a list of all guest names and the number of reservations per guest, sorted starting with the guest with the most reservations and then by the guest's last name.
SELECT CONCAT(g.FirstName, ' ', g.LastName) GuestName, SUM(r.GuestID)
FROM Guests g
INNER JOIN Reservations r ON g.GuestID = r.GuestID
GROUP BY r.GuestID
ORDER BY SUM(r.GuestID) DESC, g.LastName;
-- 11 rows

-- Write a query that displays the name, address, and phone number of a guest based on their phone number. (Choose a phone number from the existing data.)
SELECT CONCAT(g.FirstName, ' ', g.LastName) GuestName, g.Address, g.PhoneNumber
FROM Guests g
WHERE g.PhoneNumber = '(847) 553-0508';
-- 1 row for Samuel Zun