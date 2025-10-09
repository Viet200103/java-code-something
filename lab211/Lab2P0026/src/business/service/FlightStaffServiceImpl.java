package business.service;

import business.entity.FlightStaff;
import business.service.staff.IFlightStaffService;
import data.dao.FlightDaoImpl;
import data.dao.IFlightDao;

import java.util.List;

public class FlightStaffServiceImpl implements IFlightStaffService {

    private final IFlightDao flightDao = FlightDaoImpl.getInstance();

    @Override
    public void addStaff(FlightStaff staff) throws Exception {
        flightDao.addStaff(staff);
    }

    @Override
    public FlightStaff getStaff(String staffId) {
        return flightDao.getStaff(staffId);
    }

    @Override
    public boolean updateStaff(FlightStaff staff) throws Exception {
        return flightDao.updateStaff(staff);
    }

    @Override
    public boolean deleteStaff(String staffId) {
        return flightDao.deleteStaff(staffId);
    }

    @Override
    public List<FlightStaff> getStaffList(String flightNumber) {
        return flightDao.getStaffList(flightNumber);
    }
}
