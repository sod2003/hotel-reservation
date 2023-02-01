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
	private static Calendar calendar = Calendar.getInstance();
	private static DateFormat DFormat = new SimpleDateFormat("MM/dd/yy");
	private static AdminMenu adminMenu;

	private static void printMenu() {
		System.out.println("1.  Find and reserve a room\n"
				+ "2.  See my reservations\n"
				+ "3.  Create an account\n"
				+ "4.  Admin\n"
				+ "5.  Exit");
	}
	
	private static void bookRoomConversation(Scanner scanner) {
		while (true) {
			System.out.println("Do you have an account?(Y or N)");
			String accountInput = scanner.nextLine();
			switch (accountInput.toLowerCase()) {
			case "y":
				findARoom(scanner, null);
				break;
			case "n":
				while (true) {
					System.out.println("Would you like to create an account? (Y or N)");
					String createInput = scanner.nextLine();
					switch (createInput.toLowerCase()) {
					case "y":
						String email = createCustomerAccount(scanner);
						if (email != null) {
							findARoom(scanner, email);
						} else {
							continue;
						}
						break;
					case "n":
						System.out.println("Sorry, you can't book a room without an account.\n");
						break;
					default:
						System.out.println("I'm sorry. I don't recognize that output\n");
						continue;
					}
					break;
				}
				break;
			default:
				System.out.println("I'm sorry. I don't recognize that output\n");
				continue;
			}
			break;
		}
	}
	
	private static void findARoom(Scanner scanner, String email) {
		if (email == null) {
			System.out.println("Please enter your account email. (example@example.com)");
			email = scanner.nextLine();
		}
		while (true) {
			if (hotelResource.getCustomer(email) == null) {
				System.out.println("Customer doesn't exist.\n");
				break;
			}
			try {
				System.out.println("Please enter a check-in date. (MM/DD/YY)");
				String input = scanner.nextLine();
				Date checkin = DFormat.parse(input);
				System.out.println("Please enter a check-out date. (MM/DD/YY)");
				input = scanner.nextLine();
				Date checkout = DFormat.parse(input);
				if (checkin.before(checkout)) {
					Collection<IRoom> rooms = hotelResource.findARoom(checkin, checkout);
					if (rooms == null || rooms.size() == 0) {
						System.out.println("Couldn't find rooms for your date. Trying 1 week out.\n");
						calendar.setTime(checkin);
						calendar.add(calendar.DATE, 7);
						checkin = calendar.getTime();
						calendar.setTime(checkout);
						calendar.add(calendar.DATE, 7);
						checkout = calendar.getTime();
						rooms = hotelResource.findARoom(checkin, checkout);
					}
					
					if (rooms != null && rooms.size() != 0) {
						System.out.println("Rooms available: " + rooms.size() + "\n");
						for (IRoom room : rooms) {
							System.out.println(room);
						}
						String selection;
						while (true) {
							System.out.println("Enter a room number to book, or enter nothing to return to Main Menu.");
							selection = scanner.nextLine();
							if (selection.isEmpty()) {
								break;
							}
							IRoom room = hotelResource.getRoom(selection);
							if (rooms.contains(room)) {
								try {
									Reservation reservation = hotelResource.bookARoom(email, room, checkin, checkout);
									if (reservation != null) {
										System.out.println("\nYour reservation:\n" + reservation + "\n");
										break;
									}
								} catch (Exception e) {
									System.out.println("Something went wrong. Try again.\n");
								}
							}
						}
						break;
					} else {
						System.out.println("Sorry! No rooms available for your entered dates.\n");
						break;
					}
				} else {
					System.out.println("Oops! Looks like you entered a check-out date before your check-in date! Please try again!");
				}
			} catch (ParseException e) {
				System.out.println("Incorrect date formats entered. Try again\n");
			}
		}
	}
	
	private static void getCustomerReservations(Scanner scanner) {
		System.out.println("Please enter your account email. (example@example.com):\n");
		String input = scanner.nextLine();
		Collection<Reservation> reservations = hotelResource.getCustomerReservations(input);
		if (!reservations.isEmpty()) {
			for (Reservation reservation : reservations) {
				System.out.println(reservation);
			}
		} else {
			System.out.println("This customer has no reservations.\n");
		}
	}
	
	private static String createCustomerAccount(Scanner scanner) {
		System.out.println("What is your first name?");
		String firstName = scanner.nextLine();
		System.out.println("What is your last name?");
		String lastName = scanner.nextLine();
		System.out.println("What is your email account?");
		String email = scanner.nextLine();
		hotelResource.createACustomer(email, firstName, lastName);
		Customer customer = hotelResource.getCustomer(email);
		if (customer == null) {
			System.out.println("Couldn't create customer account. Please try again.\n");
			return null;
		} else {
			return customer.getEmail();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to the Hotel Reservation application!\n"
				+ "------------------------------------------------------");
		Scanner scanner = new Scanner(System.in);
		adminMenu = new AdminMenu(scanner);
		boolean user = true;
		while (user) {
			printMenu();
			String menuInput = scanner.nextLine();
			switch (menuInput) {
				case "1":
					bookRoomConversation(scanner);
					break;
				case "2":
					getCustomerReservations(scanner);
					break;
				case "3":
					createCustomerAccount(scanner);
					continue;
				case "4":
					adminMenu.startMenu();
					break;
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
