package business.entity;

import business.FlightStaffRule;

public class FlightStaff {
    private final String staffId;
    private final String flightNumber;

    private final FlightStaffRule rule;

    private final String staffName;


    public FlightStaff(String staffId, String flightNumber, String staffName, FlightStaffRule rule) {
        this.staffId = staffId;
        this.flightNumber = flightNumber;
        this.rule = rule;
        this.staffName = staffName;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public FlightStaffRule getRule() {
        return rule;
    }

    public String getStaffName() {
        return staffName;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s", staffId, staffName, rule.getValue());
    }
}
