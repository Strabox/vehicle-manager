package com.pt.pires.domain.exceptions;

public abstract class VehicleManagerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6376693643714304974L;

	@Override
	public abstract String getLocalizedMessage();
}
