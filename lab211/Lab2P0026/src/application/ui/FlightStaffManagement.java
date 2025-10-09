package application.ui;

import application.utilities.DataIOHelper;
import business.FlightStaffRule;
import business.entity.Flight;
import business.entity.FlightStaff;
import business.service.FlightStaffServiceImpl;
import business.service.staff.IFlightStaffService;

import java.util.List;

public class FlightStaffManagement {
    private final DataIOHelper ioHelper = DataIOHelper.getInstance();
    private final IFlightStaffService staffService = new FlightStaffServiceImpl();
    private final Flight flight;
    public FlightStaffManagement(Flight flight) {
        this.flight = flight;
    }

    public void displayMenu() {

        try {
            int choice;
            boolean isRunning = true;

            do {
                System.out.println("--------------------------------");
                System.out.println("******Flight Staff Management******");
                Menu.print(
                        "1. Add staff" + "|" +
                        "2. Update staff" + "|" +
                        "3. Delete staff" + "|" +
                        "4. Print staff list" + "|" +
                        "5. Stop"
                );
                choice = ioHelper.getIntegerNumber();
                System.out.println("--------------------------------");

                switch (choice) {
                    case 1 -> processAddStaff();
                    case 2 -> processUpdateStaff();
                    case 3 -> processDeleteStaff();
                    case 4 -> printStaffList();
                    case 5 -> isRunning = false;
                    default -> Menu.printRequireNotFound();
                }

            } while (isRunning);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void processAddStaff() {
        String staffId = ioHelper.getStringNotEmpty("Enter staff id: ", "Staff id cannot be empty");
        String staffName = ioHelper.getStringNotEmpty("Enter staff name: ", "Staff name cannot be empty");
        FlightStaffRule staffRule = getStaffRule(false);

        FlightStaff staff = new FlightStaff(staffId, flight.getFlightNumber(), staffName, staffRule);

        try {
            staffService.addStaff(staff);
            DataIOHelper.printlnNotification("Staff added successfully!");
        } catch (Exception e) {
            DataIOHelper.printlnNotification(e.getMessage());
        }
    }

    private FlightStaffRule getStaffRule(boolean acceptEmpty) {
        do {
            try {
                int typeChoice;
                DataIOHelper.printlnMessage("Enter staff type: ");
                Menu.print(
                        "1. " + FlightStaffRule.CAPTAIN.getValue() + "|" +
                        "2. " + FlightStaffRule.CO_PILOT.getValue() + "|" +
                        "3. " + FlightStaffRule.ATTENDANT.getValue() + "|" +
                        "4. " + FlightStaffRule.GROUND.getValue()
                );

                if (acceptEmpty) {
                    String sType = ioHelper.getString();
                    if (sType.isBlank()) {
                        return FlightStaffRule.EMPTY;
                    }

                    try {
                        typeChoice = Integer.parseInt(sType);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("The staff type is not valid");
                    }

                } else {
                    typeChoice = ioHelper.getIntegerNumber();
                }

                switch (typeChoice) {
                    case 1 -> {
                        return FlightStaffRule.CAPTAIN;
                    }
                    case 2 -> {
                        return FlightStaffRule.CO_PILOT;
                    }
                    case 3 -> {
                        return FlightStaffRule.ATTENDANT;
                    }
                    case 4 -> {
                        return FlightStaffRule.GROUND;
                    }
                    default -> throw new IllegalArgumentException("The staff type is not valid");
                }
            } catch (Exception e) {
                DataIOHelper.printlnNotification(e.getMessage());
                DataIOHelper.displayTryAgainMessage();
            }
        } while (true);
    }

    private void processUpdateStaff() {
        String staffId = ioHelper.getStringWithMessage("Enter staff id: ");
        FlightStaff staff = staffService.getStaff(staffId);
        if (staff == null) {
            DataIOHelper.printlnNotification("Staff does not exists");
            return;
        }

        String staffName = ioHelper.getStringWithMessage("Enter staff name: ");
        if (staffName.isBlank()) {
            staffName = staff.getStaffName();
        }

        FlightStaffRule staffRule = getStaffRule(true);
        if (staffRule == FlightStaffRule.EMPTY) {
            staffRule = staff.getRule();
        }


        FlightStaff newStaff = new FlightStaff(staffId, flight.getFlightNumber(), staffName, staffRule);

        try {
            if (staffService.updateStaff(newStaff)) {
                DataIOHelper.printlnNotification("Staff update successully!");
            } else  {
                DataIOHelper.printlnNotification("Update staff is failed!");
            }
        } catch (Exception e) {
            DataIOHelper.printlnNotification(e.getMessage());
        }
    }

    private void processDeleteStaff() {
        String staffId = ioHelper.getStringWithMessage("Enter staff id: ");
        if (staffService.deleteStaff(staffId)) {
            DataIOHelper.printlnNotification("Staff delete successfully!");
        } else  {
            DataIOHelper.printlnNotification("delete staff is failed!");
        }
    }

    private void printStaffList() {
        List<FlightStaff> staffList = staffService.getStaffList(flight.getFlightNumber());
        for (int i = 0; i < staffList.size(); i++) {
            DataIOHelper.printlnMessage(i + ". " + staffList.get(i));
        }
    }

    public static void callStaffManagement(Flight flight) {
        if (flight == null) {
            DataIOHelper.printlnNotification("Flight number is not exists");
            return;
        }
        FlightStaffManagement staffManagement = new FlightStaffManagement(flight);
        staffManagement.displayMenu();
    }
}
