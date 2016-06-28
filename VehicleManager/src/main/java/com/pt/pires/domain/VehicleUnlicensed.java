package com.pt.pires.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.pt.pires.domain.exceptions.InvalidVehicleBrandException;
import com.pt.pires.domain.exceptions.InvalidVehicleNameException;

@Entity
public class VehicleUnlicensed extends Vehicle {

	public VehicleUnlicensed(String name, String brand,Date acquisitionDate) 
			throws InvalidVehicleNameException, InvalidVehicleBrandException {
		super(name, brand, acquisitionDate);
	}
	
	public VehicleUnlicensed() { }	//Needed for JPA/JSON

}
