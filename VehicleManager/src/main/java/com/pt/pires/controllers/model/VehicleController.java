package com.pt.pires.controllers.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.local.IVehicleService;

@Controller
public class VehicleController {

	@Autowired
	@Qualifier("vehicleService") 
	private IVehicleService vehicleService;
	
	@RequestMapping(value = "/home")
	public String homeVehicle(Model model){
		return "home";
	}
	
	@RequestMapping(value = "/vehicles")
	public String listVehicles(Model model) throws VehicleManagerException{
		model.addAttribute("listLicensed",vehicleService.getLicensedVehicles());
		model.addAttribute("listUnlicensed",vehicleService.getUnlicensedVehicles());
		return "vehicles";
	}
	
	@RequestMapping(value = "/licensedVehicle",params={ "name" })
	public String licensedVehicle(Model model,@RequestParam("name") String name){
		try {
			Vehicle v = vehicleService.getLicensedVehicle(name);
			model.addAttribute("vehicle",v);
			System.out.println("Acquisition Years: " + v.calculateAcquisitionYears());
		} catch (VehicleDoesntExistException e) {
			return "error";
		} catch (VehicleManagerException e) {
			e.printStackTrace();
		}
		return "licensedVehicle";
	}
}
