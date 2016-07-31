package com.pt.pires.services.integrator;

import java.util.Date;

import com.pt.pires.domain.exceptions.VehicleManagerException;

public interface IVehicleIntegratorService {

	void createUnlicensedVehicle(String vehicleName,String brand,Date acquisitionDate,
			int fabricationYear, boolean image,byte[] imageFile) throws VehicleManagerException;
	
	void createLicensedVehicle(String vehicleName,String brand,Date acquisitionDate,
			String license,Date licenseDate,boolean image,byte[] imageFile) throws VehicleManagerException;
	
	void addOrUpdateVehiclePortrait(String vehicleName, byte[] imageFile) throws VehicleManagerException;
	
	byte[] getVehiclePortraitImage(String vehicleName) throws VehicleManagerException;
	
	byte[] getVehiclePortraitThumbImage(String vehicleName) throws VehicleManagerException;
	
	void removeVehicle(String vehicleName) throws VehicleManagerException;
	
}
