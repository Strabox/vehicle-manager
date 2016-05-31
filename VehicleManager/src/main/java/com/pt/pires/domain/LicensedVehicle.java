package com.pt.pires.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class LicensedVehicle extends Vehicle{

	@OneToOne(cascade = {CascadeType.ALL})
	private License license;
	
	public LicensedVehicle(String name, String brand,Date acquisitonDate, License license) {
		super(name, brand, acquisitonDate);
		setLicense(license);
	}
	
	public LicensedVehicle() { }	//Needed for JPA/JSON
	
	/* == Getters and Setters === */
	
	public License getLicense(){
		return license;
	}
	
	public void setLicense(License license){
		this.license = license;
	}
	
}
