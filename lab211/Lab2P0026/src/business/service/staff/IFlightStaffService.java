package business.service.staff;

import business.entity.FlightStaff;

import java.util.List;

public interface IFlightStaffService {

    FlightStaff getStaff(String staffId);

    void addStaff(FlightStaff staff) throws Exception;

    boolean updateStaff(FlightStaff staff) throws Exception;

    boolean deleteStaff(String staffId);

    List<FlightStaff> getStaffList(String flightNumber);
}
