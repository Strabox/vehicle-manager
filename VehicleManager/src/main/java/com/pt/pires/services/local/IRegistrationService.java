package com.pt.pires.services.local;

import java.util.Collection;
import java.util.Date;

import com.pt.pires.domain.Registration;
import com.pt.pires.domain.exceptions.VehicleManagerException;

/**
 * Services related to registrations
 * @author André
 *
 */
public interface IRegistrationService {

	Long createRegistration(String vehicleName,long time,String description,Date date) throws VehicleManagerException;
	
	void removeRegistration(String vehicleName,long regId) throws VehicleManagerException;
	
	Registration getRegistrationById(long regId) throws VehicleManagerException;
	
	Collection<Registration> getVehicleRegistrations(String vehicleName) throws VehicleManagerException;
	
}
