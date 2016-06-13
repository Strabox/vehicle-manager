package com.pt.pires.controllers.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		System.out.println("[Vehicles Request]");
		model.addAttribute("listLicensed",vehicleService.getLicensedVehicles());
		model.addAttribute("listUnlicensed",vehicleService.getUnlicensedVehicles());
		return "vehicles";
	}
	
	@RequestMapping(value = "/vehicle/{name}",params = { "type" })
	public String vehicle(Model model,@PathVariable("name") String name,
			@RequestParam("type")String type){
		try {
			if(type.equals("licensed")){
				model.addAttribute("type", "licensed");
			}else if(type.equals("unlicensed")){
				model.addAttribute("type", "unlicensed");
			}
			else{
				return "error";
			}
			model.addAttribute("vehicle",vehicleService.getVehicle(name));
			return "vehicle";
		} catch (VehicleDoesntExistException e) {
			return "error";
		} catch (VehicleManagerException e) {
			return "error";
		}
	}
	
}
