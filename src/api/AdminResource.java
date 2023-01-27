package api;

import java.util.Collection;
import java.util.List;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

public class AdminResource {
	private static AdminResource INSTANCE;
	private CustomerService customerService;
	private ReservationService reservationService;
	
	private AdminResource() {
		customerService = CustomerService.getInstance();
		reservationService = ReservationService.getInstance();
	}
	
	public static AdminResource getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AdminResource();
		}
		
		return INSTANCE;
	}
	
	public Customer getCustomer(String email) {
		return customerService.getCustomer(email);
	}
	
	public void addRoom(List<IRoom> rooms) {
		for (IRoom room : rooms) {
			reservationService.addRoom(room);
		}
	}
	
	public Collection<IRoom> getAllRooms() {
		return reservationService.getAllRooms();
	}
	
	public Collection<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}
	
	public void displayAllReservations() {
		reservationService.printAllReservations();
	}
}
