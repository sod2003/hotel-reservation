package service;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import model.Customer;
import model.IRoom;
import model.Reservation;

public class ReservationService {
	private static ReservationService INSTANCE;
	private List<Reservation> reservations;
	private HashSet<IRoom> rooms;
	
	private ReservationService() {
		rooms = new HashSet<IRoom>();
		reservations = new LinkedList<Reservation>();
	}
	
	public static ReservationService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ReservationService();
		}
		
		return INSTANCE;
	}
	
	public void addRoom(IRoom room) {
		boolean roomCheck = true;
		for (IRoom currentRoom : rooms) {
			String roomNumber = room.getRoomNumber();
			String curRoomNumber = currentRoom.getRoomNumber();
			if (roomNumber.equals(curRoomNumber)) {
				System.out.println("Room " + room.getRoomNumber() + " already exists.");
				roomCheck = false;
			}
		}
		if (roomCheck) {
			rooms.add(room);
			System.out.println("Room " + room.getRoomNumber() + " added.");
		}
	}
	
	public IRoom getRoom(String roomId) {
		for (IRoom room : rooms) {
			if (room.getRoomNumber().equals(roomId)) {
				return room;
			}
		}
		
		return null;
	}
	
	public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
		if (checkInDate.before(checkOutDate)) {
			Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
			reservations.add(reservation);
			return reservation;
		}
		
		return null;
	}
	
	public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
		Set<IRoom> bookableRooms = new HashSet<IRoom>();
		Set<IRoom> nonBookableRooms = new HashSet<IRoom>();
		
		if (reservations.isEmpty()) {
			return rooms;
		}
		
		for (Reservation reservation : reservations) {
			Date resIn = reservation.getCheckInDate();
			Date resOut = reservation.getCheckOutDate();
			if (resIn.before(checkInDate) || resIn.equals(checkInDate)) {
				if (resOut.after(checkInDate)) {
					nonBookableRooms.add(reservation.getRoom());
				}
			} else if (resIn.before(checkOutDate)) {
				if (resOut.after(checkOutDate)) {
					nonBookableRooms.add(reservation.getRoom());
				}
			}
		}
		
		for (IRoom room : rooms) {
			if (!nonBookableRooms.contains(room)) {
				bookableRooms.add(room);
			}
		}
		
		return bookableRooms;
	}
	
	public Collection<Reservation> getCustomersReservation(Customer customer) {
		List<Reservation> customerReservations = new LinkedList<Reservation>();
		for (Reservation reservation : reservations) {
			if (reservation.getCustomer().equals(customer)) {
				customerReservations.add(reservation);
			}
		}
		
		return customerReservations;
	}
	
	public void printAllReservations() {
		for (Reservation reservation : reservations) {
			System.out.println(reservation);
		}
	}

	public Collection<IRoom> getAllRooms() {
		return rooms;
	}
	
}
