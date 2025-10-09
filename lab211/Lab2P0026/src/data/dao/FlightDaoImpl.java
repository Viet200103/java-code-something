package data.dao;

import business.FlightStaffRule;
import business.entity.*;
import data.managers.FileManager;
import data.managers.IFileManager;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FlightDaoImpl implements IFlightDao {
    private final HashMap<String, ReservationFlight> rMap = new HashMap<>();

    private final HashMap<String, FlightStaff> staffMap = new HashMap<>();

    private final HashMap<String, Flight> fMap = new HashMap<>();

    private final IFileManager flightFManager;
    private final IFileManager reservationFManager;
    private final IFileManager staffFFileManager;

    private FlightDaoImpl() {
        try {
            flightFManager = new FileManager("flight.txt");
            reservationFManager = new FileManager("reservation.txt");
            staffFFileManager = new FileManager("staff.txt");
            loadFlightFromFile();
            loadStaffFromFile();
            loadReservationFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFlightFromFile() throws IOException {
        List<String> rawList = flightFManager.readDataFromFile();
        rawList.forEach(
                (raw) -> {
                    Flight flight = rawConvertToFlight(raw);
                    fMap.put(flight.getFlightNumber(), flight);
                }
        );
    }

    private void loadStaffFromFile() throws IOException {
        List<String> rawList = staffFFileManager.readDataFromFile();
        rawList.forEach(
                (raw) -> {
                    FlightStaff staff = rawConvertToStaff(raw);
                    staffMap.put(staff.getStaffId(), staff);
                }
        );
    }

    private void loadReservationFromFile() throws IOException {
        List<String> rawList = reservationFManager.readDataFromFile();
        rawList.forEach(
                (raw) -> {
                    ReservationFlight reservation = rawConvertToReservation(raw);
                    rMap.put(reservation.getReservationID(), reservation);
                }
        );
    }

    private Flight rawConvertToFlight(String raw) {
        String[] components = raw.split(",");

        StringBuilder sBuilder = new StringBuilder(components[8]);
        sBuilder.deleteCharAt(0);
        sBuilder.deleteCharAt(sBuilder.length() - 1);

        String seatRaw = sBuilder.toString();

        LinkedHashSet<String> seat = new LinkedHashSet<>();

        if (!seatRaw.isBlank()) {
            seat.addAll(
                    Arrays.asList(seatRaw.split(" "))
            );
        }

        return new Flight(
                components[0],
                components[1],
                components[2],
                components[3],
                components[4],
                components[5],
                components[6],
                components[7],
                seat
        );
    }

    private FlightStaff rawConvertToStaff(String raw) {
        String[] components = raw.split(",");

        return new FlightStaff(
                components[0],
                components[1],
                components[2],
                FlightStaffRule.valueOf(components[3])
        );
    }

    private ReservationFlight rawConvertToReservation(String raw) {
        String[] components = raw.split(",");
        return new ReservationFlight(
                components[0],
                components[1],
                components[2],
                components[3],
                components[4].matches("1"),
                (components[5].matches("-") ? null : components[5])
        );
    }

    @Override
    public void addFlight(Flight flight) {

        if (fMap.containsKey(flight.getFlightNumber())) {
            throw new IllegalArgumentException("Flight number is duplicated!.");
        }

        fMap.put(flight.getFlightNumber(), flight);

    }

    @Override
    public List<Flight> searchFlight(String deLoc, String deDate, String desLoc, String arrivalDate) {

        String deLocFormatted = deLoc.toLowerCase();
        String desLocFormatted = desLoc.toLowerCase();

        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");

        ArrayList<Flight> flightList = new ArrayList<>();

        fMap.values().forEach(
                (flight) -> {

                    if (!flight.isAvailable()) return;

                    boolean isValidDeLoc = deLocFormatted.isBlank() || flight.getDepartureLocation().toLowerCase().contains(deLocFormatted);
                    boolean isValidDesLoc = desLocFormatted.isBlank() || flight.getDestinationLocation().toLowerCase().contains(desLocFormatted);

                    boolean isValidDeDate = false;
                    boolean isValidDesDate = true;
                    try {
                        isValidDeDate = deDate.isBlank() || sd.parse(deDate).equals(sd.parse(flight.getDepartureDate()));
                        isValidDesDate = arrivalDate.isBlank() || sd.parse(arrivalDate).equals(sd.parse(flight.getArrivalDate()));

                    } catch (ParseException ignored) {
                    }

                    if (isValidDeLoc && isValidDeDate && isValidDesLoc && isValidDesDate) {
                        flightList.add(flight);
                    }
                }
        );

        return flightList;
    }

    @Override
    public List<Flight> loadAllFlight() {
        return new ArrayList<>(fMap.values());
    }

    @Override
    public ReservationForm getReservation(String reservationId) {
        ReservationFlight rFlight = rMap.get(reservationId);

        if (rFlight == null) {
            return null;
        }

        if (rFlight.isCheckIn()) {
            return null;
        }

        Flight flight = fMap.get(rFlight.getFlightNumber());

        if (flight == null) {
            return null;
        }

        return new ReservationForm(flight, rFlight);
    }

    @Override
    public List<FlightStaff> getStaffList(String flightNumber) {
        return staffMap.values().stream().filter(
                (staff -> staff.getFlightNumber().equals(flightNumber))
        ).toList();
    }

    @Override
    public Flight getFlight(String flightNumber) {
        return fMap.get(flightNumber);
    }

    @Override
    public FlightStaff getStaff(String staffId) {
        return staffMap.get(staffId);
    }

    @Override
    public void addStaff(FlightStaff staff) {
        if (staffMap.containsKey(staff.getStaffId())) {
            throw new IllegalArgumentException("Staff id is duplicated");
        }

        staffMap.put(staff.getStaffId(), staff);
    }

    @Override
    public boolean updateStaff(FlightStaff staff) {
        if (staffMap.containsKey(staff.getStaffId())) {
            return staffMap.put(staff.getStaffId(), staff) != null;
        } else {
            return false;
        }

    }

    @Override
    public boolean deleteStaff(String staffId) {
        return staffMap.remove(staffId) != null;
    }

    @Override
    public void saveToFile() throws Exception {
        saveFlightToFile();
        saveReservationToFile();
        saveStaffToFile();
    }

    private void saveFlightToFile() throws IOException {
        ArrayList<String> raw = new ArrayList<>();
        fMap.values().forEach(
                (flight) -> raw.add(
                        flight.getFlightNumber() + "," +
                                flight.getAirline() + "," +
                                flight.getDepartureLocation() + "," +
                                flight.getDestinationLocation() + "," +
                                flight.getDepartureDate() + "," +
                                flight.getDepartureTime() + "," +
                                flight.getArrivalDate() + "," +
                                flight.getArrivalTime() + "," +
                                flight.getAvailableSeats().toString().replace(",", "")
                )

        );
        flightFManager.commit(raw);
    }

    private void saveReservationToFile() throws IOException {
        ArrayList<String> raw = new ArrayList<>();
        rMap.values().forEach(
                (reservation) -> raw.add(
                        reservation.getFlightNumber() + "," +
                                reservation.getReservationID() + "," +
                                reservation.getPassengerName() + "," +
                                reservation.getPassengerPhone() + "," +
                                (reservation.isCheckIn() ? '1' : '0') + "," +
                                ((reservation.getSeat() == null) ? '-' : reservation.getSeat())
                )
        );
        reservationFManager.commit(raw);
    }

    private void saveStaffToFile() throws IOException {
        ArrayList<String> raw = new ArrayList<>();
        staffMap.values().forEach(
                (reservation) -> raw.add(
                        reservation.getStaffId() + "," +
                                reservation.getFlightNumber() + "," +
                                reservation.getStaffName() + "," +
                                reservation.getRule().name()

                )
        );
        staffFFileManager.commit(raw);
    }

    @Override
    public ReservationForm reserve(ReservationFlight reservationFlight) {

        Flight flight = fMap.get(reservationFlight.getFlightNumber());

        if (flight == null || rMap.containsKey(reservationFlight.getReservationID())) {
            return null;
        }

        rMap.put(reservationFlight.getReservationID(), reservationFlight);

        return new ReservationForm(flight, reservationFlight);
    }

    private static IFlightDao INSTANCE = null;

    public static IFlightDao getInstance() {

        if (INSTANCE == null) {
            synchronized (FlightDaoImpl.class) {
                INSTANCE = new FlightDaoImpl();
            }
        }

        return INSTANCE;
    }
}
