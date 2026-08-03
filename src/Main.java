import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final HotelReservationSystem system =
            new HotelReservationSystem(new DataStore("rooms.txt", "reservations.txt"));

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   WELCOME TO THE HOTEL RESERVATION SYSTEM");
        System.out.println("=========================================");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": searchRooms(); break;
                case "2": bookRoom(); break;
                case "3": cancelReservation(); break;
                case "4": viewAllReservations(); break;
                case "5": viewReservationsByGuest(); break;
                case "6": viewAllRooms(); break;
                case "0":
                    running = false;
                    System.out.println("Thank you for using the Hotel Reservation System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n----------------- MENU -----------------");
        System.out.println("1. Search available rooms");
        System.out.println("2. Book a room");
        System.out.println("3. Cancel a reservation");
        System.out.println("4. View all reservations");
        System.out.println("5. View reservations by guest name");
        System.out.println("6. View all rooms (status overview)");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void searchRooms() {
        System.out.println("\nFilter by room type:");
        System.out.println("1. STANDARD  2. DELUXE  3. SUITE  4. ANY");
        System.out.print("Choice: ");
        String c = sc.nextLine().trim();
        RoomType type = switch (c) {
            case "1" -> RoomType.STANDARD;
            case "2" -> RoomType.DELUXE;
            case "3" -> RoomType.SUITE;
            default -> null;
        };
        List<Room> results = system.searchAvailableRooms(type);
        if (results.isEmpty()) {
            System.out.println("No available rooms found for that category.");
            return;
        }
        System.out.println("\nAvailable rooms:");
        for (Room r : results) {
            System.out.println("  " + r);
        }
    }

    private static void bookRoom() {
        System.out.print("\nGuest name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Guest name cannot be empty.");
            return;
        }

        System.out.print("Room number to book: ");
        int roomNumber;
        try {
            roomNumber = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid room number.");
            return;
        }

        LocalDate checkIn = readDate("Check-in date (YYYY-MM-DD): ");
        if (checkIn == null) return;
        LocalDate checkOut = readDate("Check-out date (YYYY-MM-DD): ");
        if (checkOut == null) return;

        System.out.print("Payment method (CARD / UPI / WALLET): ");
        String method = sc.nextLine().trim();
        if (method.isEmpty()) method = "CARD";

        HotelReservationSystem.BookingResult result =
                system.bookRoom(name, roomNumber, checkIn, checkOut, method);

        System.out.println();
        if (result.success) {
            System.out.println("BOOKING CONFIRMED!");
            System.out.println(result.message);
            System.out.println(result.reservation);
        } else {
            System.out.println("BOOKING FAILED: " + result.message);
        }
    }

    private static void cancelReservation() {
        System.out.print("\nEnter reservation ID to cancel: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid reservation ID.");
            return;
        }
        HotelReservationSystem.CancelResult result = system.cancelReservation(id);
        System.out.println(result.success ? "SUCCESS: " + result.message : "FAILED: " + result.message);
    }

    private static void viewAllReservations() {
        List<Reservation> all = system.getAllReservations();
        if (all.isEmpty()) {
            System.out.println("\nNo reservations found.");
            return;
        }
        System.out.println("\nAll reservations:");
        for (Reservation r : all) {
            System.out.println("  " + r);
        }
    }

    private static void viewReservationsByGuest() {
        System.out.print("\nGuest name: ");
        String name = sc.nextLine().trim();
        List<Reservation> list = system.getReservationsByGuest(name);
        if (list.isEmpty()) {
            System.out.println("No reservations found for " + name + ".");
            return;
        }
        for (Reservation r : list) {
            System.out.println("  " + r);
        }
    }

    private static void viewAllRooms() {
        System.out.println("\nAll rooms:");
        for (Room r : system.getAllRooms()) {
            System.out.println("  " + r);
        }
    }

    private static LocalDate readDate(String prompt) {
        System.out.print(prompt);
        String input = sc.nextLine().trim();
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return null;
        }
    }
}