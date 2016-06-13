package com.pt.pires.domain.exceptions;

public class ImpossibleDeleteDirectoryException extends VehicleManagerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4018497149526745105L;

	private static final String message = "Impossible delete directory";
	
	@Override
	public String getLocalizedMessage() {
		return message;
	}

}
