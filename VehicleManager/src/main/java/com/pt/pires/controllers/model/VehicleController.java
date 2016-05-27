package com.pt.pires.controllers.model;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VehicleController {

	@RequestMapping("/showVehicle")
	public String showVehicle(Model model){
		model.addAttribute("vehicleName","Wow");
		return "showVehicle";
	}
	
}
