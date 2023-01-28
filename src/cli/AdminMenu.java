package cli;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import api.AdminResource;
import model.Customer;
import model.FreeRoom;
import model.IRoom;
import model.Room;
import model.RoomType;

public class AdminMenu {
	private AdminResource adminResource = AdminResource.getInstance();
	private Scanner scanner;

	public AdminMenu(Scanner scanner) {
		this.scanner = scanner;
	}
	
	private void printMenu() {
		System.out.println("1. See all Customers\n"
				+ "2. See all Rooms\n"
				+ "3. See all Reservations\n"
				+ "4. Add a Room\n"
				+ "5. Back to Main Menu");
	}
	
	private void displayCustomers() {
		Collection<Customer> customers = adminResource.getAllCustomers();
		for (Customer customer : customers) {
			System.out.println(customer);
		}
	}
	
	private void displayRooms() {
		Collection<IRoom> rooms = adminResource.getAllRooms();
		for (IRoom room : rooms) {
			System.out.println(room);
		}
	}
	


	private List<IRoom> addARoom(List<IRoom> rooms) {
		if (rooms == null) {
			rooms = new LinkedList<IRoom>();
		}
		
		while (true) {
			System.out.println("Enter a room number");
			String roomNum = scanner.nextLine();
			double price;
			while (true) {
				try {
					System.out.println("Enter a room price. (X.XX or 0)");
					String priceString = scanner.nextLine();
					price = Double.valueOf(priceString);
					break;
				} catch (Exception e) {
					System.out.println("Didn't recognize price entry. Try again.");
				}
			}
			RoomType type;
			while (true) {
				System.out.println("Enter room type. (SINGLE or DOUBLE)");
				String typeString = scanner.nextLine();
				switch (typeString.toLowerCase()) {
				case "single":
					type = RoomType.SINGLE;
					break;
				case "double":
					type = RoomType.DOUBLE;
					break;
				default:
					System.out.println("Sorry, I don't recognize the input. Try again.");
					continue;
				}
				break;
			}
			if (price > 0) {
				IRoom room = new Room(roomNum, price,type);
				rooms.add(room);
			} else {
				IRoom room = new FreeRoom(roomNum, type);
				rooms.add(room);
			}
			while (true) {
				System.out.println("Would you like to add another room? (Y or N)");
				String selection = scanner.nextLine();
				switch (selection.toLowerCase()) {
				case "y":
					rooms = addARoom(rooms);
					return rooms;
				case "n":
					return rooms;
				default:
					System.out.println("Sorry, I don't recognize the input. Try again.");
					continue;
				}
			}
		}
	}
	
	public void startMenu() {
		boolean user = true;
		while (user) {
			printMenu();
			String selection = scanner.nextLine();
			switch (selection) {
			case "1":
				displayCustomers();
				break;
			case "2":
				displayRooms();
				break;
			case "3":
				adminResource.displayAllReservations();
				break;
			case "4":
				List<IRoom> rooms = addARoom(null);
				adminResource.addRoom(rooms);
				break;
			case "5":
				user = false;
				break;
			default:
				System.out.println("I don't recognize the input. Try again.");
			}
		}
	}
}
