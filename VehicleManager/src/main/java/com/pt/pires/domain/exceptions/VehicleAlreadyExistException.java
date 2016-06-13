package com.pt.pires.domain.exceptions;

public class VehicleAlreadyExistException extends VehicleManagerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4265287056667048798L;

	private static final String message = "Vehicle name already exist";

	@Override
	public String getLocalizedMessage() {
		return message;
	}
	
	
}
