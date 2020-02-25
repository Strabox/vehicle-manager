package com.pt.pires.domain.exceptions;

public class NoteDoesntExistException extends VehicleManagerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3408737106191184186L;

	@Override
	public String getLocalizedMessage() {
		return "Note doesn't exist";
	}

}
