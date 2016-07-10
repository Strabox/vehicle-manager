package com.pt.pires.services.external;

import com.pt.pires.domain.exceptions.VehicleManagerException;

/**
 * External service to manage file accesses 
 * @author André
 *
 */
public interface IFileService {

	void addOrReplaceVehiclePortraitImage(String vehicleName,byte[] imageBytes) throws VehicleManagerException;
	
	byte[] getVehiclePortraitImage(String vehicleName) throws VehicleManagerException;
	
	void addFileToVehicle(String vehicleName,String fileName,byte[] file) throws VehicleManagerException;
	
	void removeVehicleFiles(String vehicleName) throws VehicleManagerException;
	
}
