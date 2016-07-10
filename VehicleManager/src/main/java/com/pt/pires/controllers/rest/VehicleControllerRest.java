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

import com.pt.pires.controllers.ControllerExceptionHandler;
import com.pt.pires.controllers.dtoUI.VehicleLicensedDTO;
import com.pt.pires.controllers.dtoUI.VehicleUnlicensedDTO;
import com.pt.pires.domain.VehicleLicensed;
import com.pt.pires.domain.VehicleUnlicensed;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.integrator.IVehicleIntegratorService;
import com.pt.pires.services.local.IVehicleService;

/**
 * Rest endpoints for vehicle services
 * @author André
 */
@RestController
public class VehicleControllerRest extends ControllerExceptionHandler {
	
	@Autowired
	@Qualifier("vehicleService") 
	private IVehicleService vehicleService;
	
	@Autowired
	@Qualifier("vehicleIntegratorService")
	private IVehicleIntegratorService vehicleIntegratorService;
	
	
	/**
	 * Create an UnlicensedVehicle
	 * @param vehicle UnlicensedVehicle Object (From JSON)
	 * @param file Portrait image file
	 * @return
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/unlicensed",method = RequestMethod.POST,consumes = {"multipart/form-data"})
	@ResponseBody
	public ResponseEntity<String> createUnlicensedVehicle(@RequestPart("vehicle")VehicleUnlicensed vehicle,
			@RequestPart(value = "file",required = false) MultipartFile file) throws VehicleManagerException {
		System.out.println("[Create Unlicensed Vehicle]");
		try{
			if(file == null) {
				vehicleIntegratorService.createUnlicensedVehicle(vehicle.getName(), vehicle.getBrand(),
						vehicle.getAcquisitionDate(),vehicle.getFabricationYear(),false, null);
			}
			else {
				vehicleIntegratorService.createUnlicensedVehicle(vehicle.getName(), vehicle.getBrand(),
						vehicle.getAcquisitionDate(),vehicle.getFabricationYear(),true, file.getBytes());
			}
		}catch(IOException e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * Create a LincensedVehicle
	 * @param vehicle LicensedVehicle Object (From JSON)
	 * @param file Portrait image file
	 * @return
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/licensed",method = RequestMethod.POST,consumes = {"multipart/form-data"})
	@ResponseBody
	public ResponseEntity<String> createLicensedVehicle(@RequestPart("vehicle") VehicleLicensed vehicle,
			@RequestPart(value = "file",required = false) MultipartFile file) throws VehicleManagerException {
		System.out.println("[Create Licensed]");
		try {
			if(file == null) {
				vehicleIntegratorService.createLicensedVehicle(
						vehicle.getName(), vehicle.getBrand(),
						vehicle.getAcquisitionDate(), vehicle.getLicense().getLicense(),
						vehicle.getLicense().getDate(),false,null);
			}else{
				vehicleIntegratorService.createLicensedVehicle(
						vehicle.getName(), vehicle.getBrand(),
						vehicle.getAcquisitionDate(), vehicle.getLicense().getLicense(),
						vehicle.getLicense().getDate(),true,file.getBytes());
			}
		} catch (IOException e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * Remove a vehicle if already exist
	 * @param vehicleName Name of the vehicle
	 * @return
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/{vehicleName}",method = RequestMethod.DELETE)
	public ResponseEntity<String> removeVehicle(@PathVariable String vehicleName) throws VehicleManagerException {
		System.out.println("[Remove Vehicle] Vehicle Name: " + vehicleName);
		vehicleIntegratorService.removeVehicle(vehicleName);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * Verify if vehicle exist (comparing vehicle names)
	 * @param vehicleName Name of the vehicle
	 * @return Http 200 OK doesnt exist, Http 40X already exist
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle",method = RequestMethod.GET,params = {"name"})
	public ResponseEntity<String> vehicleExist(@RequestParam("name")String vehicleName) throws VehicleManagerException {
		System.out.println("[Verify vehicle existence]");
		if(vehicleService.vehicleExist(vehicleName)) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}else {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
	}
	
	/**
	 * Change licensed vehicles data
	 * @param vehicleName Name of the vehicle
	 * @param v New licensed vehicle data (from JSON) from HTTP Post
	 * @return
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/licensed/{vehicleName}/edit", method = RequestMethod.POST)
	public ResponseEntity<String> changeVehicleLicensedData(@PathVariable String vehicleName,
			@RequestBody VehicleLicensedDTO v) throws VehicleManagerException {
		System.out.println("[Change vehicle licensed data] Vehicle Name: " + vehicleName);
		vehicleService.changeVehicleLicensedData(vehicleName, v.getBrand(),
				v.getAcquisitionDate(), v.getLicense(), v.getLicenseDate());
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * Change unlicensed vehicles data
	 * @param vehicleName Name of the vehicle
	 * @param v New unlicensed vehicle data (from JSON) from HTTP Post
	 * @return
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/unlicensed/{vehicleName}/edit", method = RequestMethod.POST)
	public ResponseEntity<String> changeVehicleUnlicensedData(@PathVariable String vehicleName,
			@RequestBody VehicleUnlicensedDTO v) throws VehicleManagerException {
		System.out.println("[Change vehicle unlicensed data] Vehicle Name: " + vehicleName);
		vehicleService.changeVehicleUnlicensedData(vehicleName, v.getBrand(),
				v.getAcquisitionDate(), v.getFabricationYear());
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * Get vehicle acquisition years
	 * @param vehicleName Name of the vehicle
	 * @return Vehicle acquisition years if vehicle exist
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/{vehicleName}/acquisitionyears", method = RequestMethod.GET)
	public ResponseEntity<String> getVehicleAcquisitonYears(@PathVariable String vehicleName) 
			throws VehicleManagerException {
		System.out.println("[Get acquisition years] Vehicle Name: " + vehicleName);
		int res = vehicleService.calculateVehicleAcquisitionYears(vehicleName);
		return new ResponseEntity<String>(res+"",HttpStatus.OK);
	}
	
	/**
	 * Get vehicle licensed years
	 * @param vehicleName Name of the vehicle
	 * @return Vehicle licensed years
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/{vehicleName}/licenseyears", method = RequestMethod.GET)
	public ResponseEntity<String> getVehicleLicensedYears(@PathVariable String vehicleName)
			throws VehicleManagerException {
		System.out.println("[Get license years] Vehicle Name: " + vehicleName);
		int res = vehicleService.calculateVehicleLicensedYears(vehicleName);
		return new ResponseEntity<String>(String.valueOf(res),HttpStatus.OK);
	}
	
	/**
	 * Get unlicensed vehicle years
	 * @param vehicleName Name of the vehicle
	 * @return
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/{vehicleName}/years", method = RequestMethod.GET)
	public ResponseEntity<String> getVehicleUnlicensedYears(@PathVariable String vehicleName) 
			throws VehicleManagerException {
		System.out.println("[Get unlicensed years] Vehicle Name: " + vehicleName);
		int res = vehicleService.calculateVehicleUnlicensedYears(vehicleName);
		return new ResponseEntity<String>(String.valueOf(res),HttpStatus.OK);
	}
	
	/**
	 * Add or update a vehicle portrait image
	 * @param vehicleName Vehicle's name
	 * @param portraitImage Portrait image
	 * @return
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/{vehicleName}/portrait",method = RequestMethod.POST)
	public ResponseEntity<String> addOrUpdateVehiclePortrait(@PathVariable String vehicleName,
			@RequestParam("editImage") MultipartFile portraitImage)
		throws VehicleManagerException {
		System.out.println("[Add/Update Portrait] Vehicle Name: " + vehicleName);
		try {
			vehicleIntegratorService.addOrUpdateVehiclePortrait(vehicleName, portraitImage.getBytes());
		} catch (IOException e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * Get a vehicle portrait image
	 * @param vehicleName Vehicles' name
	 * @return Portrait image
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/image/{vehicleName}",method = RequestMethod.GET)
    @ResponseBody
    public byte[] getVehiclePortraitImage(@PathVariable(value = "vehicleName") String vehicleName) 
    		throws VehicleManagerException {
        return vehicleIntegratorService.getVehiclePortraitImage(vehicleName);
    }
	
}
