package com.pt.pires.controllers.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping(value = "/licensedVehicle")
	public String licensedVehicle(Model model){
		model.addAttribute("vehicle",vehicleService.getLicensedVehicle("a"));
		return "licensedVehicle";
	}
}
