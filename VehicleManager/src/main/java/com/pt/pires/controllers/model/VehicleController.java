package com.pt.pires.controllers.model;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.local.INoteService;
import com.pt.pires.services.local.INotificationTaskService;
import com.pt.pires.services.local.IRegistrationService;
import com.pt.pires.services.local.IVehicleService;

/**
 * Controller for vehicle page
 * @author André
 *
 */
@Controller
@Scope("session")
public class VehicleController {

	private final static String VEHICLE_TYPE_LICENSED = "licensed";
	private final static String VEHICLE_TYPE_UNLICENSED = "unlicensed";
	
	private final static String PRINT_OPTION_REGISTRATIONS = "reg";
	private final static String PRINT_OPTION_NOTES = "note";
	private final static String PRINT_OPTION_NOTES_AND_REGISTRATIONS = "regNote";
	
	@Inject
	@Named("vehicleService") 
	private IVehicleService vehicleService;
	
	@Inject
	@Named("registrationService")
	private IRegistrationService registrationService;
	
	@Inject
	@Named("notificationService")
	private INotificationTaskService notificationTaskService;
	
	@Inject
	@Named("noteService")
	private INoteService noteService;
	
	
	public VehicleController(IVehicleService vs) {
		vehicleService = vs;
	}
	
	public VehicleController() { }
	
	/**
	 * Build the presentation vehicle page and return it
	 * @param model Model to HTML processor
	 * @param vehicleName Name of the vehicle
	 * @param type {unlicensed , licensed} type of vehicle
	 * @return HTML file
	 */
	@RequestMapping(value = "/vehicle/{vehicleName}",params = { "type" })
	public String vehicle(Model model,@PathVariable("vehicleName") String vehicleName,
			@RequestParam(value = "type", required = true) String type) {
		System.out.println("[Vehicle Page] Vehicle Name: " + vehicleName);
		try {
			if(type.equals(VEHICLE_TYPE_LICENSED)) {
				model.addAttribute("type", VEHICLE_TYPE_LICENSED);
			}
			else if(type.equals(VEHICLE_TYPE_UNLICENSED)) {
				model.addAttribute("type", VEHICLE_TYPE_UNLICENSED);
			}
			model.addAttribute("vehicle",vehicleService.getVehicle(vehicleName));
			return "/normal/vehicle";
		} catch (VehicleManagerException e) {
			e.printStackTrace();
			return "/normal/errors/error";
		}
	}
	
	/**
	 * Return a HTML page to be printed from browser
	 * @param model Model to HTML processor with a specified vehicle data
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
				return "/normal/errors/error";
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
				return "/normal/errors/error";
			}			
			model.addAttribute("vehicle",vehicleService.getVehicle(vehicleName));
			return "/normal/printVehicle";
		} catch (VehicleManagerException e) {
			return "/normal/errors/error";
		}
	}
	
	/**
	 * Return a HTML page to be printed from browser with all vehicles data
	 * @param model Model to HTML processor with a specified vehicle data
	 * @return HTML file
	 */
	@RequestMapping(value = "/vehicles/print")
	public String printAllVehiclesData(Model model) {
		System.out.println("[Print all vehicles data] " + vehicleService.getAllVehicles().size());
		model.addAttribute("vehicles",vehicleService.getAllVehicles());
		return "/normal/printVehicles";
	}
	
	/* ====================== Get fragments for ajax calls ======================= */
	
	/**
	 * Get registration table row
	 * @param model Model to HTML processor
	 * @param vehicleName Vehicle's name
	 * @param regId Registration identifier
	 * @return Registration Table Row HTML
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/registrationTableRow/{vehicleName}/regId/{regId}", method = RequestMethod.GET)
	public String getVehicleRegistrationTableRow(Model model,
			@PathVariable("vehicleName") String vehicleName,
			@PathVariable("regId") long regId) throws VehicleManagerException {
		System.out.println("[Get vehicle registration table row]");
		model.addAttribute("registration", registrationService.getRegistrationById(regId));
		return "/fragments/tablesFragments :: vehicleRegistrationTableRow";
	}
	
	/**
	 * Get note table row
	 * @param model Model to HTML processor
	 * @param vehicleName Vehicle's name
	 * @param noteId Note identifier
	 * @return Note Table Row HTML
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/noteTableRow/{vehicleName}/noteId/{noteId}", method = RequestMethod.GET)
	public String getVehicleNoteTableRow(Model model,
			@PathVariable("vehicleName") String vehicleName,
			@PathVariable("noteId") long noteId) throws VehicleManagerException {
		System.out.println("[Get vehicle note table row]");
		model.addAttribute("note", noteService.getNoteById(noteId));
		return "/fragments/tablesFragments :: vehicleNoteTableRow";
	}
	
	/**
	 * Get Notification Task row
	 * @param model Model to HTML processor
	 * @param vehicleName Vehicle's name
	 * @param notiId Notification Task identifier
	 * @return Notification Table Row HTML
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/notificationTaskTableRow/{vehicleName}/notiId/{notiId}", method = RequestMethod.GET)
	public String getVehicleNotificationTaskTableRow(Model model,
			@PathVariable("vehicleName") String vehicleName,
			@PathVariable("notiId") long notiId) throws VehicleManagerException {
		System.out.println("[Get vehicle notification task table row]");
		model.addAttribute("alert", notificationTaskService.getNotificationTaskById(notiId));
		return "/fragments/tablesFragments :: vehicleNotificationTaskTableRow";
	}
	
}
