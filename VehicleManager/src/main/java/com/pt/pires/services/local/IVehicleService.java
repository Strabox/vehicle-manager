package com.pt.pires.services.local;

import java.util.Collection;
import java.util.Date;

import com.pt.pires.domain.VehicleLicensed;
import com.pt.pires.domain.VehicleUnlicensed;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.VehicleManagerException;

/**
 * Service related to vehicles
 * @author André
 *
 */
public interface IVehicleService {

	Collection<VehicleLicensed> getLicensedVehicles();
	
	Collection<VehicleUnlicensed> getUnlicensedVehicles();
	
	Collection<Vehicle> getAllVehicles();
	
	Vehicle getVehicle(String vehicleName) throws VehicleManagerException;
	
	VehicleLicensed getLicensedVehicle(String vehicleName) throws VehicleManagerException;
	
	VehicleUnlicensed getUnlicensedVehicle(String vehicleName) throws VehicleManagerException;
	
	void removeVehicle(String vehicleName) throws VehicleManagerException;;
	
	boolean vehicleExist(String vehicleName) throws VehicleManagerException;;
	
	int calculateVehicleAcquisitionYears(String vehicleName) throws VehicleManagerException;
	
	int calculateVehicleLicensedYears(String vehicleName) throws VehicleManagerException;
	
	int calculateVehicleUnlicensedYears(String vehicleName) throws VehicleManagerException;
	
	void createUnlicensedVehicle(String vehicleName,String brand,Date acquisitionDate,int fabricationYear) throws VehicleManagerException;
	
	void createLicensedVehicle(String vehicleName,String brand,Date acquisitionDate,
			String license ,Date licenseDate) throws VehicleManagerException;
	
	void changeVehicleLicensedData(String vehicleName,String newBrand,
			Date newAcquisitionDate,String newLicense,Date newLicenseDate) throws VehicleManagerException;
	
	void changeVehicleUnlicensedData(String vehicleName,String newBrand,
			Date newAcquisitionDate,int newFabricationYear) throws VehicleManagerException;
	
	
}
