package com.pt.pires.domain.exceptions;

public class UserAlreadyExistsException extends VehicleManagerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1449438987549183600L;

	@Override
	public String getLocalizedMessage() {
		return "User already exists";
	}

}
