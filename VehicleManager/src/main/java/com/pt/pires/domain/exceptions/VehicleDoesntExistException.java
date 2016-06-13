package com.pt.pires.domain.exceptions;

public class VehicleDoesntExistException extends VehicleManagerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7226934474538924147L;

	private static final String message = "Vehicel doesn'te exist";

	@Override
	public String getLocalizedMessage() {
		return message;
	}
	
	
}
