package com.pt.pires.domain.exceptions;

public class ImpossibleSaveFileException extends VehicleManagerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2956969459274325869L;

	private static final String message = "Impossible save the file";
	
	@Override
	public String getLocalizedMessage() {
		return message;
	}

}
