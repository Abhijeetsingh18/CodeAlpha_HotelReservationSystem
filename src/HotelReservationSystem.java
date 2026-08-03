import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class HotelReservationSystem {
    private final DataStore dataStore;
    private List<Room> rooms;
    private List<Reservation> reservations;
    private int nextReservationId;

    public HotelReservationSystem(DataStore dataStore) {
        this.dataStore = dataStore;
        this.rooms = dataStore.loadRooms();
        this.reservations = dataStore.loadReservations();
        this.nextReservationId = reservations.stream()
                .mapToInt(Reservation::getReservationId).max().orElse(1000) + 1;
    }

    // ---------- SEARCH ----------


    public List<Room> searchAvailableRooms(RoomType type) {
        return rooms.stream()
                .filter(r -> r.isAvailable() && (type == null || r.getType() == type))
                .sorted(Comparator.comparingInt(Room::getRoomNumber))
                .collect(Collectors.toList());
    }

    public List<Room> getAllRooms() {
        return rooms.stream()
                .sorted(Comparator.comparingInt(Room::getRoomNumber))
                .collect(Collectors.toList());
    }

    // ---------- BOOKING ----------

    public static class BookingResult {
        public final boolean success;
        public final String message;
        public final Reservation reservation;

        BookingResult(boolean success, String message, Reservation reservation) {
            this.success = success;
            this.message = message;
            this.reservation = reservation;
        }
    }

    public BookingResult bookRoom(String guestName, int roomNumber, LocalDate checkIn,
                                  LocalDate checkOut, String paymentMethod) {
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            return new BookingResult(false, "Check-out date must be after check-in date.", null);
        }
        Optional<Room> roomOpt = rooms.stream().filter(r -> r.getRoomNumber() == roomNumber).findFirst();
        if (roomOpt.isEmpty()) {
            return new BookingResult(false, "Room #" + roomNumber + " does not exist.", null);
        }
        Room room = roomOpt.get();
        if (!room.isAvailable()) {
            return new BookingResult(false, "Room #" + roomNumber + " is not available.", null);
        }

        long nights = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);
        double total = nights * room.getRate();

        PaymentSimulator.PaymentResult payment = PaymentSimulator.processPayment(paymentMethod, total);
        if (!payment.success) {
            return new BookingResult(false, "Booking failed: " + payment.message, null);
        }

        Reservation reservation = new Reservation(
                nextReservationId++,
                guestName,
                room.getRoomNumber(),
                room.getType(),
                checkIn,
                checkOut,
                total,
                ReservationStatus.CONFIRMED,
                true,
                payment.transactionId
        );

        room.setAvailable(false);
        reservations.add(reservation);
        persist();

        return new BookingResult(true, payment.message, reservation);
    }

    // ---------- CANCELLATION ----------

    public static class CancelResult {
        public final boolean success;
        public final String message;

        CancelResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }

    public CancelResult cancelReservation(int reservationId) {
        Optional<Reservation> resOpt = reservations.stream()
                .filter(r -> r.getReservationId() == reservationId).findFirst();
        if (resOpt.isEmpty()) {
            return new CancelResult(false, "Reservation #" + reservationId + " not found.");
        }
        Reservation res = resOpt.get();
        if (res.getStatus() == ReservationStatus.CANCELLED) {
            return new CancelResult(false, "Reservation #" + reservationId + " is already cancelled.");
        }

        String refundMsg = "";
        if (res.isPaid()) {
            PaymentSimulator.PaymentResult refund =
                    PaymentSimulator.processRefund(res.getPaymentReference(), res.getTotalAmount());
            refundMsg = " " + refund.message;
        }

        res.setStatus(ReservationStatus.CANCELLED);
        rooms.stream()
                .filter(r -> r.getRoomNumber() == res.getRoomNumber())
                .findFirst()
                .ifPresent(r -> r.setAvailable(true));

        persist();
        return new CancelResult(true, "Reservation #" + reservationId + " cancelled." + refundMsg);
    }

    // ---------- VIEW / REPORTING ----------

    public List<Reservation> getAllReservations() {
        return reservations.stream()
                .sorted(Comparator.comparingInt(Reservation::getReservationId))
                .collect(Collectors.toList());
    }

    public List<Reservation> getReservationsByGuest(String guestName) {
        return reservations.stream()
                .filter(r -> r.getGuestName().equalsIgnoreCase(guestName))
                .sorted(Comparator.comparingInt(Reservation::getReservationId))
                .collect(Collectors.toList());
    }

    public Optional<Reservation> getReservationById(int id) {
        return reservations.stream().filter(r -> r.getReservationId() == id).findFirst();
    }

    private void persist() {
        dataStore.saveRooms(rooms);
        dataStore.saveReservations(reservations);
    }
}