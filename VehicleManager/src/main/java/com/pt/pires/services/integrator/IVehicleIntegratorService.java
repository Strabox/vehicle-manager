package com.pt.pires.services.integrator;

import java.util.Date;

import com.pt.pires.domain.exceptions.VehicleManagerException;

public interface IVehicleIntegratorService {

	void createUnlicensedVehicle(String vehicleName,String brand,Date acquisitionDate
			,boolean image,byte[] imageFile) throws VehicleManagerException;
	
	void createLicensedVehicle(String vehicleName,String brand,Date acquisitionDate,
			String license,Date licenseDate,boolean image,byte[] imageFile) throws VehicleManagerException;
	
	void addOrUpdateVehiclePortrait(String vehicleName, byte[] imageFile) throws VehicleManagerException;
	
	void removeVehicle(String vehicleName) throws VehicleManagerException;
	
}
