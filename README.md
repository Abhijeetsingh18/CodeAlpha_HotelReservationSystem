# 🏨 Hotel Reservation System (Java)

A console-based **Hotel Reservation System** developed using **Core Java**, **Object-Oriented Programming (OOP)** principles, and **File I/O** for persistent data storage. The application allows users to search available rooms, book reservations, cancel bookings, simulate payments, and manage hotel room availability without using a database.

---

## ✨ Features

- 🔍 Search available rooms by category
- 🛏️ Book hotel rooms with guest details
- ❌ Cancel reservations
- 💳 Simulated payment processing and refunds
- 📂 Persistent data storage using File I/O
- 📋 View room status and reservation history
- 🏗️ Modular OOP architecture

---

## 🛠️ Technologies Used

- Java (JDK 11+)
- Object-Oriented Programming (OOP)
- Java Collections Framework
- Java File I/O & NIO
- Java Time API (`java.time`)

---

## 📁 Project Structure

```
HotelReservationSystem/
│── Main.java
│── HotelReservationSystem.java
│── DataStore.java
│── PaymentSimulator.java
│── Reservation.java
│── ReservationStatus.java
│── Room.java
│── RoomType.java
│── rooms.txt
│── reservations.txt
└── README.md
```

---

## 📄 File Description

| File | Description |
|------|-------------|
| **Main.java** | Console-based user interface |
| **HotelReservationSystem.java** | Core business logic |
| **DataStore.java** | File handling for rooms and reservations |
| **PaymentSimulator.java** | Simulates payment and refund operations |
| **Reservation.java** | Reservation model |
| **ReservationStatus.java** | Reservation status enum |
| **Room.java** | Room model |
| **RoomType.java** | Room categories and pricing |

---

## 🚀 How to Run

### Clone the repository

```bash
git clone https://github.com/your-username/Hotel-Reservation-System.git
```

### Open the project

Import the project into your preferred Java IDE:

- IntelliJ IDEA
- Eclipse
- VS Code

### Compile

```bash
javac *.java
```

### Run

```bash
java Main
```

---

## 💾 Data Storage

The application automatically creates the following files during the first execution:

- `rooms.txt`
- `reservations.txt`

These files store room details and reservation records, ensuring data is preserved across application restarts.

---

## 📚 OOP Concepts Used

- Encapsulation
- Abstraction
- Enums
- Composition
- Modular Design
- Separation of Concerns

---

## 📌 Future Enhancements

- Java Swing GUI
- Admin Login System
- Customer Login
- Database Integration (MySQL)
- Online Payment Gateway
- Search by Date
- Room Images
- Email Notifications
- PDF Invoice Generation

---

## 👨‍💻 Author

**Abhijeet Singh**

- GitHub: https://github.com/Abhijeetsingh18
- LinkedIn: https://linkedin.com/in/abhijeet-singh-933988327

---

## ⭐ Support

If you found this project helpful, consider giving it a **⭐ Star** on GitHub.
