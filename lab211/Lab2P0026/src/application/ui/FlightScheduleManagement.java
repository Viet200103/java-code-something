package application.ui;

import application.utilities.DataIOHelper;
import business.entity.Flight;
import business.service.IAirlineService;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class FlightScheduleManagement {

    private final IAirlineService service;
    private final DataIOHelper ioHelper;


    public FlightScheduleManagement(IAirlineService service, DataIOHelper ioHelper) {
        this.service = service;
        this.ioHelper = ioHelper;
    }

    public void addNewFlight() {
        boolean isContinue;
        do {
            try {
                String fNumber = ioHelper.getFlightNumber("Enter flight number: ",true);

                if (fNumber == null) {
                    DataIOHelper.printlnNotification("Add flight is canceled");
                    return;
                }

                String airline = ioHelper.getStringNotEmpty("Enter airline name: ", "Airline name cannot be empty");
                String deCity = ioHelper.getStringNotEmpty("Enter departure location: ", "Departure City cannot be empty.");
                String desCity = ioHelper.getStringNotEmpty("Enter destination location: ", "Destination city cannot be empty");
                String deDate = ioHelper.getDateTime("Enter departure date: ");
                String deTime = ioHelper.getTime("Enter departure time: ");
                String arrivalDate = ioHelper.getDateTime("Enter arrival date: ");
                String arrivalTime = ioHelper.getTime("Enter arrival time: ");

                String availableSeat = ioHelper.getStringWithMessage("Enter available seats: ").toUpperCase();

                LinkedHashSet<String> seat = new LinkedHashSet<>();

                if (!availableSeat.isBlank()) {
                    String[] arrSeats = availableSeat.split(" ");
                    Arrays.sort(arrSeats);

                    seat.addAll(
                            Arrays.asList(arrSeats)
                    );
                }

                Flight flight = new Flight(fNumber, airline, deCity, desCity, deDate, deTime, arrivalDate, arrivalTime, seat);

                service.addFlight(flight);

                DataIOHelper.printlnNotification("Flight: " + flight.getFlightNumber() + " is added successfully!");
            } catch (Exception e) {
                DataIOHelper.printlnNotification(e.getMessage());
            }

            DataIOHelper.printlnMessage("--------------------------------");
            DataIOHelper.printMessage("Do you want to continue(Y/N)? ");
            isContinue = ioHelper.getString().matches("[\\s{y, Y}]");
            DataIOHelper.printlnMessage("--------------------------------");
        } while (isContinue);
    }

    private void printFlightList(List<Flight> flightList) {
        for (Flight f : flightList) {
            DataIOHelper.printMessage(f.toString());
            DataIOHelper.printlnMessage("\n-------------------------------------------");
        }
    }


    public void printFlightList() {
        printFlightList(service.loadFlightList());
    }
}
