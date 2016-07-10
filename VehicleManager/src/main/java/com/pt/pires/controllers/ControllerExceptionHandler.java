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
	private ResponseEntity<ResponseError> handler1(VehicleDoesntExistException ex,HttpServletRequest request) {
		ResponseError response = new ResponseError(404,ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(VehicleAlreadyExistException.class)
	private ResponseEntity<ResponseError> handler2(VehicleAlreadyExistException ex,HttpServletRequest req) {
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidVehicleNameException.class)
	private ResponseEntity<ResponseError> handler3(InvalidVehicleNameException ex,
			HttpServletRequest req) {
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidVehicleBrandException.class)
	private ResponseEntity<ResponseError> handler4(InvalidVehicleBrandException ex,HttpServletRequest req) {
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidLicenseException.class)
	private ResponseEntity<ResponseError> handler5(InvalidLicenseException ex,HttpServletRequest req) {
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidRegistrationException.class)
	private ResponseEntity<ResponseError> handler6(InvalidRegistrationException ex,HttpServletRequest req) {
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidNoteException.class)
	private ResponseEntity<ResponseError> handler7(InvalidNoteException ex,HttpServletRequest req) {
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ImpossibleSaveFileException.class)
	private ResponseEntity<ResponseError> handler8(ImpossibleSaveFileException ex,HttpServletRequest req) {
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ImpossibleDeleteDirectoryException.class)
	private ResponseEntity<ResponseError> handler9(ImpossibleDeleteDirectoryException ex,HttpServletRequest req) {
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(LicenseAlreadyExistException.class)
	private ResponseEntity<ResponseError> handler10(LicenseAlreadyExistException ex,HttpServletRequest req) {
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidImageFormatException.class)
	private ResponseEntity<ResponseError> handler11(InvalidImageFormatException ex,HttpServletRequest req) {
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidNotificationTaskException.class)
	private ResponseEntity<ResponseError> handler12(InvalidNotificationTaskException ex,HttpServletRequest req) {
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidRegistrationTimeException.class)
	private ResponseEntity<ResponseError> handler13(InvalidRegistrationTimeException ex,HttpServletRequest req) {
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidTimeException.class)
	private ResponseEntity<ResponseError> handler14(InvalidTimeException ex,HttpServletRequest req) {
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(NotificationDoesntExistException.class)
	private ResponseEntity<ResponseError> handler15(NotificationDoesntExistException ex,HttpServletRequest req) {
		ResponseError response = new ResponseError(409, ex.getLocalizedMessage());
		return new ResponseEntity<ResponseError>(response,HttpStatus.CONFLICT);
	}
	
	/**
	 * Catch exceptions not seen by the developer.
	 * @param ex VehicleManagerException
	 * @param request
	 * @return 409 HTTP Response
	 */
	@ExceptionHandler(VehicleManagerException.class)
	private ResponseEntity<ResponseError> handlerGeneral(VehicleManagerException ex,
			HttpServletRequest request) {
		return new ResponseEntity<ResponseError>(new ResponseError(409,ex.getLocalizedMessage()),HttpStatus.CONFLICT);
	}
	
}
