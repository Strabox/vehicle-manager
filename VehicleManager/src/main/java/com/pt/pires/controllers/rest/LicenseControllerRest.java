package com.pt.pires.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pt.pires.services.local.LicenseService;

/**
 * Rest controller for license
 * @author André
 *
 */
@RestController
public class LicenseControllerRest {

	@Autowired
	private LicenseService licenseService;
	
	/**
	 * Verify if a given license is valid
	 * @param license Given license
	 * @return 200 (OK) if valid 4XX (NOT OK) otherwise
	 */
	@RequestMapping(value = "/license",method = RequestMethod.GET,params = {"license"})
	public ResponseEntity<String> validateLicense(@RequestParam(value = "license", required = true) String license){
		System.out.println("[Validating License] " + license);
		if(!licenseService.validateLicense(license))
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
