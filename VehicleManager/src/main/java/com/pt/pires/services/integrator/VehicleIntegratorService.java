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
public class VehicleIntegratorService implements IVehicleIntegratorService {

	@Autowired
	@Qualifier("vehicleService")
	private IVehicleService localVehicleService;
	
	@Autowired
	@Qualifier("fileService")
	private IFileService fileService;
	
	
	public VehicleIntegratorService(IVehicleService vs,IFileService fs) {
		this.localVehicleService = vs;
		this.fileService = fs;
	}
	
	public VehicleIntegratorService() { }
	
	@Override
	public void createUnlicensedVehicle(String vehicleName, String brand, Date acquisitionDate
			,int fabricationYear,boolean image, byte[] imageFile)throws VehicleManagerException {
		if(vehicleName == null || brand == null || acquisitionDate == null || (image && imageFile == null)) {
			throw new IllegalArgumentException();
		}
		localVehicleService.createUnlicensedVehicle(vehicleName, brand, acquisitionDate,fabricationYear);
		if(image) {
			try {
				fileService.addOrReplaceVehiclePortraitImage(vehicleName, imageFile);
			} catch(VehicleManagerException e) {
				localVehicleService.removeVehicle(vehicleName);
				throw new ImpossibleSaveFileException();
			}
		}
	}

	@Override
	public void createLicensedVehicle(String vehicleName, String brand, Date acquisitionDate,
			String license, Date licenseDate,boolean image, byte[] imageFile) throws VehicleManagerException {
		if(vehicleName == null || brand == null || acquisitionDate == null || licenseDate == null
				|| license == null || (image && imageFile == null)) {
			throw new IllegalArgumentException();
		}
		localVehicleService.createLicensedVehicle(vehicleName, brand, acquisitionDate, license, licenseDate);
		if(image) {
			try {
				fileService.addOrReplaceVehiclePortraitImage(vehicleName, imageFile);
			} catch(VehicleManagerException e) {
				localVehicleService.removeVehicle(vehicleName);
				throw new ImpossibleSaveFileException();
			}
		}
	}
	
	@Override
	public void removeVehicle(String vehicleName) throws VehicleManagerException {
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		localVehicleService.removeVehicle(vehicleName);
		try {
			fileService.removeVehicleFiles(vehicleName);
		} catch (VehicleManagerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addOrUpdateVehiclePortrait(String vehicleName, byte[] imageFile) throws VehicleManagerException {
		if(vehicleName == null || imageFile == null) {
			throw new IllegalArgumentException();
		}
		fileService.addOrReplaceVehiclePortraitImage(vehicleName, imageFile);
	}

	@Override
	public byte[] getVehiclePortraitImage(String vehicleName) throws VehicleManagerException {
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		return fileService.getVehiclePortraitImage(vehicleName);
	}

}
