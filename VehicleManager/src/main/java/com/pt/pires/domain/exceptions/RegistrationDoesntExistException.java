package com.pt.pires.domain.exceptions;

public class RegistrationDoesntExistException extends VehicleManagerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4055928701852222179L;

	@Override
	public String getLocalizedMessage() {
		return "Registration doesn't exist";
	}

}
