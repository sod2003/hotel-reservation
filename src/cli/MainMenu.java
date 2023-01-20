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
				+ "3. Create an account\n"
				+ "4.  Admin\n"
				+ "5.  Exit");
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to the Hotel Reservation application!\n"
				+ "------------------------------------------------------");
		Scanner scanner = new Scanner(System.in);
		while (true) {
			printMenu();
			String input = scanner.nextLine();
			switch (input) {
				case "1":
					continue;
				case "2":
					continue;
				case "3":
					continue;
				case "4":
					continue;
				case "5":
					System.out.println("Shutting down");
					scanner.close();
					break;
				default:
					System.out.println("I'm sorry. I don't recognize that output");
			}
		}
	}
}
