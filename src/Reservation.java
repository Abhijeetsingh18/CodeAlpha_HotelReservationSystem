import java.time.LocalDate;


public class Reservation {
    private final int reservationId;
    private final String guestName;
    private final int roomNumber;
    private final RoomType roomType;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final double totalAmount;
    private ReservationStatus status;
    private boolean paid;
    private String paymentReference;

    public Reservation(int reservationId, String guestName, int roomNumber, RoomType roomType,
                       LocalDate checkIn, LocalDate checkOut, double totalAmount,
                       ReservationStatus status, boolean paid, String paymentReference) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalAmount = totalAmount;
        this.status = status;
        this.paid = paid;
        this.paymentReference = paymentReference;
    }

    public int getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public int getRoomNumber() { return roomNumber; }
    public RoomType getRoomType() { return roomType; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public double getTotalAmount() { return totalAmount; }
    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }
    public String getPaymentReference() { return paymentReference; }
    public void setPaymentReference(String ref) { this.paymentReference = ref; }

    public long getNights() {
        return java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);
    }



    public String toFileString() {
        return reservationId + "|" + guestName + "|" + roomNumber + "|" + roomType.name() + "|"
                + checkIn + "|" + checkOut + "|" + totalAmount + "|" + status.name() + "|"
                + paid + "|" + (paymentReference == null ? "-" : paymentReference);
    }

    public static Reservation fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        return new Reservation(
                Integer.parseInt(p[0]),
                p[1],
                Integer.parseInt(p[2]),
                RoomType.valueOf(p[3]),
                LocalDate.parse(p[4]),
                LocalDate.parse(p[5]),
                Double.parseDouble(p[6]),
                ReservationStatus.valueOf(p[7]),
                Boolean.parseBoolean(p[8]),
                p[9].equals("-") ? null : p[9]
        );
    }

    @Override
    public String toString() {
        return String.format(
                "Reservation #%d | Guest: %-15s | Room #%d (%s) | %s -> %s | %d night(s) | Rs.%.2f | %s | Payment: %s",
                reservationId, guestName, roomNumber, roomType, checkIn, checkOut,
                getNights(), totalAmount, status, paid ? "PAID (" + paymentReference + ")" : "UNPAID");
    }
}