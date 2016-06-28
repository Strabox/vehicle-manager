package com.pt.pires.domain.exceptions;

public class InvalidTimeException extends VehicleManagerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -187507341081857312L;

	@Override
	public String getLocalizedMessage() {
		return "Invalid time";
	}

}
