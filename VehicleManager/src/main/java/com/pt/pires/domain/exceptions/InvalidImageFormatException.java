package com.pt.pires.domain.exceptions;

public class InvalidImageFormatException extends VehicleManagerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1083534561423779496L;

	@Override
	public String getLocalizedMessage() {
		return "Invalid image format";
	}

}
