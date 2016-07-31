package com.pt.pires.controllers.rest;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pt.pires.controllers.ControllerExceptionHandler;
import com.pt.pires.domain.Note;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.local.INoteService;

/**
 * Rest endpoints for note services
 * @author André
 *
 */
@RestController
public class NoteControllerRest extends ControllerExceptionHandler {

	@Inject
	@Named("noteService") 
	private INoteService noteService;
	
	
	public NoteControllerRest(INoteService ns) { 
		noteService = ns;
	}
	
	public NoteControllerRest() { }
	
	/**
	 * Add a note to a vehicle
	 * @param vehicleName Name of the vehicle
	 * @param note Note object (From JSON) from HTTP Post
	 * @return
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/{vehicleName}/note",method = RequestMethod.POST)
	public ResponseEntity<Long> addNoteToVehicle(@PathVariable String vehicleName,
			@RequestBody Note note) throws VehicleManagerException {
		System.out.println("[Add Note] Vehicle: " + vehicleName);
		Long noteId = noteService.createNote(vehicleName, note.getDescription());
		return new ResponseEntity<Long>(noteId,HttpStatus.OK);
	}
	
	/**
	 * Remove a note from a vehicle <b>if already exist</b>
	 * @param vehicleName Name of the vehicle
	 * @param noteId Note id
	 * @return
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/{vehicleName}/note/{noteId}",method = RequestMethod.DELETE)
	public ResponseEntity<String> removeNoteFromVehicle(@PathVariable String vehicleName,
			@PathVariable long noteId) throws VehicleManagerException {
		System.out.println("[Remove Note] Vehicle: " + vehicleName);
		noteService.removeNote(vehicleName, noteId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
