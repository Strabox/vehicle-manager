package com.pt.pires.service.local;

import java.util.Date;
import java.util.List;

import org.junit.Before;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pt.pires.VehicleManagerApplication;
import com.pt.pires.domain.License;
import com.pt.pires.domain.VehicleLicensed;
import com.pt.pires.domain.Note;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.VehicleUnlicensed;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.persistence.LicensedVehicleRepository;
import com.pt.pires.persistence.NoteRepository;
import com.pt.pires.persistence.RegistrationRepository;
import com.pt.pires.persistence.UnlicensedVehicleRepository;
import com.pt.pires.persistence.VehicleRepository;

/**
 * 
 * @author André
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
public abstract class VehicleManagerServiceTest {
	
	@Autowired
	private VehicleRepository vehicleRepository; 
	
	@Autowired
	private UnlicensedVehicleRepository unlicensedRepository;
	
	@Autowired
	private LicensedVehicleRepository licensedRepository;
	
	@Autowired
	private RegistrationRepository regRepository;
	
	@Autowired
	private NoteRepository noteRepository;
	
	/**
	 * Executed before each test.
	 * @throws VehicleManagerException
	 */
	@Before
	public void beforeTest() throws VehicleManagerException {
		populate();
	}
	
	
	public abstract void populate() throws VehicleManagerException;
	
	/* ================= Auxiliary test methods =============== */
	
	protected void newUnlicensedVehicle(String name,String brand,Date acquisitionDate){
		unlicensedRepository.save(new VehicleUnlicensed(name, brand, acquisitionDate));
	}
	
	protected void newLicensedVehicle(String name,String brand,Date acquisitionDate,
			String license,Date licenseDate) throws VehicleManagerException {
		License licenseO = new License(license,licenseDate);
		licensedRepository.save(new VehicleLicensed(name, brand, acquisitionDate, licenseO));
	}
	
	protected Vehicle obtainVehicle(String name){
		return vehicleRepository.findOne(name);
	}
	
	protected VehicleUnlicensed obtainUnlicensedVehicle(String name){
		return unlicensedRepository.findOne(name);
	}
	
	protected VehicleLicensed obtainLicensedVehicle(String name){
		return licensedRepository.findOne(name);
	}
	
	protected void deleteVehicle(String name){
		if(vehicleRepository.exists(name))
			vehicleRepository.delete(name);
	}
	
	protected Long newRegistration(String vehicleName,long time,String description,Date date){
		if(vehicleRepository.exists(vehicleName)){
			Vehicle v = vehicleRepository.findOne(vehicleName);
			Registration reg = regRepository.save(new Registration(time,description,date));
			v.addRegistration(reg);
			vehicleRepository.save(v);
			return reg.getId();
			
		}
		return null;
	}
	
	protected Long newNote(String vehicleName,String noteDescription){
		if(vehicleRepository.exists(vehicleName)){
			Vehicle v = vehicleRepository.findOne(vehicleName);
			Note note = noteRepository.save(new Note(noteDescription));
			v.addNote(note);
			vehicleRepository.save(v);
			return note.getId();
		}
		return null;
	}
	
	protected Registration obtainRegistration(String vehicleName,Long regId){
		List<Registration> regs = (List<Registration>) vehicleRepository.findOne(vehicleName).getRegistries();
		for(Registration reg : regs){
			if(reg.getId() == regId){
				return reg;
			}
		}
		return null;
	}
	
	protected Note obtainNote(String vehicleName,Long noteId){
		List<Note> notes = (List<Note>) vehicleRepository.findOne(vehicleName).getNotes();
		for(Note note : notes){
			if(note.getId() == noteId){
				return note;
			}
		}
		return null;
	}
	
	protected List<Registration> obtainRegistrations(String vehicleName){
		if(vehicleRepository.exists(vehicleName)){
			return (List<Registration>) vehicleRepository.findOne(vehicleName).getRegistries();
		}
		return null;
	}
	
	protected List<Note> obtainNotes(String vehicleName){
		if(vehicleRepository.exists(vehicleName)){
			return (List<Note>) vehicleRepository.findOne(vehicleName).getNotes();
		}
		return null;
	}
	
}
