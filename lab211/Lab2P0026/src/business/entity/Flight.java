package business.entity;

import java.util.LinkedHashSet;
import java.util.Set;

public class Flight {
    private final String flightNumber;
    private final String airline;
    private final String departureLocation;
    private final String destinationLocation;
    private final String departureDate;
    private final String departureTime;
    private final String arrivalDate;
    private final String arrivalTime;
    private final LinkedHashSet<String> availableSeats;

    public Flight(
            String flightNumber,
            String airline,
            String departureLocation,
            String destinationLocation,
            String departureDate,
            String departureTime,
            String arrivalDate,
            String arrivalTime,
            LinkedHashSet<String> availableSeats) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.departureLocation = departureLocation;
        this.destinationLocation = destinationLocation;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getAirline() {
        return airline;
    }

    public Set<String> getAvailableSeats() {
        return availableSeats;
    }

    public boolean isAvailable() {
        return !availableSeats.isEmpty();
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

        public Boolean isAvailableSeats(String seat) {
        return availableSeats.contains(seat);
    }

    @Override
    public String toString() {

        return  departureDate + " to " + arrivalDate +
                "\n" +
                airline.toUpperCase() +
                "\n" +
                "FROM TRIP " + departureLocation.toUpperCase() + " TO " + destinationLocation.toUpperCase() +
                "\n" +
                "FLIGHT NUMBER: " + flightNumber +
                "\n" +
                "DEPARTING AT: " + departureTime +
                "\n" +
                "ARRIVING AT: " + arrivalTime +
                "\n" +
                "AVAILABLE SEATS: " + availableSeats;
    }

    public boolean chooseSeat(String seat) {
        return availableSeats.remove(seat);
    }
}
