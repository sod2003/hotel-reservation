package api;

import java.util.Collection;
import java.util.Date;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

public class HotelResource {
	private static HotelResource INSTANCE;
	private static CustomerService customerService;
	private static ReservationService reservationService;
	
	private HotelResource() {
		customerService = CustomerService.getInstance();
		reservationService = ReservationService.getInstance();
	}
	
	public static HotelResource getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new HotelResource();
		}
		
		return INSTANCE;
	}
	
	public Customer getCustomer(String email) {
		return customerService.getCustomer(email);
	}
	
	public void createACustomer(String email, String firstName, String lastName) {
		customerService.addCustomer(email, firstName, lastName);
	}
	
	public IRoom getRoom(String roomNumber) {
		return reservationService.getRoom(roomNumber);
	}
	
	public Reservation bookARoom(String customerEmail, IRoom room, Date CheckInDate, Date CheckOutDate) {
		return reservationService.reserveARoom(getCustomer(customerEmail), room, CheckInDate, CheckOutDate);
	}
	
	public Collection<Reservation> getCustomerReservations(String customerEmail) {
		return reservationService.getCustomersReservation(getCustomer(customerEmail));
	}
	
	public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
		return reservationService.findRooms(checkInDate, checkOutDate);
	}
}
