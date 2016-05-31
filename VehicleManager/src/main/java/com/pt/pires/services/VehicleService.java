package com.pt.pires.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pt.pires.domain.LicensedVehicle;
import com.pt.pires.domain.Note;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.UnlicensedVehicle;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.InvalidVehicleBrandException;
import com.pt.pires.domain.exceptions.InvalidVehicleNameException;
import com.pt.pires.domain.exceptions.VehicleAlreadyExistException;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.persistence.LicensedVehicleRepository;
import com.pt.pires.persistence.UnlicensedVehicleRepository;
import com.pt.pires.persistence.VehicleRepository;

@Service
public class VehicleService {

	@Autowired
	private UnlicensedVehicleRepository unlicensedRepository;
	
	@Autowired
	private LicensedVehicleRepository licensedRepository;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	public Collection<UnlicensedVehicle> getUnlicensedVehicles(){
		return (List<UnlicensedVehicle>) unlicensedRepository.findAll();
	}
	
	public Collection<LicensedVehicle> getLicensedVehicles(){
		return (List<LicensedVehicle>) licensedRepository.findAll();
	}
	
	public LicensedVehicle getLicensedVehicle(String name) throws VehicleDoesntExistException{
		LicensedVehicle v = licensedRepository.findOne(name);
		if(v == null)
			throw new VehicleDoesntExistException();
		return v;
	}
	
	public UnlicensedVehicle getUnlicensedVehicle(String name) throws VehicleDoesntExistException{
		UnlicensedVehicle v = unlicensedRepository.findOne(name);
		if(v == null){
			throw new VehicleDoesntExistException();
		}
		return v;
	}
	
	public void removeVehicle(String vehicleName){
		vehicleRepository.delete(vehicleName);
	}
	
	@Transactional
	public void createUnlicensedVehicle(String name,String brand,Date acquisitionDate) 
			throws VehicleAlreadyExistException, InvalidVehicleNameException, 
			InvalidVehicleBrandException{
		if(vehicleRepository.exists(name)){
			throw new VehicleAlreadyExistException();
		}
		else if(name.isEmpty()){
			throw new InvalidVehicleNameException();
		}
		else if(brand.isEmpty()){
			throw new InvalidVehicleBrandException();
		}
		UnlicensedVehicle v = new UnlicensedVehicle(name, brand, acquisitionDate);
		vehicleRepository.save(v);
	}
	
	@Transactional
	public void addRegistrationToVehicle(String name,long time,String description,Date date) 
			throws VehicleDoesntExistException{
		Vehicle v = getVehicle(name);
		v.addRegistration(new Registration(time,description,date));
		vehicleRepository.save(v);
	}
	
	@Transactional
	public void addNoteToVehicle(String name,String note) throws VehicleDoesntExistException{
		Vehicle v = getVehicle(name);
		v.addNote(new Note(note));
		vehicleRepository.save(v);
	}
	
	/* ============================ Private Methods ========================== */
	
	private Vehicle getVehicle(String name) throws VehicleDoesntExistException{
		Vehicle v;
		v = vehicleRepository.findOne(name);
		if(v == null)
			throw new VehicleDoesntExistException();
		return v;
	}
}
