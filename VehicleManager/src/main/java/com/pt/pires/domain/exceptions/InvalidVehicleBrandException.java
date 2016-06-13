package com.pt.pires.domain.exceptions;

public class InvalidVehicleBrandException extends VehicleManagerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6028723999757500600L;

	private static final String message = "Invalid brand for vehicle";

	@Override
	public String getLocalizedMessage() {
		return message;
	}
}
