package business.service;

import business.entity.Flight;
import business.entity.ReservationFlight;
import business.entity.ReservationForm;
import data.dao.FlightDaoImpl;
import data.dao.IFlightDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AirlineServiceImpl implements IAirlineService {
    SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat time = new SimpleDateFormat("HH:mm");

    private final IFlightDao flightDao = FlightDaoImpl.getInstance();

    @Override
    public void addFlight(Flight flight) throws Exception {
        flightDao.addFlight(flight);
    }

    @Override
    public Flight getFlight(String flightNumber) {
        return flightDao.getFlight(flightNumber);
    }

    @Override
    public ReservationForm reserveFlight(ReservationFlight reservationFlight) throws Exception {
        return flightDao.reserve(reservationFlight);
    }

    @Override
    public List<Flight> searchFlight(String deLoc, String deDate, String desLoc, String desDate) {
        return sortFlightList(
                flightDao.searchFlight(deLoc, deDate, desLoc, desDate)
        );
    }

    @Override
    public ReservationForm getReservation(String reservationId) {
        return flightDao.getReservation(reservationId);
    }


    @Override
    public void saveProgram() throws Exception {
        flightDao.saveToFile();
    }

    // https://stackoverflow.com/questions/43766914/sort-list-based-on-conditions-ascending-or-descending
    @Override
    public List<Flight> loadFlightList() {
        return sortFlightList(
                flightDao.loadAllFlight()
        );
    }

    private List<Flight> sortFlightList(List<Flight> flightList) {
        flightList.sort(createFlightComparator());
        return flightList;
    }

    private Comparator<Flight> createFlightComparator() {
       return (f1, f2) -> {
           try {
               Date d1 = sd.parse(f1.getDepartureDate());
               Date d2 = sd.parse(f2.getDepartureDate());

               if (d1.equals(d2)) {
                   Date a1 = sd.parse(f1.getArrivalDate());
                   Date a2 = sd.parse(f2.getArrivalDate());

                   if (a1.equals(a2)) {
                       Date dt1 = time.parse(f1.getDepartureTime());
                       Date dt2 = time.parse(f2.getDepartureTime());

                       if (dt1.equals(dt2)) {
                           Date at1 = time.parse(f1.getArrivalTime());
                           Date at2 = time.parse(f2.getArrivalTime());

                           return (int) (at2.getTime() - at1.getTime());

                       } else  {
                           return (int) (dt1.getTime() - dt2.getTime());
                       }

                   } else {
                       return (int) (a2.getTime() - a1.getTime());
                   }
               } else {
                   return (int) (d2.getTime() - d1.getTime());
               }
           } catch (ParseException e) {
               return 0;
           }
       };
    }
}
