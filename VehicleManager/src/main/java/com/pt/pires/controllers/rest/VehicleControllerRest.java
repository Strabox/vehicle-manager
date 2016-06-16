package com.pt.pires.controllers.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pt.pires.controllers.ResponseError;
import com.pt.pires.domain.VehicleLicensed;
import com.pt.pires.domain.Note;
import com.pt.pires.domain.NotificationYear;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.VehicleUnlicensed;
import com.pt.pires.domain.exceptions.ImpossibleDeleteDirectoryException;
import com.pt.pires.domain.exceptions.ImpossibleSaveFileException;
import com.pt.pires.domain.exceptions.InvalidLicenseException;
import com.pt.pires.domain.exceptions.InvalidNoteException;
import com.pt.pires.domain.exceptions.InvalidRegistrationException;
import com.pt.pires.domain.exceptions.InvalidVehicleBrandException;
import com.pt.pires.domain.exceptions.InvalidVehicleNameException;
import com.pt.pires.domain.exceptions.VehicleAlreadyExistException;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.integrator.IVehicleIntegratorService;
import com.pt.pires.services.local.IVehicleService;
import com.pt.pires.util.FileUtil;

@RestController
public class VehicleControllerRest {
	
	@Autowired
	@Qualifier("vehicleService") 
	private IVehicleService vehicleService;

	@Autowired
	@Qualifier("vehicleIntegratorService")
	private IVehicleIntegratorService vehicleIntegratorService;
	
	
	/* ================== REST Endpoints ==================== */
	
	@RequestMapping(value="/vehicle/unlicensed",method=RequestMethod.POST,consumes={"multipart/form-data"})
	@ResponseBody
	public ResponseEntity<String> createUnlicensedVehicle(@RequestPart("vehicle")VehicleUnlicensed v,
			@RequestPart("file") MultipartFile file) throws VehicleManagerException {
		System.out.println("[Creating Unlicensed]");
		try{
			vehicleIntegratorService.createUnlicensedVehicle(v.getName(), v.getBrand(),
					v.getAcquisitionDate(), file.getBytes());
		}catch(IOException e){
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/vehicle/licensed",method=RequestMethod.POST,consumes={"multipart/form-data"})
	@ResponseBody
	public ResponseEntity<String> createLicensedVehicle(@RequestBody VehicleLicensed v,
			@RequestParam("file") MultipartFile file) throws VehicleManagerException{
		System.out.println("[Creating Licensed");
		try {
			vehicleIntegratorService.createLicensedVehicle(v.getName(), v.getBrand(), v.getAcquisitionDate(),
					v.getLicense().getLicense(), v.getLicense().getDate(),file.getBytes());
		} catch (IOException e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/vehicle/{vehicleName}",method=RequestMethod.DELETE)
	public ResponseEntity<String> removeVehicle(@PathVariable String vehicleName) throws VehicleManagerException{
		System.out.println("[Removing Vehicle] Vehicle: " + vehicleName);
		vehicleIntegratorService.removeVehicle(vehicleName);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/vehicle",method=RequestMethod.GET,params = {"name"})
	public ResponseEntity<String> vehicleExist(@RequestParam("name")String vehicleName) throws VehicleManagerException{
		if(vehicleService.vehicleExist(vehicleName)){
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}else{
			return new ResponseEntity<String>(HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/vehicle/{vehicleName}/registration",method=RequestMethod.POST)
	public ResponseEntity<Long> addRegistrationToVehicle(@PathVariable String vehicleName
			,@RequestBody Registration reg) throws VehicleManagerException{
		System.out.println("[Adding Registration] Vehicle: " + vehicleName);
		Long res = vehicleService.addRegistrationToVehicle(vehicleName, reg.getTime(), reg.getDescription(), reg.getDate());
		return new ResponseEntity<Long>(res,HttpStatus.OK);
	}
	
	@RequestMapping(value="/vehicle/{vehicleName}/registration/{regId}",method=RequestMethod.DELETE)
	public ResponseEntity<String> removeRegistrationFromVehicle(@PathVariable String vehicleName
			,@PathVariable long regId) throws VehicleManagerException{
		System.out.println("[Adding Registration] Vehicle: " + vehicleName);
		vehicleService.removeRegistrationFromVehicle(vehicleName, regId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/vehicle/{vehicleName}/note",method=RequestMethod.POST)
	public ResponseEntity<Long> addNoteToVehicle(@PathVariable String vehicleName,
			@RequestBody Note note) throws VehicleManagerException{
		System.out.println("[Adding Note] Vehicle: " + vehicleName);
		Long res = vehicleService.addNoteToVehicle(vehicleName, note.getDescription());
		return new ResponseEntity<Long>(res,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/vehicle/{vehicleName}/note/{noteId}",method=RequestMethod.DELETE)
	public ResponseEntity<String> removeNoteFromVehicle(@PathVariable String vehicleName,
			@PathVariable long noteId) throws VehicleManagerException{
		System.out.println("[Remove Note] Vehicle: " + vehicleName);
		vehicleService.removeNoteFromVehicle(vehicleName, noteId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param vehicleName Target vehicle to add the notification
	 * @param notification Notification to add
	 * @param type Http param expected { Year, HalfYear }
	 * @return Id of the added notification 
	 * @throws VehicleManagerException 
	 */
	@RequestMapping(value="/vehicle/{vehicleName}/notification",method=RequestMethod.POST,
			params = {"type"})
	public ResponseEntity<Long> addNotification(@PathVariable String vehicleName,
			@RequestBody NotificationYear notification,@RequestParam("type")String type)
					throws VehicleManagerException{
		System.out.println("[Add notification year]");
		Long res;
		if(type.equals("Year")){
			res = vehicleService.addYearNotification(vehicleName, notification.getDescription(),
					notification.getNotiDate());
		}else if(type.equals("HalfYear")){
			res = vehicleService.addHalfYearNotification(vehicleName, notification.getDescription(),
					notification.getNotiDate());
		}
		else{
			return new ResponseEntity<Long>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Long>(res,HttpStatus.OK);
	}
	
	@RequestMapping(value="/vehicle/{vehicleName}/alert/{alertId}",method=RequestMethod.DELETE)
	public ResponseEntity<String> removeAlertFromVehicle(@PathVariable String vehicleName,
			@PathVariable long alertId) throws VehicleManagerException{
		System.out.println("[Remove Alert] Vehicle: " + vehicleName);
		vehicleService.removeAlertFromVehicle(vehicleName, alertId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/image/{vehicleName}")
    @ResponseBody
    public byte[] getPortrait(@PathVariable(value = "vehicleName") String vehicleName){
        File serverFile = new File(FileUtil.getPortraitFilePath(vehicleName));
        try{
        	return Files.readAllBytes(serverFile.toPath());
        }catch(IOException e){
        	Resource resource = new ClassPathResource("/static/images/VehiclePhotoNotFound.png");
        	try {
				return Files.readAllBytes(resource.getFile().toPath());
			} catch (IOException e1) {
				e1.printStackTrace();	//DO Nothing!
				return null;
			}
        }
    }

	/* =========== Response Exceptions Handlers ============= */
	/* This handlers catch the respective exceptions that arrive in controllers */
	
	@ExceptionHandler(VehicleDoesntExistException.class)
	private ResponseEntity<ResponseError> handler1(VehicleDoesntExistException ex,HttpServletRequest request){
		ResponseError response = new ResponseError(404,ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(VehicleAlreadyExistException.class)
	private ResponseEntity<ResponseError> handler2(VehicleAlreadyExistException ex,HttpServletRequest req){
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidVehicleNameException.class)
	private ResponseEntity<ResponseError> handler3(InvalidVehicleNameException ex,
			HttpServletRequest req){
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidVehicleBrandException.class)
	private ResponseEntity<ResponseError> handler4(InvalidVehicleBrandException ex,HttpServletRequest req){
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidLicenseException.class)
	private ResponseEntity<ResponseError> handler5(InvalidLicenseException ex,HttpServletRequest req){
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidRegistrationException.class)
	private ResponseEntity<ResponseError> handler6(InvalidRegistrationException ex,HttpServletRequest req){
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidNoteException.class)
	private ResponseEntity<ResponseError> handler7(InvalidNoteException ex,HttpServletRequest req){
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ImpossibleSaveFileException.class)
	private ResponseEntity<ResponseError> handler8(ImpossibleSaveFileException ex,HttpServletRequest req){
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ImpossibleDeleteDirectoryException.class)
	private ResponseEntity<ResponseError> handler9(ImpossibleDeleteDirectoryException ex,HttpServletRequest req){
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	/**
	 * Manage other exception not seen by the developer.
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(VehicleManagerException.class)
	private ResponseEntity<ResponseError> handlerGeneral(VehicleManagerException ex,
			HttpServletRequest request){
		System.out.print(" [General Exception Handler Called]");
		return new ResponseEntity<ResponseError>(new ResponseError(409,ex.getLocalizedMessage()),HttpStatus.CONFLICT);
	}
}
