package application.ui;

import application.utilities.DataIOHelper;
import business.entity.Flight;
import business.service.AirlineServiceImpl;
import business.service.IAirlineService;

public class AdministratorsManagement {
    DataIOHelper ioHelper = DataIOHelper.getInstance();
    private final IAirlineService service = new AirlineServiceImpl();
    private final FlightScheduleManagement flightManagement = new FlightScheduleManagement(service, ioHelper);

    public void displayMenu() {
        try {
            int choice;
            boolean isRunning = true;
            do {
                System.out.println("******Flight Program******");
                Menu.print(
                        "1. Add flight" + "|" +
                        "2. Print flight list" + "|" +
                        "3. Staff management" + "|" +
                        "4. Stop"
                );
                try {
                    choice = ioHelper.getIntegerNumber();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println("--------------------------------");

                switch (choice) {
                    case 1 -> flightManagement.addNewFlight();
                    case 2 -> flightManagement.printFlightList();
                    case 3-> callStaffManagement();
                    case 4 -> isRunning = false;
                    default -> Menu.printRequireNotFound();
                }

            } while (isRunning);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void callStaffManagement() {
        String flightNumber = ioHelper.getFlightNumber("Enter flight number: ", true);
        Flight flight = service.getFlight(flightNumber);
        FlightStaffManagement.callStaffManagement(flight);
    }

    public void saveProgram() {
        try {
            service.saveProgram();
            DataIOHelper.printlnMessage("Save to file successfully!.");
            DataIOHelper.printlnMessage("--------------------------------");
        } catch (Exception e) {
            DataIOHelper.printlnNotification(e.getMessage());
        }
    }
}
