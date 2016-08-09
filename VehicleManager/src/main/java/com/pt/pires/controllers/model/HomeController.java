package com.pt.pires.controllers.model;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.context.annotation.Scope;
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
@Scope("session")
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
			@RequestParam(value = "vehicles", required = false) String vehicles) 
			throws VehicleManagerException {
		System.out.println("[Home]");
		if(vehicles != null) {
			model.addAttribute("vehicles",true);
		} else {
			model.addAttribute("vehicles",false);
		}
		model.addAttribute("activeNotifications", notificationService.getActiveNotificationsTasks(new Date()));
		model.addAttribute("listLicensed",vehicleService.getLicensedVehicles());
		model.addAttribute("listUnlicensed",vehicleService.getUnlicensedVehicles());
		return "/normal/home";
	}
	
	/* ====================== Get fragments for ajax calls ======================= */
	
	/**
	 * Return a table row for a licensed vehicle
	 * @param model Model to HTML processor
	 * @param vehicleName Vehicle's name
	 * @return
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/home/vehicleTableRow/{vehicleName}/type/licensed",method = RequestMethod.GET)
	public String homeLicensedVehicleTableRow(Model model,
			@PathVariable("vehicleName") String vehicleName) throws VehicleManagerException {
		Vehicle v = vehicleService.getVehicle(vehicleName);
		model.addAttribute("vehicle",v);
		return "/fragments/tablesFragments :: licensedVehiclesTableRow";
	}
	
	/**
	 * Return a table row for a unlicensed vehicle
	 * @param model Model to HTML processor
	 * @param vehicleName Vehicle's name
	 * @return
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/home/vehicleTableRow/{vehicleName}/type/unlicensed",method = RequestMethod.GET)
	public String homeUnlicensedVehicleTableRow(Model model,
			@PathVariable("vehicleName") String vehicleName) throws VehicleManagerException {
		Vehicle v = vehicleService.getVehicle(vehicleName);
		model.addAttribute("vehicle",v);
		return "/fragments/tablesFragments :: unlicensedVehiclesTableRow";
	}
	
}
