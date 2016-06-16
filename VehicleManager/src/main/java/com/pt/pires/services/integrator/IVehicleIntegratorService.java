package com.pt.pires.services.integrator;

import java.util.Date;

import com.pt.pires.domain.exceptions.VehicleManagerException;

public interface IVehicleIntegratorService {

	void createUnlicensedVehicle(String name,String brand,Date acquisitionDate
			,byte[] file) throws VehicleManagerException;
	
	void createLicensedVehicle(String name,String brand,Date acquisitionDate,
			String license,Date licenseDate,byte[] file) throws VehicleManagerException;
	
	void removeVehicle(String vehicleName) throws VehicleManagerException;
	
}
