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
import com.pt.pires.domain.NotificationTaskHalfYear;
import com.pt.pires.domain.NotificationTaskOneTime;
import com.pt.pires.domain.NotificationTaskYear;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.VehicleUnlicensed;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.InvalidLicenseException;
import com.pt.pires.domain.exceptions.InvalidNoteException;
import com.pt.pires.domain.exceptions.InvalidNotificationException;
import com.pt.pires.domain.exceptions.InvalidRegistrationException;
import com.pt.pires.domain.exceptions.InvalidRegistrationTimeException;
import com.pt.pires.domain.exceptions.InvalidVehicleBrandException;
import com.pt.pires.domain.exceptions.InvalidVehicleNameException;
import com.pt.pires.domain.exceptions.LicenseAlreadyExistException;
import com.pt.pires.domain.exceptions.VehicleAlreadyExistException;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.persistence.LicenseRepository;
import com.pt.pires.persistence.LicensedVehicleRepository;
import com.pt.pires.persistence.NoteRepository;
import com.pt.pires.persistence.NotificationRepository;
import com.pt.pires.persistence.RegistrationRepository;
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
	private RegistrationRepository registrationRepository;
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private NotificationRepository notiRepository;
	
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
		Collection<VehicleUnlicensed> vs =(Collection<VehicleUnlicensed>) unlicensedRepository.findAll();
		if(vs == null)
			vs = new ArrayList<VehicleUnlicensed>();
		return vs;
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
		VehicleLicensed v = licensedRepository.findOne(vehicleName);
		if(v == null)
			throw new VehicleDoesntExistException();
		return v;
	}
	
	@Override
	@Transactional(readOnly = true)
	public VehicleUnlicensed getUnlicensedVehicle(String vehicleName) throws VehicleDoesntExistException{
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		VehicleUnlicensed v = unlicensedRepository.findOne(vehicleName);
		if(v == null) {
			throw new VehicleDoesntExistException();
		}
		return v;
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
	public void createUnlicensedVehicle(String vehicleName,String brand,Date acquisitionDate) 
			throws VehicleAlreadyExistException, InvalidVehicleNameException, 
			InvalidVehicleBrandException {
		if(vehicleName == null || brand == null || acquisitionDate == null) {
			throw new IllegalArgumentException();
		}
		if(vehicleRepository.exists(vehicleName)){
			throw new VehicleAlreadyExistException();
		}		
		VehicleUnlicensed v = new VehicleUnlicensed(vehicleName, brand, acquisitionDate);
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
	@Transactional(readOnly = false)
	public Long addRegistrationToVehicle(String vehicleName,long time,String description,Date date) 
			throws VehicleDoesntExistException, InvalidRegistrationException, InvalidRegistrationTimeException {
		if(vehicleName == null || description == null || date == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = obtainVehicle(vehicleName);
		Registration reg = registrationRepository.save(new Registration(time,description,date)); 
		v.addRegistration(reg);
		return reg.getId();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<Note> getVehicleNotes(String vehicleName) throws VehicleManagerException {
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = obtainVehicle(vehicleName);
		return v.getNotes();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<Registration> getVehicleRegistrations(String vehicleName) throws VehicleManagerException {
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = obtainVehicle(vehicleName);
		return v.getRegistries();
	}
	
	@Override
	@Transactional(readOnly = false)
	public Long addNoteToVehicle(String vehicleName,String note) 
			throws VehicleDoesntExistException, InvalidNoteException{
		if(vehicleName == null || note == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = obtainVehicle(vehicleName);
		Note noteObj = noteRepository.save(new Note(note));
		v.addNote(noteObj);
		return noteObj.getId();
	}
	
	@Override
	@Transactional(readOnly = false)
	public void removeRegistrationFromVehicle(String vehicleName, long regId) 
			throws VehicleDoesntExistException {
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = obtainVehicle(vehicleName);
		v.removeRegistration(regId);
	}

	@Override
	@Transactional(readOnly = false)
	public void removeNoteFromVehicle(String vehicleName, long noteId)
			throws VehicleDoesntExistException {
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = obtainVehicle(vehicleName);
		v.removeNote(noteId);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Long addYearNotification(String vehicleName,String notiDescription,Date initDate)
			throws VehicleDoesntExistException, InvalidNotificationException {
		if(vehicleName == null || notiDescription == null || initDate == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = obtainVehicle(vehicleName);
		NotificationTaskYear newNotif = new NotificationTaskYear(initDate,notiDescription);
		newNotif.setVehicle(v);
		NotificationTaskYear noti = notiRepository.save(newNotif);
		v.addNotification(noti);
		vehicleRepository.save(v);
		return noti.getId();
	}
	
	@Override
	@Transactional(readOnly = false)
	public Long addHalfYearNotification(String vehicleName,String notiDescription,Date initDate)
			throws VehicleDoesntExistException, InvalidNotificationException {
		if(vehicleName == null || notiDescription == null || initDate == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = obtainVehicle(vehicleName);
		NotificationTaskHalfYear newNotif = new NotificationTaskHalfYear(initDate,notiDescription);
		newNotif.setVehicle(v);
		NotificationTaskHalfYear noti = notiRepository.save(newNotif);
		v.addNotification(noti);
		vehicleRepository.save(v);
		return noti.getId();
	}
	
	@Override
	public Long addOneTimeNotification(String vehicleName, String description, Date initDate)
			throws VehicleManagerException {
		if(vehicleName == null || description == null || initDate == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = obtainVehicle(vehicleName);
		NotificationTaskOneTime newNotif = new NotificationTaskOneTime(initDate,description);
		newNotif.setVehicle(v);
		newNotif = notiRepository.save(newNotif);
		v.addNotification(newNotif);
		vehicleRepository.save(v);
		return newNotif.getId();
	}
	
	@Override
	@Transactional(readOnly = false)
	public void removeNotificationFromVehicle(String vehicleName, long alertId) throws VehicleManagerException {
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = obtainVehicle(vehicleName);
		v.removeNotification(alertId);
	}
	
	/* ========================== Private Auxiliary Methods ========================== */
	
	private Vehicle obtainVehicle(String name) throws VehicleDoesntExistException {
		Vehicle v = vehicleRepository.findOne(name);
		if(v == null) {
			throw new VehicleDoesntExistException();
		}
		return v;
	}
	
}
