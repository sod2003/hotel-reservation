package cli;

import java.util.Scanner;

import api.AdminResource;
import api.HotelResource;

public class MainMenu {
	private static HotelResource hotelResource = HotelResource.getInstance();
	private static AdminResource adminResource = AdminResource.getInstance();

	private static void printMenu() {
		System.out.println("1.  Find and reserve a room\n"
				+ "2.  See my reservations\n"
				+ "3.  Create an account\n"
				+ "4.  Admin\n"
				+ "5.  Exit");
	}
	
	private static void getCustomerReservations(Scanner scanner) {
		System.out.println("Please enter your account email. (example@example.com):\n");
		String input = scanner.nextLine();
		hotelResource.getCustomerReservations(input);
	}
	
	private static void createCustomerAccount(Scanner scanner) {
		System.out.println("");
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
					System.out.println("Do you have an account?(Y or N)\n");
					input = scanner.nextLine();
					if (input.toLowerCase() == "y") {
						getCustomerReservations(scanner);
					} else if (input.toLowerCase() == "n") {
						
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
