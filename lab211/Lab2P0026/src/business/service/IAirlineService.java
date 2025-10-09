package business.service;

import business.entity.Flight;
import business.entity.ReservationFlight;
import business.entity.ReservationForm;

import java.util.List;

public interface IAirlineService {
    void addFlight(Flight flight) throws Exception;

    Flight getFlight(String flightNumber);

    List<Flight> searchFlight(String deLoc, String deDate, String desLoc, String desDate);

    ReservationForm reserveFlight(ReservationFlight reservationFlight) throws Exception;

    List<Flight> loadFlightList();

    ReservationForm getReservation(String reservationId);

    void saveProgram() throws Exception;
}
