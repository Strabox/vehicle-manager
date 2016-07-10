package com.pt.pires.services.local;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.domain.Registration;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.persistence.RegistrationRepository;

@Service("registrationService")
public class RegistrationService implements IRegistrationService{

	@Autowired
	private RegistrationRepository registrationRepository;
	
	@Autowired
	@Qualifier("vehicleService")
	private IVehicleService vehicleService;
	
	
	@Override
	@Transactional(readOnly = false)
	public Long createRegistration(String vehicleName, long time, String description, Date date)
			throws VehicleManagerException {
		if(vehicleName == null || description == null || date == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = vehicleService.getVehicle(vehicleName);
		Registration reg = registrationRepository.save(new Registration(time,description,date)); 
		v.addRegistration(reg);
		return reg.getId();
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Registration> getVehicleRegistrations(String vehicleName) throws VehicleManagerException {
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = vehicleService.getVehicle(vehicleName);
		return v.getRegistries();
	}

	@Override
	@Transactional(readOnly = false)
	public void removeRegistration(String vehicleName, long regId) throws VehicleManagerException {
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = vehicleService.getVehicle(vehicleName);
		v.removeRegistration(regId);
	}

	
}
