package com.pt.pires.domain.exceptions;

public class InvalidRegistrationException extends VehicleManagerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7054293828989840254L;

	private static final String message = "Invalid registration";
	
	@Override
	public String getLocalizedMessage() {
		return message;
	}

}
