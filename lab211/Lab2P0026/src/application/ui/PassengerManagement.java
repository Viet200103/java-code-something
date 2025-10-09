package application.ui;

import application.utilities.DataIOHelper;
import business.ReservationCodeGenerator;
import business.entity.Flight;
import business.entity.ReservationFlight;
import business.entity.ReservationForm;
import business.service.AirlineServiceImpl;
import business.service.IAirlineService;

import java.util.List;

public class PassengerManagement {
    private final IAirlineService service = new AirlineServiceImpl();
    private final DataIOHelper ioHelper = DataIOHelper.getInstance();
    
    public void processBooking() {
        String deLoc = ioHelper.getStringWithMessage("Enter departure location: ");
        String deDate = ioHelper.getDateTime("Enter departure date: ", true);
        String desLoc = ioHelper.getStringWithMessage("Enter arrival location: ");
        String desDate = ioHelper.getDateTime("Enter arrival date: ", true);

        DataIOHelper.printlnMessage("-------------------------------------------");
        DataIOHelper.printlnMessage("----------------Flight List----------------");

        List<Flight> fligtList = service.searchFlight(deLoc, deDate, desLoc, desDate);
        printFlightList(fligtList);

        DataIOHelper.printlnMessage("-------------------------------------------");

        if (fligtList.isEmpty()) {
            return;
        }

        String fNumber = ioHelper.getFlightNumber("Enter flight number: ",true);

        if (fNumber == null) {
            DataIOHelper.printlnNotification("Flight reservation is canceled");
            return;
        }

        String psName = ioHelper.getStringNotEmpty("Enter your name: ", "Your name cannot be empty");
        String psPhone = ioHelper.getPhoneNumber("Enter your phone: ");

        boolean isBooked = ioHelper.getStringWithMessage("Do you want to book a flight? (Y/N) ").matches("[\\s{y,Y}]");
        if (isBooked) {

            ReservationFlight reservationFlight = new ReservationFlight(
                    fNumber, ReservationCodeGenerator.generateReservationCode(), psName, psPhone, false, null
            );

            try {
                ReservationForm rForm = service.reserveFlight(reservationFlight);
                if (rForm != null) {
                    DataIOHelper.printlnNotification("Reserve flight is succeed!");
                    DataIOHelper.printlnMessage("-------------------------------------------");
                    DataIOHelper.printlnMessage(
                            rForm.buildReservationForm()
                    );
                    DataIOHelper.printlnMessage("-------------------------------------------");
                } else {
                    DataIOHelper.printlnNotification("Reserve flight is failed!");
                }
            } catch (Exception e) {
                DataIOHelper.printlnNotification(e.getMessage());
                DataIOHelper.printlnNotification("Reserve flight is failed!");
            }

        } else {
            DataIOHelper.printlnNotification("Flight reservation is canceled");
        }
    }

    public void processCheckIn() {
        String reservationId = ioHelper.getStringWithMessage("Enter reservation code: ");
        ReservationForm rForm = service.getReservation(reservationId);

        if (rForm == null) {
            DataIOHelper.printlnNotification("Reservation code is not exists");
            return;
        }

        DataIOHelper.printlnMessage("-------------------------------------------");
        DataIOHelper.printlnMessage(
                rForm.buildReservationForm()
        );
        DataIOHelper.printlnMessage("-------------------------------------------");

        Flight flight = rForm.getFlight();

        DataIOHelper.printlnMessage("Available seats: " + flight.getAvailableSeats());
        do {
            String seat = ioHelper.getStringWithMessage("Choose your seats: ").toUpperCase();


            if (flight.isAvailableSeats(seat) && flight.chooseSeat(seat)) {

                rForm.checkIn();
                rForm.getReservationFlight().setSeat(seat);

                DataIOHelper.printlnNotification("Check-in successfully!.");
                DataIOHelper.printlnMessage("-------------------------------------------");
                DataIOHelper.printlnMessage(rForm.buildFormCompleteCheckIn());
                DataIOHelper.printlnMessage("-------------------------------------------");

                break;
            } else  {
                DataIOHelper.printlnNotification(seat + ": is not valid");
            }
        } while (true);
    }

    private void printFlightList(List<Flight> flightList) {
        for (Flight f : flightList) {
            DataIOHelper.printMessage(f.toString());
            DataIOHelper.printlnMessage("\n-------------------------------------------");
        }
    }
}
