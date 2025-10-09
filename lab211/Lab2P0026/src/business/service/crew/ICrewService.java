package business.service.crew;

import business.entity.Flight;

import java.util.List;

public interface ICrewService {
    List<Flight> loadFlightList();

    Flight getFlight(String flightNumber);
}
