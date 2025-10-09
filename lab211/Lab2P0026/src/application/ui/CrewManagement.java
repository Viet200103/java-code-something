package application.ui;

import application.utilities.DataIOHelper;
import business.entity.Flight;
import business.service.crew.CrewServiceImpl;
import business.service.crew.ICrewService;

public class CrewManagement {
    private final DataIOHelper ioHelper = DataIOHelper.getInstance();

    private final ICrewService crewService = new CrewServiceImpl();

    public void  displayMenu() {

        try {
            int choice;
            boolean isRunning = true;

            do {
                System.out.println("******Crew Management******");
                Menu.print(
                        "1. Print all flights" + "|" +
                        "2. Staff management" + "|" +
                        "3. Stop"
                );
                choice = ioHelper.getIntegerNumber();
                System.out.println("--------------------------------");

                switch (choice) {
                    case 1 -> printFlightList();
                    case 2 -> callStaffManagement();
                    case 3 -> isRunning = false;
                    default -> Menu.printRequireNotFound();
                }

            } while (isRunning);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printFlightList() {
        for (Flight f : crewService.loadFlightList()) {
            DataIOHelper.printMessage(f.toString());
            DataIOHelper.printlnMessage("\n-------------------------------------------");
        }
    }

    private void callStaffManagement() {
        String flightNumber = ioHelper.getFlightNumber("Enter flight number: ", true);
        Flight flight = crewService.getFlight(flightNumber);

        FlightStaffManagement.callStaffManagement(flight);
    }
}
