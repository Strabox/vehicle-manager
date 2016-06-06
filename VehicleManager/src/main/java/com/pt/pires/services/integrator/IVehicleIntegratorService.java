package com.pt.pires.services.integrator;

import java.util.Date;

import com.pt.pires.domain.exceptions.VehicleManagerException;

public interface IVehicleIntegratorService {

	void createUnlicensedVehicle(String name,String brand,Date acquisitionDate
			,byte[] file) throws VehicleManagerException;
	
}
