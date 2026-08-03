
public enum RoomType {
    STANDARD(1500.0),
    DELUXE(2800.0),
    SUITE(5000.0);

    private final double baseRate;

    RoomType(double baseRate) {
        this.baseRate = baseRate;
    }

    public double getBaseRate() {
        return baseRate;
    }
}