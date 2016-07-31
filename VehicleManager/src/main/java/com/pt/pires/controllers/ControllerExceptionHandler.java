package com.pt.pires.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pt.pires.domain.exceptions.ImpossibleDeleteDirectoryException;
import com.pt.pires.domain.exceptions.ImpossibleSaveFileException;
import com.pt.pires.domain.exceptions.InvalidImageFormatException;
import com.pt.pires.domain.exceptions.InvalidLicenseException;
import com.pt.pires.domain.exceptions.InvalidNoteException;
import com.pt.pires.domain.exceptions.InvalidNotificationTaskException;
import com.pt.pires.domain.exceptions.InvalidRegistrationException;
import com.pt.pires.domain.exceptions.InvalidRegistrationTimeException;
import com.pt.pires.domain.exceptions.InvalidTimeException;
import com.pt.pires.domain.exceptions.InvalidVehicleBrandException;
import com.pt.pires.domain.exceptions.InvalidVehicleNameException;
import com.pt.pires.domain.exceptions.LicenseAlreadyExistException;
import com.pt.pires.domain.exceptions.NotificationDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleAlreadyExistException;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;

public class ControllerExceptionHandler {

	/* ======================== Response Exceptions Handlers ========================== */
	/* !!!!!!! This handlers catch the respective exceptions that arrive in controllers */
	
	@ExceptionHandler(VehicleDoesntExistException.class)
	private ResponseEntity<Response> handler1(VehicleDoesntExistException ex,HttpServletRequest request) {
		Response response = new Response(404,ex.getLocalizedMessage());
		return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(VehicleAlreadyExistException.class)
	private ResponseEntity<Response> handler2(VehicleAlreadyExistException ex,HttpServletRequest req) {
		Response response = new Response(409, ex.getLocalizedMessage());
		return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidVehicleNameException.class)
	private ResponseEntity<Response> handler3(InvalidVehicleNameException ex,
			HttpServletRequest req) {
		Response response = new Response(409, ex.getLocalizedMessage());
		return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidVehicleBrandException.class)
	private ResponseEntity<Response> handler4(InvalidVehicleBrandException ex,HttpServletRequest req) {
		Response response = new Response(409, ex.getLocalizedMessage());
		return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidLicenseException.class)
	private ResponseEntity<Response> handler5(InvalidLicenseException ex,HttpServletRequest req) {
		Response response = new Response(409, ex.getLocalizedMessage());
		return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidRegistrationException.class)
	private ResponseEntity<Response> handler6(InvalidRegistrationException ex,HttpServletRequest req) {
		Response response = new Response(409, ex.getLocalizedMessage());
		return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidNoteException.class)
	private ResponseEntity<Response> handler7(InvalidNoteException ex,HttpServletRequest req) {
		Response response = new Response(409, ex.getLocalizedMessage());
		return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ImpossibleSaveFileException.class)
	private ResponseEntity<Response> handler8(ImpossibleSaveFileException ex,HttpServletRequest req) {
		Response response = new Response(409, ex.getLocalizedMessage());
		return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ImpossibleDeleteDirectoryException.class)
	private ResponseEntity<Response> handler9(ImpossibleDeleteDirectoryException ex,HttpServletRequest req) {
		Response response = new Response(409, ex.getLocalizedMessage());
		return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(LicenseAlreadyExistException.class)
	private ResponseEntity<Response> handler10(LicenseAlreadyExistException ex,HttpServletRequest req) {
		Response response = new Response(409, ex.getLocalizedMessage());
		return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidImageFormatException.class)
	private ResponseEntity<Response> handler11(InvalidImageFormatException ex,HttpServletRequest req) {
		Response response = new Response(409, ex.getLocalizedMessage());
		return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidNotificationTaskException.class)
	private ResponseEntity<Response> handler12(InvalidNotificationTaskException ex,HttpServletRequest req) {
		Response response = new Response(409, ex.getLocalizedMessage());
		return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidRegistrationTimeException.class)
	private ResponseEntity<Response> handler13(InvalidRegistrationTimeException ex,HttpServletRequest req) {
		Response response = new Response(409, ex.getLocalizedMessage());
		return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidTimeException.class)
	private ResponseEntity<Response> handler14(InvalidTimeException ex,HttpServletRequest req) {
		Response response = new Response(409, ex.getLocalizedMessage());
		return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(NotificationDoesntExistException.class)
	private ResponseEntity<Response> handler15(NotificationDoesntExistException ex,HttpServletRequest req) {
		Response response = new Response(409, ex.getLocalizedMessage());
		return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
	}
	
	/**
	 * Catch exceptions not seen (forgotten) by the developer.
	 * @param ex VehicleManagerException
	 * @param request
	 * @return 409 HTTP Response
	 */
	@ExceptionHandler(VehicleManagerException.class)
	private ResponseEntity<Response> handlerGeneral(VehicleManagerException ex,
			HttpServletRequest request) {
		return new ResponseEntity<Response>(new Response(409,ex.getLocalizedMessage()),HttpStatus.CONFLICT);
	}
	
}
