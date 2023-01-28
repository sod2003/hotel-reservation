package model;

import java.util.Date;

public final class Reservation {
	private final Customer customer;
	private final IRoom room;
	private final Date checkInDate;
	private final Date checkOutDate;
	
	public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
		this.customer = customer;
		this.room = room;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}

	public final Customer getCustomer() {
		return customer;
	}

	public final Date getCheckInDate() {
		return checkInDate;
	}

	public final Date getCheckOutDate() {
		return checkOutDate;
	}

	public final IRoom getRoom() {
		return room;
	}
	
	@Override
	public String toString() {
		return "Reservation for " + customer.fullName() + "\nRoom: " + room + "\nCheck-In: "
				+ checkInDate + "\nCheck-Out: " + checkOutDate + "\n";
	}
}
