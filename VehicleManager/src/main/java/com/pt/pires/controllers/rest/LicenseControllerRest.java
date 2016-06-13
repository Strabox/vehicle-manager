package com.pt.pires.controllers.rest;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pt.pires.services.local.LicenseService;

@RestController
public class LicenseControllerRest {

	@Autowired
	private LicenseService licenseService;
	
	@RequestMapping(value="/license",method = RequestMethod.GET,params = {"license"})
	public ResponseEntity<String> validateLicense(@PathParam("license") String license){
		if(!licenseService.validateLicense(license))
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
