package business.entity;

public class ReservationFlight {
    private final String flightNumber;

    private final String reservationID;

    private final String passengerName;
    private final String passengerPhone;

    private boolean isCheckIn;

    private String seat;

    public ReservationFlight(String flightNumber, String reservationID, String passengerName, String passengerPhone, boolean isCheckIn, String seat) {
        this.isCheckIn = isCheckIn;
        this.flightNumber = flightNumber;
        this.reservationID = reservationID;
        this.passengerName = passengerName;
        this.passengerPhone = passengerPhone;
        this.seat = seat;
    }

    public boolean isCheckIn() {
        return isCheckIn;
    }

    public void checkIn() {
        isCheckIn = true;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getReservationID() {
        return reservationID;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getSeat() {
        return seat;
    }
}
