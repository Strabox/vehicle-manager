package com.pt.pires.controllers.model;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.local.INotificationTaskService;
import com.pt.pires.services.local.IVehicleService;

/**
 * Controller for the home/main page
 * @author André
 *
 */
@Controller
public class HomeController {

	@Autowired
	@Qualifier("notificationService")
	private INotificationTaskService notificationService;
	
	@Autowired
	@Qualifier("vehicleService")
	private IVehicleService vehicleService;
	
	
	/**
	 * Return the Home page HTML
	 * @param model Model to HTML processor
	 * @return Home page
	 * @throws VehicleManagerException 
	 */
	@RequestMapping(value = "/home",method = RequestMethod.GET)
	public String homeVehicle(Model model,
			@RequestParam(value = "vehicles", required = false)String vehicles) throws VehicleManagerException {
		System.out.println("[Home]");
		if(vehicles != null) {
			model.addAttribute("vehicles",true);
		} else {
			model.addAttribute("vehicles",false);
		}
		model.addAttribute("activeNotifications", notificationService.getActiveNotificationsTasks(new Date()));
		model.addAttribute("listLicensed",vehicleService.getLicensedVehicles());
		model.addAttribute("listUnlicensed",vehicleService.getUnlicensedVehicles());
		return "home";
	}
	
}
