package model;

public final class FreeRoom extends Room {

	public FreeRoom(String roomNumber, RoomType enumeration) {
		super(roomNumber, 0, enumeration);
	}
	
	@Override 
	public final String toString() {
		return "Room Number: " + roomNumber + "\nFREE ROOM\nRoom Type: " + enumeration;
	}
}
