package com.pt.pires.services.local;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pt.pires.domain.License;
import com.pt.pires.domain.LicensedVehicle;
import com.pt.pires.domain.Note;
import com.pt.pires.domain.NotificationHalfYear;
import com.pt.pires.domain.NotificationYear;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.UnlicensedVehicle;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.InvalidLicenseException;
import com.pt.pires.domain.exceptions.InvalidNoteException;
import com.pt.pires.domain.exceptions.InvalidRegistrationException;
import com.pt.pires.domain.exceptions.InvalidVehicleBrandException;
import com.pt.pires.domain.exceptions.InvalidVehicleNameException;
import com.pt.pires.domain.exceptions.VehicleAlreadyExistException;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.persistence.LicensedVehicleRepository;
import com.pt.pires.persistence.UnlicensedVehicleRepository;
import com.pt.pires.persistence.VehicleRepository;

@Service
public class VehicleService implements IVehicleService{

	@Autowired
	private UnlicensedVehicleRepository unlicensedRepository;
	
	@Autowired
	private LicensedVehicleRepository licensedRepository;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	
	@Override
	@Transactional
	public Collection<LicensedVehicle> getLicensedVehicles() {
		Collection<LicensedVehicle> vs = (Collection<LicensedVehicle>) licensedRepository.findAll();
		if(vs == null)
			vs = new ArrayList<LicensedVehicle>();
		return vs;
	}
	
	@Override
	@Transactional
	public Collection<UnlicensedVehicle> getUnlicensedVehicles() {
		Collection<UnlicensedVehicle> vs =(Collection<UnlicensedVehicle>) unlicensedRepository.findAll();
		if(vs == null)
			vs = new ArrayList<UnlicensedVehicle>();
		return vs;
	}
	
	@Override
	@Transactional
	public LicensedVehicle getLicensedVehicle(String name) throws VehicleDoesntExistException{
		LicensedVehicle v = licensedRepository.findOne(name);
		if(v == null)
			throw new VehicleDoesntExistException();
		return v;
	}
	
	@Override
	@Transactional
	public UnlicensedVehicle getUnlicensedVehicle(String name) throws VehicleDoesntExistException{
		UnlicensedVehicle v = unlicensedRepository.findOne(name);
		if(v == null){
			throw new VehicleDoesntExistException();
		}
		return v;
	}
	
	@Override
	@Transactional
	public void removeVehicle(String vehicleName){
		if(vehicleRepository.exists(vehicleName))
			vehicleRepository.delete(vehicleName);
	}
	
	@Override
	@Transactional
	public void createLicensedVehicle(String vehicleName, String brand, Date acquisitionDate,
			String license, Date licenseDate) throws VehicleAlreadyExistException,
			InvalidVehicleNameException, InvalidVehicleBrandException, InvalidLicenseException {
		if(vehicleRepository.exists(vehicleName))
			throw new VehicleAlreadyExistException();
		else if(vehicleName.isEmpty()){
			throw new InvalidVehicleNameException();
		}
		else if(brand.isEmpty()){
			throw new InvalidVehicleBrandException();
		}
		License licenseo = new License(license, licenseDate);
		LicensedVehicle v = new LicensedVehicle(vehicleName, brand, acquisitionDate, licenseo);
		vehicleRepository.save(v);
	}
	
	@Override
	@Transactional
	public void createUnlicensedVehicle(String vehicleName,String brand,Date acquisitionDate) 
			throws VehicleAlreadyExistException, InvalidVehicleNameException, 
			InvalidVehicleBrandException{
		if(vehicleRepository.exists(vehicleName)){
			throw new VehicleAlreadyExistException();
		}
		else if(vehicleName.isEmpty()){
			throw new InvalidVehicleNameException();
		}
		else if(brand.isEmpty()){
			throw new InvalidVehicleBrandException();
		}
		UnlicensedVehicle v = new UnlicensedVehicle(vehicleName, brand, acquisitionDate);
		vehicleRepository.save(v);
	}
	
	@Override
	@Transactional
	public void addRegistrationToVehicle(String name,long time,String description,Date date) 
			throws VehicleDoesntExistException, InvalidRegistrationException{
		if(description.isEmpty()){
			throw new InvalidRegistrationException();
		}
		Vehicle v = getVehicle(name);
		v.addRegistration(new Registration(time,description,date));
		vehicleRepository.save(v);
	}
	
	@Override
	@Transactional
	public void addNoteToVehicle(String vehicleName,String note) 
			throws VehicleDoesntExistException, InvalidNoteException{
		if(note.isEmpty()){
			throw new InvalidNoteException();
		}
		Vehicle v = getVehicle(vehicleName);
		v.addNote(new Note(note));
		vehicleRepository.save(v);
	}
	
	@Transactional
	public void addYearNotification(String vehicleName,String notiDescription,Date initDate)
			throws VehicleDoesntExistException{
		Vehicle v = getVehicle(vehicleName);
		v.addNotification(new NotificationYear(initDate,notiDescription));
	}
	
	@Transactional
	public void addHalfYearNotification(String vehicleName,String notiDescription,Date initDate)
			throws VehicleDoesntExistException{
		Vehicle v = getVehicle(vehicleName);
		v.addNotification(new NotificationHalfYear(initDate, notiDescription));
	}
	
	/* ============================ Private Methods ========================== */
	
	private Vehicle getVehicle(String name) throws VehicleDoesntExistException{
		Vehicle v = vehicleRepository.findOne(name);
		if(v == null)
			throw new VehicleDoesntExistException();
		return v;
	}
}
