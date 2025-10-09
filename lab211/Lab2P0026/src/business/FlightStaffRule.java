package business;

public enum FlightStaffRule {
    CAPTAIN("Captain"),
    CO_PILOT("Co-pilot"),
    ATTENDANT("Staff Attendant"),
    GROUND("Ground staff"),
    EMPTY(null);

    private final String value;

    FlightStaffRule(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
