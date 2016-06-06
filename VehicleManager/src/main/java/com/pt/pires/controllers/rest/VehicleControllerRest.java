package com.pt.pires.controllers.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.pt.pires.domain.LicensedVehicle;
import com.pt.pires.domain.Note;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.UnlicensedVehicle;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.integrator.IVehicleIntegratorService;
import com.pt.pires.services.local.IVehicleService;

@RestController
public class VehicleControllerRest {
	
	@Autowired
	@Qualifier("vehicleService") 
	private IVehicleService vehicleService;

	@Autowired
	@Qualifier("vehicleIntegratorService")
	private IVehicleIntegratorService vehicleIntegratorService;
	
	/* ================== REST Endpoints ==================== */
	
	@RequestMapping(value="/vehicle/unlicensed",method = RequestMethod.POST,consumes = {"multipart/form-data"})
	@ResponseBody
	public ResponseEntity<String> createUnlicensedVehicle(@RequestPart("vehicle")UnlicensedVehicle v,
			@RequestPart("file") MultipartFile file){
		System.out.println("[Creating Unlicensed]");
		try {
			vehicleIntegratorService.createUnlicensedVehicle(v.getName(), v.getBrand(), v.getAcquisitionDate(), file.getBytes());
		} catch (VehicleManagerException | IOException e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("a",HttpStatus.OK);
	}
	
	@RequestMapping(value="/vehicle/licensed",method = RequestMethod.POST,consumes = {"multipart/form-data"})
	@ResponseBody
	public ResponseEntity<String> createLicensedVehicle(@RequestBody LicensedVehicle v,
			@RequestParam("file") MultipartFile file){
		System.out.println("[Creating Licensed");
		try {
			vehicleService.createLicensedVehicle(v.getName(), v.getBrand(), v.getAcquisitionDate(),
					v.getLicense().getLicense(), v.getLicense().getDate());
		}catch (VehicleManagerException e){
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
		} catch (VehicleManagerException e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
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
		} catch (VehicleManagerException e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/* ================ Auxiliary Method ================= */
	
}
