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
		System.out.println("[Vehicle] " + vehicleName);
		try {
			if(type.equals("licensed")) {
				model.addAttribute("type", "licensed");
			}
			else if(type.equals("unlicensed")) {
				model.addAttribute("type", "unlicensed");
			}
			else {
				return "error";
			}
			model.addAttribute("vehicle",vehicleService.getVehicle(vehicleName));
			return "vehicle";
		} catch (VehicleManagerException e) {
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
	public String printRegistrations(Model model,
			@PathVariable("vehicleName") String vehicleName, 
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "print", required = true) String print) {
		System.out.println("[Print] " + vehicleName + " " + type + " " + print);
		try {
			if(type.equals("licensed")) {
				model.addAttribute("type", "licensed");
			}
			else if(type.equals("unlicensed")) {
				model.addAttribute("type", "unlicensed");
			}
			else {
				return "error";
			}
			
			if(print.equals("reg")) {
				model.addAttribute("print", "reg");
			}
			else if(print.equals("note")) {
				model.addAttribute("print", "note");
			}
			else if(print.equals("regNote")) {
				model.addAttribute("print", "regNote");
			}
			else {
				return "error";
			}			
			model.addAttribute("vehicle",vehicleService.getVehicle(vehicleName));
			System.out.println("[Print End]");
			return "printVehicle";
		} catch (VehicleManagerException e) {
			return "error";
		}
	}
	
}
