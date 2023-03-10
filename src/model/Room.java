package model;

public class Room implements IRoom {
	protected final String roomNumber;
	protected final double price;
	protected final RoomType enumeration;

	public Room(String roomNumber, double price, RoomType enumeration) {
		this.roomNumber = roomNumber;
		this.price = price;
		this.enumeration = enumeration;
	}
	
	@Override
	public final String getRoomNumber() {
		return roomNumber;
	}

	@Override
	public final double getRoomPrice() {
		return price;
	}

	@Override
	public final RoomType getRoomType() {
		return enumeration;
	}

	@Override
	public final boolean isFree() {
		return price == 0;
	}
	
	@Override
	public String toString() {
		return "Room: " + roomNumber + "\nPrice: " + price + "\nRoom Type: " + enumeration + "\n";
	}
	
	@Override
    public boolean equals(Object obj) {
		
		if(this == obj) {
			return true;
		}
		
		if((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		
        return this.hashCode() == obj.hashCode();
	}
	
	@Override
	public int hashCode() {
		return roomNumber.hashCode();
	}
}
