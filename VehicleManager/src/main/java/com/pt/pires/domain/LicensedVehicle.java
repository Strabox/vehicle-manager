package com.pt.pires.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class LicensedVehicle extends Vehicle{

	@OneToOne(cascade = {CascadeType.ALL})
	private License license;
	
	public LicensedVehicle(String name, String brand,License license) {
		super(name, brand);
		setLicense(license);
	}
	
	public LicensedVehicle() { }
	
	public License getLicense(){
		return license;
	}
	
	public void setLicense(License license){
		this.license = license;
	}
	
}
