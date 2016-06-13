package com.pt.pires.services.local;

import java.util.Collection;
import java.util.Date;

import com.pt.pires.domain.LicensedVehicle;
import com.pt.pires.domain.Note;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.UnlicensedVehicle;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.VehicleManagerException;

public interface IVehicleService {

	Collection<LicensedVehicle> getLicensedVehicles();
	
	Collection<UnlicensedVehicle> getUnlicensedVehicles();
	
	Vehicle getVehicle(String vehicleName) throws VehicleManagerException;
	
	LicensedVehicle getLicensedVehicle(String vehicleName) throws VehicleManagerException;
	
	UnlicensedVehicle getUnlicensedVehicle(String vehicleName) throws VehicleManagerException;
	
	void removeVehicle(String vehicleName);
	
	boolean vehicleExist(String vehicleName);
	
	void createUnlicensedVehicle(String vehicleName,String brand,Date acquisitionDate) throws VehicleManagerException;
	
	void createLicensedVehicle(String vehicleName,String brand,Date acquisitionDate,
			String license ,Date licenseDate) throws VehicleManagerException;
	
	Collection<Registration> getVehicleRegistrations(String vehicleName) throws VehicleManagerException;
	
	Long addRegistrationToVehicle(String vehicleName,long time,String description,Date date) throws VehicleManagerException;
	
	void removeRegistrationFromVehicle(String vehicleName,long regId) throws VehicleManagerException;
	
	Collection<Note> getVehicleNotes(String vehicleName) throws VehicleManagerException;
	
	Long addNoteToVehicle(String vehicleName,String note) throws VehicleManagerException;
	
	void removeNoteFromVehicle(String vehicleName,long noteId) throws VehicleManagerException;
	
	Long addYearNotification(String vehicleName,String description,Date initDate) throws VehicleManagerException;
	
	Long addHalfYearNotification(String vehicleName,String description,Date initDate) throws VehicleManagerException;
	
}
