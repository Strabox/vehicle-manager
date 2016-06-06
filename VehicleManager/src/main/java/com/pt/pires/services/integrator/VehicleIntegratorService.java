package com.pt.pires.services.integrator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pt.pires.domain.exceptions.ImpossibleSaveFileException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.external.IFileService;
import com.pt.pires.services.local.IVehicleService;

@Service
public class VehicleIntegratorService implements IVehicleIntegratorService{

	@Autowired
	@Qualifier("vehicleService")
	private IVehicleService localVehicleService;
	
	@Autowired
	@Qualifier("fileService")
	private IFileService fileService;
	
	@Override
	public void createUnlicensedVehicle(String vehicleName, String brand, Date acquisitionDate,byte[] image)
			throws VehicleManagerException {
		localVehicleService.createUnlicensedVehicle(vehicleName, brand, acquisitionDate);
		try{
			fileService.addPortraitImage(vehicleName, image);
		}catch(VehicleManagerException e){
			localVehicleService.removeVehicle(vehicleName);
			throw new ImpossibleSaveFileException();
		}
	}

}
