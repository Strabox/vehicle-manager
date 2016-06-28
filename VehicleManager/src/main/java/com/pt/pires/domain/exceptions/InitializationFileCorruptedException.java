package com.pt.pires.domain.exceptions;

public class InitializationFileCorruptedException extends VehicleManagerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4304835341068501310L;

	@Override
	public String getLocalizedMessage() {
		return "Initialization file not found";
	}

}
