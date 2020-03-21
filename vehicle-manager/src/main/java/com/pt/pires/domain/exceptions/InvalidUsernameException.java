package com.pt.pires.domain.exceptions;

public class InvalidUsernameException extends VehicleManagerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 740819707740708419L;

	@Override
	public String getLocalizedMessage() {
		return "Invalid username";
	}

}
