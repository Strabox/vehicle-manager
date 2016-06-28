package com.pt.pires.domain.exceptions;

public class InvalidRegistrationTimeException extends VehicleManagerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4372170321443699898L;

	@Override
	public String getLocalizedMessage() {
		return "Invalid registration time";
	}

}
