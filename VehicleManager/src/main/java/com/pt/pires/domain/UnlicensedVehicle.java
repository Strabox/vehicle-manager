package com.pt.pires.domain;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class UnlicensedVehicle extends Vehicle{

	public UnlicensedVehicle(String name, String brand,Date acquisitionDate) {
		super(name, brand, acquisitionDate);
	}
	
	public UnlicensedVehicle() { }	//Needed for JPA/JSON

}
