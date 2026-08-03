import java.io.*;
import java.nio.file.*;
import java.util.*;


public class DataStore {
    private final String roomsFile;
    private final String reservationsFile;

    public DataStore(String roomsFile, String reservationsFile) {
        this.roomsFile = roomsFile;
        this.reservationsFile = reservationsFile;
    }


    public List<Room> loadRooms() {
        List<Room> rooms = new ArrayList<>();
        Path path = Paths.get(roomsFile);
        if (!Files.exists(path)) {
            rooms = createDefaultRooms();
            saveRooms(rooms);
            return rooms;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(roomsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                rooms.add(Room.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading rooms: " + e.getMessage());
        }
        return rooms;
    }

    public void saveRooms(List<Room> rooms) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(roomsFile))) {
            for (Room r : rooms) {
                bw.write(r.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving rooms: " + e.getMessage());
        }
    }

    public List<Reservation> loadReservations() {
        List<Reservation> list = new ArrayList<>();
        Path path = Paths.get(reservationsFile);
        if (!Files.exists(path)) {
            return list;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(reservationsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                list.add(Reservation.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading reservations: " + e.getMessage());
        }
        return list;
    }

    public void saveReservations(List<Reservation> reservations) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(reservationsFile))) {
            for (Reservation r : reservations) {
                bw.write(r.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving reservations: " + e.getMessage());
        }
    }


    private List<Room> createDefaultRooms() {
        List<Room> rooms = new ArrayList<>();
        int roomNum = 101;
        for (int i = 0; i < 4; i++) rooms.add(new Room(roomNum++, RoomType.STANDARD, true));
        for (int i = 0; i < 4; i++) rooms.add(new Room(roomNum++, RoomType.DELUXE, true));
        for (int i = 0; i < 2; i++) rooms.add(new Room(roomNum++, RoomType.SUITE, true));
        return rooms;
    }
}