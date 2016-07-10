package com.pt.pires.services.local;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.domain.License;
import com.pt.pires.domain.VehicleLicensed;
import com.pt.pires.domain.VehicleUnlicensed;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.InvalidImageFormatException;
import com.pt.pires.domain.exceptions.InvalidLicenseException;
import com.pt.pires.domain.exceptions.InvalidVehicleBrandException;
import com.pt.pires.domain.exceptions.InvalidVehicleNameException;
import com.pt.pires.domain.exceptions.LicenseAlreadyExistException;
import com.pt.pires.domain.exceptions.VehicleAlreadyExistException;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.persistence.LicenseRepository;
import com.pt.pires.persistence.LicensedVehicleRepository;
import com.pt.pires.persistence.UnlicensedVehicleRepository;
import com.pt.pires.persistence.VehicleRepository;

@Service("vehicleService")
public class VehicleService implements IVehicleService {

	@Autowired
	private UnlicensedVehicleRepository unlicensedRepository;
	
	@Autowired
	private LicensedVehicleRepository licensedRepository;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private LicenseRepository licenseRepository;
	
	
	@Override
	@Transactional(readOnly = true)
	public Collection<VehicleLicensed> getLicensedVehicles() {
		Collection<VehicleLicensed> vs = (Collection<VehicleLicensed>) licensedRepository.findAll();
		if(vs == null)
			vs = new ArrayList<VehicleLicensed>();
		return vs;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<VehicleUnlicensed> getUnlicensedVehicles() {
		Collection<VehicleUnlicensed> vs = (Collection<VehicleUnlicensed>) unlicensedRepository.findAll();
		if(vs == null)
			vs = new ArrayList<VehicleUnlicensed>();
		return vs;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<Vehicle> getAllVehicles() {
		Collection<Vehicle> vehicles = (Collection<Vehicle>) vehicleRepository.findAll();
		if(vehicles == null) {
			vehicles = new ArrayList<Vehicle>();
		}
		return vehicles;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Vehicle getVehicle(String vehicleName) throws VehicleDoesntExistException{
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		return obtainVehicle(vehicleName);
	}
	
	@Override
	@Transactional(readOnly = true)
	public VehicleLicensed getLicensedVehicle(String vehicleName) throws VehicleDoesntExistException {
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		return obtainVehicleLicensed(vehicleName);
	}
	
	@Override
	@Transactional(readOnly = true)
	public VehicleUnlicensed getUnlicensedVehicle(String vehicleName) throws VehicleDoesntExistException{
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		return obtainVehicleUnlicensed(vehicleName);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void removeVehicle(String vehicleName){
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		if(vehicleRepository.exists(vehicleName))
			vehicleRepository.delete(vehicleName);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void createLicensedVehicle(String vehicleName, String brand, Date acquisitionDate,
			String license, Date licenseDate) throws VehicleAlreadyExistException,
			InvalidVehicleNameException, InvalidVehicleBrandException, InvalidLicenseException,
			LicenseAlreadyExistException {
		if(vehicleName == null || brand == null || acquisitionDate == null || license == null
				|| licenseDate == null) {
			throw new IllegalArgumentException();
		}
		if(vehicleRepository.exists(vehicleName)){
			throw new VehicleAlreadyExistException();
		}
		else if(licenseRepository.exists(license)){
			throw new LicenseAlreadyExistException();
		}
		License licenseObject = new License(license, licenseDate);
		VehicleLicensed v = new VehicleLicensed(vehicleName, brand, acquisitionDate, licenseObject);
		vehicleRepository.save(v);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void createUnlicensedVehicle(String vehicleName,String brand,Date acquisitionDate,int fabricationYear) 
			throws VehicleAlreadyExistException, InvalidVehicleNameException, 
			InvalidVehicleBrandException, InvalidImageFormatException {
		if(vehicleName == null || brand == null || acquisitionDate == null) {
			throw new IllegalArgumentException();
		}
		if(vehicleRepository.exists(vehicleName)){
			throw new VehicleAlreadyExistException();
		}		
		VehicleUnlicensed v = new VehicleUnlicensed(vehicleName, brand, acquisitionDate, fabricationYear);
		vehicleRepository.save(v);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean vehicleExist(String vehicleName) {
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		return vehicleRepository.exists(vehicleName);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int calculateVehicleAcquisitionYears(String vehicleName) throws VehicleManagerException {
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = obtainVehicle(vehicleName);
		return v.calculateAcquisitionYears();
	}

	@Override
	@Transactional(readOnly = true)
	public int calculateVehicleLicensedYears(String vehicleName) throws VehicleManagerException {
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		VehicleLicensed v = obtainVehicleLicensed(vehicleName);
		return v.getLicense().calculateLicenseYears();
	}

	@Override
	@Transactional(readOnly = true)
	public int calculateVehicleUnlicensedYears(String vehicleName) throws VehicleManagerException {
		VehicleUnlicensed v = obtainVehicleUnlicensed(vehicleName);
		return v.calculateAge();
	}
	
	@Override
	@Transactional(readOnly = false)
	public void changeVehicleLicensedData(String vehicleName, String newBrand,
			Date newAcquisitionDate, String newLicense, Date newLicenseDate) throws VehicleManagerException {
		if(vehicleName == null || newBrand == null ||
				newAcquisitionDate == null || newLicense == null || newLicenseDate == null) {
			throw new IllegalArgumentException();
		}
		VehicleLicensed v = obtainVehicleLicensed(vehicleName);
		v.setBrand(newBrand);
		v.setAcquisitionDate(newAcquisitionDate);
		if(!newLicense.equals(v.getLicense().getLicense())) {
			v.setLicense(new License(newLicense,newLicenseDate));
		} else {
			License currentLicense = v.getLicense();
			currentLicense.setDate(newLicenseDate);
			v.setLicense(currentLicense);
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public void changeVehicleUnlicensedData(String vehicleName, String newBrand,
			Date newAcquisitionDate, int newFabricationYear) throws VehicleManagerException {
		if(vehicleName == null || newBrand == null || newAcquisitionDate == null) {
			throw new IllegalArgumentException();
		}
		VehicleUnlicensed v = obtainVehicleUnlicensed(vehicleName);
		v.setBrand(newBrand);
		v.setAcquisitionDate(newAcquisitionDate);
		v.setFabricationYear(newFabricationYear);
	}
	
	/* ========================== Private Auxiliary Methods ========================== */
	
	private Vehicle obtainVehicle(String vehicleName) throws VehicleDoesntExistException {
		Vehicle v = vehicleRepository.findOne(vehicleName);
		if(v == null) {
			throw new VehicleDoesntExistException();
		}
		return v;
	}
	
	private VehicleLicensed obtainVehicleLicensed(String vehicleName) throws VehicleDoesntExistException {
		VehicleLicensed v = licensedRepository.findOne(vehicleName);
		if(v == null) {
			throw new VehicleDoesntExistException();
		}
		return v;
	}
	
	private VehicleUnlicensed obtainVehicleUnlicensed(String vehicleName) throws VehicleDoesntExistException {
		VehicleUnlicensed v = unlicensedRepository.findOne(vehicleName);
		if(v == null) {
			throw new VehicleDoesntExistException();
		}
		return v;
	}
	
}
