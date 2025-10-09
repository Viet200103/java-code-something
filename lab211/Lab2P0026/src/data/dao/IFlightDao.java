package data.dao;

import business.entity.*;

import java.util.List;

public interface IFlightDao {
    void addFlight(Flight flight) throws Exception;

    List<Flight> searchFlight(String deLoc, String deDate, String desLoc, String desDate);

    List<Flight> loadAllFlight();

    Flight getFlight(String flightNumber);

    ReservationForm reserve(ReservationFlight reservationFlight) throws Exception;

    ReservationForm getReservation(String reservationId);

    FlightStaff getStaff(String staffId);

    void addStaff(FlightStaff staff) throws Exception;

    boolean updateStaff(FlightStaff staff) throws Exception;

    boolean deleteStaff(String staffId);

    void saveToFile() throws Exception;

    List<FlightStaff> getStaffList(String flightNumber);
}
