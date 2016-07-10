package com.pt.pires.services.local;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.domain.Note;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.persistence.NoteRepository;

@Service("noteService")
public class NoteService implements INoteService{

	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	@Qualifier("vehicleService")
	private IVehicleService vehicleService;
	
	
	@Override
	@Transactional(readOnly = false)
	public Long createNote(String vehicleName, String note) throws VehicleManagerException {
		if(vehicleName == null || note == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = vehicleService.getVehicle(vehicleName);
		Note noteObj = noteRepository.save(new Note(note));
		v.addNote(noteObj);
		return noteObj.getId();
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Note> getVehicleNotes(String vehicleName) throws VehicleManagerException {
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = vehicleService.getVehicle(vehicleName);
		return v.getNotes();
	}

	@Override
	@Transactional(readOnly = false)
	public void removeNote(String vehicleName, long noteId) throws VehicleManagerException {
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = vehicleService.getVehicle(vehicleName);
		v.removeNote(noteId);
	}

}
