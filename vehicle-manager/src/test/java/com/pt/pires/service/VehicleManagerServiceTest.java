package com.pt.pires.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pt.pires.VehicleManagerApplication;
import com.pt.pires.domain.License;
import com.pt.pires.domain.Note;
import com.pt.pires.domain.NotificationTask;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.User;
import com.pt.pires.domain.UserRole;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.VehicleLicensed;
import com.pt.pires.domain.VehicleUnlicensed;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.persistence.ILicenseRepository;
import com.pt.pires.persistence.ILicensedVehicleRepository;
import com.pt.pires.persistence.INoteRepository;
import com.pt.pires.persistence.INotificationRepository;
import com.pt.pires.persistence.IRegistrationRepository;
import com.pt.pires.persistence.IUnlicensedVehicleRepository;
import com.pt.pires.persistence.IUserRepository;
import com.pt.pires.persistence.IVehicleRepository;
import com.pt.pires.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
public abstract class VehicleManagerServiceTest {

	protected static final String EMPTY_STRING = "";
	
	protected static final String VALID_VEHICLE_NAME = "Carro altamente válido";
	protected static final String VALID_VEHICLE_BRAND = "Mitsubishi";
	protected static final String VALID_LICENSE = "GP-13-99";
	protected static final int VALID_FABRICATION_YEAR = 1994;
	
	protected static final String VALID_DESCRIPTION = "Uma descrição altamente bem feita e válida !!!";
	protected static final Date VALID_CURRENT_DATE = DateUtil.getSimplifyDate(new Date());
	protected static final Long VALID_TIME = new Long(777);
	
	@Inject
	private IVehicleRepository vehicleRepository; 
	
	@Inject
	private IUnlicensedVehicleRepository unlicensedRepository;
	
	@Inject
	private ILicensedVehicleRepository licensedRepository;
	
	@Inject
	private IRegistrationRepository regRepository;
	
	@Inject
	private INotificationRepository notiRepository;
	
	@Inject
	private INoteRepository noteRepository;

	@Inject
	private ILicenseRepository licensesRepository;
	
	@Inject
	private IUserRepository userRepository;
	
	
	/**
	 * Populate the database before each test.
	 * @throws VehicleManagerException
	 */
	@Before
	public final void beforeTest() throws VehicleManagerException {
		populate();
	}
	
	
	public abstract void populate() throws VehicleManagerException;
	
	/* ================= Auxiliary User test methods =============== */
	
	protected void newUser(String username,String password,String email,UserRole role) throws VehicleManagerException {
		User user = new User(username,password,email,role);
		userRepository.save(user);
	}
	
	protected void removeUser(String username) {
		if(userRepository.exists(username)) {
			userRepository.delete(username);
		}
	}
	
	protected User obtainUser(String username) {
		return userRepository.findOne(username);
	}
	
	/* ================= Auxiliary Vehicle test methods =============== */
	
	protected void newUnlicensedVehicle(String name,String brand,Date acquisitionDate,int fabricationYear) 
		throws VehicleManagerException {
		unlicensedRepository.save(new VehicleUnlicensed(name, brand, acquisitionDate,fabricationYear));
	}
	
	protected void newLicensedVehicle(String name,String brand,Date acquisitionDate,
			String license,Date licenseDate) throws VehicleManagerException {
		License licenseO = new License(license,licenseDate);
		licensedRepository.save(new VehicleLicensed(name, brand, acquisitionDate, licenseO));
	}
	
	protected Vehicle obtainVehicle(String name) {
		return vehicleRepository.findOne(name);
	}
	
	protected VehicleUnlicensed obtainUnlicensedVehicle(String name) {
		return unlicensedRepository.findOne(name);
	}
	
	protected VehicleLicensed obtainLicensedVehicle(String name) {
		return licensedRepository.findOne(name);
	}
	
	protected void deleteVehicle(String name) {
		if(vehicleRepository.exists(name))
			vehicleRepository.delete(name);
	}
	
	protected Long newRegistration(String vehicleName,long time,String description,Date date) throws VehicleManagerException {
		if(vehicleRepository.exists(vehicleName)) {
			Vehicle v = vehicleRepository.findOne(vehicleName);
			Registration reg = regRepository.save(new Registration(time,description,date));
			v.addRegistration(reg);
			vehicleRepository.save(v);
			return reg.getId();
			
		}
		return null;
	}
	
	protected Long newNote(String vehicleName,String noteDescription) throws VehicleManagerException {
		if(vehicleRepository.exists(vehicleName)) {
			Vehicle v = vehicleRepository.findOne(vehicleName);
			Note note = noteRepository.save(new Note(noteDescription));
			v.addNote(note);
			vehicleRepository.save(v);
			return note.getId();
		}
		return null;
	}
	
	
	protected Long newNotification(String vehicleName,NotificationTask notification) {
		if(vehicleRepository.exists(vehicleName)) {
			Vehicle v = vehicleRepository.findOne(vehicleName);
			NotificationTask note = notification;
			note.setVehicle(v);
			note = notiRepository.save(note);
			v.addNotification(note);
			vehicleRepository.save(v);
			return note.getId();
		}
		return null;
	}
	
	protected void deleteNotification(Long id) {
		if(notiRepository.exists(id)) {
			notiRepository.delete(id);
		}
	}
	
	protected Registration obtainRegistration(String vehicleName,Long regId) {
		List<Registration> regs = (List<Registration>) vehicleRepository.findOne(vehicleName).getRegistries();
		for(Registration reg : regs) {
			if(reg.getId() == regId) {
				return reg;
			}
		}
		return null;
	}
	
	protected Note obtainNote(String vehicleName,Long noteId) {
		List<Note> notes = (List<Note>) vehicleRepository.findOne(vehicleName).getNotes();
		for(Note note : notes) {
			if(note.getId() == noteId) {
				return note;
			}
		}
		return null;
	}
	
	protected NotificationTask obtainNotification(String vehicleName,Long notiId) {
		List<NotificationTask> notis = (List<NotificationTask>) vehicleRepository.findOne(vehicleName).getNotifications();
		for(NotificationTask noti : notis) {
			if(noti.getId() == notiId) {
				return noti;
			}
		}
		return null;
	}
	
	protected List<Registration> obtainRegistrations(String vehicleName) {
		if(vehicleRepository.exists(vehicleName)) {
			return (List<Registration>) vehicleRepository.findOne(vehicleName).getRegistries();
		}
		return null;
	}
	
	protected List<Note> obtainNotes(String vehicleName) {
		if(vehicleRepository.exists(vehicleName)) {
			return (List<Note>) vehicleRepository.findOne(vehicleName).getNotes();
		}
		return null;
	}
	
	protected List<NotificationTask> obtainNotifications(String vehicleName) {
		if(vehicleRepository.exists(vehicleName)) {
			return (List<NotificationTask>) vehicleRepository.findOne(vehicleName).getNotifications();
		}
		return null;
	}
	
	protected boolean licenseExist(String license) {
		return licensesRepository.exists(license);
	}
	
}
