package com.pt.pires.services.local;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.domain.Note;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.NoteDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.persistence.INoteRepository;

@Service
@Named("noteService")
public class NoteService implements INoteService {

	@Inject
	private INoteRepository noteRepository;
	
	@Inject
	@Named("vehicleService")
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

	@Override
	@Transactional(readOnly = false)
	public Note getNoteById(long noteId) throws VehicleManagerException {
		Note note = noteRepository.findOne(noteId);
		if(note == null) {
			throw new NoteDoesntExistException();
		}
		return note;
	}

}
