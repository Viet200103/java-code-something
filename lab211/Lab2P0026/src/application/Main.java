package application;

import application.ui.*;
import application.utilities.DataIOHelper;

public class Main {
    public static void main(String[] args) {
        DataIOHelper inputHelper = DataIOHelper.getInstance();
        try {
            int choice;

            do {
                System.out.println("******Flight Program******");
                Menu.print(
                        "1. Reservation & booking" + "|" +
                        "2. Check-in & assignment" + "|" +
                        "3. Admin access" + "|" +
                        "4. Crew access" + "|" +
                        "5. Save" + "|" +
                        "6. Exit"
                );
                try {
                    choice = inputHelper.getIntegerNumber();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println("--------------------------------");

                switch (choice) {
                    case 1 -> {
                        PassengerManagement ps = new PassengerManagement();
                        ps.processBooking();
                    }
                    case 2 -> {
                        PassengerManagement ps = new PassengerManagement();
                        ps.processCheckIn();
                    }
                    case 3-> (new AdministratorsManagement()).displayMenu();
                    case 4 -> (new CrewManagement()).displayMenu();
                    case 5 -> (new AdministratorsManagement()).saveProgram();
                    case 6 -> {
                        System.out.println("***Store Program is stopped***");
                        System.exit(0);
                    }
                    default -> Menu.printRequireNotFound();
                }

            } while (true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}