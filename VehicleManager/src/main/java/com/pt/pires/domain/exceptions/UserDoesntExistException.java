package com.pt.pires.domain.exceptions;

public class UserDoesntExistException extends VehicleManagerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3876078325895050001L;

	@Override
	public String getLocalizedMessage() {
		return "User doesn't exist";
	}

}
