package com.pt.pires.services.local;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.domain.License;
import com.pt.pires.domain.VehicleLicensed;
import com.pt.pires.domain.Note;
import com.pt.pires.domain.NotificationHalfYear;
import com.pt.pires.domain.NotificationYear;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.VehicleUnlicensed;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.InvalidLicenseException;
import com.pt.pires.domain.exceptions.InvalidNoteException;
import com.pt.pires.domain.exceptions.InvalidRegistrationException;
import com.pt.pires.domain.exceptions.InvalidVehicleBrandException;
import com.pt.pires.domain.exceptions.InvalidVehicleNameException;
import com.pt.pires.domain.exceptions.VehicleAlreadyExistException;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.persistence.LicensedVehicleRepository;
import com.pt.pires.persistence.NoteRepository;
import com.pt.pires.persistence.NotificationRepository;
import com.pt.pires.persistence.RegistrationRepository;
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
	
	@Autowired
	private RegistrationRepository registrationRepository;
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private NotificationRepository notiRepository;
	
	
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
		Collection<VehicleUnlicensed> vs =(Collection<VehicleUnlicensed>) unlicensedRepository.findAll();
		if(vs == null)
			vs = new ArrayList<VehicleUnlicensed>();
		return vs;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Vehicle getVehicle(String vehicleName) throws VehicleDoesntExistException{
		return obtainVehicle(vehicleName);
	}
	
	@Override
	@Transactional(readOnly = true)
	public VehicleLicensed getLicensedVehicle(String name) throws VehicleDoesntExistException{
		VehicleLicensed v = licensedRepository.findOne(name);
		if(v == null)
			throw new VehicleDoesntExistException();
		return v;
	}
	
	@Override
	@Transactional(readOnly = true)
	public VehicleUnlicensed getUnlicensedVehicle(String name) throws VehicleDoesntExistException{
		VehicleUnlicensed v = unlicensedRepository.findOne(name);
		if(v == null){
			throw new VehicleDoesntExistException();
		}
		return v;
	}
	
	@Override
	@Transactional(readOnly = false)
	public void removeVehicle(String vehicleName){
		if(vehicleRepository.exists(vehicleName))
			vehicleRepository.delete(vehicleName);
	}
	
	@Override
	@Transactional(readOnly = false)
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
		VehicleLicensed v = new VehicleLicensed(vehicleName, brand, acquisitionDate, licenseo);
		vehicleRepository.save(v);
	}
	
	@Override
	@Transactional(readOnly = false)
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
		VehicleUnlicensed v = new VehicleUnlicensed(vehicleName, brand, acquisitionDate);
		vehicleRepository.save(v);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean vehicleExist(String vehicleName) {
		return vehicleRepository.exists(vehicleName);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Long addRegistrationToVehicle(String name,long time,String description,Date date) 
			throws VehicleDoesntExistException, InvalidRegistrationException{
		if(description.isEmpty()){
			throw new InvalidRegistrationException();
		}
		Vehicle v = obtainVehicle(name);
		Registration reg = registrationRepository.save(new Registration(time,description,date)); 
		v.addRegistration(reg);
		return reg.getId();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<Note> getVehicleNotes(String vehicleName) throws VehicleManagerException {
		Vehicle v = obtainVehicle(vehicleName);
		return v.getNotes();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<Registration> getVehicleRegistrations(String vehicleName) throws VehicleManagerException {
		Vehicle v = obtainVehicle(vehicleName);
		return v.getRegistries();
	}
	
	@Override
	@Transactional(readOnly = false)
	public Long addNoteToVehicle(String vehicleName,String note) 
			throws VehicleDoesntExistException, InvalidNoteException{
		if(note.isEmpty()){
			throw new InvalidNoteException();
		}
		Vehicle v = obtainVehicle(vehicleName);
		Note noteObj = noteRepository.save(new Note(note));
		v.addNote(noteObj);
		return noteObj.getId();
	}
	
	@Override
	@Transactional(readOnly = false)
	public void removeRegistrationFromVehicle(String vehicleName, long regId) 
			throws VehicleDoesntExistException{
		Vehicle v = obtainVehicle(vehicleName);
		v.removeRegistration(regId);
	}

	@Override
	@Transactional(readOnly = false)
	public void removeNoteFromVehicle(String vehicleName, long noteId)
			throws VehicleDoesntExistException{
		Vehicle v = obtainVehicle(vehicleName);
		v.removeNote(noteId);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Long addYearNotification(String vehicleName,String notiDescription,Date initDate)
			throws VehicleDoesntExistException{
		Vehicle v = obtainVehicle(vehicleName);
		NotificationYear noti = notiRepository.save(new NotificationYear(initDate,notiDescription));
		v.addNotification(noti);
		return noti.getId();
	}
	
	@Override
	@Transactional(readOnly = false)
	public Long addHalfYearNotification(String vehicleName,String notiDescription,Date initDate)
			throws VehicleDoesntExistException{
		Vehicle v = obtainVehicle(vehicleName);
		NotificationHalfYear noti = notiRepository.save(new NotificationHalfYear(initDate,notiDescription));
		v.addNotification(noti);
		return noti.getId();
	}
	
	@Override
	@Transactional(readOnly = false)
	public void removeAlertFromVehicle(String vehicleName, long alertId) throws VehicleManagerException {
		Vehicle v = obtainVehicle(vehicleName);
		v.removeNotification(alertId);
	}
	
	/* ============================ Private Methods ========================== */
	
	private Vehicle obtainVehicle(String name) throws VehicleDoesntExistException{
		Vehicle v = vehicleRepository.findOne(name);
		if(v == null)
			throw new VehicleDoesntExistException();
		return v;
	}
	
}
