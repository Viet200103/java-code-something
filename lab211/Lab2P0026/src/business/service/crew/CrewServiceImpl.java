package business.service.crew;

import business.entity.Flight;
import data.dao.FlightDaoImpl;
import data.dao.IFlightDao;

import java.util.List;

public class CrewServiceImpl implements ICrewService {
    
    private final IFlightDao flightDao = FlightDaoImpl.getInstance();
    
    @Override
    public List<Flight> loadFlightList() {
        return flightDao.loadAllFlight();
    }

    @Override
    public Flight getFlight(String flightNumber) {
        return flightDao.getFlight(flightNumber);
    }
}
