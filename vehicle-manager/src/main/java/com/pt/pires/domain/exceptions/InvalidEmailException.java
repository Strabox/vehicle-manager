package com.pt.pires.domain.exceptions;

public class InvalidEmailException extends VehicleManagerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2177971072040534560L;

	@Override
	public String getLocalizedMessage() {
		return "Invalid email";
	}

}
