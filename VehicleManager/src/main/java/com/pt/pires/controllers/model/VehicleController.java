package com.pt.pires.controllers.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.services.VehicleService;

@Controller
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	
	@RequestMapping(value = "/home")
	public String homeVehicle(Model model){
		return "home";
	}
	
	@RequestMapping(value = "/vehicles")
	public String listVehicles(Model model){
		model.addAttribute("listLicensed",vehicleService.getLicensedVehicles());
		model.addAttribute("listUnlicensed",vehicleService.getUnlicensedVehicles());
		return "vehicles";
	}
	
	@RequestMapping(value = "/licensedVehicle",params={ "name" })
	public String licensedVehicle(Model model,@RequestParam("name") String name){
		try {
			Vehicle v = vehicleService.getLicensedVehicle(name);
			model.addAttribute("vehicle",v);
			System.out.println("Acquisition Years: " + v.getAcquisitionYears());
		} catch (VehicleDoesntExistException e) {
			return "error";
		}
		return "licensedVehicle";
	}
}
