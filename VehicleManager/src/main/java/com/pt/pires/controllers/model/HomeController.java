package com.pt.pires.controllers.model;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pt.pires.domain.Vehicle;
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

	@Inject
	@Named("notificationService")
	private INotificationTaskService notificationService;
	
	@Inject
	@Named("vehicleService")
	private IVehicleService vehicleService;
	
	
	public HomeController(INotificationTaskService ns,IVehicleService vs) {
		notificationService = ns;
		vehicleService = vs;
	}
	
	public HomeController() { }
	
	/**
	 * Return the Home page HTML
	 * @param model Model to HTML processor
	 * @return Home page
	 * @throws VehicleManagerException 
	 */
	@RequestMapping(value = "/home",method = RequestMethod.GET)
	public String homeVehicle(Model model,
			@RequestParam(value = "vehicles", required = false)String vehicles,
			@RequestParam(value = "startPage", required = false)Integer startPage) 
			throws VehicleManagerException {
		System.out.println("[Home]");
		if(vehicles != null) {
			model.addAttribute("vehicles",true);
			if(startPage != null) {
				model.addAttribute("startPage", startPage.intValue());
			} else {
				model.addAttribute("startPage", 0);
			}
		} else {
			model.addAttribute("vehicles",false);
		}
		model.addAttribute("activeNotifications", notificationService.getActiveNotificationsTasks(new Date()));
		model.addAttribute("listLicensed",vehicleService.getLicensedVehicles());
		model.addAttribute("listUnlicensed",vehicleService.getUnlicensedVehicles());
		return "/normal/home";
	}
	
	@RequestMapping(value = "/home/deleteVehicleButton/{vehicleName}/type/{vehicleType}",method = RequestMethod.GET)
	public String homeDeleteVehicleButton(Model model,
			@PathVariable("vehicleName") String vehicleName,
			@PathVariable("vehicleType") String vehicleType,
			@RequestParam(value = "vehicleBrand", required = true) String vehicleBrand,
			@DateTimeFormat(pattern = "yyyy-MM-dd")@RequestParam(value = "vehicleAcquisitionDate", required = true) Date vehicleAcquisitionDate)
			throws VehicleManagerException {
		Vehicle v = vehicleService.getVehicle(vehicleName);
		model.addAttribute("vehicle",v);
		model.addAttribute("vehicleName", vehicleName);
		model.addAttribute("vehicleType", vehicleType);
		model.addAttribute("vehicleAcquisitionDate", vehicleAcquisitionDate);
		model.addAttribute("vehicleBrand", vehicleBrand);
		return "/fragments/tablesFragments :: lol";
	}
	
}
