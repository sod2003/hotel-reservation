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
	private static ReservationService instance;
	private List<Reservation> reservations;
	private HashSet<IRoom> rooms;
	
	private ReservationService() {
		rooms = new HashSet<IRoom>();
		reservations = new LinkedList<Reservation>();
	}
	
	public static ReservationService getInstance() {
		if (instance == null) {
			instance = new ReservationService();
		}
		
		return instance;
	}
	
	public void addRoom(IRoom room) {
		if (rooms.contains(room)) {
			System.out.print("This Room already exists");
		} else {
			rooms.add(room);
		}
	}
	
	public IRoom getRoom(String roomId) {
		for (IRoom room : rooms) {
			if (room.getRoomNumber() == roomId) {
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
		Set<IRoom> bookableRooms = rooms;
		for (IRoom room : rooms) {
			bookableRooms.add(room);
		}
		
		for (Reservation reservation : reservations) {
			if ((reservation.getCheckInDate().after(checkInDate) && reservation.getCheckInDate().before(checkOutDate)) || 
					(reservation.getCheckOutDate().after(checkInDate) && reservation.getCheckOutDate().before(checkOutDate))) {
				bookableRooms.remove(reservation.getRoom());
			}
		}
		
		return bookableRooms;
	}
	
	public Collection<Reservation> getCustomersReservations(Customer customer) {
		List<Reservation> customerReservations = new LinkedList<Reservation>();
		for (Reservation reservation : reservations) {
			if (reservation.getCustomer() == customer) {
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
	
}
