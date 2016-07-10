package com.pt.pires.controllers.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.local.IVehicleService;

/**
 * Controller for vehicle page
 * @author André
 *
 */
@Controller
public class VehicleController {

	private final static String VEHICLE_TYPE_LICENSED = "licensed";
	private final static String VEHICLE_TYPE_UNLICENSED = "unlicensed";
	
	private final static String PRINT_OPTION_REGISTRATIONS = "reg";
	private final static String PRINT_OPTION_NOTES = "note";
	private final static String PRINT_OPTION_NOTES_AND_REGISTRATIONS = "regNote";
	
	@Autowired
	@Qualifier("vehicleService") 
	private IVehicleService vehicleService;
	
	
	/**
	 * Build the presentation vehicle page and return it
	 * @param model Model to HTML processor
	 * @param vehicleName Name of the vehicle
	 * @param type {unlicensed , licensed} type of vehicle
	 * @return HTML file
	 */
	@RequestMapping(value = "/vehicle/{vehicleName}",params = { "type" })
	public String vehicle(Model model,@PathVariable("vehicleName") String vehicleName,
			@RequestParam("type")String type) {
		System.out.println("[Vehicle Page] Vehicle Name: " + vehicleName);
		try {
			if(type.equals(VEHICLE_TYPE_LICENSED)) {
				model.addAttribute("type", VEHICLE_TYPE_LICENSED);
			}
			else if(type.equals(VEHICLE_TYPE_UNLICENSED)) {
				model.addAttribute("type", VEHICLE_TYPE_UNLICENSED);
			}
			model.addAttribute("vehicle",vehicleService.getVehicle(vehicleName));
			return "vehicle";
		} catch (VehicleManagerException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * Return a HTML page to be printed from browser
	 * @param model Model to HTML processor
	 * @param vehicleName Name of the vehicle
	 * @param type {unlicensed , licensed} type of vehicle
	 * @param print {reg , note, regNote} type of printing
	 * @return HTML file
	 */
	@RequestMapping(value = "/vehicle/{vehicleName}/print",params = { "type", "print" })
	public String printVehicleData(Model model,
			@PathVariable("vehicleName") String vehicleName, 
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "print", required = true) String print) {
		System.out.println("[Print Vehicle Data] " + vehicleName + " " + type + " " + print);
		try {
			if(type.equals(VEHICLE_TYPE_LICENSED)) {
				model.addAttribute("type", VEHICLE_TYPE_LICENSED);
			}
			else if(type.equals(VEHICLE_TYPE_UNLICENSED)) {
				model.addAttribute("type", VEHICLE_TYPE_UNLICENSED);
			}
			else {
				return "error";
			}
			
			if(print.equals(PRINT_OPTION_REGISTRATIONS)) {
				model.addAttribute("print", PRINT_OPTION_REGISTRATIONS);
			}
			else if(print.equals(PRINT_OPTION_NOTES)) {
				model.addAttribute("print", PRINT_OPTION_NOTES);
			}
			else if(print.equals(PRINT_OPTION_NOTES_AND_REGISTRATIONS)) {
				model.addAttribute("print", PRINT_OPTION_NOTES_AND_REGISTRATIONS);
			}
			else {
				return "error";
			}			
			model.addAttribute("vehicle",vehicleService.getVehicle(vehicleName));
			return "printVehicle";
		} catch (VehicleManagerException e) {
			return "error";
		}
	}
	
	/**
	 * Print all vehicles data
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/vehicles/print")
	public String printAllVehiclesData(Model model) {
		System.out.println("[Print all vehicles data] " + vehicleService.getAllVehicles().size());
		model.addAttribute("vehicles",vehicleService.getAllVehicles());
		return "printVehicles";
	}
	
}
