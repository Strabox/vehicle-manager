package com.pt.pires.services.local;

import java.util.Collection;

import com.pt.pires.domain.Note;
import com.pt.pires.domain.exceptions.VehicleManagerException;

/**
 * Services related to notes
 * @author André
 *
 */
public interface INoteService {

	Long createNote(String vehicleName,String note) throws VehicleManagerException;
	
	Collection<Note> getVehicleNotes(String vehicleName) throws VehicleManagerException;
	
	void removeNote(String vehicleName,long noteId) throws VehicleManagerException;
	
}
