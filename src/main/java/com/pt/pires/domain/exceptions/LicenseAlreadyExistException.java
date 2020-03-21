package com.pt.pires.domain.exceptions;

public class LicenseAlreadyExistException extends VehicleManagerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2957243319439429716L;

	@Override
	public String getLocalizedMessage() {
		return "License already exist";
	}

}
