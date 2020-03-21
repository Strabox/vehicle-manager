package com.pt.pires.domain.exceptions;

public class InvalidNoteException extends VehicleManagerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2716451356697117126L;

	private static final String message = "Invalid note";

	@Override
	public String getLocalizedMessage() {
		return message;
	}
	
}
