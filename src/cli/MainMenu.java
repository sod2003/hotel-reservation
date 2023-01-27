package cli;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

public class MainMenu {
	private static HotelResource hotelResource = HotelResource.getInstance();
	private static AdminResource adminResource = AdminResource.getInstance();
	private static Calendar calendar = Calendar.getInstance();
	private static DateFormat DFormat = new SimpleDateFormat("MM/DD/YY");

	private static void printMenu() {
		System.out.println("1.  Find and reserve a room\n"
				+ "2.  See my reservations\n"
				+ "3.  Create an account\n"
				+ "4.  Admin\n"
				+ "5.  Exit");
	}
	
	private static void findARoom(Scanner scanner, String email) {
		if (email == null) {
			System.out.println("Please enter your account email. (example@example.com)");
			email = scanner.nextLine();
		}
		
		try {
			System.out.println("Please enter a check-in date. (MM/DD/YY");
			String input = scanner.nextLine();
			Date checkin = DFormat.parse(input);
			System.out.println("Please enter a check-out date. (MM/DD/YY");
			input = scanner.nextLine();
			Date checkout = DFormat.parse(input);
			Collection<IRoom> rooms = hotelResource.findARoom(checkin, checkout);
			
			if (rooms == null) {
				System.out.println("Couldn't find rooms for your date. Trying 1 week out.");
				calendar.setTime(checkin);
				calendar.add(calendar.DAY_OF_MONTH, 7);
				checkin = calendar.getTime();
				calendar.setTime(checkout);
				calendar.add(calendar.DAY_OF_MONTH, 7);
				checkout = calendar.getTime();
				rooms = hotelResource.findARoom(checkin, checkout);
			}
			if (rooms != null) {
				System.out.println("Rooms availble: " + rooms.size() + "\n");
				for (IRoom room : rooms) {
					System.out.println(room);
				}
				String selection;
				while (true) {
					System.out.println("Enter a room number to book, or enter nothing to return to Main Menu.");
					selection = scanner.nextLine();
					if (selection.isEmpty()) {
						break;
					} else {
						try {
							IRoom room = hotelResource.getRoom(selection);
							Reservation reservation = hotelResource.bookARoom(email, room, checkin, checkout);
							if (reservation != null) {
								System.out.println("Your reservation:\n" + reservation);
								break;
							}
						} catch (Exception e) {
							System.out.println("Somethign went wrong. Try again.");
						}
					}
				}
			} else {
				System.out.println("Sorry! No rooms available.");
			}
			
		} catch (ParseException e) {
			System.out.println("Incorrect date formats entered. Try again");
		}
		
		System.out.println("Please enter your account email. (example@example.com)");
	}
	
	private static void getCustomerReservations(Scanner scanner) {
		System.out.println("Please enter your account email. (example@example.com):\n");
		String input = scanner.nextLine();
		hotelResource.getCustomerReservations(input);
	}
	
	private static String createCustomerAccount(Scanner scanner) {
		System.out.println("What is your first name?");
		String firstName = scanner.nextLine();
		System.out.println("What is your last name?");
		String lastName = scanner.nextLine();
		System.out.println("What is your email account?");
		String email = scanner.nextLine();
		hotelResource.createACustomer(email, firstName, lastName);
		return email;
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to the Hotel Reservation application!\n"
				+ "------------------------------------------------------");
		Scanner scanner = new Scanner(System.in);
		boolean user = true;
		while (user) {
			printMenu();
			String input = scanner.nextLine();
			switch (input) {
				case "1":
					System.out.println("Do you have an account?(Y or N)");
					input = scanner.nextLine();
					if (input.toLowerCase() == "y") {
						findARoom(scanner, null);
					} else if (input.toLowerCase() == "n") {
						System.out.println("Would you like to create an account? (Y or N");
						input = scanner.nextLine();
						if (input.toLowerCase() == "y") {
							String email = createCustomerAccount(scanner);
							findARoom(scanner, email);
						} else if (input.toLowerCase() == "n") {
							System.out.println("Sorry, you can't book a room with an account.");
						} else {
							System.out.println("I'm sorry. I don't recognize that output");
						}
					} else {
						System.out.println("I'm sorry. I don't recognize that output");
					}
					continue;
				case "2":
					getCustomerReservations(scanner);
					continue;
				case "3":
					createCustomerAccount(scanner);
					continue;
				case "4":
					continue;
				case "5":
					System.out.println("Shutting down");
					scanner.close();
					user = false;
					break;
				default:
					System.out.println("I'm sorry. I don't recognize that output");
			}
		}
	}
}
