package com.pt.pires.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pt.pires.services.VehicleService;

@RestController
public class VehicleControllerRest {
	
	@Autowired
	private VehicleService vehicle;

	@RequestMapping(value="/vehicle/{name}",method = RequestMethod.DELETE)
	public ResponseEntity<String> removeVehicle(@PathVariable String name){
		vehicle.removeVehicle(name);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
