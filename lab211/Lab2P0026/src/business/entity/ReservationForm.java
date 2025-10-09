package business.entity;

public class ReservationForm {

    private final Flight flight;
    private final ReservationFlight reservationFlight;

    public ReservationForm(Flight flight, ReservationFlight reservationFlight) {
        this.flight = flight;
        this.reservationFlight = reservationFlight;
    }

    public Flight getFlight() {
        return flight;
    }

    public ReservationFlight getReservationFlight() {
        return reservationFlight;
    }

    public String buildReservationForm() {
        return  flight.getDepartureDate() + " > " + flight.getDepartureDate() +
                "\n" +
                "RESERVATION CODE: " + reservationFlight.getReservationID() +
                "\n" +
                "PREPARED FOR: " + reservationFlight.getPassengerName().toUpperCase() +
                "\n" +
                flight.getAirline().toUpperCase() +
                "\n" +
                flight.getFlightNumber() +
                "\n" +
                flight.getDepartureLocation() + " > " + flight.getDestinationLocation() +
                "\n" +
                "Departing at: " + flight.getDepartureTime() +
                "\n" +
                "arriving at: " + flight.getArrivalTime() +
                "\n" +
                "Seats: check-in required";
    }

    public String buildFormCompleteCheckIn() {
        return  flight.getDepartureDate() + " > " + flight.getDepartureDate() +
                "\n" +
                "PREPARED FOR: " + reservationFlight.getPassengerName().toUpperCase() +
                "\n" +
                flight.getAirline().toUpperCase() +
                "\n" +
                flight.getFlightNumber() +
                "\n" +
                flight.getDepartureLocation() + " > " + flight.getDestinationLocation() +
                "\n" +
                "Departing at: " + flight.getDepartureTime() +
                "\n" +
                "arriving at: " + flight.getArrivalTime() +
                "\n" +
                "Seats: " + reservationFlight.getSeat();
    }

    public void checkIn() {
        reservationFlight.checkIn();
    }
}
