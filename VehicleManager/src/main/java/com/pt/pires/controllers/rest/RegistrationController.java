package com.pt.pires.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pt.pires.controllers.ControllerExceptionHandler;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.local.IRegistrationService;

/**
 * Rest endpoints for registration services
 * @author André
 *
 */
@RestController
public class RegistrationController extends ControllerExceptionHandler {

	@Autowired
	@Qualifier("registrationService")
	private IRegistrationService registrationService;
	
	
	/**
	 * Add a registration to a vehicle
	 * @param vehicleName Name of the vehicle
	 * @param reg Registration object (from JSON) from HTTP Post
	 * @return Id of the added registration
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/{vehicleName}/registration",method = RequestMethod.POST)
	public ResponseEntity<Long> createRegistration(@PathVariable String vehicleName
			,@RequestBody Registration reg) throws VehicleManagerException {
		System.out.println("[Adding Registration] Vehicle: " + vehicleName);
		Long regId = registrationService.createRegistration(vehicleName, reg.getTime(), reg.getDescription(), reg.getDate());
		return new ResponseEntity<Long>(regId,HttpStatus.OK);
	}
	
	/**
	 * Remove a registration from a vehicle <b>if the registration exist</b>
	 * @param vehicleName Name of the vehicle
	 * @param regId Registration id
	 * @return
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/{vehicleName}/registration/{regId}",method = RequestMethod.DELETE)
	public ResponseEntity<String> removeRegistration(@PathVariable String vehicleName
			,@PathVariable long regId) throws VehicleManagerException {
		System.out.println("[Adding Registration] Vehicle: " + vehicleName);
		registrationService.removeRegistration(vehicleName, regId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
