package com.pt.pires.domain.exceptions;

public class InvalidLicenseException extends VehicleManagerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4212926089025785870L;

	private static final String message = "Invalid license";

	@Override
	public String getLocalizedMessage() {
		return message;
	}
	
}
