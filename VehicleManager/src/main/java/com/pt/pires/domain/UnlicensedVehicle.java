package com.pt.pires.domain;

import javax.persistence.Entity;

@Entity
public class UnlicensedVehicle extends Vehicle{

	public UnlicensedVehicle(String name, String brand) {
		super(name, brand);
	}
	
	public UnlicensedVehicle() { }

}
