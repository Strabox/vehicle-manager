package com.pt.pires.domain.exceptions;

public class NotificationDoesntExistException extends VehicleManagerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8528060476624913441L;

	@Override
	public String getLocalizedMessage() {
		return "Notification doesn't exist";
	}

}
