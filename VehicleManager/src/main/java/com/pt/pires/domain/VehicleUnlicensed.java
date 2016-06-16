package com.pt.pires.domain;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class VehicleUnlicensed extends Vehicle{

	public VehicleUnlicensed(String name, String brand,Date acquisitionDate) {
		super(name, brand, acquisitionDate);
	}
	
	public VehicleUnlicensed() { }	//Needed for JPA/JSON

}
