package com.pt.pires.domain.exceptions;

public class InvalidPasswordException extends VehicleManagerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -89443560646811940L;

	@Override
	public String getLocalizedMessage() {
		return "Invalid password";
	}

}
