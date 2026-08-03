
public class Room {
    private final int roomNumber;
    private final RoomType type;
    private boolean available;

    public Room(int roomNumber, RoomType type, boolean available) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.available = available;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public double getRate() {
        return type.getBaseRate();
    }

    public String toFileString() {
        return roomNumber + "|" + type.name() + "|" + available;
    }

    public static Room fromFileString(String line) {
        String[] parts = line.split("\\|");
        int num = Integer.parseInt(parts[0]);
        RoomType t = RoomType.valueOf(parts[1]);
        boolean avail = Boolean.parseBoolean(parts[2]);
        return new Room(num, t, avail);
    }

    @Override
    public String toString() {
        String status = available ? "AVAILABLE" : "BOOKED";
        return String.format("Room #%-4d | %-8s | Rs.%-8.2f/night | %s",
                roomNumber, type, type.getBaseRate(), status);
    }
}