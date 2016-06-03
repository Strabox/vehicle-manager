package com.pt.pires.services;

import java.util.Collection;
import java.util.Date;

import com.pt.pires.domain.LicensedVehicle;
import com.pt.pires.domain.UnlicensedVehicle;
import com.pt.pires.domain.exceptions.VehicleManagerException;

public interface IVehicleService {

	Collection<LicensedVehicle> getLicensedVehicles();
	
	Collection<UnlicensedVehicle> getUnlicensedVehicles();
	
	LicensedVehicle getLicensedVehicle(String name) throws VehicleManagerException;
	
	UnlicensedVehicle getUnlicensedVehicle(String name) throws VehicleManagerException;
	
	void removeVehicle(String name);
	
	void createUnlicensedVehicle(String name,String brand,Date acquisitionDate) throws VehicleManagerException;
	
	void addRegistrationToVehicle(String name,long time,String description,Date date) throws VehicleManagerException;
	
	void addNoteToVehicle(String name,String note) throws VehicleManagerException;
	
}
