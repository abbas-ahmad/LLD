package demos.misc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarRentalSystem {
    public static void main(String[] args) {
        // Example usage
        CarRentalSystem system = new CarRentalSystem();
        Store store = new Store("1", new Location("New York", "NY", "USA", 10001));

        User1 user = new User1("123", "John Doe", "DL123456");
        Vehicle car = new Car();
        car.id = 1;
        car.vehicleType = VehicleType.CAR;
        car.number = 1234;
        car.kmDriven = 10000;
        car.status = VehicleStatus.RENTED;

        store.inventory.addVehicle(car);

        Date fromDate = new Date(); // Current date
        Date toDate = new Date(fromDate.getTime() + (1000 * 60 * 60 * 24)); // Next day

        store.reserveVehicle(car, user, fromDate, toDate);
    }
}

enum VehicleType {
    CAR, BIKE
}

enum VehicleStatus {
    IDLE, ACTIVE, RENTED
}

class Vehicle {
    int id;
    VehicleType vehicleType;
    int number;
    int kmDriven;
    VehicleStatus status;
}

class Car extends Vehicle {
}

class Bike extends Vehicle {
}

class Inventory {
    List<Vehicle> vehicles;

    Inventory() {
        vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }
}

class Store {
    String id;
    Inventory inventory;
    Location location;
    List<Reservation> allReservations;

    Store(String id, Location location) {
        this.id = id;
        this.location = location;
        this.inventory = new Inventory();
        this.allReservations = new ArrayList<>();
    }

    void reserveVehicle(Vehicle vehicle, User1 user, Date fromDate, Date toDate) {
        if (vehicle.status == VehicleStatus.IDLE) {
            Reservation reservation = new Reservation();
            reservation = reservation.createReservation(user, vehicle, fromDate, toDate);
            allReservations.add(reservation);
            vehicle.status = VehicleStatus.RENTED;
            System.out.println("Vehicle reserved successfully.");
        } else {
            System.out.println("Vehicle is not available for reservation.");
        }
    }
}

class Location {
    String city;
    String state;
    String country;
    int pincode;

    Location(String city, String state, String country, int pincode) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }
}

class Reservation {
    String reservationId;
    Date reservationDate;
    Date fromDate;
    Date toDate;
    ReservationStatus reservationStatus;
    User1 user;
    Vehicle vehicle;

    public Reservation createReservation(User1 user, Vehicle vehicle, Date fromDate, Date toDate) {
        Reservation res = new Reservation();
        res.user = user;
        res.vehicle = vehicle;
        res.reservationDate = new Date();
        res.fromDate = fromDate;
        res.toDate = toDate;
        res.reservationStatus = ReservationStatus.SCHEDULED;
        return res;
    }
}

class User {
    String id;
    String name;
    String dlNumber;

    User(String id, String name, String dlNumber) {
        this.id = id;
        this.name = name;
        this.dlNumber = dlNumber;
    }
}

enum ReservationStatus {
    SCHEDULED, ACTIVE, COMPLETED, CANCELLED
}

class Bill {
    Reservation reservation;
    boolean isPaid;
    int amount;

    Bill(Reservation reservation, int amount) {
        this.reservation = reservation;
        this.amount = amount;
        this.isPaid = false;
    }
}

class Payment {
    Bill bill;

    Payment(Bill bill) {
        this.bill = bill;
    }

    void payBill() {
        bill.isPaid = true;
        System.out.println("Paid bill amount: " + bill.amount);
    }
}
