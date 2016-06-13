package com.pt.pires.domain.exceptions;

public class InvalidVehicleNameException extends VehicleManagerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -987429658545384902L;

	private static final String message = "Invalid vehicle name";
	
	@Override
	public String getLocalizedMessage() {
		return message;
	}

}
