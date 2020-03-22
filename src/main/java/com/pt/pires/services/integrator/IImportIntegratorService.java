package com.pt.pires.services.integrator;

import com.pt.pires.domain.exceptions.VehicleManagerException;

public interface IImportIntegratorService {

	public void importFrom() throws VehicleManagerException;
	
	public void validate() throws VehicleManagerException;
	
}
