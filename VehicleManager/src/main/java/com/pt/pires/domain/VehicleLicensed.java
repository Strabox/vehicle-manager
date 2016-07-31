package com.pt.pires.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.pt.pires.domain.exceptions.InvalidVehicleBrandException;
import com.pt.pires.domain.exceptions.InvalidVehicleNameException;

/**
 * Vehicles that has a license plate
 * @author André
 *
 */
@Entity
public class VehicleLicensed extends Vehicle {

	@OneToOne(cascade = {CascadeType.ALL},orphanRemoval = true)
	private License license;
	
	
	public VehicleLicensed(String name, String brand, Date acquisitonDate, License license) 
			throws InvalidVehicleNameException, InvalidVehicleBrandException {
		super(name, brand, acquisitonDate);
		setLicense(license);
	}
	
	public VehicleLicensed() { }	//Needed for JPA/JSON
	
	/* === Getters and Setters === */
	
	public License getLicense() {
		return license;
	}
	
	public void setLicense(License license) {
		this.license = license;
	}
	
}
