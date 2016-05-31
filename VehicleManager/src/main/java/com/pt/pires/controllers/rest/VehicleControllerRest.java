package com.pt.pires.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pt.pires.domain.Note;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.UnlicensedVehicle;
import com.pt.pires.domain.exceptions.InvalidVehicleBrandException;
import com.pt.pires.domain.exceptions.InvalidVehicleNameException;
import com.pt.pires.domain.exceptions.VehicleAlreadyExistException;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.services.VehicleService;

@RestController
public class VehicleControllerRest {
	
	@Autowired
	private VehicleService vehicleService;

	
	/* ================== REST Endpoints ==================== */
	
	@RequestMapping(value="/vehicle/unlicensed",method = RequestMethod.POST)
	public ResponseEntity<String> createUnlicensedVehicle(@RequestBody UnlicensedVehicle v){
		System.out.println("[Creating Unlicensed]");
		try {
			vehicleService.createUnlicensedVehicle(v.getName(), v.getBrand(), v.getAcquisitionDate());
		} catch (VehicleAlreadyExistException | 
				 InvalidVehicleBrandException | 
				 InvalidVehicleNameException e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/vehicle/{name}",method = RequestMethod.DELETE)
	public ResponseEntity<String> removeVehicle(@PathVariable String name){
		System.out.println("[Removing Vehicle] Vehicle: " + name);
		vehicleService.removeVehicle(name);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/vehicle/{name}/registration",method=RequestMethod.POST)
	public ResponseEntity<String> addRegistrationToVehicle(@PathVariable String name
			,@RequestBody Registration reg){
		System.out.println("[Adding Registration] Vehicle: " + name);
		try {
			vehicleService.addRegistrationToVehicle(name, reg.getTime(), reg.getDescription(), reg.getDate());
		} catch (VehicleDoesntExistException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		System.out.println("hmmmmmmmmmmmmmm");
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/vehicle/{name}/note",method=RequestMethod.POST)
	public ResponseEntity<String> addNoteToVehicle(@PathVariable String name,
			@RequestBody Note note){
		System.out.println("[Adding Note] Vehicle: " + name);
		try{
			vehicleService.addNoteToVehicle(name, note.getDescription());
		}catch (VehicleDoesntExistException e){
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
