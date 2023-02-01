package model;

public final class FreeRoom extends Room {

	public FreeRoom(String roomNumber, RoomType enumeration) {
		super(roomNumber, 0, enumeration);
	}
	
	@Override 
	public final String toString() {
		return "Room: " + roomNumber + "\nFREE ROOM\nRoom Type: " + enumeration + "\n";
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
